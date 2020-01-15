package controller.core.memberinfo;

import model.memberinfo.entity.CoreMemberInfoEntity;
import model.memberinfo.service.CoreMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.dataManage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/core/memberinfo")
public class CoreMemberInfoController extends GenericController {
    @Autowired
    private CoreMemberInfoService mainService;

    @RequestMapping("findOne")
    public String findOne(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        CoreMemberInfoEntity entity = mainService.findOne(account,password);
        System.out.println("CoreMemberInfoController: " + entity.getMemberName());
        logger.debug("查询成员");
        return returnString(entity);
    }
}
