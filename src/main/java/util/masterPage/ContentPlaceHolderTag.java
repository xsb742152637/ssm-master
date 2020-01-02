package util.masterPage;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.PrintWriter;

public class ContentPlaceHolderTag extends BodyTagSupport {
    @Override
    public int doEndTag() throws JspException {

        String writeId = (String) this.pageContext.getRequest().getAttribute(Consts.WRITE_PAGE_ID);

        String attrName = writeId + "___" + this.getId();
        String attrValue = (String) this.pageContext.getRequest().getAttribute(attrName);
        if (StringUtils.isNotBlank(attrValue)) {
            this.pageContext.getRequest().removeAttribute(attrName);
            try {
                this.pageContext.getOut().write(attrValue);
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace(new PrintWriter(this.pageContext.getOut()));
            }
        }
        return EVAL_PAGE;
    }
}
