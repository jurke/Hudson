import java.util.ArrayList;


public class SQLData {
	private String searchSQL=null;
	private String insertReplaceSQL=null;
	public SQLData(String sSQL,String ipSQL){
		searchSQL=sSQL;insertReplaceSQL=ipSQL;
	}
	public String getSearchSQL(){return searchSQL;}
	public String getInsertReplaceSQL(){return insertReplaceSQL;}
	
}
