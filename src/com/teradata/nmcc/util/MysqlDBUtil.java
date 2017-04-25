package com.teradata.nmcc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDBUtil {

	private static String dbDriver = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://localhost:3306/test";
	private static String dbUser = "root";
	private static String dbPass = "";
	private static MysqlDBUtil instance = null;  

    
    public MysqlDBUtil() {  
        super();  
    }  
  
    /** 
     * 单例方式创建对象 
     * @return 
     */  
    public static MysqlDBUtil getInstance() {  
        if (instance == null) {  
            synchronized (MysqlDBUtil.class) {  
                if (instance == null) {  
                    instance = new MysqlDBUtil();  
                }  
            }  
        }  
        return instance;  
    }  
         
      
    /** 
     * 注册驱动 
     * 静态代码块 用于启动web服务器时加载驱动 
     */  
    static{  
        String className = dbDriver;  
        try {  
            Class.forName(className).newInstance();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  
      
    /** 
     * 获取数据库连接 
     * @param con 
     * @return 
     */  
    public Connection getConnection(){  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
         
    /** 
     *  依次关闭ResultSet、Statement、Connection 
     *  若对象不存在则创建一个空对象 
     * @param rs 
     * @param st 
     * @param pst 
     * @param conn 
     */  
    public void close(ResultSet rs1,ResultSet rs2,Statement st1,Statement st2,Connection conn1,Connection conn2){  
        if(rs1 != null ||rs2!=null){  
            try {  
                rs1.close();  
                rs2.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally{  
                if(st1 != null || st2 != null){  
                    try {  
                        st1.close();  
                        st2.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                    } finally{  
                        if(conn1 != null || conn2 != null){  
                            try {  
                                conn1.close();  
                                conn2.close();  
                            } catch (SQLException e) {  
                                e.printStackTrace();  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
  

}
