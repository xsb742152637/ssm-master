package model.core.menutree.service.impl;

import model.core.menutree.dao.CoreMenuTreeInfoDao;
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
    private CoreMenuTreeInfoDao dao;
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

    @Override
    public int insert(CoreMenuTreeInfoEntity pojo) {
        return dao.insert(pojo);
    }

    @Override
    public int insertSelective(CoreMenuTreeInfoEntity pojo) {
        return dao.insertSelective(pojo);
    }

    @Override
    public int insertList(List<CoreMenuTreeInfoEntity> pojo) {
        return dao.insertList(pojo);
    }

    @Override
    public int update(CoreMenuTreeInfoEntity pojo) {
        return dao.update(pojo);
    }

    /**
     * 	//根据上级，得到子级下一个排序数字
     * @param outlineLevel
     * @return
     */
    @Override
    public Integer getMenuLevelByParLevel(String outlineLevel) {
        Integer n = dao.getMenuLevelByParLevel(outlineLevel);
        if(n == null){
            return 1;
        }
        return n+1;
    }

    @Override
    public CoreMenuTreeInfoEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    /**
     *移动菜单
     * @param treeId
     * @param type(上移)、false(下移)
     */
    @Override
    public void moveTree(String treeId, boolean type) {
        CoreMenuTreeInfoEntity entity = dao.findOne(treeId);
        if(entity == null) {
            return;
        }
        int menuLevel = entity.getMenuLevel();

        if(type){
            menuLevel--;
        }else{
            menuLevel++;
        }
        String outlineLevel = entity.getOutlineLevel();
        if(outlineLevel.split("\\.").length > 1){
            outlineLevel = outlineLevel.substring(0,outlineLevel.lastIndexOf(".")) + "." + menuLevel;
        }else{
            outlineLevel = String.valueOf(menuLevel);
        }

        CoreMenuTreeInfoEntity entity2 = dao.findOneByOutlineLevel(outlineLevel);
        if(entity2 == null) {
            return;
        }

        dao.moveChildren(entity2.getOutlineLevel()+".","temp"+".");
        dao.moveChildren(entity.getOutlineLevel()+".",entity2.getOutlineLevel()+".");
        dao.moveChildren("temp"+".",entity.getOutlineLevel()+".");

        entity2.setMenuLevel(entity.getMenuLevel());
        entity2.setOutlineLevel(entity.getOutlineLevel());

        entity.setMenuLevel(menuLevel);
        entity.setOutlineLevel(outlineLevel);

        dao.update(entity);
        dao.update(entity2);
    }


    @Override
    public int updateAfterDelete(String before, String after) {
        return dao.updateAfterDelete(before,after);
    }

    @Override
    public void delete(String mainId) {
        CoreMenuTreeInfoEntity entity = dao.findOne(mainId);
        String outlineLevel = entity.getOutlineLevel();
        String before = outlineLevel.substring(0,outlineLevel.lastIndexOf(".")+1);
        String after = outlineLevel.substring(outlineLevel.lastIndexOf(".")+1);
        dao.delete(mainId);
        dao.updateAfterDelete(before,after);

    }
}
