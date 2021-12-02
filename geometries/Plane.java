package geometries;

import primitives.*;
import static primitives.Util.*;
import java.util.List;

/**
 * class plane is a Plane a point and vector 3d space
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Plane extends Geometry {
	/**
	 * point on the plane
	 */
	protected Point3D q0;
	/**
	 * vector that is orthogonal to the plane
	 */
	protected Vector normal;

	/**
	 * constructor of a plane with 3 points and a normal
	 * 
	 * @param p1 point of plane
	 * @param p2 point of plane
	 * @param p3 point of plane
	 * @param v  normal vector point
	 */
	public Plane(Point3D p1, Point3D p2, Point3D p3, Vector v) {
		this.q0 = p1;
		this.normal = v.normalized();
	}

	/**
	 * constructor of a plane with 3 points
	 * 
	 * @param p1 point of plane
	 * @param p2 point of plane
	 * @param p3 point of plane
	 */
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		this.q0 = p1;

		Vector a = p2.subtract(p1);
		Vector b = p3.subtract(p1);
		this.normal = a.crossProduct(b).normalize();
	}

	/**
	 * get point 3d of the plane
	 * 
	 * @return this point 3d
	 */
	public Point3D getPoint() {
		return this.q0;
	}

	/**
	 * Not sure if he wants a plain get normal for polygon get normal of the plane a
	 * vector that is orthogonal to the plane
	 * 
	 * @return this normal
	 */
	public Vector getNormal() {
		return this.normal;
	}

	/**
	 * get the normal of a plane
	 * 
	 * @param point point 3d
	 * @return return the normal
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return this.normal;
	}

	/**
	 * get the values of the members in plane
	 * 
	 * @return return a string of the members
	 */
	@Override
	public String toString() {
		return "Point: " + this.q0.toString() + "\nNormal: " + this.normal.toString();
	}

	/**
	 * get a ray that and check if it intersects with a objects
	 * 
	 * @param ray         a ray that might intersect a plane
	 * @param maxDistance distance
	 * @return list of points of intersection
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

		Point3D p0 = ray.getP0();

		// check if q0 = po
		if (p0.equals(q0)) {
			return null;
		}
		// normal
		Vector n = this.normal;
		Vector v = ray.getVector();

		double numerator = alignZero(n.dotProduct(q0.subtract(p0)));
		double denominator = alignZero(n.dotProduct(v));

		// check for zeroes in denominator and numerator,
		if (isZero(numerator) || isZero(denominator)) {
			return null;
		}

		// t = N . (q0 - p0) / N . v
		double t = alignZero(numerator / denominator);

		if (t <= 0) {
			return null;
		}
		// take only t>0
		if (alignZero(t - maxDistance) <= 0) {
			// return intersectionPoint;
			return List.of(new GeoPoint(this, ray.getPoint(t)));
		}
		return null;

	}

}
