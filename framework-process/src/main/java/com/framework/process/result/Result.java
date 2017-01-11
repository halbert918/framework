package com.framework.process.result;

import java.io.Serializable;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description Result——所有的jobContext中的Result都应实现接口
 */
public interface Result extends Serializable {

	/**
	 * 结果描述
	 * @return
     */
	String getMessage();
	
}
