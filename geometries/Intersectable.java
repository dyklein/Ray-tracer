package geometries;

import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

import primitives.Point3D;

/**
 * class Intersectable to define a method which receives a ray and returns its
 * intersection points with the geometry in the geometries classes
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public interface Intersectable {
	/**
	 * get the intersection point
	 * 
	 * @param ray ray
	 * @return return list of point 3d
	 */
	default List<Point3D> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	/**
	 * find intersection points that cross the ray
	 * 
	 * @param ray ray
	 * @return all the intersection point
	 */
	default List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * find intersection points that cross the ray
	 * 
	 * @param ray         ray
	 * @param maxDistance max distance
	 * @return all the intersection point
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

	/**
	 * point and it geometry
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		public GeoPoint(Geometry geo, Point3D p) {
			this.geometry = geo;
			this.point = p;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint other = (GeoPoint) obj;
			return this.geometry.equals(other.geometry) && this.point.equals(other.point);
		}

	}

}
