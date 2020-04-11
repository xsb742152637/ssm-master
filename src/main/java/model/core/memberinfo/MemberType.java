package model.core.memberinfo;

/**
 * @author xc
 */

public enum MemberType {
    Person(1,"人员"),
    Group(2,"群组");

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

    public static String getNames(){
        String str = "";
        for(MemberType en : MemberType.values()){
            str += "<option value = "+ en.getCode() + " > "+en.getName().toString() +"<option>";
        }
        return str;
    }

}
