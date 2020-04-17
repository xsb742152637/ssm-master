package model.core.memberarchives.service;


import model.core.memberarchives.entity.CoreMemberArchivesEntity;

import java.awt.image.BufferedImage;
import java.util.List;

public interface CoreMemberArchivesService {
    /**
     *
     * memberId 成员编号，获取成员头像，如果没有则自动新增
     * text 图片内容文字，如果只传入该参数，则表示临时生成一个图片
     */
    BufferedImage getPhoto(String memberId, String text);

    CoreMemberArchivesEntity findOne(String primaryId);

    void insert(CoreMemberArchivesEntity entity);
    void insert(List<CoreMemberArchivesEntity> entity);
    int update(CoreMemberArchivesEntity entity);
    void delete(String primaryId);
}
