package test.core.menuTree;

import model.core.menuTree.service.CoreMenuTreeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;

import java.util.List;
import java.util.Map;

public class CoreMenuTreeTest extends BaseTest {
    @Autowired
    private CoreMenuTreeService core;

    @Test
    public void findOne() throws Exception {
        List<Map<String,Object>> list = core.getMenuTree();
        System.out.println(list.size() + "---------------");
    }
}
