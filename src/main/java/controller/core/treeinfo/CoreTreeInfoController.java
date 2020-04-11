package controller.core.treeinfo;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import model.core.treeinfo.TreeType;
import model.core.treeinfo.service.CoreTreeInfoService;
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
import java.util.UUID;

@Controller
@RequestMapping("/core/treeinfo")
public class CoreTreeInfoController extends GenericController {
    @Autowired
    private CoreTreeInfoService mainService;

    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(HttpServletRequest request){
        String treeType = request.getParameter("treeType");
        String parentId = request.getParameter("parentId");
        String openLevel = request.getParameter("openLevel");
        if(StringUtils.isBlank(openLevel)){
            openLevel = "1";
        }
        try {
            List<Map<String,Object>> list = mainService.getMainInfo(treeType,parentId, Integer.parseInt(openLevel));
            return GenericController.returnStringByList(list);
        }catch (Exception e){
            e.printStackTrace();
            return " ";
        }

    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMainInfo(HttpServletRequest request){
        String treeType = request.getParameter("treeType");
        String parentId = request.getParameter("parentId");
        String treeId = request.getParameter("treeId");
        String treeName = request.getParameter("treeName");

        if(StringUtils.isBlank(treeType)){
            return GenericController.returnFaild("没有找到树形分类");
        }

        try {
            mainService.save(TreeType.getTreeTypeByCode(Integer.parseInt(treeType)),parentId,treeId,treeName);
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
    }

    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request){
        String mainId = request.getParameter("mainId");
        try {
            mainService.delete(mainId);
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
    }

    @RequestMapping("moveMain")
    @ResponseBody
    public String move(HttpServletRequest request){
        boolean moveOn = Boolean.parseBoolean(request.getParameter("type"));
        String mainId = request.getParameter("mainId");
        try {
            mainService.move(mainId,moveOn);
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
    }
}
