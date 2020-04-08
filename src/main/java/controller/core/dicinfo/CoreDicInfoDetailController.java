package controller.core.dicinfo;

import model.core.dicinfo.entity.CoreDicInfoDetailEntity;
import model.core.dicinfo.service.CoreDicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName CoreDicInfoController
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:09
 * @Version 1.0
 */
@Controller
@RequestMapping("core/dicdetail/")
public class CoreDicInfoDetailController {
    @Autowired
    CoreDicDetailService detailService;

    @RequestMapping("getDetailInfo")
    @ResponseBody
    public String getDetailInfo(HttpServletRequest request){
        try {
            String dicId = request.getParameter("dicId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            List<CoreDicInfoDetailEntity> list = detailService.findAll(dicId);
            return GenericController.getTable(list,list.size());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
