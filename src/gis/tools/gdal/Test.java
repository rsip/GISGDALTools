package gis.tools.gdal;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

/**
 * Test class for GDAL java bindings 
 */
public class Test {
 Dataset hDataset;
 int numBands;
 public Test(String filename){
  gdal.AllRegister();
  hDataset = gdal.Open(filename, gdalconstConstants.GA_ReadOnly);
  this.numBands = hDataset.getRasterCount();
 }
 /**
  * @param args
  */
 public static void main(String[] args) {
  if(args.length == 0){
   System.out.println("You must pass the file name as an argument");
  } else {
  Test instance = new Test("bd_da_roads");
  System.out.println(instance.numBands);
  }
 }
}