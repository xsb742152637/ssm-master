package controller.core.memberinfo;

import model.core.guide.service.CoreGuideFileService;
import model.core.guide.service.core.MenuEx;
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
import java.util.UUID;

@Controller
@RequestMapping("/core/memberInfo")
public class CoreMemberInfoController extends GenericController {
    @Autowired
    private CoreMemberInfoService mainService;
    @Autowired
    private CoreTreeInfoService treeService;
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
    public String saveMainInfo(HttpServletRequest request){
        int treeType = TreeType.MemberInfo.getCode();
        String parentId = request.getParameter("parentId");
        String memberId = request.getParameter("memberId");
        String account = request.getParameter("account");
        if(mainService.checkAccount(account) > 0){
            return returnFaild("账号已存在");
        }
        String treeId = null;

        CoreMemberInfoEntity entity ;
        if (StringUtils.isBlank(memberId)){
            entity = new CoreMemberInfoEntity();
            entity.setMemberId(UUID.randomUUID().toString());
        }else {
            entity = mainService.findOne(memberId);
            treeId = entity.getTreeId();
        }
        entity.setMemberName(request.getParameter("memberName"));
        entity.setMemberType(Integer.parseInt(request.getParameter("memberType")));
        entity.setAccount(account);
        entity.setPassword(request.getParameter("password"));
        entity.setMemberState(Boolean.parseBoolean(request.getParameter("memberState")));

        try {
            treeId = treeService.save(TreeType.getTreeTypeByCode(treeType),parentId,treeId,entity.getMemberName());
            entity.setTreeId(treeId);

            if (StringUtils.isBlank(memberId)){
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
            treeService.delete(treeId);
            mainService.delete(primaryId);
            new MenuEx().updateGuideByMemChange(primaryId,"deleteMem");
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }
}
