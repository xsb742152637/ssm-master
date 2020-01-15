package model.core.menuTree.service.impl;

import model.core.menuTree.dao.CoreMenuTreeDao;
import model.core.menuTree.service.CoreMenuTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import util.dataManage.GenericService;

import java.util.*;

@Service
public class CoreMenuTreeServiceImpl extends GenericService implements CoreMenuTreeService {
    @Autowired
    private CoreMenuTreeDao dao;

    //@Cacheable(value = "cacheManager", key = "'CoreMenuTreeServiceImpl.getMenuTree'")
    @Override
    public List<Map<String,Object>> getMenuTree() {
        //1. 得到全部显示的菜单树的列表
        List<Map<String,Object>> list = convertList(dao.getMenuTree());
        //2. 将列表转换为树形结构

        //2.1 将所有父级菜单放到此map中,方便后续菜单快速找到爹
        Map<String,Map<String,Object>> mapP = new HashMap<>();
        //2.2 最终返回的树形结构
        List<Map<String,Object>> listR = new ArrayList<>();
        for(Map<String,Object> map : list){
            map.put("text",map.get("title"));
            String outlineLevel = map.get("outlineLevel").toString();
            String parentLevel = outlineLevel.contains(".") ? outlineLevel.substring(0,outlineLevel.lastIndexOf(".")) : "";

            //map.remove("menuLevel");
            //map.remove("outlineLevel");
            //map.remove("code");
            //map.remove("menuId");
            //map.remove("title");
            //map.remove("url");
            //map.remove("urlId");
            Map<String,Object> mp = mapP.get(parentLevel);
            if(mp == null){//找不到爹就自己当祖宗
                listR.add(map);
            }else{
                List<Map<String,Object>> listC = mp.get("children") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String,Object>>) mp.get("children");
                listC.add(map);
                mp.put("children",listC);
            }
            //自己很有可能有儿子,所有先到当爹的列表中等着儿子来找.
            mapP.put(outlineLevel,map);
        }

        //listR.addAll((List<Map<String,Object>>) listR.get(0).get("children"));
        listR.get(0).put("state","open");
        return listR;
    }

}
