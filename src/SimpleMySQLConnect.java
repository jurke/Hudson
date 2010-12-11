import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


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
	public ArrayList<SQLData> writeSQLGen(SQLTable tbl,ArrayList<String> ignoreFields){
		ArrayList<SQLData> ret=new ArrayList<SQLData>();
		String tmp=null,sql=null,insertTblFields=null,insertValues=null,tmpVal=null,tmpFld=null;
		boolean ignoreField=false;
		int x=0,y=0,c=0;
		for(y=0;y<tbl.getFields().get(0).getFieldValue().size();y++){
			sql = "SELECT * FROM "+tbl.getTableName() + " WHERE ";
			insertTblFields="";insertValues="";
			for(x=0;x<tbl.getFields().size();x++){
				ignoreField=(ignoreFields!=null)&&ignoreFields.contains(tbl.getFields().get(x).getFieldName());
				if(!ignoreField)
				{
					tmpVal="'"+tbl.getFields().get(x).getFieldValue().get(y)+"'";
					tmpFld=tbl.getFields().get(x).getFieldName();
					sql=sql+tbl.getTableName()+"."+tmpFld+"="+tmpVal+" AND ";
					c++;
					insertTblFields=insertTblFields.concat("`"+tmpFld+"`,");
					insertValues=insertValues.concat(tmpVal+",");
				}
			}
			if(c>0){
				if(ignoreFields!=null && ignoreFields.size()>0) tmp="INSERT"; else tmp="REPLACE";
				ret.add(new SQLData(sql.substring(0,sql.length()-5)+";",tmp+" INTO "+tbl.getTableName()+" ("+insertTblFields.substring(0,insertTblFields.length()-2)+") VALUES "+" ("+insertValues.substring(0,insertValues.length()-1)+");"));
			}
		}
		return ret;
	}
	
	public void write(SQLTable tbl,ArrayList<String> ignoreFields){	
		Statement stmt = null;
		ArrayList<SQLData> sqlData=null;
		int x=0;
		sqlData=writeSQLGen(tbl,ignoreFields);
		if((conn!=null)&&sqlData!=null){
			for(x=0;x<sqlData.size()-1;x++){
				try {
					stmt = conn.createStatement();
					stmt.execute(sqlData.get(x).getInsertReplaceSQL());
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		stmt=null;
	}
	
	public SQLTable Read(String sql){
		SQLTable ret=null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsm=null;
		ArrayList<FieldDef> field=new ArrayList<FieldDef>();
		HashMap<String,FieldDef> h=new HashMap<String,FieldDef>();
		FieldDef tmp=null;
		int x=0;
		if((sql!=null)&&(conn!=null)){
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				rsm=rs.getMetaData();
				for(x=1;x<=rsm.getColumnCount();x++){
					tmp=new FieldDef(rsm.getColumnName(x),new ArrayList<Object>());
					h.put(rsm.getColumnClassName(x),tmp);
					field.add(tmp);
				}
				while(rs.next()){
					for(x=1;x<=rsm.getColumnCount();x++){
						tmp=h.get(rsm.getColumnClassName(x));
						tmp.getFieldValue().add(rs.getObject(x));
					}	
				}
				rs.close();
				stmt.close();
				ret=new SQLTable("SQL_"+sql.hashCode(),field);
			} catch (SQLException e) {
				
				ret=null;
			}
		}
		h.clear();
		h=null;
		rsm=null;
		rs=null;
		stmt=null;
		return ret;
	}
	
	
}
