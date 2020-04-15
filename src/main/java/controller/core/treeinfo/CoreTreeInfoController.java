package controller.core.treeinfo;

import model.core.treeinfo.TreeType;
import model.core.treeinfo.entity.CoreTreeInfoEntity;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
            return returnStringByList(list);
        }catch (Exception e){
            e.printStackTrace();
            return " ";
        }

    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMainInfo(CoreTreeInfoEntity entity){
        if(StringUtils.isBlank(entity.getTreeName())){
            return returnFaild("没有得到名称");
        }
        if(StringUtils.isBlank(entity.getParentId())){
            return returnFaild("没有找到上级编号");
        }

        try {
            String treeId = mainService.save(entity);
            return returnSuccess(treeId);
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }

    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request){
        String primaryId = request.getParameter("primaryId");
        try {
            mainService.delete(primaryId);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }

    @RequestMapping("moveMain")
    @ResponseBody
    public String move(HttpServletRequest request){
        boolean moveOn = Boolean.parseBoolean(request.getParameter("type"));
        String primaryId = request.getParameter("primaryId");
        try {
            mainService.move(primaryId,moveOn);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }
}
