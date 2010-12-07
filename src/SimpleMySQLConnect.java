import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SimpleMySQLConnect {
	private static boolean driverExist=false;
	private Connection conn=null;
	
	public SimpleMySQLConnect(){if(!driverExist) existDriver();}
	
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
		if(driverExist){
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {
				
				conn = DriverManager.getConnection("jdbc:mysql://"+serverName+"/"+databaseName+"?user="+userName+"&password="+passwd);
			} catch (SQLException e) {
				System.err.println("SQL-Verbindung fehlgeschlagen.");
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
	
	public boolean existTable(String tableName)  {
		Statement stmt=null;
		ResultSet rs=null;
		boolean ret=false;
		if((tableName!=null)&&isConnected()){
			try {
				stmt = conn.createStatement();
				
				rs=stmt.executeQuery("SELECT pr_ExistTable('"+tableName+"') AS Exist;");
				
				rs.first();
				ret=(rs.getInt(1)!=0);
				rs.close();
				rs=null;
				stmt.close();
				stmt=null;
			} catch (SQLException e) {
				rs=null;
				stmt=null;
				ret=false;
			}
		}
		return ret;
	}
	
	
	public SQLTable Read(String tableName,ArrayList<WhereDef> whereDef){
		SQLTable ret=null;
		String sql=null,wsql=null;
		Statement stmt = null;
		ResultSet rs = null;
		int x=0;
		if((tableName!=null)&&(conn!=null)&&existTable(tableName)){
			sql="SELECT * FROM "+tableName;
			if(whereDef!=null && whereDef.size()>0){
				wsql="WHERE ";
				for(x=0;x<whereDef.size();x++){
					if(wsql!=null){
						wsql=wsql+whereDef.get(x).getWhereCondition()+" ";
						if(x<whereDef.size()-1 && whereDef.get(x).getWhereLink()!=null){
							wsql=wsql+whereDef.get(x).getWhereLink();
						}else{
							wsql=null;
						}
					}
				}
				if(wsql==null) {wsql="";}
				sql=sql+wsql+";";

				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					rs.close();
					stmt.close();
					ret=new SQLTable(tableName,null);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					ret=null;
				}
			}
		}
		return ret;
	}
	
	public SQLTable Read(String sql){
		
		return null;
	}
	
	
}
