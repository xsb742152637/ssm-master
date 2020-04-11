package util.datamanage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller中的通用方法
 */
public class GenericController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    //将查询的数据转换成字符串返回
    public String getTable(Object list,int count){
        Map<String, Object> table = new HashMap<String, Object>();

        table.put("code", 0);
        table.put("msg", "查询成功");
        table.put("count", count);
        table.put("data", list);
        JSONObject jsonObject = JSONObject.fromObject(table);
        return jsonObject.toString();
    }

    public JSONObject getWXParams(HttpServletRequest request){
        JSONObject result = null;
        StringBuilder sb = new StringBuilder();
        try{
            try (BufferedReader reader = request.getReader();) {
                char[] buff = new char[1024];
                int len;
                while ((len = reader.read(buff)) != -1) {
                    sb.append(buff, 0, len);
                }
                result = JSONObject.fromObject(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //将map转为string
    public String returnStringByMap(Object obj) {
        return JSONObject.fromObject(obj).toString();
    }
    //将list转为string
    public String returnStringByList(Object obj) {
        return JSONArray.fromObject(obj).toString();
    }

    //操作成功的返回
    public String returnSuccess() {
        return returnSuccess();
    }
    public String returnSuccess(String msg) {
        JSONObject re = new JSONObject();
        if(StringUtils.isBlank(msg)){
            msg="操作成功！";
        }
        re.put("error", false);
        re.put("msg", msg);
        return re.toString();
    }

    //操作失败的返回
    public String returnFaild() {
        return returnFaild();
    }
    public String returnFaild(String msg) {
        JSONObject re = new JSONObject();
        if(StringUtils.isBlank(msg)){
            msg="操作失败！";
        }
        re.put("error", true);
        re.put("msg", msg);
        return re.toString();
    }
}
