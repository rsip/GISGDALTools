/**
 * 
 */
package gis.tools.test;

import gis.tools.gdal.Ogr2ogr;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author A.Riaydh
 *
 */
public class Ogr2ogrTest {
	@Test	
	public void mainOgr2ogrTestSuite(){
		
		String [] params1={"ogr2ogr","-f","KML C://gis_dataset/gdal_scripts/bd_da_roads_2.kml C://gis_dataset/geomatic3_gis_dataset/Scripting_Assignment/thematic_layer/bd_da_roads.shp"};
		
		String [] params2={"ogr2ogr",
				"-f ESRI Shapefile",
				"C://gis_dataset/gdal_scripts/bd_da_roads_1.kml",
				"C://gis_dataset/geomatic3_gis_dataset/Scripting_Assignment/thematic_layer/bd_da_roads.shp"};
	
		String [] params3={"ogr2ogr","-f",
				"C://gis_dataset/gdal_scripts/bd_division.kml",
				"PG:host=localhost dbname=uv_dataset user=postgres password=planner",
				"-sql",
				"SELECT * from bd_division"};
		
		// postgis database table file to kml file conversion
		//ogr2ogr -f KML C:\gis_dataset\gdal_scripts\bd_division.kml PG:"host=localhost dbname=uv_dataset user=postgres password=planner" -sql "SELECT * from bd_division"

		
		
	
		System.out.println(Arrays.toString(params3));
		
		//mainOgr2ogrTestCase(params1,"gdalinfo --formats");
		//mainOgr2ogrTestCase(params2,"gdalinfo --formats");
		mainOgr2ogrTestCase(params3,"gdalinfo --formats");
		
		
		}
		
	private void mainOgr2ogrTestCase(String [] params, String print){
		System.out.println(print);
		Ogr2ogr ogr2ogr=new Ogr2ogr();
		ogr2ogr.mainOgr2ogr(params);
		
	}
	
}
