package org.cms.core;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationLog implements MethodInterceptor {  
	  
    // 方法为执行慢速的时间标准，默认为 1000 毫秒  
    private long slowExecuteTime = 1000;  
    // 日志级别静态常量  
    private static final String LEVEL_INFO = "info";  
    private static final String LEVEL_DEBUG = "debug";  
    private static final String LEVEL_ERROR = "error";  
    private static final String LEVEL_WARAN = "waran";  
    // 日志记录器  
    private static Logger log = LoggerFactory.getLogger(OperationLog.class);  
  
    /** 
     * 获取将方法标记为执行慢速的时间标准，如果方法执行时间超过此值，将被标记为执行慢速。 
     * @return 执行慢速的时间标准 
     */  
    public long getSlowExecuteTime() {  
        return slowExecuteTime;  
    }  
  
    /** 
     * 设置将方法标记为执行慢速的时间标准，如果方法执行时间超过此值，将被标记为执行慢速。 
     * 如果不配置此参数，将默认为 1000 毫秒。 
     * @param slowExecuteTime 执行慢速的时间标准 
     */  
    public void setSlowExecuteTime(long slowExecuteTime) {  
        this.slowExecuteTime = slowExecuteTime;  
    }  
  
    /** 
     * 重写invoke(MethodInvocation invocation)方法，以便在日志中记录方法执行情况的信息。 
     * @param invocation 目标对象信息 
     * @return Object 方法返回值 
     * @throws java.lang.Throwable 异常 
     */  
    @Override  
    public Object invoke(MethodInvocation invocation) throws Throwable {  
  
        before(invocation);  
  
        long startTime = System.currentTimeMillis();  
        Object result = invocation.proceed();  
        long endTime = System.currentTimeMillis();  
  
        long executeTime = endTime - startTime;  
        logExecuteTime(invocation, executeTime);  
  
        after(invocation);  
  
        return result;  
    }  
  
    /** 
     * 在目标对象方法调用之前调用，以记录方法调用之前的情况。 
     * @param invocation 目标对象信息 
     */  
    private void before(MethodInvocation invocation) {  
        String methodName = invocation.getMethod().getName();  
  
        StringBuilder builder = new StringBuilder();  
        builder.append("Started ");  
        builder.append(methodName);  
        builder.append(" method!");  
        recordLog(builder.toString(), LEVEL_DEBUG, null);  
  
        Object[] args = invocation.getArguments();  
        builder.delete(0, builder.length());  
        builder.append(methodName);  
        builder.append(" way into the parameters are different：");  
        recordLog(builder.toString(), LEVEL_DEBUG, null);  
  
        for (int count = 0; count < args.length; count++) {  
            builder.delete(0, builder.length());  
            builder.append("The ");  
            builder.append(count);  
            builder.append(" parameters for the ");  
            builder.append(args[count] == null ? null : args[count].toString());  
            recordLog(builder.toString(), LEVEL_DEBUG, null);  
        }  
    }  
  
    /** 
     * 在目标对象方法调用之后调用，以记录方法调用之后的情况。 
     * @param invocation 目标对象信息 
     */  
    private void after(MethodInvocation invocation) {  
        String methodName = invocation.getMethod().getName();  
        recordLog(methodName + " the implementation of the end.", LEVEL_DEBUG,  
                null);  
    }  
  
    /** 
     * 记录方法执行时间。 
     * 如果执行时间超过 getSlowExecuteTime() 的返回值， 
     * 则将记录等级提升为 waran ，并标记为慢速执行。 
     * @param invocation 目标对象信息 
     * @param executeTime 方法执行时间 
     */  
    private void logExecuteTime(MethodInvocation invocation, long executeTime) {  
        long slowTime = getSlowExecuteTime();  
        StringBuilder builder = new StringBuilder();  
        builder.append(invocation.getMethod().getName());  
        if (executeTime > slowTime) {  
            builder.append(" implementation of the method is much too slow,");  
            builder.append(" the execute time for the implementation is ");  
            builder.append(executeTime);  
            builder.append(" ms.");  
            recordLog(builder.toString(), LEVEL_WARAN, null);  
        } else {  
            builder.append(" execution time od the method is ");  
            builder.append(executeTime);  
            builder.append(" ms.");  
            recordLog(builder.toString(), LEVEL_DEBUG, null);  
        }  
        builder = null;  
    }  
  
    /** 
     * 记录日志信息。 
     * @param message 日志信息 
     * @param level 日志级别 
     * @param throwable 如果日志级别是异常，此属性用来描述异常信息。 
     */  
    private void recordLog(String message, String level, Throwable throwable) {  
        if (message != null && !message.isEmpty() &&  
                level != null && !level.isEmpty()) {  
            if (LEVEL_INFO.equals(level)) {  
                if (log.isInfoEnabled()) {  
                    log.info(message);  
                }  
            } else if (LEVEL_DEBUG.equals(level)) {  
                if (log.isDebugEnabled()) {  
                    log.debug(message);  
                }  
            } else if (LEVEL_ERROR.equals(level)) {  
                if (log.isErrorEnabled()) {  
                    log.error(message);  
                }  
            } else if (LEVEL_WARAN.equals(level)) {  
                if (log.isWarnEnabled()) {  
                    if (throwable == null) {  
                        log.warn(message);  
                    } else {  
                        log.warn(message, throwable);  
                    }  
                }  
            }  
        }  
    }  
}  
