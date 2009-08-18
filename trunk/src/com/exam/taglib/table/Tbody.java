package com.exam.taglib.table;      
     
import java.io.Writer;
import java.util.Iterator;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.components.UIBean;      
import org.apache.struts2.util.MakeIterator;
import org.apache.struts2.views.annotations.StrutsTag;      
import org.apache.struts2.views.annotations.StrutsTagAttribute;      
import org.apache.struts2.views.jsp.IteratorStatus;

import com.opensymphony.xwork2.util.ValueStack;      
     
import javax.servlet.http.HttpServletRequest;      
import javax.servlet.http.HttpServletResponse;      
     
/**    
 * struts2版的分页标签    
 *     
 */     
     
@StrutsTag(name = "pager", tldTagClass = "org.icim.pager.struts2.PagerTag", description = "struts2 pager by ithink")      
public class Tbody extends ClosingUIBean {      
     
    final public static String TEMPLATE = "tbody-close";  
    final public static String TEMPLATE_OPEN = "tbody"; 
     
    protected Iterator iterator;
    protected IteratorStatus status;
    protected Object oldStatus;
    protected IteratorStatus.StatusState statusState;
    protected String statusAttr;
    protected String value;     
     
    public Tbody(ValueStack stack, HttpServletRequest request,      
            HttpServletResponse response) {      
        super(stack, request, response);      
    }      
     
    /**    
     * 用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件。    
     */     
    @Override     
    public String getDefaultTemplate() {      
        return TEMPLATE;      
    }      
     
    /**    
     * 设置UIBean的属性，一般Tag中有几个这样的属性，这里就有几个 StrutsTagAttribute注解，说明该属性是int类型，这一步很重要    
     *     
     * @param totalPage    
     */     
     
   
    // TODO 直接页面跳转      
    // 这里的directJumpType默认值为none， 可选值为 'select', 'goto'      
    @StrutsTagAttribute(description = "show type of direct jump type. such as select,textbox which can lead going to a page directly", type = "String", defaultValue = "none")      
    public void setStatusAttr(String statusAttr) {      
        this.statusAttr = statusAttr;      
    }  
    
 // 这里的directJumpType默认值为none， 可选值为 'select', 'goto'      
    @StrutsTagAttribute(description = "show type of direct jump type. such as select,textbox which can lead going to a page directly", type = "String", defaultValue = "none")      
    public void setValue(String value) {      
        this.value = value;      
    }  
     
    public boolean start(Writer writer) {
        //Create an iterator status if the status attribute was set.
        if (statusAttr != null) {
            statusState = new IteratorStatus.StatusState();
            status = new IteratorStatus(statusState);
        }

        ValueStack stack = getStack();

        if (value == null) {
            value = "top";
        }
        iterator = MakeIterator.convert(parameters.get(Table.TABLE_LIST));

        // get the first
        if ((iterator != null) && iterator.hasNext()) {
            Object currentValue = iterator.next();
            stack.push(currentValue);

            String var = getVar();

            if ((var != null) && (currentValue != null)) {
                //pageContext.setAttribute(id, currentValue);
                //pageContext.setAttribute(id, currentValue, PageContext.REQUEST_SCOPE);
                putInContext(currentValue);
            }

            // Status object
            if (statusAttr != null) {
                statusState.setLast(!iterator.hasNext());
                oldStatus = stack.getContext().get(statusAttr);
                stack.getContext().put(statusAttr, status);
            }

            return true;
        } else {
            super.end(writer, "");
            return false;
        }
    }

    public boolean end(Writer writer, String body) {
        ValueStack stack = getStack();
        if (iterator != null) {
            stack.pop();
        }

        if (iterator!=null && iterator.hasNext()) {
            Object currentValue = iterator.next();
            stack.push(currentValue);

            putInContext(currentValue);

            // Update status
            if (status != null) {
                statusState.next(); // Increase counter
                statusState.setLast(!iterator.hasNext());
            }

            return true;
        } else {
            // Reset status object in case someone else uses the same name in another iterator tag instance
            if (status != null) {
                if (oldStatus == null) {
                    stack.getContext().put(statusAttr, null);
                } else {
                    stack.getContext().put(statusAttr, oldStatus);
                }
            }
            super.end(writer, "");
            return false;
        }
    }
 
    /*
     * ////////////////////////////////////////////////////////
     */
    
    protected String var;
    
    protected void putInContext(Object value) {
        if (var != null && var.length() > 0) {
            stack.getContext().put(var, value);
        }
    }
    
    @StrutsTagAttribute(description="Name used to reference the value pushed into the Value Stack")
    public void setVar(String var) {
        if (var != null) {
            this.var = findString(var);
        }
    }
    
    /**
     * To keep backward compatibility 
     * TODO remove after 2.1
     */
    @StrutsTagAttribute(description="Deprecated. Use 'var' instead")
    public void setId(String id) {
        setVar(id);
    }
    
    protected String getVar() {
        return this.var;
    }

	@Override
	public String getDefaultOpenTemplate() {
		return this.TEMPLATE_OPEN;
	}
}    