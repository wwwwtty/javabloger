package com.exam.utils;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernatePage<T> {
	
	private static Log log=LogFactory.getLog(HibernatePage.class.getName());
	private static HibernateTemplate template;
	static{
		template=(HibernateTemplate) SpringUtils.getApplicationContext().getBean("hibernateTemplet");		
	}
		
	private final String hql;	//查询记录的HQL词句
	private final int pageSize;	//页面记录数，一页显示多少条数据；
	private int currentPageNum=1;	//默认为第一页;
	private long totleRecord; 	//记录总数
	private int totlePage;		//全部页面数
	private List<T> list;		//一页中的全部记录

	public HibernatePage(String hql){
		this(hql,0,0);
	}
	
	public HibernatePage(String hql,int pageSize,int currentPageNum){
		this.hql=hql;
		
		this.pageSize=pageSize>0?pageSize:1;
		this.currentPageNum=currentPageNum<2?1:currentPageNum;
		
//		this.calculator();
//		this.caculortPage();
	}
	
		
	/**
	 * 计算所有记录数；
	 */
	protected void calculator(){
		String colStr="";
		String conditionStr="";
		//重新组装HQL语句；
		if(hql.toLowerCase().startsWith("from")){
			colStr="*";
			conditionStr=hql;
		}
		//重装SQL语句；
		else if(hql.startsWith("select")){
			hql.replaceFirst("select", hql);
			colStr=hql.substring(0, hql.indexOf("from"));
			conditionStr=hql.substring(hql.indexOf("from"));
		}		
						
		final String countSql="select count ("+colStr+") "+conditionStr;
		
		totleRecord=(Long) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session s) throws HibernateException,SQLException {            	
                Query query = s.createQuery(countSql);
                return query.uniqueResult();
            }
        });
		//获取页面总数；
		this.totlePage=(int) (totleRecord/pageSize)+(totleRecord%pageSize==0?0:1);
	}
	/**获取指定数量的记录；
	 * @param index
	 * @return
	 */
	protected void caculortPage() {
		Session session=template.getSessionFactory().openSession();
		if(log.isDebugEnabled()){
			log.debug("get page:"+currentPageNum+";execute sql:"+hql);
		}
		Query query=session.createQuery(hql);
		//由第N条记录开始查询M条记录；
		if(currentPageNum>1){
			query.setFirstResult((currentPageNum-1)*pageSize);	
		}
		if(pageSize>1){
			query.setMaxResults(pageSize);
		}
		list= query.list();
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getTotleRecord() {
		return totleRecord;
	}

	public int getTotlePage() {
		return totlePage;
	}

	public List<T> getList() {
		return list;
	}

}
