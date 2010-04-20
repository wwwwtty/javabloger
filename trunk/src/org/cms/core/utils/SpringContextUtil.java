package org.cms.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;


/*
 *@auther heshencao 
 * 2010-1-17 下午08:43:44
 */
public class SpringContextUtil implements ApplicationContextAware, InitializingBean {
		private static ApplicationContext applicationContext; // Spring应用上下文环境

		/**
		 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
		 * 
		 * @param applicationContext
		 * @throws BeansException
		 */
					
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			SpringContextUtil.applicationContext = applicationContext;
		}

		public static void setApplicationContextInit(ApplicationContext applicationContext) throws BeansException {
			SpringContextUtil.applicationContext = applicationContext;
		}

		/**
		 * @return ApplicationContext
		 */
		public static ApplicationContext getApplicationContext() {
			
			return applicationContext!=null?applicationContext:ContextLoader.getCurrentWebApplicationContext();
		}

		/**
		 * 获取对象
		 * 
		 * @param name
		 * @return Object 一个以所给名字注册的bean的实例
		 * @throws BeansException
		 */
		public Object getBean(String name) throws BeansException {
//			ApplicationObjectSupport support=new ApplicationObjectSupport ();
			
			return getApplicationContext().getBean(name);
		}

		/**
		 * 获取类型为requiredType的对象
		 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
		 * 
		 * @param name
		 *            bean注册名
		 * @param requiredType
		 *            返回对象类型
		 * @return Object 返回requiredType类型对象
		 * @throws BeansException
		 */
		public Object getBean(String name, Class requiredType) throws BeansException {
			return getApplicationContext().getBean(name, requiredType);
		}

		/**
		 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
		 * 
		 * @param name
		 * @return boolean
		 */
		public boolean containsBean(String name) {
			return getApplicationContext().containsBean(name);
		}

		/**
		 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
		 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
		 * 
		 * @param name
		 * @return boolean
		 * @throws NoSuchBeanDefinitionException
		 */
		public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
			return getApplicationContext().isSingleton(name);
		}

		/**
		 * @param name
		 * @return Class 注册对象的类型
		 * @throws NoSuchBeanDefinitionException
		 */
		public Class getType(String name) throws NoSuchBeanDefinitionException {
			return getApplicationContext().getType(name);
		}

		/**
		 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
		 * 
		 * @param name
		 * @return
		 * @throws NoSuchBeanDefinitionException
		 */
		public String[] getAliases(String name) throws NoSuchBeanDefinitionException {
			return getApplicationContext().getAliases(name);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
		 */
		public void afterPropertiesSet() throws Exception {
			System.out.println("初始化SpringContextUtil");

		}
	}

