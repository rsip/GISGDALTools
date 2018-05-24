/**
 * 
 */
package gis.tools.geotools;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.filter.Filter;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.swing.data.JDataStoreWizard;
import org.geotools.swing.wizard.JWizard;

/**
 * @author A.Riaydh
 *
 */
public class UvsoftgroupQueryGeoTools {
private FileDataStore fileDataStore;
private DataStore dataStore;
private SimpleFeatureSource simpleFeatureSource;


public UvsoftgroupQueryGeoTools() {
	try{
		initDataStore();
		initFeatureSource();
	}
	catch(Exception ex){
		throw new IllegalStateException("Fail to find the data source!", ex);
	}
}

private void initDataStore() throws Exception{
	ClassLoader classLoader=UvsoftgroupQueryGeoTools.class.getClassLoader();
	URL fileUrl= classLoader.getResource("/poly.shp");
	fileDataStore=FileDataStoreFinder.getDataStore(fileUrl);	
}
private void initFeatureSource() throws Exception{
	simpleFeatureSource=fileDataStore.getFeatureSource();
	
}

public SimpleFeatureSource getFeatureSource() throws Exception{
	return simpleFeatureSource;
	
}


public SimpleFeatureCollection findFeatureByName() throws Exception{
	Filter filter=(Filter) ECQL.toFilter("columnName=value");
	SimpleFeatureCollection result=simpleFeatureSource.getFeatures(filter);
	return result;
	
}

public SimpleFeatureCollection findFeatureByGreaterThandArea() throws Exception{
	Filter filter=(Filter) ECQL.toFilter("columnName>=value");
	SimpleFeatureCollection result=simpleFeatureSource.getFeatures(filter);
	return result;
}

public SimpleFeatureCollection findFeatureContainPoint() throws Exception{
	Filter filter=(Filter) ECQL.toFilter("CONTAINS(the_geom, POINT(90.22,23,66))");
	SimpleFeatureCollection result=simpleFeatureSource.getFeatures(filter);
	return result;
	
}

public SimpleFeatureCollection findFeatureBoundingBoxSection() throws Exception{
	Filter filter=(Filter) ECQL.toFilter("BBOX(the_geom, 90.22,23.66,90.88,23.88)");
	SimpleFeatureCollection result=simpleFeatureSource.getFeatures(filter);
	return result;
	
}

public void postgisDatabaseConnection() throws Exception {
  
    Map<String, Object> params = new HashMap<>();
    params.put("dbtype", "postgis");
    params.put("host", "localhost");
    params.put("port", 5432);
    params.put("schema", "public");
    params.put("database", "database");
    params.put("user", "postgres");
    params.put("passwd", "postgres");
    
       //map.put( "max connections", 25);
	   //map.put( "min connections", 10);
       //map.put( "connection timeout", 5);
       //map.put( "validating connections", false);
     
    
      dataStore = DataStoreFinder.getDataStore(params);
        if (dataStore == null) {
            JOptionPane.showMessageDialog(null, "Could not connect - check parameters");
        }
        else{
        	JOptionPane.showMessageDialog(null, "Connect successful- check parameters!");	
        }
    }



}
