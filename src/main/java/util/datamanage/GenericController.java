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
    public static String getTable(List<Map<String,Object>> list,int count,int page,int rows){
        Map<String, Object> table = new HashMap<String, Object>();
        int total = count / rows;
        if (total == 0) {
            total = 1;
        } else {
            if ((count % rows) != 0) {
                total++;
            }
        }
        table.put("page", page);
        table.put("records", count);
        table.put("total", total);
        table.put("rows", list);
        JSONObject jsonObject = JSONObject.fromObject(table);
        return jsonObject.toString();
    }

    public static JSONObject getWXParams(HttpServletRequest request){
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

    public static String returnStringByMap(Object obj) {
        return JSONObject.fromObject(obj).toString();
    }
    public static String returnStringByList(Object obj) {
        return JSONArray.fromObject(obj).toString();
    }

    //操作成功的返回
    public static String returnSuccess(String msg) {
        JSONObject re = new JSONObject();
        if(StringUtils.isBlank(msg)){
            msg="操作成功！";
        }
        re.put("error", 0);
        re.put("msg", msg);
        return re.toString();
    }

    //操作失败的返回
    public static String returnFaild(String msg) {
        JSONObject re = new JSONObject();
        if(StringUtils.isBlank(msg)){
            msg="操作失败！";
        }
        re.put("error", 1);
        re.put("msg", msg);
        return re.toString();
    }
}
