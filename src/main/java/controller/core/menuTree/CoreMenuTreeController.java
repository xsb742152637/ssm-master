package controller.core.menuTree;

import model.core.menuTree.service.CoreMenuTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.dataManage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/core/menuTree")
public class CoreMenuTreeController extends GenericController {
    @Autowired
    private CoreMenuTreeService mainService;

    @RequestMapping("getMenuTree")
    @ResponseBody
    public String getMenuTree(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<Map<String,Object>> list = mainService.getMenuTree();
        return returnStringByList(list);
    }
}
