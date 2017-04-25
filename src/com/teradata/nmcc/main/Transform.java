package com.teradata.nmcc.main;

import com.teradata.nmcc.dao.FindDataImpl;

/**
 * 
 * @author wnaglei
 *
 */
public class Transform {

	public static void main(String[] args) {
		String path="D:\\file\\";
		String id ="www";
		FindDataImpl findDataImpl = new FindDataImpl();
		findDataImpl.dataTransform(path,id);
	}

}
