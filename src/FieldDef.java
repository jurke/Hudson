import java.util.ArrayList;


public class FieldDef {
	private String fieldName=null;
	private ArrayList<Object> fieldValue=null;
	
	public FieldDef(String fieldName,ArrayList<Object> fieldValue){
		setFieldName(fieldName);
		setFieldValue(fieldValue);
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public ArrayList<Object> getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(ArrayList<Object> fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean ret=false;
		if (obj instanceof FieldDef){
			ret=this.hashCode()==((FieldDef) obj).hashCode();
		}
		return ret;
	}
	
	
	@Override
	public int hashCode() {
		int result = 17,x=0;
		for(x=0;x<fieldValue.size();x++){
			result = 31 * result + fieldValue.get(x).hashCode();
		}
		result = 31 * result + fieldName.hashCode();
		return result;
	}
	
}
