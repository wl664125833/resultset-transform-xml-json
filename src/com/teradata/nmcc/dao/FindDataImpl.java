package com.teradata.nmcc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.teradata.nmcc.util.ForFile;
import com.teradata.nmcc.util.MysqlDBUtil;
import com.teradata.nmcc.util.TransformDataUtil;

public class FindDataImpl extends BaseDao implements FindDataService {

	@Override
	public void dataTransform(String path,String id) {
		long time =System.nanoTime();
		String xmlName = path+id+time+".xml";
		String jsonName = path+id+time+".json";
//		String sql = "sel top 10 * from ngbass.tap_c_module";
		String sql = "select  * from user";
		Connection cnn1 = null;
		Connection cnn2= null;
		ResultSet jsonRS = null;
		ResultSet xmlRS = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		String jsonData=null;
		try {
			cnn1 = MysqlDBUtil.getInstance().getConnection();
			cnn2 = MysqlDBUtil.getInstance().getConnection();
//			cnn1 = TeradataDBUtil.getInstance().getConnection();
//			cnn2 = TeradataDBUtil.getInstance().getConnection();
			stmt1 = cnn1.createStatement();
			stmt2 = cnn2.createStatement();
			jsonRS = stmt1.executeQuery(sql);
			xmlRS = stmt2.executeQuery(sql);
			jsonData=TransformDataUtil.resultSetToJson(jsonRS);
			TransformDataUtil.resultSetToXML(xmlRS,xmlName);
			ForFile.createFile(jsonName, jsonData);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{  
            MysqlDBUtil.getInstance().close(xmlRS,jsonRS, stmt1,stmt2, cnn1,cnn2);   
        }  
	}

}
