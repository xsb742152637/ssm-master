package util.context;

import model.core.guide.service.core.Member;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import model.core.menutree.service.impl.CoreMenuTreeServiceImpl;
import model.core.treeinfo.service.impl.CoreTreeInfoServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class Context {
    public final static String SUCCESS_URL = "/theme/pc/index.jsp?menuCode=main";
    private HttpServletRequest request;
    private HttpServletResponse response;

    private Context(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    //
    private static final ThreadLocal<Context> ACTION_CONTEXT = new ThreadLocal<Context>();
    public static Context getCurrent() {
        return (Context) ACTION_CONTEXT.get();
    }

    public static Context createContext(HttpServletRequest request, HttpServletResponse response) {
        Context c = new Context(request, response);
        ACTION_CONTEXT.set(c);
        return c;
    }

    //-------------------成员相关------------------------------
    private CoreMemberInfoEntity member = null;
    public CoreMemberInfoEntity getMember(){
        if(member == null){
            if (SecurityUtils.getSubject().isAuthenticated()) {
                try {
                    this.member = (CoreMemberInfoEntity) SecurityUtils.getSubject().getPrincipal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this.member;
    }

    //-------------------菜单相关------------------------------

    private CoreMenuTreeInfoEntity menu = null;
    public CoreMenuTreeInfoEntity getMenuTree(){
        return this.menu;
    }
    public static CoreMenuTreeInfoEntity getMenuTree(String code){
        return getMenuTree(code,true);
    }
    public static CoreMenuTreeInfoEntity getMenuTree(String code,boolean isCurrent){
        CoreMenuTreeInfoEntity menu = null;
        if(menu == null){
            menu = CoreMenuTreeServiceImpl.getInstance().findOneByCode(code);
        }if(menu == null){
            CoreMenuTreeInfoEntity entity = new CoreMenuTreeInfoEntity();
            entity.setTitle("");
            entity.setMenuId("");
            entity.setIcon("");
            entity.setUrlId("");
            return entity;
        }
        if(isCurrent) {
            Context.getCurrent().setMenuTree(menu);
        }
        return menu;
    }
    public void setMenuTree(CoreMenuTreeInfoEntity menu){
        this.menu = menu;
    }

    //-------------------session相关------------------------------

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获得当前上下文的编号，实际为用户会话的编号。
     *
     * @return
     */
    public UUID getSessionId() {
        return UUID.fromString(SecurityUtils.getSubject().getSession().getId().toString());
        //return this.getSession().getId();
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public boolean isPhone(){
        return !isPC(getRequest().getHeader("user-agent"));
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

    static void setCharset(String charset) {
        setCharset(Charset.forName(charset));
    }

    public static Charset getCharset() {
        return _charset;
    }

    public String getRequestPath(){
        return getRequest().getRequestURI();
    }

    public boolean isAdmini(){
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
