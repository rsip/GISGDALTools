package gis.tools.gdal;
import org.gdal.ogr.Geometry;

public class ShowCoordinate {


public static void showCoords(Geometry geom){
		double[][] coords =geom.GetPoints();
		for(int i=0;i<coords.length;i++){
			 if(i>0)
			 System.out.print(",");
			 if(coords[i].length==2)
			 System.out.print(coords[i][0]+""+coords[i][1]);
	         else
	        	 System.out.print(coords[i][0]+""+coords[i][1]+""+coords[i][2]);
	        	 System.out.print("\n");
			
		}
	}	
		
	
public static void showCoords(Geometry geom, int nCoordDimension){
	double[][] coords =geom.GetPoints(nCoordDimension);
	for(int i=0;i<coords.length;i++){
		 if(i>0)
		 System.out.print(",");
		 if(coords[i].length==2)
		 System.out.print(coords[i][0]+""+coords[i][1]);
         else
        	 System.out.print(coords[i][0]+""+coords[i][1]+""+coords[i][2]);
        	 System.out.print("\n");
		
	}
}		


}
