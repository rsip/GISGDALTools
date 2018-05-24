/**
 * 
 */
package gis.tools.test;

import gis.tools.gdal.Gdalinfo;

import org.junit.Test;
import gis.tools.gdal.GDALOverviews;

/**
 * @author A.Riaydh
 *
 */
public class GDALOverviewsTest {
	@Test	
	public void mainGDALOverviewsTestSuite(){
			
		String [] params1={"gdaloverviews", "byte.tiff", "C://javaworkspace/GISGDALTools/resources/byte.tiff 2 4"};
		mainGDALOverviewsTestCase(params1,"gdaloverviews");
		
		}
		
	private void mainGDALOverviewsTestCase(String [] params, String print){
		System.out.println(print);
		GDALOverviews gDALOverviews=new GDALOverviews();
		gDALOverviews.mainGDALOverviews(params);
		
	}
	

}
