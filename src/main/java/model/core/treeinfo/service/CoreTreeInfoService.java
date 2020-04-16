package model.core.treeinfo.service;

import model.core.treeinfo.TreeType;
import model.core.treeinfo.entity.CoreTreeInfoEntity;

import java.util.List;
import java.util.Map;

public interface CoreTreeInfoService {
    void intoTrees();

    List<Map<String,Object>> getMainInfo(String treeType);
    List<Map<String,Object>> getMainInfo(String treeType, String parentId,int openLevel);

    String save(CoreTreeInfoEntity entity);

    void move(String primaryId, Boolean moveOn);

    void delete(String primaryId);
}
