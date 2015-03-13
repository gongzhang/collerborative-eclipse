package com.collaborativeeclipse.cct;

/**
 * 标签的接口，所有标签接口应实现它
 * @author bleastrind
 *
 */
public interface ITag {
	/**
	 * 获得其目标
	 * @return 目标
	 */
	ITarget getTarget();
	
	/**
	 * 获得数据
	 * @return
	 */
	String getData();
}
