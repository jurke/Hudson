import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import java.util.ArrayList;

import org.junit.Test;
public class testFieldDef {
	@Test
	public void testEquals() {
		int x=0;
		ArrayList<Object> values1=new ArrayList<Object>(),values2=new ArrayList<Object>();
		ArrayList<FieldDef> fields1=new ArrayList<FieldDef>(),fields2=new ArrayList<FieldDef>();
		for(x=0;x<=10;x++){
			values1.add("Testdaten"+x);
			values2.add("Testdaten"+x);
		}
		fields1.add(new FieldDef("Feld",values1));
		fields1.add(new FieldDef("Feld",values2));
		fields2.add(new FieldDef("Feld",values1));
		fields2.add(new FieldDef("Feld",values2));
		assertEquals("Equal : ", fields1,fields2);
	}
	@Test
	public void testNotEqualInValue() {
		int x=0;
		ArrayList<Object> values1=new ArrayList<Object>(),values2=new ArrayList<Object>();
		ArrayList<FieldDef> fields1=new ArrayList<FieldDef>(),fields2=new ArrayList<FieldDef>();
		for(x=0;x<=10;x++){
			values1.add("Testdaten"+x);
			values2.add("Testdaten1"+x);
		}
		fields1.add(new FieldDef("Feld",values1));
		fields2.add(new FieldDef("Feld",values2));
		assertFalse(fields1.equals(fields2));
	}
	@Test
	public void testNotEqualInFieldName() {
		int x=0;
		ArrayList<Object> values1=new ArrayList<Object>(),values2=new ArrayList<Object>();
		ArrayList<FieldDef> fields1=new ArrayList<FieldDef>(),fields2=new ArrayList<FieldDef>();
		for(x=0;x<=10;x++){
			values1.add("Testdaten"+x);
			values2.add("Testdaten"+x);
		}
		fields1.add(new FieldDef("Feld",values1));
		fields2.add(new FieldDef("Feld1",values2));
		assertFalse(fields1.equals(fields2));
	}
	@Test
	public void testNotEqualInDim() {
		int x=0;
		ArrayList<Object> values1=new ArrayList<Object>(),values2=new ArrayList<Object>();
		ArrayList<FieldDef> fields1=new ArrayList<FieldDef>(),fields2=new ArrayList<FieldDef>();
		for(x=0;x<=10;x++){
			values1.add("Testdaten"+x);
			values2.add("Testdaten"+x);
		}
		fields1.add(new FieldDef("Feld",values1));
		fields2.add(new FieldDef("Feld",values1));
		fields2.add(new FieldDef("Feld",values2));
		assertFalse(fields1.equals(fields2));
	}
	
}
