/**
 * 
 */
package gis.tools.gdal;

import org.gdal.ogr.Geometry;

/**
 * @author A.Riaydh
 * 
 */
public class WKT2WKB {
	

public static  void convertion(String [] myList) {
	         for(String ob:myList){
	        	 Geometry geom = Geometry.CreateFromWkt(ob);
	     		int wkbSize = geom.WkbSize();
	     		byte[] wkb = geom.ExportToWkb();
	     		if (wkb.length != wkbSize) {
	     			System.exit(-1);
	     		}
	     		if (wkbSize > 0) {
	     			System.out.print("wkt-->wkb:");
	     			for (int i = 0; i < wkbSize; i++) {
	     				if (i > 0)
	     					System.out.print("-");
	     				int val = wkb[i];
	     				if (val < 0)
	     					val = 256 + val;
	     				String hexVal = Integer.toHexString(val);
	     				if (hexVal.length() == 1)
	     					System.out.print("0");
	     				System.out.print(hexVal);
	     			}
	     			System.out.print("\n");
	     // wkb --> wkt (reverse test)
	     			Geometry geom2 = Geometry.CreateFromWkb(wkb);
	     			String geom_wkt = geom2.ExportToWkt();
	     			System.out.println("wkb->wkt: " + geom_wkt);
	     		}
	     // wkt --> gml transformation
	     		String gml = geom.ExportToGML();
	     		System.out.println("wkt->gml: " + gml);
	     		Geometry geom3 = Geometry.CreateFromGML(gml);
	     		String geom_wkt2 = geom3.ExportToWkt();
	     		System.out.println("gml->wkt:" + geom_wkt2);
	     	}
	 }
	

}
