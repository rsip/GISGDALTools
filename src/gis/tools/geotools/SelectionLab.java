package gis.tools.geotools;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;

/**
 * 
 * @author A.Riaydh
 *
 */
public class SelectionLab {
    // Factories that we will use to create style and filter objects
    private StyleFactory sf = CommonFactoryFinder.getStyleFactory();
    private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
    //Convenient constants for the type of feature geometry in the shapefile
    private enum GeomType {POINT,LINE,POLYGON};
    //Some default style variables
    private static final Color LINE_COLOUR = Color.BLUE;
    private static final Color FILL_COLOUR = Color.MAGENTA;
    private static final Color SELECTED_COLOUR = Color.GREEN;
    private static final float OPACITY = 1.0f;
    private static final float LINE_WIDTH = 1.0f;
    private static final float POINT_SIZE = 10.0f;
    private JMapFrame mapFrame;
    private SimpleFeatureSource featureSource;
    private String geometryAttributeName;
    private GeomType geometryType;

    public static void main(String[] args) throws Exception {
        SelectionLab selectionLab = new SelectionLab();
        File file = JFileDataStoreChooser.showOpenFile("shp", null);
        if (file == null) {
            return;
        }
        selectionLab.displayShapefile(file);
    }
    /**
     * This method connects to the shapefile; retrieves information about its features; creates a
     * map frame to display the shapefile and adds a custom feature selection tool to the toolbar of
     * the map frame.
     */
    public void displayShapefile(File file) throws Exception {
        FileDataStore fileDataStore = FileDataStoreFinder.getDataStore(file);
        featureSource =fileDataStore.getFeatureSource();
        setGeometry();
        MapContent map = new MapContent();
        map.setTitle("Feature Selection Tool");
        Style style = createDefaultStyle();
        Layer layer = new FeatureLayer(featureSource, style);
        map.addLayer(layer);
        mapFrame = new JMapFrame(map);
        mapFrame.enableToolBar(true);
        mapFrame.enableStatusBar(true);

        /*
         * Before making the map frame visible we add a new button to its
         * toolbar for our custom feature selection tool
         */
        JToolBar toolBar = mapFrame.getToolBar();
        JButton btn = new JButton("Select Feature");
        toolBar.addSeparator();
        toolBar.add(btn);

        /*
         * When the user clicks the button we want to enable
         * our custom feature selection tool. Since the only
         * mouse action we are intersted in is 'clicked', and
         * we are not creating control icons or cursors here,
         * we can just create our tool as an anonymous sub-class
         * of CursorTool.
         */
        btn.addActionListener(e->
             mapFrame.getMapPane()
                     .setCursorTool(
                      new CursorTool() {
                       @Override
                       public void onMouseClicked(MapMouseEvent ev) {
                       selectFeatures(ev);
                        }
                      }));
        mapFrame.setSize(600, 600);
        mapFrame.setVisible(true);
    }
    /**
     * This method is called by our feature selection tool when the user has clicked on the map.
     * @param ev the mouse event being handled
     */
    void selectFeatures(MapMouseEvent ev) {
        System.out.println("Mouse click Position: " + ev.getMapPosition());
        //Construct a 5x5 pixel rectangle centred on the mouse click position
        Point screenPos = ev.getPoint();
        Rectangle screenRect = new Rectangle(screenPos.x - 2, screenPos.y - 2, 5, 5);
        /*
         * Transform the screen rectangle into bounding box in the coordinate
         * reference system of our map context. Note: we are using a naive method
         * here but GeoTools also offers other, more accurate methods.
         */
        AffineTransform screenToWorld = mapFrame.getMapPane().getScreenToWorldTransform();
        Rectangle2D worldRect = screenToWorld.createTransformedShape(screenRect).getBounds2D();
        ReferencedEnvelope bbox =new ReferencedEnvelope(worldRect, mapFrame.getMapContent().getCoordinateReferenceSystem());
        //Create a Filter to select features that intersect with the bounding box
        Filter filter = ff.intersects(ff.property(geometryAttributeName), ff.literal(bbox));
        // Use the filter to identify the selected features
        try {
            SimpleFeatureCollection selectedFeatures = featureSource.getFeatures(filter);
            Set<FeatureId> IDs = new HashSet<>();
            SimpleFeatureIterator iter = selectedFeatures.features();
              while (iter.hasNext()) {
                    SimpleFeature feature = iter.next();
                    IDs.add(feature.getIdentifier());
                    System.out.println("   " + feature.getIdentifier());
                }
            if (IDs.isEmpty()) {
                System.out.println("No Feature Selected!");
            }
            else{
            	 displaySelectedFeatures(IDs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Sets the display to paint selected features yellow and unselected features in the default style.@param IDs identifiers of currently selected features
    public void displaySelectedFeatures(Set<FeatureId> ids) {
        Style style;
        if (ids.isEmpty()) {
            style = createDefaultStyle();
        } else {
            style = createSelectedStyle(ids);
        }
        Layer layer = mapFrame.getMapContent().layers().get(0);
        ((FeatureLayer) layer).setStyle(style);
        mapFrame.getMapPane().repaint();
    }
    // Create a default Style for feature display
    private Style createDefaultStyle() {
        Rule rule = createRule(LINE_COLOUR, FILL_COLOUR);
        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        fts.rules().add(rule);
        Style style = sf.createStyle();
        style.featureTypeStyles().add(fts);
        return style;
    }
    /**
     * Create a Style where features with given IDs are painted yellow, while others are painted
     * with the default colors.
     */
    private Style createSelectedStyle(Set<FeatureId> IDs) {
        Rule selectedRule = createRule(SELECTED_COLOUR, SELECTED_COLOUR);
        selectedRule.setFilter(ff.id(IDs));
        Rule otherRule = createRule(LINE_COLOUR, FILL_COLOUR);
        otherRule.setElseFilter(true);
        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        fts.rules().add(selectedRule);
        fts.rules().add(otherRule);
        Style style = sf.createStyle();
        style.featureTypeStyles().add(fts);
        return style;
    }
    /**
     * Helper for createXXXStyle methods. Creates a new Rule containing a Symbolizer tailored to the
     * geometry type of the features that we are displaying.
     */
    private Rule createRule(Color outlineColor, Color fillColor) {
        Symbolizer symbolizer = null;
        Fill fill = null;
        Stroke stroke = sf.createStroke(ff.literal(outlineColor), ff.literal(LINE_WIDTH));
        switch (geometryType) {
            case POLYGON:
                fill = sf.createFill(ff.literal(fillColor), ff.literal(OPACITY));
                symbolizer = sf.createPolygonSymbolizer(stroke, fill, geometryAttributeName);
                break;
            case LINE:
                symbolizer = sf.createLineSymbolizer(stroke, geometryAttributeName);
                break;
            case POINT:
                fill = sf.createFill(ff.literal(fillColor), ff.literal(OPACITY));
                Mark mark = sf.getCircleMark();
                mark.setFill(fill);
                mark.setStroke(stroke);
                Graphic graphic = sf.createDefaultGraphic();
                graphic.graphicalSymbols().clear();
                graphic.graphicalSymbols().add(mark);
                graphic.setSize(ff.literal(POINT_SIZE));
                symbolizer = sf.createPointSymbolizer(graphic, geometryAttributeName);
        }
        Rule rule = sf.createRule();
        rule.symbolizers().add(symbolizer);
        return rule;
    }
   
    /** Retrieve information about the feature geometry */
    private void setGeometry() {
        GeometryDescriptor geomDesc = featureSource.getSchema().getGeometryDescriptor();
        geometryAttributeName = geomDesc.getLocalName();
        Class<?> clazz = geomDesc.getType().getBinding();
        if (Polygon.class.isAssignableFrom(clazz) || MultiPolygon.class.isAssignableFrom(clazz)) {
            geometryType = GeomType.POLYGON;
        } else if (LineString.class.isAssignableFrom(clazz) || MultiLineString.class.isAssignableFrom(clazz)) {
            geometryType = GeomType.LINE;
        } else {
            geometryType = GeomType.POINT;
        }
    }
   

}
