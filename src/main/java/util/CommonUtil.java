package util;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /** 驼峰转下划线*/
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    //以流的形式,将对象序列化后再反序列化,从而实现任意对象的深度克隆
    public static Object objectClone(Object obj){
        try (ByteOutputStream bos = new ByteOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos);) {
            // 写入对象
            oos.writeObject(obj);
            oos.flush();
            // 使用ByteArrayInputStream和ObjectInputStream反序列化
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.getBytes()));) {
                // 读取对象
                return ois.readObject();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
