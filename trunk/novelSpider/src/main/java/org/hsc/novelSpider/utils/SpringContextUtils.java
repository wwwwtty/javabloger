package org.hsc.novelSpider.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringContextUtils implements BeanFactoryAware{

	private BeanFactory factory=null;
	private static SpringContextUtils instance=null;
	
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		instance=this;
		this.factory=factory;
	}
	
	public static BeanFactory getBeanFacotory(){
		if(instance==null)
			throw new RuntimeException("系统还未初始化，请注意检查代码！");
		
		return instance.factory;
	}
	
	public static <X> X getBean(String name){
		return (X) getBeanFacotory().getBean(name);
	}

	public static <X> X getBean(Class<X> class1) {
		return getBeanFacotory().getBean(class1);
	}
}
