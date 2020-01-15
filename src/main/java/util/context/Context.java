package util.context;

import model.core.memberInfo.entity.CoreMemberInfoEntity;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;

public class Context {
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    private Context(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public static Context createContext(HttpServletRequest request, HttpServletResponse response){
        return new Context(request,response);
    }

    //-------------------成员相关------------------------------
    private static CoreMemberInfoEntity member = null;//当前登录用户
    private static String MEMBER_SESSION_KEY = "member";
    public static CoreMemberInfoEntity getMember(){
//        if(member == null)
//            member = (CoreMemberInfoEntity) getSession(MEMBER_SESSION_KEY);
        return member;
    }
    public static void setMember(CoreMemberInfoEntity mem){
        member = mem;
//        setSession(MEMBER_SESSION_KEY,member);
    }


    //-------------------菜单相关------------------------------

//    private static CoreMenuTreeInfoEntity app = null;//当前访问页面
//    private static String APP_SESSION_KEY = "app";
//    public static CoreMenuTreeInfoEntity getMenuTree(String code){
////        if(app == null){
////            app = (CoreMenuTreeInfoEntity) getSession(APP_SESSION_KEY);
////        }
//        if(app == null){
//            app = CoreMenuTreeService.getInstance().findOneByCode(code);
//        }if(app == null){
//            CoreMenuTreeInfoEntity entity = new CoreMenuTreeInfoEntity();
//            entity.setTitle("");
//            entity.setMenuId("");
//            entity.setIcon("");
//            entity.setUrlId("");
//            return entity;
//        }else{
////            setSession(APP_SESSION_KEY,app);
//        }
//        return app;
//    }
//    public static void setMenuTree(CoreMenuTreeInfoEntity entity){
//        app = entity;
//    }

    //-------------------session相关------------------------------

    public static Object getSession(String key) {
        if(request != null){
            return request.getSession().getAttribute(key);
        }
        return null;
    }
    public static void setSession(String key,Object o){
        if(request != null){
            request.getSession().setAttribute(key,o);
        }
    }

    public static HttpServletRequest getRequest() {
        return request;
    }

    public static HttpServletResponse getResponse() {
        return response;
    }

    private static boolean isPC(String agent) {

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
    public static boolean isPhone(){
        return !isPC(getRequest().getHeader("user-agent"));
    }


    private static ServletContext _servletContext = null;

    static void setServletContext(ServletContext servletContext) {
        _servletContext = servletContext;
    }

    public static ServletContext getServletContext() {
        return _servletContext;
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

    static void setCharset(String charset) {
        setCharset(Charset.forName(charset));
    }

    public static Charset getCharset() {
        return _charset;
    }

    public static String getRequestPath(){
        return getRequest().getRequestURI();
    }
}
