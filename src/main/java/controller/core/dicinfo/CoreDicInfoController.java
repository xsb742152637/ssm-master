package controller.core.dicinfo;

import model.core.dicinfo.entity.CoreDicInfoEntity;
import model.core.dicinfo.service.CoreDicInfoService;
import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.context.Context;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName CoreDicInfoController
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:09
 * @Version 1.0
 */
@Controller
@RequestMapping("core/dicinfo/")
public class CoreDicInfoController extends GenericController{
    @Autowired
    CoreDicInfoService mainService;

    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows){
        String searchKey = request.getParameter("searchKey");
        try {
            List<CoreDicInfoEntity> list = mainService.getMainInfo(searchKey,page,rows);
            Integer count = mainService.getMainCount(searchKey);
            return getTable(list,count);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMain(CoreDicInfoEntity entity)throws Exception{
        if(entity == null){
            return returnSuccess("没有接收到有效数据");
        }
        try{
            entity.setMemberId(Context.getCurrent().getMember().getMemberId());
            entity.setSysTime(new Timestamp(System.currentTimeMillis()));
            if(StringUtils.isBlank(entity.getDicId())){
                entity.setDicId(UUID.randomUUID().toString());
                mainService.insert(entity);
            }else{
                mainService.update(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
        return returnSuccess();
    }

    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String primaryId = request.getParameter("primaryId");
        try {
            mainService.delete(primaryId);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }
}
