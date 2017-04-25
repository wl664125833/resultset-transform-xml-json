package com.teradata.nmcc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeradataDBUtil {

	private static String dbDriver = "com.teradata.jdbc.TeraDriver";
	private static String dbUrl = "jdbc:teradata://10.220.8.12/TMODE=TERA,CHARSET=ASCII,CLIENT_CHARSET=cp936,DATABASE=NGBASS,lob_support=on";
	private static String dbUser = "cim";
	private static String dbPass = "cim";
	private static TeradataDBUtil instance = null;  

    
    public TeradataDBUtil() {  
        super();  
    }  
  
    /** 
     * 单例方式创建对象 
     * @return 
     */  
    public static TeradataDBUtil getInstance() {  
        if (instance == null) {  
            synchronized (TeradataDBUtil.class) {  
                if (instance == null) {  
                    instance = new TeradataDBUtil();  
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
     * modify 2012-4-17 
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
    public void close(ResultSet rs,Statement st,Connection conn){  
        if(rs != null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally{  
                if(st != null){  
                    try {  
                        st.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                    } finally{  
                        if(conn != null){  
                            try {  
                                conn.close();  
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
