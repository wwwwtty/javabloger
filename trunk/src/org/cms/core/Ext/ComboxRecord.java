package org.cms.core.Ext;

/**Ext下拉列表框
 * @author heshencao
 *
 */
public class ComboxRecord {


	private String name;
	private String tip;
	private String value;
	
	public ComboxRecord(String name, String tip, String value) {
		super();
		this.name = name;
		this.tip = tip;
		this.value = value;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
