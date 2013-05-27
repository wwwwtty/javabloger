package org.hsc.novelSpider;

import java.util.Dictionary;
import java.util.Hashtable;

import org.hsc.novelSpider.service.CaptureService_17shu;
import org.hsc.novelSpider.service.ICaputerService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

public class Active implements BundleActivator,ServiceListener{

//	ClassPathXmlApplicationContext beanFactory=new ClassPathXmlApplicationContext();
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("API服务启动");
		 context.addServiceListener(this);
		 ICaputerService cap=new CaptureService_17shu();
		 Dictionary<String, Object> dt= new Hashtable<String, Object>();
		 context.registerService(ICaputerService.class, cap,dt );
//		 beanFactory.setConfigLocation("applicationContext.xml");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		 context.removeServiceListener(this);
//		 beanFactory.destroy();
		 
		System.out.println("API服务关闭");
		
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		String[] objectClass = (String[])
        event.getServiceReference().getProperty("objectClass");

        if (event.getType() == ServiceEvent.REGISTERED)
        {
            System.out.println(
                "Ex1: Service of type " + objectClass[0] + " registered.");
        }
        else if (event.getType() == ServiceEvent.UNREGISTERING)
        {
            System.out.println(
                "Ex1: Service of type " + objectClass[0] + " unregistered.");
        }
        else if (event.getType() == ServiceEvent.MODIFIED)
        {
            System.out.println(
                "Ex1: Service of type " + objectClass[0] + " modified.");
        }
	}


}
