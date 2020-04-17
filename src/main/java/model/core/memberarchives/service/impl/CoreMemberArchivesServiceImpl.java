package model.core.memberarchives.service.impl;

import model.core.memberarchives.dao.CoreMemberArchivesDao;
import model.core.memberarchives.entity.CoreMemberArchivesEntity;
import model.core.memberarchives.service.CoreMemberArchivesService;
import model.core.memberinfo.dao.CoreMemberInfoDao;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.context.ApplicationContext;
import util.converter.BlobAndImageConverter;
import util.converter.BlobImpl;
import util.datamanage.GenericService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.sql.Blob;
import java.util.List;

@Service
public class CoreMemberArchivesServiceImpl extends GenericService<CoreMemberArchivesEntity> implements CoreMemberArchivesService {
    @Autowired
    private CoreMemberArchivesDao dao;
    @Autowired
    private CoreMemberInfoService memberInfoService;

    public static CoreMemberArchivesServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreMemberArchivesServiceImpl.class);
    }

    @Override
    public CoreMemberArchivesEntity findOne(String primaryId) {
        return dao.findOne(primaryId);
    }

    @Override
    public BufferedImage getPhoto(String memberId, String text) {
        BufferedInputStream inputimage=null;
        BufferedImage image = null;
        try{
            if(StringUtils.isNotBlank(memberId)){
                CoreMemberArchivesEntity entity = findOne(memberId);

                boolean have=false;
                if(entity != null && entity.getPhoto() != null){
                    inputimage = new BufferedInputStream(new BlobImpl(entity.getPhoto()).getBinaryStream());
                    try{
                        image = ImageIO.read(inputimage);
                        if(image==null){
                            have=true;
                        }else{
                            have=false;
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }else{
                    have=true;
                }
                /*
                * 如果头像为空，自动生成临时头像！
                * 2016-05-12 xsb
                * */
                if(have){
                    CoreMemberInfoEntity mem = memberInfoService.findOne(memberId);
                    //名称
                    if(mem != null && mem.getMemberName() != null){
                        text = getStr(mem.getMemberName().toString(),6);//处理字符串超长问题
                    }else{
                        text="空";
                    }
                    image = createPhoto(text);
                    Blob blob = BlobAndImageConverter.getBlobByBufferedImage(image);
                    if(entity!=null){
                        entity.setPhoto(new BlobImpl(blob).getBytes());
                        update(entity);
                    }else{
                        entity = new CoreMemberArchivesEntity();
                        entity.setMemberId(memberId);
                        entity.setPhoto(new BlobImpl(blob).getBytes());
                        insert(entity);
                    }
                }
            }else if(StringUtils.isNotBlank(text)){
                image=createPhoto(text.trim());
                Blob blob = BlobAndImageConverter.getBlobByBufferedImage(image);

                inputimage = new BufferedInputStream(blob.getBinaryStream());
                image = ImageIO.read(inputimage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputimage != null){
                try{
                    inputimage.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return image;
    }

    private BufferedImage createPhoto(String name){
        //备选背景色
        int[] rC={45,90,70,80,200,200,220,140,110,80,180};
        int[] gC={204,200,140,80,120,120,150,200,150,15,200};
        int[] bC={112,200,200,180,200,120,40,100,160,10,160};
        int i=(int)(Math.random()*10);
        int width=100,height=100;

        BufferedImage image= new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, width, height);
        Color c=new Color(rC[i],gC[i],bC[i]);//背景颜色
        g.setColor(c);
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(255,255,255));//字体颜色 白色
        Font font = new Font("微软雅黑", Font.CENTER_BASELINE, 25);
        g.setFont(font);

        //字体居中
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(name, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawString(name, (int) x, (int) baseY);
        return image;
    }

    private String getStr(String str,int len){
        int i=0;
        String newStr="";
        for(String s:str.split("")){
            i+=s.getBytes().length;
            if(i>len){
                newStr+="..";
                break;
            }
            newStr+=s;
        }
        return newStr;
    }

    @Override
    @Transactional
    public void insert(CoreMemberArchivesEntity entity) {
        dao.insert(convertList(entity));
    }

    @Override
    @Transactional
    public void insert(List<CoreMemberArchivesEntity> list) {
        dao.insert(convertList(list));
    }

    @Override
    @Transactional
    public int update(CoreMemberArchivesEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    @Transactional
    public void delete(String primaryId) {
        dao.delete(primaryId);
    }
}
