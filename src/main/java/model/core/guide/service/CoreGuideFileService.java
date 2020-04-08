package model.core.guide.service;

import model.core.guide.entity.CoreGuideFileEntity;
import org.dom4j.Document;

import java.util.List;

public interface CoreGuideFileService {
    String findOne(String mainId);
    List<CoreGuideFileEntity> findAll();

    int insert(CoreGuideFileEntity entity);

    CoreGuideFileEntity insert(String projectId, String str);
    CoreGuideFileEntity insert(String projectId, Document d);

    int update(CoreGuideFileEntity entity);
    int update(List<CoreGuideFileEntity> list);
    CoreGuideFileEntity update(String projectId, String str);

    int deleteAll();
    int delete(String mainId);
    void deleteMenu(String menuId);

    String createMemberXml(String sourceType,String memberId,String memberName);
    void addMenu(String parId,String menuId,String menuName);
}
