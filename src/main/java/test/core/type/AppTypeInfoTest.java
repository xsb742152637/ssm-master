package test.core.type;

import model.core.type.dao.AppTypeInfoDao;
import model.core.type.entity.AppTypeInfoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;

public class AppTypeInfoTest extends BaseTest {
    @Autowired
    private AppTypeInfoDao dao;

    @Test
    public void findOne() throws Exception {
        String typeId = "a47eec14-17a4-4a09-9349-dc97c8c3c992";
        AppTypeInfoEntity entity = dao.findOne(typeId);
        System.out.println(entity.getTypeName());
    }
}
