package controller.core.menuurl;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import model.core.menuurl.service.CoreMenuUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.datamanage.GenericController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/core/menuurl")
public class CoreMenuUrlController extends GenericController {

    @Autowired
    private CoreMenuUrlService mainService;

    //注解式：通过在执行的Java方法上放置相应的注解完成：
    //@RequiresRoles("admin")
    @RequestMapping("getMainInfo")
    @ResponseBody
    public String getMainInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows)throws Exception{
//        List<CoreMenuUrlInfoEntity> li = new ArrayList<>();
//        CoreMenuUrlInfoEntity entity = mainService.findOne("54298359-3964-459a-a094-7e0a43df1bc6");
//        entity.setTitle("11");
//        entity.setSysTime(new Timestamp(System.currentTimeMillis()));
//        li.add(entity);
//
//        entity = mainService.findOne("5912065a-e275-4a4e-a00d-7057af2c54ff");
//        entity.setTitle("22");
//        li.add(entity);
//
//        mainService.update(li);



        String mainId = request.getParameter("mainId");
        String searchKey = request.getParameter("searchKey");
        try {
            List<CoreMenuUrlInfoEntity> list = mainService.getMainInfo(mainId,searchKey,page,rows);
            Integer count = mainService.getMainCount(mainId,searchKey);
            return getTable(list,count);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("getMenuUrl")
    @ResponseBody
    public String getMenuUrl(HttpServletRequest request, HttpServletResponse response)throws Exception{
        try {
            List<CoreMenuUrlInfoEntity> list = mainService.getMainInfo(null,null,1,-1);
            return returnStringByList(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("deleteMain")
    @ResponseBody
    public String deleteMain(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String mainId = request.getParameter("mainId");
        try {
            mainService.delete(mainId);
            return GenericController.returnSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }

    }

    @RequestMapping("saveMain")
    @ResponseBody
    public String saveMain(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String mainId = request.getParameter("urlId");
        String title = request.getParameter("title");
        String code = request.getParameter("code");
        String url = request.getParameter("url");
        String parameter = request.getParameter("parameter");

        CoreMenuUrlInfoEntity entity = new CoreMenuUrlInfoEntity();
        if(StringUtils.isBlank(mainId)){
            entity.setUrlId(UUID.randomUUID().toString());
        }else{
            entity = mainService.findOne(mainId);
        }
        entity.setTitle(title);
        entity.setCode(code);
        entity.setUrl(url);
        entity.setParameter(parameter);
        entity.setSysTime(new Timestamp(System.currentTimeMillis()));
        try{
            if(StringUtils.isBlank(mainId)){
                mainService.insert(entity);
            }else{
                mainService.update(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
            return GenericController.returnFaild(null);
        }
        return GenericController.returnSuccess(null);
    }
}
