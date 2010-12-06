import java.util.ArrayList;


public class SQLTable {
	private String tableName=null;
	private ArrayList<FieldDef> fields=null;
	
	public SQLTable(String tableName,ArrayList<FieldDef> fields){
		setTableName(tableName);
		setFields(fields);
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ArrayList<FieldDef> getFields() {
		return fields;
	}
	public void setFields(ArrayList<FieldDef> fields) {
		this.fields = fields;
	}
	@Override
	public boolean equals(Object obj) {
		boolean ret=false;
		if (obj instanceof SQLTable){
			ret=this.hashCode()==((SQLTable) obj).hashCode();
		}
		return ret;
	}
	
	@Override
	public int hashCode() {
		int result = 17,x=0;
		for(x=0;x<fields.size();x++){
			result = 31 * result + fields.get(x).hashCode();
		}
		result = 31 * result + tableName.hashCode();
		return result;
	}
}
