package controller.core.menuurl;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import model.core.menuurl.service.CoreMenuUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/core/menuurl")
public class CoreMenuUrlController extends GenericController{

    @Autowired
    private CoreMenuUrlService mainService;

    //注解式：通过在执行的Java方法上放置相应的注解完成：
    //@RequiresRoles("admin")
    @RequestMapping("getMainInfo")
    public String getMainInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows)throws Exception{
        String primaryId = request.getParameter("primaryId");
        String searchKey = request.getParameter("searchKey");
        try {
            List<CoreMenuUrlInfoEntity> list = mainService.getMainInfo(primaryId,searchKey,page,rows);
            Integer count = mainService.getMainCount(primaryId,searchKey);
            return getTable(list,count);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("getMenuUrl")
    public String getMenuUrl(HttpServletRequest request, HttpServletResponse response)throws Exception{
        try {
            List<CoreMenuUrlInfoEntity> list = mainService.getMainInfo(null,null,1,-1);
            return returnStringByList(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("saveMain")
    public String saveMain(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String primaryId = request.getParameter("urlId");
        String title = request.getParameter("title");
        String code = request.getParameter("code");
        String url = request.getParameter("url");
        String parameter = request.getParameter("parameter");

        CoreMenuUrlInfoEntity entity = new CoreMenuUrlInfoEntity();
        if(StringUtils.isBlank(primaryId)){
            entity.setUrlId(UUID.randomUUID().toString());
        }else{
            entity = mainService.findOne(primaryId);
        }
        entity.setTitle(title);
        entity.setCode(code);
        entity.setUrl(url);
        entity.setParameter(parameter);
        entity.setSysTime(new Timestamp(System.currentTimeMillis()));
        try{
            if(StringUtils.isBlank(primaryId)){
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
