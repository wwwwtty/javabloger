package com.exam.taglib.table;      
     
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
     
/**    
 * struts2 版分页标签    
 */     
public class TableTag extends AbstractClosingTag {      
   
	private Object list;
	
    private static final long serialVersionUID = 719669934024053141L;      
          
     
     
    protected void populateParams() { 
        super.populateParams(); 
        Table table = (Table) component;       
        table.setList(list);   
    }      
     
    @Override     
    public Component getBean(ValueStack stack, HttpServletRequest request,      
            HttpServletResponse response) {      
        return new Table(stack, request, response);      
    }  
    
//    @Override
//    public int doAfterBody() throws JspException {
//        boolean again = component.end(pageContext.getOut(), getBody());
//
//        if (again) {
//            return EVAL_BODY_AGAIN;
//        } else {
//            if (bodyContent != null) {
//                try {
//                    bodyContent.writeOut(bodyContent.getEnclosingWriter());
//                } catch (Exception e) {
//                    throw new JspException(e.getMessage());
//                }
//            }
//            return SKIP_BODY;
//        }
//    }

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}
}   