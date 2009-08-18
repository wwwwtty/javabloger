package com.exam.taglib.table;      
     
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;
import org.apache.struts2.views.jsp.ui.AbstractUITag;      
import org.apache.struts2.components.Component;      
import com.opensymphony.xwork2.util.ValueStack;      
     
import javax.servlet.http.HttpServletRequest;      
import javax.servlet.http.HttpServletResponse;      
import javax.servlet.jsp.JspException;
     
/**    
 * struts2 版分页标签    
 */     
public class TbodyTag extends AbstractClosingTag {      
          
    private static final long serialVersionUID = 719669934024053141L;      
          
    protected String statusAttr;
    protected String value;      
     
    protected void populateParams() {      
     
        super.populateParams();    
        Tbody tbody = (Tbody) component;      
        tbody.setValue(value);
        tbody.setStatusAttr(statusAttr);        
     
    }      
     
    @Override     
    public Component getBean(ValueStack stack, HttpServletRequest request,      
            HttpServletResponse response) {      
        return new Tbody(stack, request, response);      
    }
    
    @Override
    public int doAfterBody() throws JspException {
        boolean again = component.end(pageContext.getOut(), getBody());

        if (again) {
            return EVAL_BODY_AGAIN;
        } else {
            if (bodyContent != null) {
                try {
                    bodyContent.writeOut(bodyContent.getEnclosingWriter());
                } catch (Exception e) {
                    throw new JspException(e.getMessage());
                }
            }
            return SKIP_BODY;
        }
    }

	public String getStatusAttr() {
		return statusAttr;
	}

	public void setStatusAttr(String statusAttr) {
		this.statusAttr = statusAttr;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}          
}   