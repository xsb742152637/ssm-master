package controller.core.dictionary;

import model.core.dictionary.service.CoreDicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

/**
 * @ClassName CoreDicInfoController
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:09
 * @Version 1.0
 */
@Controller
@RequestMapping("core/dicinfo/")
public class CoreDicInfoController {
    @Autowired
    CoreDicInfoService service;

    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(){
        try {
            return GenericController.getTable(service.findAll(),service.findAllCount());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
