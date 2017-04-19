package com.hd.pfirs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {
   
	public Statement st;

	public DbUtil() {
	
	}
	
	public Statement getStatement(Map<String,String> paraMap) throws SQLException{
		try {
			Class.forName(paraMap.get("driver"));
			Connection conn =  DriverManager.getConnection(paraMap.get("jdbc"),paraMap.get("username"),paraMap.get("password"));
			if(!conn.isClosed()){
				
			}else{
				return null;
			}
			 Statement statement = conn.createStatement();
             return statement;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	public List<String> getTableNames(Map<String,String> dbInfoMap,String dbName){
		List<String> tableList = new ArrayList<String>();
		try {
			st = this.getStatement(dbInfoMap);
			if(st == null){
				return null;
			}
			String selTableSql = dbInfoMap.get("showTable").toString().replace("%", dbName);
			ResultSet tabRs = st.executeQuery(selTableSql);
			while(tabRs.next()){
				tableList.add(tabRs.getString(1));
			}
		} catch (SQLException e) {
			return null;
		}
		return tableList;
	}
	public List<Map<String,String>> getColumnNames(Map<String,String> dbInfoMap,String tabName){
		List<Map<String,String>> colList = new ArrayList<Map<String,String>>();
		 try {
			st = this.getStatement(dbInfoMap);
			if(st == null){
				return null;
			}
			String selColumnSql = dbInfoMap.get("showColumns").toString().replace("%", tabName);
			ResultSet columnRs = st.executeQuery(selColumnSql);
			while(columnRs.next()){
			Map<String,String> colMap = new HashMap<String,String>();
			colMap.put("filed",columnRs.getString(1));
			colMap.put("type",columnRs.getString(2));
			colList.add(colMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
           return null;
		}
		return colList;
		
	}
}
