package model.core.dictionary.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ClassName CoreDicInfoEntity
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 13:48
 * @Version 1.0
 */
@Entity
@Table(name = "core_dic_info")
public class CoreDicInfoEntity {
    private String typeId;
    private String typeName;
    private String typeCode;
    private Timestamp sysTime;
    private String memberId;

    @Id
    @Column(name = "TYPE_ID")
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "TYPE_NAME")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "TYPE_CODE")
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "SYS_TIME")
    public Timestamp getSysTime() {
        return sysTime;
    }

    public void setSysTime(Timestamp sysTime) {
        this.sysTime = sysTime;
    }

    @Basic
    @Column(name = "MEMBER_ID")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreDicInfoEntity that = (CoreDicInfoEntity) o;

        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (typeCode != null ? !typeCode.equals(that.typeCode) : that.typeCode != null) return false;
        if (sysTime != null ? !sysTime.equals(that.sysTime) : that.sysTime != null) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId != null ? typeId.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (typeCode != null ? typeCode.hashCode() : 0);
        result = 31 * result + (sysTime != null ? sysTime.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }
}
