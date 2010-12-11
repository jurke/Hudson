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
	
	public void Write(SQLTable tbl,ArrayList<String> ignoreFields){
		String sql=null,insertTblFields=null,insertValues=null,tmpVal=null,tmpFld=null;
		boolean ignoreField =false;
		ArrayList<String> isql=new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		int x=0,y=0,c=0;
		sql = "SELECT * FROM "+tbl.getTableName() + " WHERE ";
		for(y=0;y<tbl.getFields().get(x).getFieldValue().size();y++){
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
				sql=sql.substring(0,sql.length()-5)+";";
				System.out.println(sql);
				if((conn!=null)){
					try {
						stmt = conn.createStatement();
						rs = stmt.executeQuery(sql);
						rs.last();
						if(rs.getRow()==0){
							insertValues=insertValues.substring(0,insertValues.length()-2);
							insertTblFields=insertTblFields.substring(0,insertTblFields.length()-2);
							sql="INSERT INTO "+tbl.getTableName()+"("+insertTblFields+") VALUES ("+insertValues+");";
							isql.add(sql);
						}
						rs.close();
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		for(x=0;x<isql.size();x++){
			System.out.println(isql.get(x));
		}
		rs=null;
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
