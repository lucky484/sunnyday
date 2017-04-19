package com.f2b2c.eco.model.market;

/**
 * 接口返回的数据参数类
 * @author jing.liu
 *
 * @param <T>
 */
public class DataToCResultModel<T extends Object> {
    
	/**
	 * 状态码（200:表示成功,400：表示失败）
	 */
	private Integer status;
    
	/**
	 * 成功或错误信息
	 */
	private String msg;
	
	public DataToCResultModel(){
		
	}
	
	public DataToCResultModel(Integer status,String msg,T data){
		this.status=status;
		this.msg=msg;
		this.data=data;
	}
    
	/**
	 * 数据 类型是泛型
	 */
	private T data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
