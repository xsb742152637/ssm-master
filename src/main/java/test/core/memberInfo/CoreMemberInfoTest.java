package test.core.memberInfo;

import model.core.memberInfo.entity.CoreMemberInfoEntity;
import model.core.memberInfo.service.CoreMemberInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;

public class CoreMemberInfoTest extends BaseTest {
    @Autowired
    private CoreMemberInfoService service;

    @Test
    public void findOne() throws Exception {
        String account = "xc";
        String password = "111";
        CoreMemberInfoEntity entity = service.findOne(account,password);
        System.out.println(entity.getMemberName());
    }
}
