package model.core.menuurl.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "core_menu_url_info")
public class CoreMenuUrlInfoEntity {
    private String urlId;
    private String title;
    private String code;
    private String url;
    private String parameter;
    private Timestamp sysTime;

    @Id
    @Column(name = "URL_ID")
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "PARAMETER")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Basic
    @Column(name = "SYS_TIME")
    public Timestamp getSysTime() {
        return sysTime;
    }

    public void setSysTime(Timestamp sysTime) {
        this.sysTime = sysTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreMenuUrlInfoEntity that = (CoreMenuUrlInfoEntity) o;

        if (urlId != null ? !urlId.equals(that.urlId) : that.urlId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (parameter != null ? !parameter.equals(that.parameter) : that.parameter != null) return false;
        if (sysTime != null ? !sysTime.equals(that.sysTime) : that.sysTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = urlId != null ? urlId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + (sysTime != null ? sysTime.hashCode() : 0);
        return result;
    }
}
