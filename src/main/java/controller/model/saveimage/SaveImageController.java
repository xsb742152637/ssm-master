package controller.model.saveimage;

import model.core.menutree.service.CoreMenuTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import util.converter.BlobAndImageConverter;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/model")
public class SaveImageController extends GenericController {
    @Autowired
    private CoreMenuTreeService mainService;

    @RequestMapping(value = "saveImage", method = RequestMethod.POST)
    public String saveImage(HttpServletRequest request, HttpServletResponse response)throws Exception{

        //获取保存路径
        try {
            String image=request.getParameter("image");
            BlobAndImageConverter.getFileByBase64(image,"图片.png");

        }catch (Exception e){
            System.out.println("手写签章失败："+e.getMessage());
            return returnFaild(e.getMessage());
        }
        return returnSuccess("保存成功");
    }
}
