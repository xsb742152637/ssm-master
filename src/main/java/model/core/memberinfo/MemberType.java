package model.core.memberinfo;

/**
 * @author xc
 */

public enum MemberType {
    Dept(1,"部门"),
    Post(2,"岗位"),
    Person(3,"人员");

    private Integer code;
    private String name;

    private MemberType(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode(){return this.code;}

    public String getName(){return this.name;}

    public static String getNameByCode(Integer code){
        for(MemberType en : MemberType.values()){
            if(en.getCode().intValue() == code){
                return en.getName().toString();
            }
        }
        return "";
    }

}
