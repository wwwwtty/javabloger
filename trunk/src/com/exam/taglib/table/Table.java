package com.exam.taglib.table;      
     
import java.io.Writer;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.components.Component;
import org.apache.struts2.util.MakeIterator;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
     
/**    
 * struts2版的分页标签    
 *     
 */     
     
@StrutsTag(name = "pager", tldTagClass = "org.icim.pager.struts2.PagerTag", description = "struts2 pager by ithink")      
public class Table extends ClosingUIBean { 
	public static final String TABLE_LIST="list";
	
    final public static String TEMPLATE = "table-close";      
    final public static String TEMPLATE_OPEN = "table"; 
    
    private Object list;
    
    public Table(ValueStack stack, HttpServletRequest request,      
            HttpServletResponse response) {      
        super(stack,request,response);      
    }     

    @Override     
    protected void evaluateExtraParams() {      
        super.evaluateExtraParams();      
        // findValue()方法本身已对OGNL进行了处理      
     
        
        if (list == null) {
            list = parameters.get("list");
        }

        Object value=null;
        if (list instanceof String) {
            value =findValue((String) list);
        } else if (list instanceof Collection) {
            value = list;
        } else if (MakeIterator.isIterable(list)) {
            value = MakeIterator.convert(list);
        }
        if (value == null) {
//            if (throwExceptionOnNullValueAttribute) {
        	if(true){
                // will throw an exception if not found
                value = findValue((list == null) ? (String) list : list.toString(), "list",
                    "The requested list key '" + list + "' could not be resolved as a collection/array/map/enumeration/iterator type. " +
                    "Example: people or people.{name}");
            }
            else {
                // ww-1010, allows value with null value to be compatible with ww
                // 2.1.7 behaviour
                value = findValue((list == null)?(String) list:list.toString());
            }
        }

        if (value instanceof Collection) {
            addParameter("list", value);
        } else {
            addParameter("list", MakeIterator.convert(value));
        }
     
    }    
     
    /**    
     * 设置UIBean的属性，一般Tag中有几个这样的属性，这里就有几个 StrutsTagAttribute注解，说明该属性是int类型，这一步很重要    
     *     
     * @param totalPage    
     */  
    
    public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}
	/**    
     * 用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件。    
     */ 
	@Override
	public String getDefaultOpenTemplate() {
		return this.TEMPLATE_OPEN; 
	}
	@Override
	protected String getDefaultTemplate() {
		return this.TEMPLATE;
	}   
}    