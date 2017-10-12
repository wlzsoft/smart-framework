package com.smartframe.dto;

/**
 * 
 * <p><b>Title:</b><i>传递结果信息</i></p>
 * <p>Desc: 包含复杂对象结果信息</p>
 * <p>source folder:{@docRoot}</p>
 * @version Version 0.1
 * @param <T>
 */
public class ResultObject{
	
	
	/**
	 * 方法用途: 成功提示信息<br>
	 * 操作步骤: TODO<br>
	 * @param message
	 * @return
	 */
	public static Result<String> successMessage(String message) {
		Result<String> result = new Result<String>(message);
		result.setCode(HttpStatusEnum.OK.value());
		result.setSuccess(true);
		result.setValue(null);
		result.setMessage(message);
		return result;
	}
	
	/**
	 * 方法用途: 业务提示警告信息<br>
	 * 操作步骤: TODO<br>
	 * @param message
	 * @return
	 */
	public static Result<String> warnMessage(String message) {
		Result<String> result = new Result<String>(message);
		result.setCode(HttpStatusEnum.INVALID.value());
		result.setSuccess(true);
		result.setValue(null);
		result.setMessage(message);
		return result;
	}
	
	
	/**
	 * 方法用途: 业务提示验证权限<br>
	 * 操作步骤: TODO<br>
	 * @param message
	 * @return
	 */
	public static Result<String> sucreMessage(String message) {
		Result<String> result = new Result<String>(message);
		result.setCode(HttpStatusEnum.UNAUTHORIZED.value());
		result.setSuccess(true);
		result.setValue(null);
		result.setMessage(message);
		return result;
	}
	
	
	/**
	 * 方法用途: 成功返回对象<br>
	 * 操作步骤: TODO<br>
	 * @param message
	 * @return
	 */
	public static <T> Result<T> successObject(T t,String message) {
		Result<T> result = new Result<T>(t);
		result.setCode(HttpStatusEnum.OK.value());
		result.setSuccess(true);
		if(null==message||message.equals("")){
			message="成功";
		}
		result.setMessage(message);
		result.setValue(t);
		return result;
	}
}
