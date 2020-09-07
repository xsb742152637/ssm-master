package util.context;

import model.core.guide.service.core.Member;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import model.core.menutree.service.impl.CoreMenuTreeServiceImpl;
import model.core.treeinfo.service.impl.CoreTreeInfoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.dom4j.Element;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class Context {
    public final static String SUCCESS_URL = "/theme/pc/index.jsp?menuCode=main";

    public static Subject getCurrent() {
        return SecurityUtils.getSubject();
    }

    //-------------------成员相关------------------------------
    public static CoreMemberInfoEntity getMember(){
        if (getCurrent().isAuthenticated()) {
            return (CoreMemberInfoEntity) getCurrent().getPrincipal();
        }
        return null;
    }

    //-------------------菜单相关------------------------------

    public static String getMenuCode(){
        return (String) getSession().getAttribute("menuCode");
    }

    public static CoreMenuTreeInfoEntity getMenuTree(){
        return (CoreMenuTreeInfoEntity) getSession().getAttribute("menu");
    }

    public static CoreMenuTreeInfoEntity setMenuTree(String code){
        return setMenuTree(code,true);
    }
    public static CoreMenuTreeInfoEntity setMenuTree(String code,boolean isCurrent){
        Object obj = getSession().getAttribute("menu_" + code);
        CoreMenuTreeInfoEntity menu = null;
        if(obj == null){
            if(StringUtils.isNotBlank(code)){
                menu = CoreMenuTreeServiceImpl.getInstance().findOneByCode(code);
            }
        }else{
            menu = (CoreMenuTreeInfoEntity) obj;
        }
        if(menu == null){
            CoreMenuTreeInfoEntity entity = new CoreMenuTreeInfoEntity();
            entity.setTitle("");
            entity.setMenuId("");
            entity.setIcon("");
            entity.setUrlId("");
            return entity;
        }

        if(isCurrent) {
            getSession().setAttribute("menu", menu);
            getSession().setAttribute("menuCode", code);
        }
        return menu;
    }

    //-------------------session相关------------------------------

    public static Session getSession() {
        return getCurrent().getSession();
    }

    /**
     * 获得当前上下文的编号，实际为用户会话的编号。
     *
     * @return
     */
    public static UUID getSessionId() {
        return UUID.fromString(getSession().getId().toString());
        //return this.getSession().getId();
    }

    private boolean isPC(String agent) {

        String[] keywords = new String[]{
                "Windows NT",
                "Macintosh"
        };


        for (int i = 0, j = keywords.length; i < j; i++) {
            if (agent.indexOf(keywords[i]) >= 0) {
                return true;
            }
        }
        return false;

    }

    private static Charset _charset = null;

    static void setCharset(Charset charset) {
        _charset = charset;

        //为 Tomcat 设置缺省的 Charset;
        Class<?> tomcat = null;
        try {
            tomcat = Class.forName("org.apache.tomcat.util.http.Parameters");
        } catch (ClassNotFoundException ignore) {
        }
        if (tomcat != null) {
            //当前 Web 容器是 Tomcat
            Field field;
            Field modifiersField;
            try {
                field = tomcat.getDeclaredField("DEFAULT_ENCODING");
                field.setAccessible(true);
                modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                //System.out.println(Modifier.toString(field.getModifiers()));
                field.set(null, _charset.name());
                //System.out.println("DEFAULT_ENCODING=" + field.get(tomcat));
            } catch (NoSuchFieldException | IllegalAccessException ignore) {
            }
            try {
                field = tomcat.getDeclaredField("DEFAULT_CHARSET");
                field.setAccessible(true);
                modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                //System.out.println(Modifier.toString(field.getModifiers()));
                field.set(null, _charset);
                //System.out.println("DEFAULT_CHARSET=" + field.get(tomcat));
            } catch (NoSuchFieldException | IllegalAccessException ignore) {
            }

        }
    }

    public static void setCharset(String charset) {
        setCharset(Charset.forName(charset));
    }

    public static Charset getCharset() {
        return _charset;
    }

    public static boolean isAdmini(){
        CoreMemberInfoEntity member = getMember();
        if(member != null){
            try{
                Member memberEntity = new Member();
                List<Element> list = memberEntity.getAncestor(memberEntity.getMemItem(member.getMemberId()));
                for(Element mem : list){
                    if(CoreTreeInfoServiceImpl.ADMINI_GROUP_ID.equals(memberEntity.getIdByItem(mem))){
                        return true;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
