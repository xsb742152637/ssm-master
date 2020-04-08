package model.core.dictionary.entity;

import javax.persistence.*;

/**
 * @ClassName CoreDicDetailEntity
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 13:48
 * @Version 1.0
 */
@Entity
@Table(name = "core_dic_detail")
public class CoreDicDetailEntity {
    private String typeDetailId;
    private String typeId;
    private String detailName;
    private String detailCode;
    private String detailValue;
    private int detailLevel;
    private String comment;
    private boolean isValid;

    @Id
    @Column(name = "TYPE_DETAIL_ID")
    public String getTypeDetailId() {
        return typeDetailId;
    }

    public void setTypeDetailId(String typeDetailId) {
        this.typeDetailId = typeDetailId;
    }

    @Basic
    @Column(name = "TYPE_ID")
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "DETAIL_NAME")
    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    @Basic
    @Column(name = "DETAIL_CODE")
    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    @Basic
    @Column(name = "DETAIL_VALUE")
    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    @Basic
    @Column(name = "DETAIL_LEVEL")
    public int getDetailLevel() {
        return detailLevel;
    }

    public void setDetailLevel(int detailLevel) {
        this.detailLevel = detailLevel;
    }

    @Basic
    @Column(name = "COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "IS_VALID")
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreDicDetailEntity that = (CoreDicDetailEntity) o;

        if (detailLevel != that.detailLevel) return false;
        if (isValid != that.isValid) return false;
        if (typeDetailId != null ? !typeDetailId.equals(that.typeDetailId) : that.typeDetailId != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (detailName != null ? !detailName.equals(that.detailName) : that.detailName != null) return false;
        if (detailCode != null ? !detailCode.equals(that.detailCode) : that.detailCode != null) return false;
        if (detailValue != null ? !detailValue.equals(that.detailValue) : that.detailValue != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeDetailId != null ? typeDetailId.hashCode() : 0;
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (detailName != null ? detailName.hashCode() : 0);
        result = 31 * result + (detailCode != null ? detailCode.hashCode() : 0);
        result = 31 * result + (detailValue != null ? detailValue.hashCode() : 0);
        result = 31 * result + detailLevel;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (isValid ? 1 : 0);
        return result;
    }
}
