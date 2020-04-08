package model.core.dicinfo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "core_dic_info")
public class CoreDicInfoEntity {
    private String dicId;
    private String dicName;
    private String dicCode;
    private String dicDes;
    private Timestamp sysTime;
    private String memberId;

    @Id
    @Column(name = "DIC_ID")
    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    @Basic
    @Column(name = "DIC_NAME")
    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    @Basic
    @Column(name = "DIC_CODE")
    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    @Basic
    @Column(name = "DIC_DES")
    public String getDicDes() {
        return dicDes;
    }

    public void setDicDes(String dicDes) {
        this.dicDes = dicDes;
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

        if (dicId != null ? !dicId.equals(that.dicId) : that.dicId != null) return false;
        if (dicName != null ? !dicName.equals(that.dicName) : that.dicName != null) return false;
        if (dicCode != null ? !dicCode.equals(that.dicCode) : that.dicCode != null) return false;
        if (dicDes != null ? !dicDes.equals(that.dicDes) : that.dicDes != null) return false;
        if (sysTime != null ? !sysTime.equals(that.sysTime) : that.sysTime != null) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dicId != null ? dicId.hashCode() : 0;
        result = 31 * result + (dicName != null ? dicName.hashCode() : 0);
        result = 31 * result + (dicCode != null ? dicCode.hashCode() : 0);
        result = 31 * result + (dicDes != null ? dicDes.hashCode() : 0);
        result = 31 * result + (sysTime != null ? sysTime.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }
}
