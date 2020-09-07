package controller.core.guide;

import model.core.guide.GuideType;
import model.core.guide.service.CoreGuideFileService;
import model.core.guide.service.core.MenuEx;
import model.core.guide.service.impl.CoreGuideFileServiceImpl;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import model.core.menutree.service.CoreMenuTreeService;
import model.core.menutree.service.impl.CoreMenuTreeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.context.Context;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/core/guide")
public class CoreGuideFileController extends GenericController {

    @Autowired
    private CoreGuideFileService mainService;
    @Autowired
    private CoreMenuTreeService menuTreeService;

    @RequestMapping("getXmlTree")
    public void getXmlTree(HttpServletRequest request, HttpServletResponse response){
        String projectId = request.getParameter("projectId");
        response.setContentType("text/xml;charset=UTF-8");
        String xmlStr =  "";
        try {
            if(StringUtils.isNotBlank(projectId)){
                CoreGuideFileServiceImpl.IS_UPDATE = false;
                xmlStr = mainService.findOne(projectId);
                PrintWriter print = response.getWriter();
                print.print(xmlStr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //保存授权
    @RequestMapping("saveGuide")
    public String saveGuide(HttpServletRequest request){
        String projectId = request.getParameter("projectId");
        String str = request.getParameter("str");
        if(StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(str)){
            if(CoreGuideFileServiceImpl.IS_UPDATE){
                return returnFaild("保存失败！成员或菜单信息已改变，当前页面将重新加载");
            }
            try{
                str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+str;
                mainService.update(projectId,str);

                System.out.println("修改权限" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()))+"\nprojectId："+projectId+"\n"+str+"\n\n");
                return returnSuccess("保存成功！");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return returnFaild("保存失败！");
    }

    //清空授权
    @RequestMapping("removeGuide")
    public String removeGuide(){
        try{
            mainService.deleteAll();
            System.out.println("清空权限");
            return returnSuccess("清空成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnFaild("清空失败！");
    }

    //复制到人，同一项目部不同成员之间的复制，但会影响上下级菜单及成员
    @RequestMapping("copyMem")
    public String copyMem(String copy_memberIds,String projectId,String memberId)throws Exception{
        if(StringUtils.isBlank(copy_memberIds)){
            return returnFaild("请至少选择一个需要复制授权的成员！");
        }else{
            int r = new MenuEx().copyMem(copy_memberIds,projectId,memberId);
            return returnSuccess("成功复制" + r + "条权限！");
        }
    }

    //是否可编辑某个模块
    @RequestMapping("canUpdate")
    public boolean canUpdate(String menuCode)throws Exception{
        boolean isHave = false;
        CoreMenuTreeInfoEntity entity = null;
        if(StringUtils.isBlank(menuCode)){
            entity = Context.getMenuTree();
        }else{
            entity = menuTreeService.findOneByCode(menuCode);
        }

        if(entity != null){
            isHave = SecurityUtils.getSubject().isPermitted(entity.getMenuId() + ":" + GuideType.Update.getCode());
        }
        return isHave;
    }
}
