package controller.core.memberinfo;

import model.memberinfo.entity.CoreMemberInfoEntity;
import model.memberinfo.service.CoreMemberInfoService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/core/memberinfo")
public class CoreMemberInfoController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoreMemberInfoService mainService;

    @RequestMapping("findOne")
    public String findOne(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        CoreMemberInfoEntity entity = mainService.findOne(account,password);
        System.out.println("CoreMemberInfoController: " + entity.getMemberName());
        return JSONObject.fromObject(entity).toString();
    }
}
