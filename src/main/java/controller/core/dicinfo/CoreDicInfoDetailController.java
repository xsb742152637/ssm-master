package controller.core.dicinfo;

import model.core.dicinfo.entity.CoreDicInfoDetailEntity;
import model.core.dicinfo.entity.CoreDicInfoDetailEntity;
import model.core.dicinfo.service.CoreDicDetailService;
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
@RequestMapping("core/dicdetail/")
public class CoreDicInfoDetailController extends GenericController{
    @Autowired
    CoreDicDetailService detailService;

    @RequestMapping("getDetailInfo")
    @ResponseBody
    public String getDetailInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows){
        String dicId = request.getParameter("dicId");
        String searchKey = request.getParameter("searchKey");
        try {
            List<CoreDicInfoDetailEntity> list = detailService.getDetailInfo(dicId,searchKey,page,rows);
            Integer count = detailService.getDetailCount(dicId,searchKey);
            return getTable(list,count);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    @RequestMapping("saveDetail")
    @ResponseBody
    public String saveDetail(CoreDicInfoDetailEntity entity)throws Exception{
        if(entity == null){
            return returnSuccess("没有接收到有效数据");
        }
        try{
            if(StringUtils.isBlank(entity.getDetailId())){
                entity.setDetailId(UUID.randomUUID().toString());
                detailService.insert(entity);
            }else{
                detailService.update(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
        return returnSuccess();
    }

    @RequestMapping("deleteDetail")
    @ResponseBody
    public String deleteMain(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String detailId = request.getParameter("detailId");
        try {
            detailService.delete(detailId);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFaild();
        }
    }
}
