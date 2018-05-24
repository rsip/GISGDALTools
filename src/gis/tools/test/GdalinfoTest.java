/**
 * 
 */
package gis.tools.test;

import gis.tools.gdal.Gdalinfo;

import org.junit.Test;

/**
 * @author A.Riaydh
 *
 */
public class GdalinfoTest {
	
	@Test	
	public void gdalinfoGeneralTestCaseTestSuite(){
		String [] params1={"gdalinfo", "--formats"};
		gdalinfoGeneralTestCase(params1,"gdalinfo --formats");
		
		}
		
	private void gdalinfoGeneralTestCase(String [] params, String print){
		System.out.println(print);
		Gdalinfo gdalinfo=new Gdalinfo();
		gdalinfo.gdalinfoGeneral(params);
		
	}

}
