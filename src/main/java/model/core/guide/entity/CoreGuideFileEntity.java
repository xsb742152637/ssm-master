package model.core.guide.entity;

import javax.persistence.*;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xie
 * Date: 2020/4/7 11:45
 */
@Entity
@Table(name = "core_guide_file")
public class CoreGuideFileEntity {
    private String guideId;
    private String document;

    @Id
    @Column(name = "GUIDE_ID")
    public String getGuideId() {
        return guideId;
    }

    public void setGuideId(String guideId) {
        this.guideId = guideId;
    }

    @Basic
    @Column(name = "DOCUMENT")
    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreGuideFileEntity that = (CoreGuideFileEntity) o;

        if (guideId != null ? !guideId.equals(that.guideId) : that.guideId != null) return false;
        if (document != null ? !document.equals(that.document) : that.document != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guideId != null ? guideId.hashCode() : 0;
        result = 31 * result + (document != null ? document.hashCode() : 0);
        return result;
    }
}
