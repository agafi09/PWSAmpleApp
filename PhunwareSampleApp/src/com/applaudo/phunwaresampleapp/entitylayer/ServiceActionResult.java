package com.applaudo.phunwaresampleapp.entitylayer;


/**
 * Wrapper class for the downloaded information
 */
public class ServiceActionResult {

	/**
	 * Empty constructor required for the creation of a new instance of the wrapper object
	 */
	public enum ServiceActionResultCode {
		SERVICE_CODE_SUCCESS, 
		SERVICE_CODE_RECORDSFOUND, 
		SERVICE_CODE_NORECORDSFOUND, 
		SERVICE_CODE_ERROR,
		SERVICE_CODE_WARNING, 
		SERVICE_CODE_FAILURE, 
		SERVICE_CODE_USERNOTFOUND,
		SERVICE_CODE_SESSIONEXPIRED
	}

	private Object result;
	private String message;
	private ServiceActionResultCode returnCode;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ServiceActionResultCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(ServiceActionResultCode serviceCodeSuccess) {
		this.returnCode = serviceCodeSuccess;
	}

	/**
	 * Empty constructor required for the creation of a new instance of the wrapper object
	 */
	public ServiceActionResult() {
		
	}
	
}