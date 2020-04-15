package model.core.treeinfo.entity;

import javax.persistence.*;

@Entity
@Table(name = "core_tree_info")
public class CoreTreeInfoEntity {
    private String treeId;
    private String parentId;
    private String treeName;
    private int treeLeft;
    private int treeRight;
    private int treeType;
    private Boolean canUpdate = Boolean.TRUE;
    private Boolean canDelete = Boolean.TRUE;

    @Id
    @Column(name = "TREE_ID")
    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    @Basic
    @Column(name = "PARENT_ID")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "TREE_NAME")
    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    @Basic
    @Column(name = "TREE_LEFT")
    public int getTreeLeft() {
        return treeLeft;
    }

    public void setTreeLeft(int treeLeft) {
        this.treeLeft = treeLeft;
    }

    @Basic
    @Column(name = "TREE_RIGHT")
    public int getTreeRight() {
        return treeRight;
    }

    public void setTreeRight(int treeRight) {
        this.treeRight = treeRight;
    }

    @Basic
    @Column(name = "TREE_TYPE")
    public int getTreeType() {
        return treeType;
    }

    public void setTreeType(int treeType) {
        this.treeType = treeType;
    }

    @Basic
    @Column(name = "CAN_UPDATE")
    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    @Basic
    @Column(name = "CAN_DELETE")
    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
}
