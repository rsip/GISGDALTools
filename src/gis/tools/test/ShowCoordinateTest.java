package gis.tools.test;

import gis.tools.gdal.ShowCoordinate;


import org.gdal.ogr.Geometry;
import org.junit.Test;

public class ShowCoordinateTest {
	
	@Test	
	public void showCoordsTestSuite(){

			
		showCoordsTestCase(Geometry.CreateFromWkt("LINESTRING(0 1,2 3)"));
		showCoordsTestCase(Geometry.CreateFromWkt("LINESTRING(0 1 2,3 4 5)"));
		showCoordsTestCase(Geometry.CreateFromWkt("LINESTRING(0 1,2 3)"),3);
		showCoordsTestCase(Geometry.CreateFromWkt("LINESTRING(0 1 2,3 4 5)"),2);
		
		}
		
	private void showCoordsTestCase(Geometry geom){
		ShowCoordinate showCoordinate=new ShowCoordinate();
		showCoordinate.showCoords(geom);
		
	}
	
	private void showCoordsTestCase(Geometry geom, int nCoordDimension){
		ShowCoordinate showCoordinate=new ShowCoordinate();
		showCoordinate.showCoords(geom,nCoordDimension);
		
	}
	

}
