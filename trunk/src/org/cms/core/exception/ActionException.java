package org.cms.core.exception;

public class ActionException extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	private String msg;
	public ActionException(){
		
	}
	public ActionException(String msg){
		this.msg=msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public String getMessage(){
		return this.msg;
	}

}
