package model.core.guide;

public enum GuideType {
    Read("r","查看全部"),
    Update("u","编辑权限"),
    ReadSelf("s","查看自己");

    private String code;
    private String name;

    private GuideType(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
