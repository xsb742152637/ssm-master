package model.core.menuurl.dao;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;

import java.util.List;

public interface CoreMenuUrlDao {

    List<CoreMenuUrlInfoEntity> findAll();
}
