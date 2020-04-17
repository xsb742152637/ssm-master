package util.converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import util.io.Attachment;
import util.io.AttachmentUtils;
import util.io.WebPathUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;

/**
 * Created by xiucai on 2019/10/22.
 * 将Image与Blob互相转换
 */
public class BlobAndImageConverter {

    //相对路径转blob
    public  static Blob getBlobByPath(String path){
        return getBlobByRealPath(AttachmentUtils.getAttachByUri(path).getPath().toString());
    }
    //绝对路径转blob
    public static Blob getBlobByRealPath(String absolutePath){
        return new BlobImpl(getBytesByAbsolutePath(absolutePath));
    }

    //相对路径转byte
    public  static byte[] getBytesByPath(String path){
        return getBytesByAbsolutePath(AttachmentUtils.getAttachByUri(path).getPath().toString());
    }

    //绝对路径转byte
    public static byte[] getBytesByAbsolutePath(String absolutePath){
        FileInputStream is = null;
        byte[] image = null;
        try {
            File file = new File(absolutePath);
//              System.out.println(file.toString());
//              File file2=new File(zplj.substring(0,zplj.lastIndexOf("."))+".jpg");
//
//              FileInputStream in=new FileInputStream(file);
//              FileOutputStream out=new FileOutputStream(file2);
//              int c;
//              byte buffer[]=new byte[1024];
//              while((c=in.read(buffer))!=-1){
//                  for(int i=0;i<c;i++)
//                      out.write(buffer[i]);
//              }
//              in.close();
//              out.close();

            is = new FileInputStream(file);
            int streamLength = (int)file.length();
            image = new byte[streamLength];
            is.read(image, 0, streamLength);//将字节流转换为byte数组
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                //关闭输出流
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static BufferedImage getImageByBlob(Blob blob){
        BufferedInputStream inputimage=null;
        BufferedImage image = null;
        try{
            try{
                inputimage = new BufferedInputStream(blob.getBinaryStream());
                image = ImageIO.read(inputimage);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(inputimage != null)
                    inputimage.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }

    public static String getBase64ByBlob(Blob blob) {
        String result = "";
        if(null != blob) {
            try {
                InputStream msgContent = blob.getBinaryStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[100];
                int n = 0;
                while (-1 != (n = msgContent.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                result =new BASE64Encoder().encode(output.toByteArray()) ;
                result = "data:image/jpeg;base64," + result;
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Blob getBlobByBufferedImage(BufferedImage image){
        return getBlobByBufferedImage(image,"jpg");
    }
    public static Blob getBlobByBufferedImage(BufferedImage image,String formatName){
        Blob blob = null;
        try{
            ByteArrayOutputStream bs =new ByteArrayOutputStream();
            ImageOutputStream imOut =ImageIO.createImageOutputStream(bs);
            ImageIO.write(image,formatName,imOut); //scaledImage1为BufferedImage
            byte[] b=bs.toByteArray();
            blob = new BlobImpl(b);
        }catch (Exception e){
            e.printStackTrace();
        }

        return blob;
    }

    //将文件转化为字节数组字符串，并对其进行Base64编码处理
    public static String getBase64ByFile(File file) {
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组

        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);

    }

    //将base64字符转换为文件，并返回文件绝对路径
    public static String getFileByBase64(String fileName, String base64Code){
        FileOutputStream write =null;
        String tmpUri = "";
        try {
            String _tempPath = WebPathUtils.getNewTempRelativePath();
            String _tempRealPath = WebPathUtils.getAbsolutePathByRelativePath(_tempPath);
            Path _path = Paths.get(_tempRealPath);
            if(!Files.exists(_path)){
                Files.createDirectories(_path);
            }

            int n =base64Code.indexOf(";base64,"); //data:image/jpeg;base64,aaaaaaaaaaaa
            String header =  base64Code.substring(0,n);
            String hz = header.substring(header.indexOf("/")+1);
            String code =base64Code.substring(n+8);


            if(StringUtils.isNotBlank(hz) && StringUtils.isNotBlank(code)){
                Attachment attachment = AttachmentUtils.getAttachByRelativePath(_tempPath + fileName+"."+hz);
                write = new FileOutputStream(attachment.getPath().toString());
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] decoderBytes = decoder.decodeBuffer(code);
                write.write(decoderBytes);
                write.flush();
                tmpUri = attachment.getPath().toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(write !=null){
                try {
                    write.close();
                }catch (Exception e){}
            }
        }
        return tmpUri;
    }

    public static Attachment getAttaByBase64(String fileName, String base64Code){
        return AttachmentUtils.getAttachByUri(getFileByBase64(fileName,base64Code));
    }
}
