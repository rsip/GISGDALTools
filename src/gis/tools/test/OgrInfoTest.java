/**
 * 
 */
package gis.tools.test;

import gis.tools.gdal.OgrInfo;

import org.junit.Test;

/**
 * @author A.Riaydh
 *
 */
public class OgrInfoTest {
	
	@Test	
	public void mainOgrInfoTestSuite(){

		String [] params2={"ogrinfo","--formats"};
		
		//mainOgrInfoTestCase(params1,"gdalinfo --formats");
		mainOgrInfoTestCase(params2,"ogrinfo --formats");
		
		}
		
	private void mainOgrInfoTestCase(String [] params, String print){
		System.out.println(print);
		OgrInfo ogrInfo=new OgrInfo();
		ogrInfo.mainOgrInfo(params);
		
	}

}
