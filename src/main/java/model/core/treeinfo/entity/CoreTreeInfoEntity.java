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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreTreeInfoEntity that = (CoreTreeInfoEntity) o;

        if (treeLeft != that.treeLeft) return false;
        if (treeRight != that.treeRight) return false;
        if (treeType != that.treeType) return false;
        if (treeId != null ? !treeId.equals(that.treeId) : that.treeId != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (treeName != null ? !treeName.equals(that.treeName) : that.treeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = treeId != null ? treeId.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (treeName != null ? treeName.hashCode() : 0);
        result = 31 * result + treeLeft;
        result = 31 * result + treeRight;
        result = 31 * result + treeType;
        return result;
    }
}
