package model.core.memberinfo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @ClassName CoreMemberInfoEntity
 * @Description TODO
 * @Author
 * @Date 2020/3/30
 * @Version 1.0
 */
@Entity
@Table(name = "core_member_info")
public class CoreMemberInfoEntity  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberId;
    private String memberName;
    private int memberType;
    private String account;
    private String password;
    private Boolean memberState;
    private String treeId;

    @Id
    @Column(name = "MEMBER_ID")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "MEMBER_NAME")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    @Basic
    @Column(name = "MEMBER_TYPE")
    public int getMemberType() { return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    @Basic
    @Column(name = "ACCOUNT")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "MEMBER_STATE")
    public Boolean getMemberState() {
        return memberState;
    }

    public void setMemberState(Boolean memberState) {
        this.memberState = memberState;
    }

    @Basic
    @Column(name = "TREE_ID")
    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

}
