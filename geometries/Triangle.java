package geometries;

import java.util.ArrayList;
import java.util.List;
import static primitives.Util.*;

import primitives.*;

/**
 * class Triangle is polygon with vertexes in a 3d space
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Triangle extends Polygon {
	/**
	 * constructor of a triangle
	 * 
	 * @param p1 point of triangle
	 * @param p2 point of triangle
	 * @param p3 point of triangle
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		super(p1, p2, p3);
	}

	/**
	 * get the values of the members in triangle
	 * 
	 * @return return a string of the members
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * get a ray that and check if it intersects with a objects
	 * 
	 * @param ray         ray
	 * @param maxDistance max distance
	 * @return list of points of intersection
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

		// list of all the points
		// List<Point3D> points = new ArrayList<Point3D>();
		Point3D p0 = ray.getP0();

		// get all the points in the triangle
		// foreach (Point3d point3d : this.vertices) {
		// points.add(point3d);
		// }

		// get all Vi in the triangle
		List<Vector> vec = new ArrayList<Vector>();
		for (int i = 0; i < 3; i++) {
			vec.add(vertices.get(i).subtract(p0));
		}

		// Check if the intersection point with its plane is inside the polygon/triangle
		List<GeoPoint> planeResult = plane.findGeoIntersections(ray, maxDistance);
		if (planeResult != null) {
			// signs
			boolean sign = false;
			// normal
			Vector n;

			for (int i = 0; i < 3; i++) {
				n = vec.get(i).crossProduct(vec.get((i + 1) % 3)).normalize();
				// Constraint compromise: if one or more are 0.0 no intersection
				if (isZero(ray.getVector().dotProduct(n))) {
					return null;
				}
				// The point is inside if all v.Ni have the same sign (+/-)
				double inside = ray.getVector().dotProduct(n);
				// dont update on the first pass through
				if (i == 0) {
					sign = (inside > 0);
				} else {
					if (sign != (inside > 0)) {
						return null;
					}
				}
			}
			planeResult.get(0).geometry = this;
			return planeResult;
		}
		return null;

	}
}
