package controller.core.memberinfo;

import com.fasterxml.jackson.databind.node.ObjectNode;
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
@RequestMapping("/core/memberInfo")
public class CoreMemberInfoController extends GenericController {
    @Autowired
    private CoreMemberInfoService mainService;
    @Autowired
    private CoreTreeInfoService treeService;

    @RequestMapping("findOneByTreeId")
    @ResponseBody
    public String findOne(HttpServletRequest request, HttpServletResponse response){
        String treeId = request.getParameter("treeId");
        CoreMemberInfoEntity entity = mainService.findOneByTreeId(treeId);
        System.out.println("CoreMemberInfoController: " + entity.getMemberName());
        logger.debug("查询成员");
        return returnStringByMap(entity);
    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMainInfo(HttpServletRequest request){
        int treeType = TreeType.MemberInfo.getCode();
        String parentId = request.getParameter("parentId");
        String memberId = request.getParameter("memberId");
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
        entity.setAccount(request.getParameter("account"));
        entity.setPassword(request.getParameter("password"));
        entity.setIsFrozen(Boolean.parseBoolean(request.getParameter("isFrozen")));

        try {
            treeId = treeService.save(TreeType.getTreeTypeByCode(treeType),parentId,treeId,entity.getMemberName());
            entity.setTreeId(treeId);

            if (StringUtils.isBlank(memberId)){
                mainService.insert(entity);
            }else {
                mainService.update(entity);
            }
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
    }


    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request){
        String treeId = request.getParameter("treeId");
        String mainId = request.getParameter("mainId");
        try {
            treeService.delete(treeId);
            mainService.delete(mainId);
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
    }
}
