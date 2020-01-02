package util.masterPage;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class ContentPageTag extends BodyTagSupport {

    @Override
    public void doInitBody() throws JspException {
        this.setId(UUID.randomUUID().toString());
        this.pageContext.getRequest().setAttribute(Consts.WRITE_PAGE_ID, this.getId());
    }
    @Override
    public int doEndTag() throws JspException {
        //将当前页面当成一个参数放到request里面
        this.pageContext.getRequest().setAttribute(Consts.PAGE_ATTRIBUTE, this.bodyContent.getString());
        try {
            //页面跳转到母版页
            this.pageContext.getServletContext().getRequestDispatcher(Consts.INDEX_JSP).include(this.pageContext.getRequest(),this.pageContext.getResponse());
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            e.printStackTrace(new PrintWriter(this.pageContext.getOut()));
        }

        return EVAL_PAGE;//继续处理页面，doEndTag()函数可用   （让服务器继续执行页面 ）
    }

    private String materPageId = null;

    public String getMaterPageId() {
        return materPageId;
    }

    public void setMaterPageId(String materPageId) {
        this.materPageId = materPageId;
    }

    private boolean theme = true;

    public boolean isTheme() {
        return this.theme;
    }

    public void setTheme(boolean theme) {
        this.theme = theme;
    }

}
