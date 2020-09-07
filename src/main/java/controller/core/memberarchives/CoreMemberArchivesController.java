package controller.core.memberarchives;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import model.core.memberarchives.entity.CoreMemberArchivesEntity;
import model.core.memberarchives.service.CoreMemberArchivesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.converter.BlobAndImageConverter;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@RestController
@RequestMapping("/core/memberArchives")
public class CoreMemberArchivesController extends GenericController {
    @Autowired
    private CoreMemberArchivesService mainService;

    /**
     *
     * memberId 成员编号，获取成员头像，如果没有则自动新增
     * text 图片内容文字，如果只传入该参数，则表示临时生成一个图片
     */
    @RequestMapping("getPhoto")
    public void getPhoto(HttpServletRequest request, HttpServletResponse response){
        String memberId = request.getParameter("memberId");
        String text = request.getParameter("text");

        BufferedImage image = mainService.getPhoto(memberId,text);
        if(image!=null){
            try{
                OutputStream sos = response.getOutputStream();
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
                encoder.encode(image);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @RequestMapping("saveMain")
    public String saveMainInfo(HttpServletRequest request, HttpServletResponse response){
        String memberId = request.getParameter("memberId");
        String photoUrl = request.getParameter("photoUrl");

        if(StringUtils.isBlank(memberId)){
            return returnFaild("没有正确接收到参数");
        }

        try{
            CoreMemberArchivesEntity entity = mainService.findOne(memberId);
            if(entity == null){
                entity = new CoreMemberArchivesEntity();
                entity.setMemberId(memberId);
                entity.setPhoto(BlobAndImageConverter.getBytesByPath(photoUrl));
                mainService.insert(entity);
            }else{
                entity.setPhoto(BlobAndImageConverter.getBytesByPath(photoUrl));
                mainService.update(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnSuccess();
    }

    @RequestMapping("deleteMain")
    public String deleteMain(HttpServletRequest request){
        String primaryId = request.getParameter("primaryId");
        try {
            mainService.delete(primaryId);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }
}
