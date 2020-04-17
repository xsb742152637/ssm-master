package util.io;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import util.io.file.Directorys;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttachmentUtils {
    static final String _downloadServet;
    static final String _displayServet;
    static final String _webDavServet;
    static final String _fileInfoServet;

    private static final String _validAttachSeparator = ":";
    private static final String _multiAttachSeparator = "|";

    static {
        String tmpStr;
        tmpStr = WebPathUtils.createRelativePath("file", "download");
        _downloadServet = tmpStr.substring(0, tmpStr.length() - 1);
        tmpStr = WebPathUtils.createRelativePath("file", "display");
        _displayServet = tmpStr.substring(0, tmpStr.length() - 1);
        tmpStr = WebPathUtils.createRelativePath("file", "webdav");
        _webDavServet = tmpStr.substring(0, tmpStr.length() - 1);
        tmpStr = WebPathUtils.createRelativePath("file", "info");
        _fileInfoServet = tmpStr.substring(0, tmpStr.length() - 1);
    }

    public static Attachment getAttachByRelativePath(String relativePath) {
        return new Attachment(relativePath);
    }

    public static Attachment getAttachByPath(Path path) {
        return new Attachment(WebPathUtils.getRelativePathByAbsolutePath(path.toString()));
    }

    public static Collection<Attachment> getAttachByDirectory(String relativePath, boolean recursive) {
        Collection<Attachment> attachments = new LinkedHashSet<Attachment>();
        Path dirPath = Paths.get(WebPathUtils.getAbsolutePathByRelativePath(relativePath));
        if (Files.exists(dirPath)) {
            try {
                for (Path p : Directorys.getFiles(dirPath, recursive)) {
                    attachments.add(getAttachByPath(p));
                }
                for(Path f : Directorys.getPaths(dirPath,recursive)){
                    for (Path p : Directorys.getFiles(f, recursive)) {
                        attachments.add(getAttachByPath(p));
                    }
                }
            } catch (IOException ignored) {
            }
        }
        return attachments;
    }

    public static String getUriByAttach(Collection<Attachment> attachments) {
        Collection<String> attachStr = new LinkedHashSet<String>();
        for (Attachment a : attachments) {
            attachStr.add(a.getRelativePath());
        }
        return StringUtils.join(attachStr, _multiAttachSeparator);
    }

    public static Collection<Attachment> getAttachByRelativePaths(String relativePath) {
        Collection<Attachment> attachments = new LinkedHashSet<Attachment>();
        String[] tmpPending = relativePath.split(_validAttachSeparator);
        if (tmpPending.length > 0) {
            for (String s : StringUtils.split(tmpPending[0], _multiAttachSeparator)) {
                attachments.add(getAttachByRelativePath(s));
            }
        }
        return attachments;
    }

    public static Collection<Attachment> getAttachByMultiUri(String multiAttachUri) {
        Collection<Attachment> attachments = new LinkedHashSet<Attachment>();
        String[] tmpPending = multiAttachUri.split(_validAttachSeparator);
        if (tmpPending.length > 0) {
            for (String s : StringUtils.split(tmpPending[0], _multiAttachSeparator)) {
                attachments.add(getAttachByRelativePath(s));
            }
        }
        return attachments;
    }

    public static Attachment getAttachByUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            throw new IllegalArgumentException("参数 uri 不能为空!");
        }
        String encryptoVirtualPath = uri;
        if (encryptoVirtualPath.startsWith(_displayServet)) {
            encryptoVirtualPath = encryptoVirtualPath.substring(_displayServet.length());
        } else if (encryptoVirtualPath.startsWith(_downloadServet)) {
            encryptoVirtualPath = encryptoVirtualPath.substring(_downloadServet.length());
        } else if (encryptoVirtualPath.startsWith(_webDavServet)) {
            encryptoVirtualPath = encryptoVirtualPath.substring(_webDavServet.length());
        } else if (encryptoVirtualPath.startsWith(_fileInfoServet)) {
            encryptoVirtualPath = encryptoVirtualPath.substring(_fileInfoServet.length());
        }

        return getAttachByRelativePath(encryptoVirtualPath);
    }

    public static String processHtmlSubmitUri(String saveRelativePath, String multiAttachUri) {

        Path savePath = Paths.get(WebPathUtils.getAbsolutePathByRelativePath(saveRelativePath));

        String[] tmpPending = multiAttachUri.split(_validAttachSeparator);
        Collection<Attachment> validAttachments = new LinkedHashSet<Attachment>();
        if (tmpPending.length > 0) {
            for (String s : StringUtils.split(tmpPending[0], _multiAttachSeparator)) {
                validAttachments.add(getAttachByRelativePath(s));
            }
        }
        if (tmpPending.length > 1) {
            for (String s : StringUtils.split(tmpPending[1], _multiAttachSeparator)) {
                Path thePath = (getAttachByRelativePath(s)).getPath();
                if (Files.exists(thePath)) {
                    if (thePath.startsWith(savePath)) {
                        try {
                            Files.delete(thePath);
                        } catch (IOException e) {
                            throw new UnsupportedOperationException(e);
                        }
                    }
                }
            }
        }

        Collection<Attachment> saveAttachment = new LinkedHashSet<Attachment>();
        for (Attachment attachment : validAttachments) {
            Path thePath = attachment.getPath();
            if (Files.exists(thePath)) {
                if (thePath.startsWith(savePath)) {
                    saveAttachment.add(getAttachByPath(thePath));
                } else {
                    try {
                        Files.createDirectories(savePath);

                        //检查重复文件，并自动重命名
                        String name=setName(savePath.toString(),thePath.getFileName().toString());
                        Path destFile = Paths.get(savePath.toString()).resolve(name);
                        Files.copy(thePath, destFile, StandardCopyOption.REPLACE_EXISTING);
                        saveAttachment.add(getAttachByPath(destFile));
                    } catch (Exception e) {
                        throw new UnsupportedOperationException("拷贝文件发生错误.", e);
                    }
                }
            }
        }

        cleanInvalidAttachment(saveRelativePath, saveAttachment);
        return getRelativePath(saveAttachment);

    }

    public static String getRelativePath(Collection<Attachment> attachments) {
        Collection<String> multiString = new LinkedList<String>();
        for (Attachment a : attachments) {
            multiString.add(a.getRelativePath());
        }
        return StringUtils.join(multiString, _multiAttachSeparator);
    }

    public static void cleanInvalidAttachment(String saveRelativePath, Collection<Attachment> validAttachments) {
        Collection<Path> validPaths = Collections2.transform(validAttachments, new Function<Attachment, Path>() {
            @Override
            public Path apply(Attachment input) {
                return input.getPath();
            }
        });

        Path savePath = Paths.get(WebPathUtils.getAbsolutePathByRelativePath(saveRelativePath));
        if (Files.exists(savePath)) {
            try {
                for (Path path : Directorys.getFiles(savePath, true)) {
                    if (!validPaths.contains(path)) {
                        Files.delete(path);
                    }
                }
                Directorys.deleteDescendantsAndSelfIfEmpty(savePath);
            } catch (IOException e) {
                throw new UnsupportedOperationException("清理无效的附件发生错误.", e);
            }
        }
    }
    //设置文件名称，防止名称重复
    private static String setName(String path,String originalName)throws Exception{
        String re=originalName;
        int hzIndex = originalName.lastIndexOf(".");
        String hz = originalName.substring(hzIndex);
        String name = originalName.substring(0, hzIndex);
        String regEx = "(.*)\\((\\d+)\\)$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(name);
        String name_ = name;
        Integer order = null;
        if(mat.find()){
            name_= mat.group(1);
            order= Integer.parseInt( mat.group(2));
        }
        File[] files=new File(path).listFiles();

        int maxorder = 0;
        boolean hasOrder = false;
        for(File f : files){
            String str=f.getName().toString();
            mat = pat.matcher(str.substring(0, str.lastIndexOf(".")));
            if(mat.find()){
                maxorder = Math.max( Integer.parseInt( mat.group(2)),maxorder);
                hasOrder = true;
            }
        }
        Map<String,String> map = new HashMap<>();
        if(files.length>0){
            if(!hasOrder && order==null){
                re=name_+"("+1+")"+hz;
            }else if( hasOrder && order==null){
                map.put("originalName",originalName);
                re=name_+"("+(maxorder+1)+")"+hz;
            }else if(hasOrder && order !=null){
                re=name_+"("+Math.max(maxorder+1,order)+")"+hz;
            }
        }
        return re;
    }

    public static String getJsonByAttach(Collection<Attachment> attachments) {
        try {
            return JSONArray.fromObject(attachments).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getJsonByAttach(Attachment... attachments) {
        return getJsonByAttach(Arrays.asList(attachments));
    }


    /**
     * //将临时附件转存到永久附件目录下，并返回目录路径
     * @param attachments
     * @param code 模块类型
     * @return
     * @throws IOException
     */
    public static String getUrlByAttachments(String attachments,String code) throws IOException {
        if(StringUtils.isBlank(code)){
            code = "other";
        }
        String AbsoluteFilePath = "";
        //上传文件转换为附件对象集合
        Set<File> attachmentSet = new HashSet<>();
        for (Attachment attachment : AttachmentUtils.getAttachByMultiUri(attachments)) {
            AbsoluteFilePath = attachment.getRelativePath();
            if (!WebPathUtils.isAbsolutePath(AbsoluteFilePath)) {
                AbsoluteFilePath = WebPathUtils.getAbsolutePathByRelativePath(AbsoluteFilePath);
            }
            File tmpFile = new File(AbsoluteFilePath);
            attachmentSet.add(tmpFile);
        }
        //拷贝附件
        String basePath = WebPathUtils.getAttachmentRelativePath(new Date(), code, UUID.randomUUID().toString());
        Path dirAttach = Paths.get(WebPathUtils.getAbsolutePathByRelativePath(basePath));
        try {
            Files.createDirectories(dirAttach);
        } catch (IOException e) {
            throw e;
        }
        try {
            for (File a : attachmentSet) {
                CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES};
                //检查重复文件，并自动重命名
                String name=setName(dirAttach.toString(),a.getName().toString());
                Path destPath =  Paths.get(WebPathUtils.getAbsolutePathByRelativePath(basePath)).resolve(name);
                Files.copy(Paths.get(a.getPath()), destPath, options);
            }
        } catch (Exception e) {
        }
        return basePath;
    }


    /**
     * 根据目录路径得到附件
     * @param urlStr
     * @return
     */
    public static String getAttachmentsByUrl(String urlStr){
        String attachments = "";
        for (Attachment att : AttachmentUtils.getAttachByDirectory(urlStr, false)) {
            if (attachments.equals("")) {
                attachments = att.getDownloadUri();
            } else {
                attachments += "|" + att.getDownloadUri();
            }
        }
        return attachments;
    }
}
