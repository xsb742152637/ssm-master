package controller.core.memberinfo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
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

    @RequestMapping("findOne")
    public String findOne(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        CoreMemberInfoEntity entity = mainService.findOne(account,password);
        System.out.println("CoreMemberInfoController: " + entity.getMemberName());
        logger.debug("查询成员");
        return returnStringByMap(entity);
    }

    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(HttpServletRequest request){
        String parentId = request.getParameter("parentId");
        try {
            List<Map<String,Object>> list = mainService.getMainInfo(parentId);
            return GenericController.returnStringByList(list);
        }catch (Exception e){
            e.printStackTrace();
            return " ";
        }

    }


    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMainInfo(HttpServletRequest request){
        String parentId = request.getParameter("parentId");
        String memberId = request.getParameter("memberId");
        CoreMemberInfoEntity fatherEntity = mainService.findOnebyId(parentId);
        CoreMemberInfoEntity entity ;
        if (StringUtils.isBlank(memberId)){
            entity = new CoreMemberInfoEntity();
            entity.setMemberId(UUID.randomUUID().toString());
        }else {
            entity = mainService.findOnebyId(memberId);
        }
        entity.setParentId(fatherEntity.getMemberId());
        entity.setMemberName(request.getParameter("memberName"));
        entity.setMemberType(Integer.parseInt(request.getParameter("memberType")));
        entity.setAccount(request.getParameter("account"));
        entity.setPassword(request.getParameter("password"));
        entity.setIsFrozen(Boolean.parseBoolean(request.getParameter("isFrozen")));
        entity.setMemberLeft(fatherEntity.getMemberRight());
        entity.setMemberRight(entity.getMemberLeft()+1);
        try {
            if (StringUtils.isBlank(memberId)){
                mainService.insert(entity,fatherEntity);
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
            mainService.moveMainInfo(mainId,moveOn);
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
    }
}
