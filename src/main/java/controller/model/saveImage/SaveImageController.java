package controller.model.saveImage;

import model.core.menuTree.service.CoreMenuTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.dataManage.GenericController;
import util.file.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/model")
public class SaveImageController extends GenericController {
    @Autowired
    private CoreMenuTreeService mainService;

    @RequestMapping(value = "saveImage", method = RequestMethod.POST)
    @ResponseBody
    public String saveImage(HttpServletRequest request, HttpServletResponse response)throws Exception{

        //获取保存路径
        try {
            String image=request.getParameter("image");
            FileUtils.base64ToFile(image,"图片.png");

        }catch (Exception e){
            System.out.println("手写签章失败："+e.getMessage());
            return returnFaild(e.getMessage());
        }
        return returnSuccess("保存成功");
    }
}
