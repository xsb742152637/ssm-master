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

    int delete(String mainId);
    int deleteAll();
}
