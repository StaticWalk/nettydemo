package com.iot.AttributeMap;

import java.util.Date;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/19
 * Time:21:17
 *
 * AttributeMap 存储的是自定义对象
 */
public class NettyChannel {

	private String name;


	private Date createDate;


	public NettyChannel(String name,Date createDate) {
		this.name = name;
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}