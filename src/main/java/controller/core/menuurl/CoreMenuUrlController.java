package controller.core.menuurl;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import model.core.menuurl.service.CoreMenuUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/core/menuurl")
public class CoreMenuUrlController extends GenericController {

    @Autowired
    private CoreMenuUrlService mainService;

    //注解式：通过在执行的Java方法上放置相应的注解完成：
    //@RequiresRoles("admin")
    @RequestMapping("getMenuUrl")
    @ResponseBody
    public String getMenuTree(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<CoreMenuUrlInfoEntity> list = mainService.findAll();
        return returnStringByList(list);
    }
}
