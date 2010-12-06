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
}
