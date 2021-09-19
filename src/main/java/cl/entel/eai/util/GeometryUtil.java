package cl.entel.eai.util;

import oracle.spatial.geometry.JGeometry;

public class GeometryUtil {

    public static JGeometry createPointFromLatLong(String x, String y) {
        return new JGeometry(new Double(x), new Double (y),4326);
    }
    public static JGeometry createPointFromLatLong(double x, double y) {
        return new JGeometry(x, y,4326);
    }

    public static JGeometry createPointFromLatLong(double x, double y, int srid) {
        return new JGeometry(x, y,srid);
    }
}
