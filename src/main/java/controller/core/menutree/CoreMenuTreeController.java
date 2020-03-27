package controller.core.menutree;

import model.core.menutree.service.CoreMenuTreeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/core/menuTree")
public class CoreMenuTreeController extends GenericController {
    @Autowired
    private CoreMenuTreeService mainService;

    //注解式：通过在执行的Java方法上放置相应的注解完成：
    //@RequiresRoles("admin")
    @RequestMapping("getMenuTree")
    @ResponseBody
    public String getMenuTree(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String isTop = request.getParameter("isTop");
        String isShow = request.getParameter("isShow");

        Boolean isShow2 = null;
        if(StringUtils.isBlank(isTop)){
            isTop = "true";
        }
        if(StringUtils.isNotBlank(isShow)){
            isShow2 = Boolean.parseBoolean(isShow);
        }

        List<Map<String,Object>> list = mainService.getMenuTree(Boolean.parseBoolean(isTop),isShow2);
        return returnStringByList(list);
    }
}
