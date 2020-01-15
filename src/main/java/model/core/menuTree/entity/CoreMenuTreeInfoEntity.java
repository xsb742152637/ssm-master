package model.core.menuTree.entity;

import javax.persistence.*;

/**
 * Created by xiucai on 2017/10/24.
 */
@Entity
@Table(name = "core_menu_tree_info")
public class CoreMenuTreeInfoEntity {
    private String menuId;
    private Integer menuLevel;
    private String outlineLevel;
    private String title;
    private String urlId;
    private String icon;
    private Boolean type;
    private Boolean isShow;

    @Id
    @Column(name = "MENU_ID")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "MENU_LEVEL")
    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    @Basic
    @Column(name = "OUTLINE_LEVEL")
    public String getOutlineLevel() {
        return outlineLevel;
    }

    public void setOutlineLevel(String outlineLevel) {
        this.outlineLevel = outlineLevel;
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
    @Column(name = "URL_ID")
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Basic
    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "TYPE")
    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Basic
    @Column(name = "IS_SHOW")
    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }
}
