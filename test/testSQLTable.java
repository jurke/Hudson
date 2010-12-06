
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import java.util.ArrayList;

import org.junit.Test;


public class testSQLTable {
	@Test
	public void testEquals() {
		int x=0;
		ArrayList<Object> valuesTbl1=new ArrayList<Object>(),valuesTbl2=new ArrayList<Object>();
		ArrayList<FieldDef> fieldsTbl1=null,fieldsTbl2=null;
		for(x=0;x<=10;x++){
			valuesTbl1.add("Testdaten"+x);
			valuesTbl2.add("Testdaten"+x);
		}
		fieldsTbl1=new ArrayList<FieldDef>();
		fieldsTbl1.add(new FieldDef("Feld",valuesTbl1));
		fieldsTbl2=new ArrayList<FieldDef>();
		fieldsTbl2.add(new FieldDef("Feld",valuesTbl2));
		SQLTable tbl1=new SQLTable("Tbl",fieldsTbl1);
		SQLTable tbl2=new SQLTable("Tbl",fieldsTbl2);
		assertEquals("Equal : ", tbl1,tbl2);
	}
	@Test
	public void testNotEqualInValues() {
		int x=0;
		ArrayList<Object> valuesTbl1=new ArrayList<Object>(),valuesTbl2=new ArrayList<Object>();
		ArrayList<FieldDef> fieldsTbl1=null,fieldsTbl2=null;
		for(x=0;x<=10;x++){
			valuesTbl1.add("Testdaten"+x);
			valuesTbl2.add("Testdaten1"+x);
		}
		fieldsTbl1=new ArrayList<FieldDef>();
		fieldsTbl1.add(new FieldDef("Feld",valuesTbl1));
		fieldsTbl2=new ArrayList<FieldDef>();
		fieldsTbl2.add(new FieldDef("Feld",valuesTbl2));
		SQLTable tbl1=new SQLTable("Tbl",fieldsTbl1);
		SQLTable tbl2=new SQLTable("Tbl",fieldsTbl2);
		assertFalse(tbl1.equals(tbl2));
	}
	@Test
	public void testNotEqualInField() {
		int x=0;
		ArrayList<Object> valuesTbl1=new ArrayList<Object>(),valuesTbl2=new ArrayList<Object>();
		ArrayList<FieldDef> fieldsTbl1=null,fieldsTbl2=null;
		for(x=0;x<=10;x++){
			valuesTbl1.add("Testdaten"+x);
			valuesTbl2.add("Testdaten"+x);
		}
		fieldsTbl1=new ArrayList<FieldDef>();
		fieldsTbl1.add(new FieldDef("Feld",valuesTbl1));
		fieldsTbl2=new ArrayList<FieldDef>();
		fieldsTbl2.add(new FieldDef("Feld1",valuesTbl2));
		SQLTable tbl1=new SQLTable("Tbl",fieldsTbl1);
		SQLTable tbl2=new SQLTable("Tbl",fieldsTbl2);
		assertFalse(tbl1.equals(tbl2));
	}
	@Test
	public void testNotEqualInTableName() {
		int x=0;
		ArrayList<Object> valuesTbl1=new ArrayList<Object>(),valuesTbl2=new ArrayList<Object>();
		ArrayList<FieldDef> fieldsTbl1=null,fieldsTbl2=null;
		for(x=0;x<=10;x++){
			valuesTbl1.add("Testdaten"+x);
			valuesTbl2.add("Testdaten"+x);
		}
		fieldsTbl1=new ArrayList<FieldDef>();
		fieldsTbl1.add(new FieldDef("Feld",valuesTbl1));
		fieldsTbl2=new ArrayList<FieldDef>();
		fieldsTbl2.add(new FieldDef("Feld",valuesTbl2));
		SQLTable tbl1=new SQLTable("Tbl",fieldsTbl1);
		SQLTable tbl2=new SQLTable("Tbl1",fieldsTbl2);
		assertFalse(tbl1.equals(tbl2));
	}
}
