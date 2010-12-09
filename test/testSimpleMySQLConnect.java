

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class testSimpleMySQLConnect {
	private static SimpleMySQLConnect cut;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cut = new SimpleMySQLConnect();
	}
	@Test
	public void testExistDriver() {
		assertTrue(cut.existDriver());
	}
	@Test
	public void testConnect() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		assertTrue(cut.Connect("localhost", "mysql", "root", ""));
	}
	@Test
	public void testExistTable() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		SimpleMySQLConnect s=new SimpleMySQLConnect();

		try {
			s.Connect("localhost", "hudson","root","");
			assertTrue(s.existTable("t_buildinfo"));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testRead(){
		SimpleMySQLConnect s=new SimpleMySQLConnect();
		SQLTable tbl=null;
		try {
			s.Connect("localhost", "hudson","root","");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tbl=s.Read("SELECT * FROM t_messages;");
		if(tbl!=null){
			System.out.println(tbl.getTableName());
			for(int x=0;x<tbl.getFields().size();x++){
				System.out.println("Fld:"+tbl.getFields().get(x).getFieldName());
				for(int y=0;y<tbl.getFields().get(x).getFieldValue().size();y++){
					System.out.println(tbl.getFields().get(x).getFieldValue().get(y).toString());
				}
			}
		} 
	}
}
