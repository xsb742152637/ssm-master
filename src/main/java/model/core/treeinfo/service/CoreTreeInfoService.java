package model.core.treeinfo.service;

import model.core.treeinfo.TreeType;

import java.util.List;
import java.util.Map;

public interface CoreTreeInfoService {
    List<Map<String,Object>> getMainInfo(String treeType);
    List<Map<String,Object>> getMainInfo(String treeType, String parentId,int openLevel);

    String save(TreeType treeType, String parentId, String treeId, String treeName);

    void move(String mainId, Boolean moveOn);

    void delete(String mainId);
}
