package util.masterpage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class ContentTag extends BodyTagSupport {
    @Override
    public int doEndTag() throws JspException {

        String writeId = (String) this.pageContext.getRequest().getAttribute(Consts.WRITE_PAGE_ID);

        try {
            if(this.bodyContent != null){
                String content = this.bodyContent.getString();
                this.pageContext.getRequest().setAttribute(writeId + "___" + this.getContentPlaceHolderId(), content);
                //清空当前标签的内容
                this.bodyContent.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;//default
    }
    private String contentPlaceHolderId;

    public String getContentPlaceHolderId() {
        return contentPlaceHolderId;
    }

    public void setContentPlaceHolderId(String contentPlaceHolderId) {
        this.contentPlaceHolderId = contentPlaceHolderId;
    }


}
