package model.core.menutree.service.impl;

import model.core.menutree.dao.CoreMenuTreeDao;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import model.core.menutree.service.CoreMenuTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoreMenuTreeServiceImpl extends GenericService implements CoreMenuTreeService {
    @Autowired
    private CoreMenuTreeDao dao;
    /**
     * 获取实例
     */
    public static CoreMenuTreeServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreMenuTreeServiceImpl.class);
    }


    //@Cacheable(value = "cacheManager", key = "'CoreMenuTreeServiceImpl.getMenuTree'")
    @Override
    public List<Map<String,Object>> getMenuTree(Boolean isTop,Boolean isShow) {
        //1. 得到全部显示的菜单树的列表
        List<Map<String,Object>> list = convertList(dao.getMenuTree(isShow));
        //2. 将列表转换为树形结构

        //2.1 将所有父级菜单放到此map中,方便后续菜单快速找到爹
        Map<String,Map<String,Object>> mapP = new HashMap<>();
        //2.2 最终返回的树形结构
        List<Map<String,Object>> listR = new ArrayList<>();
        for(Map<String,Object> map : list){
            if(!isTop && "root".equals(map.get("menuId").toString())) {
                continue;
            }
            if("root".equals(map.get("menuId").toString())) {
                //根节点默认展开
                map.put("spread",true);
            }
            map.put("id",map.get("menuId"));
            String outlineLevel = map.get("outlineLevel").toString();
            String parentLevel = outlineLevel.contains(".") ? outlineLevel.substring(0,outlineLevel.lastIndexOf(".")) : "";

            Map<String,Object> mp = mapP.get(parentLevel);
            //找不到爹就自己当祖宗
            if(mp == null){
                listR.add(map);
            }else{
                List<Map<String,Object>> listC = mp.get("children") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String,Object>>) mp.get("children");
                listC.add(map);
                mp.put("children",listC);
            }
            //自己很有可能有儿子,所有先到当爹的列表中等着儿子来找.
            mapP.put(outlineLevel,map);
        }

        listR.get(0).put("state","open");
        return listR;
    }

    @Override
    public CoreMenuTreeInfoEntity findOneByCode(String code){
        List<CoreMenuTreeInfoEntity> list = dao.findOneByCode(code);
        if(list != null && list.size() > 0){
            if(list.size() > 1) {
                System.out.println("菜单code："+code+"重复了。");
            }
            return list.get(0);
        }else{
            System.out.println("菜单code："+code+"没有找到。");
            return null;
        }
    }
}