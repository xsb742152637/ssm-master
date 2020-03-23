package util.io;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import util.context.ApplicationContext;
import util.io.file.Directorys;

import java.io.IOException;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xiucai
 * Date: 2020/3/23 16:50
 */
public class WebPathUtils {
    private static Path _rootPath = null;
    static final String _relativePathSeparator = "/";
    private static String _realPathSeparator = "\\";
    final private static String _tempFolderName = "temp";
    static private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    static {
        _realPathSeparator = FileSystems.getDefault().getSeparator();
    }

    //得到项目附件根目录
    public static Path getAttachRootPath(){
        if(_rootPath == null){
            //得到spring-mvc.xml文件中配置的附件目录
            String path = ApplicationContext.get("fileStorage");
            if(StringUtils.isBlank(path))
                path = "fileStorage";

            Path tempRootPath = Paths.get(path);
            //判断附件目录是否为绝对路径
            if(!tempRootPath.isAbsolute()){
                Path clasPath = null;
                try{
                    clasPath = new org.springframework.core.io.ClassPathResource("").getFile().toPath().getParent();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(clasPath != null){
                    _rootPath = clasPath.resolveSibling(tempRootPath);
                }
            }else{
                _rootPath = tempRootPath;
            }
        }
        return _rootPath;
    }

    //路径合并：将数组more合并到路径字符串first(相对或绝对路径都无所谓)的后面
    public static String mergePath(String first, String... more) {
        Collection<String> names = new LinkedList<>();
        Collections.addAll(names, StringUtils.split(StringUtils.replace(first, _realPathSeparator, _relativePathSeparator), _relativePathSeparator));
        for (String m : more) {
            Collections.addAll(names, StringUtils.split(StringUtils.replace(m, _realPathSeparator, _relativePathSeparator), _relativePathSeparator));
        }
        return first.startsWith("\\\\") ?
                ("\\\\" + StringUtils.join(names, _relativePathSeparator)) :
                (_relativePathSeparator + StringUtils.join(names, _relativePathSeparator));
    }

    /**
     * 绝对路径转相对路径
     * @param absolutePath 文件绝对路径(绝对路径)
     * @return 返回相对路径
     */
    public static String getRelativePathByAbsolutePath(String absolutePath) {
        if (isAbsolutePath(absolutePath)) {
            return _relativePathSeparator + StringUtils.replace(getAttachRootPath().relativize(Paths.get(absolutePath)).toString(), _realPathSeparator, _relativePathSeparator);
        }
        if (absolutePath.startsWith(_relativePathSeparator)) {
            return absolutePath;
        }
        throw new IllegalArgumentException("错误的绝对路径.");
    }

    /**
     * 相对转绝对
     * @param relativePath 文件相对路径
     * @return 返回文件的绝对路径
     */
    public static String getAbsolutePathByRelativePath(String relativePath) {
        if (isRelativePath(relativePath)) {
            return getAttachRootPath().resolve(relativePath.startsWith(_relativePathSeparator) ? relativePath.substring(1) : relativePath).toString();
        }
        return relativePath;
    }

    /**
     * 判断是否是绝对路径
     * @param path 文件路径
     * @return 如果是绝对路径返回true, 否则返回false
     */
    public static boolean isAbsolutePath(String path) {
        return StringUtils.startsWithIgnoreCase(path, getAttachRootPath().toString());
    }

    /**
     * 判断是否是相对路径
     * @param path 文件路径
     * @return 如果是相对路径返回true, 否则返回false
     */
    public static boolean isRelativePath(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        if (isAbsolutePath(path)) {
            return false;
        }
        return path.startsWith(_relativePathSeparator);
    }

    static String createRelativePath(String... names) {
        StringBuilder sb = new StringBuilder();
        sb.append(_relativePathSeparator);
        for (String n : names) {
            sb.append(n.trim());
            sb.append(_relativePathSeparator);
        }
        return sb.toString();
    }

    /**
     * 返回 yyyyMMdd 格式的字符串。
     *
     * @param date 日期
     * @return 返回 yyyyMMdd 格式的字符串
     */
    public static String getDateFolder(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 得到应用程序数据的附件目录
     *
     * @param date 数据时间
     * @param code 模块编码
     * @param id   数据编号
     * @return 得到应用程序数据的附件目录
     */
    public static String getAttachmentRelativePath(Date date, String code, String id) {
        return createRelativePath("attachment", getDateFolder(date), code, id);
    }

    /**
     * 判断是否是一个临时路径
     * @param tmpRelativePath 传入文件相对路径
     * @return 如果是一个临时路径返回true, 否则返回false
     */
    public static boolean isTempRelativePath(String tmpRelativePath) {
        return StringUtils.startsWithIgnoreCase(tmpRelativePath, createRelativePath(_tempFolderName));
    }

    /**
     * 按当前时间获取一个新的临时相对目录。
     * @return 返回新的临时相对目录
     */
    public static String getNewTempRelativePath() {
        return createRelativePath(_tempFolderName, getDateFolder(new Date()), UUID.randomUUID().toString());
    }

    /**
     * 得到相对路径的占用空间大小
     * @param relativePath 文件相对路径
     * @return 获取指定目录或文件及子目录下所有文件占用的空间大小
     */
    public static long getSize(String relativePath) {
        long size = 0;
        if (isRelativePath(relativePath)) {
            try {
                size = Directorys.size(Paths.get(getAbsolutePathByRelativePath(relativePath)));
            } catch (IOException ignored) {
            }
        }
        return size;
    }

    public static void cleanTempFolder() {
        cleanTempFolder(Calendar.DAY_OF_MONTH, -1);//取当前日期的前一天.
    }

    public static void cleanTempFolder(int field, int amount) {
        //String today = getDateFolder(cal.getTime());
        DateTime endDate = DateTime.now();
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        cal.add(field, amount);
        //String yesterday = getDateFolder(cal.getTime());
        DateTime startDate = new DateTime(cal);
        Path cleanTemp = Paths.get(getAbsolutePathByRelativePath(createRelativePath(_tempFolderName)));
        if (Files.exists(cleanTemp)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(cleanTemp)) {
                for (Path tmpPath : directoryStream) {
                    if (Files.isDirectory(tmpPath)) {
                        DateTime targetDate = null;
                        try {
                            targetDate = new DateTime(dateFormat.parse(tmpPath.getFileName().toString()));
                        } catch (ParseException e) {
                        }
                        if (targetDate != null) {
                            if ((targetDate.isAfter(startDate) && targetDate.isBefore(endDate))
                                    || targetDate.isEqual(startDate)
                                    || targetDate.isEqual(endDate)) {
                                continue;
                            }
                        }
                        try {
                            Directorys.delete(tmpPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("删除文件夹“" + tmpPath.toString() + "”出错：" + e.getMessage());
                        }
                    } else {
                        try {
                            Files.delete(tmpPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("删除文件“" + tmpPath.toString() + "”出错：" + e.getMessage());
                        }
                    }
                }
            } catch (IOException me) {
                me.printStackTrace();
                System.out.println("清理临时文件失败：" + me.getMessage());
            }
        }
    }

}
