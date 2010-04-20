package org.cms.core.exception;

public class AuthException extends RuntimeException {
	private static final long serialVersionUID = 7700173463044132728L;
	public AuthException(){}
	private String msg;
	public AuthException(String msg){
		this.msg=msg;
	}
	@Override
	public String getMessage(){
		return this.msg;
	}
}
