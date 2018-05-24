package gis.tools.test;

import org.junit.Test;

import gis.tools.gdal.WKT2WKB;

public class WKT2WKBTest {
	
@Test	
public void wkt2wkbTestSuite(){
	String [] myList = {"POINT(47.0 19.2)","POINT(48.0 20.2)"};
	wkt2wkbTestCase(myList);
		
	}
	
private void wkt2wkbTestCase(String [] myList){
	WKT2WKB wKT2WKB=new WKT2WKB();
	wKT2WKB.convertion(myList);
	
}
	
	
	

}
