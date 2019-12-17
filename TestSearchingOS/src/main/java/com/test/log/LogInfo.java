package com.test.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LogInfo {

	private String sql;
	private String mappingData;
	private SqlSession sqlSession;
	private Object VO;
	private String VOName;
	private Class c;
	private String mapperId;
	private String callClassName;
	private String callMethodName;
	
	public LogInfo() {}
	
	public LogInfo(SqlSession sqlSession, String mapperId, Class c, Object VO ){
		this.sql = sql;
		this.mappingData = mappingData;
		this.sqlSession = sqlSession;
		this.VO = VO;
		this.VOName = VOName;
		this.c = c;
		this.mapperId = mapperId;
		c.cast(VO);
		
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		StackTraceElement beforeStack = stacks[1];
		this.callClassName = beforeStack.getClassName();
		this.callMethodName = beforeStack.getMethodName();
		getSql(sqlSession, mapperId, c, VO);
	}
	
	public String getSql(SqlSession sqlSession, String mapperId, Class c, Object VO ) {
		
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		
		StackTraceElement beforeStack = stacks[1];
		
		this.callClassName = beforeStack.getClassName();
		
		this.callMethodName = beforeStack.getMethodName();
		
		c.cast(VO);
		
		BoundSql bSql = sqlSession.getConfiguration().getMappedStatement(mapperId).getBoundSql(c);
		String sql = bSql.getSql();
		Object pObj = bSql.getParameterObject();
		String parameters = "";
		try {
			if(pObj != null) {
	            List<ParameterMapping> paramMapping = bSql.getParameterMappings();
	            int index = 1;
	            for (ParameterMapping mapping : paramMapping) {
	                String requestParamKey = mapping.getProperty();
	                String requestParamValue = (String) getLogJsonParser(c,VO).get(requestParamKey);
	                sql = sql.replaceFirst("\\?", "'" + getLogJsonParser(c,VO).get(requestParamKey) + "'");
	                parameters += "param[" +index + "]" +"(" + requestParamKey + " : " + requestParamValue + ") \n";
	                index++;
	            }
	            sql = "[START]\n"
	            	+ "[LOGTIME - "+ getDateTime() + "]\n"
	            	+ "[PARAMETERS - LOG (KEY : VALUE)] \n"
	            	+  parameters
	            	+ "[CALL CLASS - " + callClassName + "]\n"
	            	+ "[CALL METHOD - " + callMethodName + "]\n"
	            	+ "[QUERY - LOG] \n" + sql  ;
	            setLogWrite(sql);
	        }
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	public JSONObject getLogJsonParser(Class c, Object VO) {
		Method method;
		JSONObject jsonObj= null;
		try {
			method = c.getMethod("toStringJson");
			JSONParser parser = new JSONParser();
	        Object obj = parser.parse((String)method.invoke(VO));
	        jsonObj = (JSONObject) obj;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	public String getDateTime(){
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				
		Date date = new Date();
				
		String time = format1.format(date);
		
		return time;
	}
	
	public void setLogWrite(String str) {
		
		File file = new File("/Users/nam-oseung/dev/SpringProject/log/logger.log");
		PrintWriter pw = null;
		String fileName = "/Users/nam-oseung/dev/SpringProject/log/logger.log";
		
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(fileName,true));
			pw = new PrintWriter(bw,true);
			
			pw.write("\n" + str);
	        pw.flush();
	        pw.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPrintErr(String err) {
		setLogWrite("[ERROR - LOG] \n" + err);
		setLogWrite("[END]");
	}

}
