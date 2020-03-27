package model.core.menuurl.service;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;

import java.util.List;

public interface CoreMenuUrlService {

    List<CoreMenuUrlInfoEntity> findAll();
}
