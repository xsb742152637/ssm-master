package controller.core.dictionary;

import model.core.dictionary.service.CoreDicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CoreDicDetailController
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:09
 * @Version 1.0
 */
@Controller
@RequestMapping("core/dicdetail/")
public class CoreDicDetailController {
    @Autowired
    CoreDicDetailService service;

    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(HttpServletRequest request){
        try {
            String typeId = request.getParameter("dicinfoId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            return GenericController.getTable(service.findAll(typeId),service.findAllCount(typeId));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
