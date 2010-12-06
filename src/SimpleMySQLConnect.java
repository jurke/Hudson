import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SimpleMySQLConnect {
	private static boolean driverExist=false;
	private Connection conn=null;
	public boolean existDriver(){
		boolean ret=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ret=true;
		} catch (ClassNotFoundException e) {
			ret=false;
		}
		driverExist=ret;
		return ret;
	}
	public boolean Connect(String serverName,String databaseName, String userName,String passwd) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		conn=null;
		if(driverExist){
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {
				conn = DriverManager.getConnection("jdbc:mysql://"+serverName+"/"+databaseName+"?user="+userName+"&password="+passwd);
			} catch (SQLException e) {
				conn=null;
			}
		}
		return conn!=null;
	}
	public boolean isConnected(){return conn!=null;}
	
	public void closeConnection(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				conn=null;
			}
		}
	}
	
	private boolean existTable(String tableName){
		String sql=null;
		boolean ret=false;
		if(tableName!=null){
			sql="";
		}
		
		return false;
	}
	
	
	public SQLTable Read(String tableName,ArrayList<String> whereDef){
		String sql=null;
		if(tableName!=null){
			sql="SELECT * FROM "+tableName;
		}
		return null;
	}
	
	public SQLTable Read(String sql){
		
		return null;
	}
	
	
}
