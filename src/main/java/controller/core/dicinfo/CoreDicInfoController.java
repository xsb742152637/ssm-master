package controller.core.dicinfo;

import model.core.dicinfo.entity.CoreDicInfoEntity;
import model.core.dicinfo.service.CoreDicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import java.util.List;

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
    CoreDicInfoService mainService;

    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(){
        try {
            List<CoreDicInfoEntity> list = mainService.findAll();
            return GenericController.getTable(list,list.size());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
