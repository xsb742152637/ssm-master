package controller.core.menutree;

import model.core.guide.service.CoreGuideFileService;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
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
import java.util.UUID;

@Controller
@RequestMapping("/core/menuTree")
public class CoreMenuTreeController extends GenericController {
    @Autowired
    private CoreMenuTreeService mainService;
    @Autowired
    private CoreGuideFileService guideFileService;

    //注解式：通过在执行的Java方法上放置相应的注解完成：
    //@RequiresRoles("admin")
    @RequestMapping("getMenuTree")
    @ResponseBody
    public String getMenuTree(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String needGuide = request.getParameter("needGuide");
        String isTop = request.getParameter("isTop");
        String isShow = request.getParameter("isShow");

        Boolean isShow2 = null;
        if(StringUtils.isBlank(needGuide)){
            needGuide = "false";
        }
        if(StringUtils.isBlank(isTop)){
            isTop = "true";
        }
        if(StringUtils.isNotBlank(isShow)){
            isShow2 = Boolean.parseBoolean(isShow);
        }
        List<Map<String,Object>> list = mainService.getMenuTree(Boolean.parseBoolean(needGuide),Boolean.parseBoolean(isTop),isShow2);
        return returnStringByList(list);
    }


    @RequestMapping("moveMain")
    @ResponseBody
    public String moveTree(HttpServletRequest request) throws Exception {
        String primaryId = request.getParameter("primaryId");
        String type = request.getParameter("type");
        try{
            mainService.moveTree(primaryId,Boolean.parseBoolean(type));
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnFaild();
    }

    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request){
        String primaryId = request.getParameter("primaryId");
        try{
            mainService.delete(primaryId);
            guideFileService.deleteMenu(primaryId);
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
        return returnSuccess();
    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMain(HttpServletRequest request){
        String parentId = request.getParameter("parentId");
        String primaryId = request.getParameter("menuId");
        String title = request.getParameter("title");
        String urlId = request.getParameter("urlId");
        String icon = request.getParameter("icon");
        String isShow = request.getParameter("isShow");
        CoreMenuTreeInfoEntity entity = new CoreMenuTreeInfoEntity();
        if(StringUtils.isBlank(primaryId)){
            //如果是新增，需要调整上级菜单
            CoreMenuTreeInfoEntity entityP = mainService.findOne(parentId);
            entityP.setType(false);
            try{
                mainService.update(entityP);
            }catch (Exception e){
                e.printStackTrace();
            }
            entity.setMenuId(UUID.randomUUID().toString());
            entity.setMenuLevel(mainService.getMenuLevelByParLevel(entityP.getOutlineLevel()));
            entity.setOutlineLevel(entityP.getOutlineLevel() + "." + String.valueOf(entity.getMenuLevel()));
            entity.setType(true);
        }else{
            entity = mainService.findOne(primaryId);
        }
        entity.setTitle(title);
        entity.setUrlId(urlId);
        entity.setIcon(icon);
        entity.setIsShow(Boolean.parseBoolean(isShow));
        try{
            if(StringUtils.isBlank(primaryId)){
                mainService.insert(entity);
            }else {
                mainService.update(entity);
            }
            guideFileService.addMenu(parentId,entity.getMenuId(),entity.getTitle());
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnFaild();
    }
}
