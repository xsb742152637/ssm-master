package model.core.treeinfo;

/**
 * @author xc
 */

public enum TreeType {
    MemberInfo(1,"成员树"),
    DiskFile(2,"网盘树");

    private Integer code;
    private String name;

    private TreeType(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode(){return this.code;}

    public String getName(){return this.name;}

    public static TreeType getTreeTypeByCode(Integer code){
        for(TreeType en : TreeType.values()){
            if(en.getCode().intValue() == code){
                return en;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code){
        for(TreeType en : TreeType.values()){
            if(en.getCode().intValue() == code){
                return en.getName().toString();
            }
        }
        return "";
    }

    public static String getNames(){
        String str = "";
        for(TreeType en : TreeType.values()){
            str += "<option value = "+ en.getCode() + " > "+en.getName().toString() +"<option>";
        }
        return str;
    }

}
