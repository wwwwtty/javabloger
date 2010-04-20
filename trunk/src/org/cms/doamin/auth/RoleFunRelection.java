package org.cms.doamin.auth;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AuthRoleFunRelection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_role_fun_relection", catalog = "tinycms")
public class RoleFunRelection implements java.io.Serializable {

	// Fields
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	@OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity = Role.class) 
	@JoinColumn(name = "role_id", referencedColumnName = "role_id", unique = true)  
	private Role role;
	@OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity = Function.class) 
	@JoinColumn(name = "fun_id", referencedColumnName = "fun_id", unique = true)  
	private Function fun;
	@Column(name = "enable")
	private Boolean enable;
	
	
	 /* 
     * @OneToOne注释指明Person 与IDCard为一对一关系，@OneToOne注释五个属性：targetEntity、cascade、fetch、optional 和mappedBy， 
     *fetch属性默认值是FetchType.EAGER。optional = true设置idcard属性可以为null,也就是允讦没有身份证，未成年人就是没有身份证的。 
     * 
     *targetEntity属性:Class类型的属性。定义关系类的类型，默认是该成员属性对应的类类型，所以通常不需要提供定义。 
     *cascade属性：CascadeType[]类型。该属性定义类和类之间的级联关系。定义的级联关系将被容器视为对当前类对象及其关联类对象采取相同的操作， 
     *而且这种关系是递归调用的。cascade的值只能从CascadeType.PERSIST（级联新建）、CascadeType.REMOVE（级联删除）、 
     *CascadeType.REFRESH（级联刷新）、CascadeType.MERGE（级联更新）中选择一个或多个。还有一个选择是使用CascadeType.ALL，表示选择全部四项。 
     * 
     *fetch属性：FetchType类型的属性。可选择项包括：FetchType.EAGER 和FetchType.LAZY。 
     *FetchType.EAGER表示关系类(本例是OrderItem类)在主类加载的时候同时加载，FetchType.LAZY表示关系类在被访问时才加载。默认值是FetchType.LAZY。 
     * 
     *@OrderBy(value = "id ASC")注释指明加载元组时按id的升序排序（降序 "DESC"） 
     */  
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Function getFun() {
		return fun;
	}
	public void setFun(Function fun) {
		this.fun = fun;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Role getRole() {
		return role;
	}

	
	
}