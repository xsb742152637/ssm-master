package model.core.dicinfo.entity;

import javax.persistence.*;

@Entity
@Table(name = "core_dic_info_detail")
public class CoreDicInfoDetailEntity {
    private String detailId;
    private String dicId;
    private String detailName;
    private String detailCode;
    private String detailValue;
    private int detailLevel;
    private String comment;
    private boolean isValid;

    @Id
    @Column(name = "DETAIL_ID")
    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    @Basic
    @Column(name = "DIC_ID")
    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
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

        CoreDicInfoDetailEntity that = (CoreDicInfoDetailEntity) o;

        if (detailLevel != that.detailLevel) return false;
        if (isValid != that.isValid) return false;
        if (detailId != null ? !detailId.equals(that.detailId) : that.detailId != null) return false;
        if (dicId != null ? !dicId.equals(that.dicId) : that.dicId != null) return false;
        if (detailName != null ? !detailName.equals(that.detailName) : that.detailName != null) return false;
        if (detailCode != null ? !detailCode.equals(that.detailCode) : that.detailCode != null) return false;
        if (detailValue != null ? !detailValue.equals(that.detailValue) : that.detailValue != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = detailId != null ? detailId.hashCode() : 0;
        result = 31 * result + (dicId != null ? dicId.hashCode() : 0);
        result = 31 * result + (detailName != null ? detailName.hashCode() : 0);
        result = 31 * result + (detailCode != null ? detailCode.hashCode() : 0);
        result = 31 * result + (detailValue != null ? detailValue.hashCode() : 0);
        result = 31 * result + detailLevel;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (isValid ? 1 : 0);
        return result;
    }
}
