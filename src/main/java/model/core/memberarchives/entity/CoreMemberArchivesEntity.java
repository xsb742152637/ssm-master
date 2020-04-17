package model.core.memberarchives.entity;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xiucai
 * Date: 2020/4/17 17:43
 */
@Entity
@Table(name = "core_member_archives")
public class CoreMemberArchivesEntity {
    private String memberId;
    private byte[] photo;

    @Id
    @Column(name = "MEMBER_ID")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "PHOTO")
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreMemberArchivesEntity that = (CoreMemberArchivesEntity) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (!Arrays.equals(photo, that.photo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = memberId != null ? memberId.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
