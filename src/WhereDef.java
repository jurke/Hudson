
public class WhereDef {
	private String whereCondition=null;
	private String whereLink=null;
	
	public WhereDef(String whereCondition,String link){
		this.whereCondition=whereCondition;
		this.whereLink=link;
	}
	public String getWhereCondition() {
		return whereCondition;
	}
	public String getWhereLink() {
		return whereLink;
	}
	
}
