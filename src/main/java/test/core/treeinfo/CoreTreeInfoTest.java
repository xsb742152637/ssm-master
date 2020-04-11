package test.core.treeinfo;

import model.core.menutree.service.CoreMenuTreeService;
import model.core.treeinfo.TreeType;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreTreeInfoTest extends BaseTest {
    @Autowired
    private CoreTreeInfoService core;

    @Test
    public void getMainInfo() throws Exception {
        //List<Map<String,Object>> list = core.getMenuTree();
        //System.out.println(list.size() + "---------------");
        String treeType = "1";
        List<Map<String,Object>> list = core.getMainInfo(treeType);

        System.out.println("\n\n-------------------------------------------------------");
        while (list != null && list.size() > 0){
            List<Map<String,Object>> list1 = new ArrayList<>();
            for(Map<String,Object> map : list){
                System.out.println(map.get("title"));
                if(map.get("children") != null){
                    list1.addAll((List<Map<String,Object>>) map.get("children"));
                }
            }
            list = list1;
        }
        System.out.println("-------------------------------------------------------\n\n");
    }

    @Test
    public void save() throws Exception {
        String treeType = "1";
        String parentId = "61771a0b-e8a3-4087-b71a-7cafe467cd39";
        String treeId = "cad28b8f-7451-4173-bfeb-29d410d38b43";
        String treeName = "三";

        core.save(TreeType.getTreeTypeByCode(Integer.parseInt(treeType)),parentId,treeId,treeName);
    }

    @Test
    public void move() throws Exception {
        String treeType = "1";
        String parentId = "61771a0b-e8a3-4087-b71a-7cafe467cd39";
        String treeId = "cad28b8f-7451-4173-bfeb-29d410d38b43";
        String treeName = "三";

        core.move(treeId,true);
    }
}
