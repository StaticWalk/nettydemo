package com.iot.Netty权威指南.TimeDemo;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/21
 * Time:16:13
 */
public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		if (args!=null&&args.length>0){
			try{
				port=Integer.valueOf(args[0]);
			}catch (NumberFormatException e){
				//
			}
		}
//		Multiplexer
	}
}
