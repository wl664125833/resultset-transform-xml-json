package com.teradata.nmcc.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TransformDataUtil {
	static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	* @Title: resultSetToJson 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param rs
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String resultSetToJson(ResultSet rs) {
		// json数组
		JsonArray array = new JsonArray();
		ResultSetMetaData metaData;
		try {
			metaData = rs.getMetaData();
			// 获取列数
			int columnCount = metaData.getColumnCount();
			if(rs!=null){
				// 遍历ResultSet中的每条数据
				while (rs.next()) {
					JsonObject jsonObj = new JsonObject();
					// 遍历每一列
					for (int i = 1; i <= columnCount; i++) {
						String columnName = metaData.getColumnLabel(i);
						if(metaData.getColumnType(i)==Types.DATE){
							String dt = fmt.format(rs.getTimestamp(i));		//时间戳转时间
							jsonObj.addProperty(columnName, dt);
						}else{
							String value = rs.getString(columnName);
							jsonObj.addProperty(columnName, value);
						}
					}
					array.add(jsonObj);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array.toString();
	}
	/**
	 * 
	* @Title: resultSetToXML 
	* @Description: TODO(使用Dom4j将ResultSet 转 XML) 
	* @param @param rs
	* @param @return    设定文件 
	* @return Document    返回类型 
	* @throws
	 */
	public static void resultSetToXML(ResultSet rs, String path) {
		//创建根节点  
		Document document=DocumentHelper.createDocument();  
		Element root=DocumentHelper.createElement("ROOT");  
		document.setRootElement(root);  
		try {
			if(rs!=null){
				while (rs.next()) {  
					Element row = root.addElement("ROW");  
					ResultSetMetaData metaData = rs.getMetaData();
					int colCount;
					colCount = metaData.getColumnCount();
					for(int i=1;i<=colCount;i++){
						Element element = row.addElement(metaData.getColumnName(i));	//大写
						if(rs.getObject(i)!=null){
							if(metaData.getColumnType(i)==Types.DATE){
								String dt = fmt.format(rs.getTimestamp(i));		//时间戳转时间
								element.setText(dt);
							}else{
								//将所有的值转为String
								element.setText(String.valueOf(rs.getObject(i)));
							}
						}else{
							element.setText("");
						}
					}
				}  
			} 
		 }
		catch (SQLException e) {
			e.printStackTrace();
		}
        try {
        	//创建文件
            FileOutputStream file = new FileOutputStream(path);
            XMLWriter xml = new XMLWriter(file); 
            xml.write(document);
	        xml.close();  
	        System.out.println("success create file,the file is "
					+ path);
        } catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
