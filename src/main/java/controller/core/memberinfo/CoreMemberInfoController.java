package controller.core.memberinfo;

import model.core.guide.service.CoreGuideFileService;
import model.core.guide.service.core.MenuEx;
import model.core.memberarchives.service.CoreMemberArchivesService;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/core/memberInfo")
public class CoreMemberInfoController extends GenericController {
    @Autowired
    private CoreMemberInfoService mainService;
    @Autowired
    private CoreTreeInfoService treeService;
    @Autowired
    private CoreMemberArchivesService memberArchivesService;
    @Autowired
    private CoreGuideFileService guideFileService;

    @RequestMapping("findOneByTreeId")
    @ResponseBody
    public String findOne(HttpServletRequest request, HttpServletResponse response){
        String treeId = request.getParameter("treeId");
        CoreMemberInfoEntity entity = mainService.findOneByTreeId(treeId);
        logger.debug("查询成员");
        return returnStringByMap(entity);
    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMainInfo(CoreMemberInfoEntity entity){
        if(StringUtils.isNotBlank(entity.getAccount()) && mainService.checkAccount(entity.getAccount()) > 0){
            return returnFaild("账号已存在");
        }else if(StringUtils.isBlank(entity.getTreeId())){
            return returnFaild("没有找到当前成员的树形编号");
        }

        try {
            if (StringUtils.isBlank(entity.getMemberId())){
                entity.setMemberId(UUID.randomUUID().toString());
                mainService.insert(entity);
                //授权xml同步成员
                guideFileService.createMemberXml("add",entity.getMemberId(),entity.getMemberName());
            }else {
                mainService.update(entity);
                new MenuEx().updateGuideByMemChange(entity.getMemberId(),"updateMem");
            }
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }

    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request){
        String treeId = request.getParameter("treeId");
        String primaryId = request.getParameter("primaryId");
        try {
            treeService.delete(treeId);//删除树形
            memberArchivesService.delete(primaryId);//删除成员档案
            mainService.delete(primaryId);//删除成员信息
            new MenuEx().updateGuideByMemChange(primaryId,"deleteMem");//更新授权信息
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }
}
