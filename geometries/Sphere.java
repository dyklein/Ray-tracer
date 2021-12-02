package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * class Sphere is sphere in a 3d space
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Sphere extends Geometry {
	/**
	 * center of the sphere
	 */
	private Point3D center;
	/**
	 * radius of the sphere
	 */
	private double radius;

	/**
	 * constructor of a sphere
	 * 
	 * @param p0 point of sphere
	 * @param r  radius of the sphere
	 */
	public Sphere(double r, Point3D p0) {
		this.center = p0;
		this.radius = r;
	}

	/**
	 * get center of the circle
	 * 
	 * @return this point3d
	 */
	public Point3D getCenter() {
		return this.center;
	}

	/**
	 * get the radius of circle
	 * 
	 * @return this radius
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * get the normal of the sphere
	 * 
	 * @param point point 3d
	 * @return return a normal
	 */
	@Override
	public Vector getNormal(Point3D point) {

		// n = normalize(p -o)
		Vector returnVector = new Vector((point.subtract(this.center)).getHead());
		returnVector.normalize();
		return returnVector;

	}

	/**
	 * get the values of the members in sphere
	 * 
	 * @return return a string of the members
	 */
	@Override
	public String toString() {
		return "Center: " + this.center.toString() + "\nRadius: " + this.radius;
	}

	/**
	 * get a ray that and check if it intersects with a objects
	 * 
	 * @param ray         ray
	 * @param maxDistance distance
	 * @return list of points of intersection
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		Vector u;
		// test for zero vector
		try {
			// u= O-p0
			u = this.center.subtract(ray.getP0());
		} catch (IllegalArgumentException e) {
			// check if ray are close to light source
			if (alignZero(this.radius - maxDistance) <= 0) {
				// return List.of(ray.getPoint(this.radius));
				return List.of(new GeoPoint(this, ray.getPoint(this.radius)));
			} else {
				return null;
			}

		}

		// tm=v.u
		double tm = alignZero(ray.getVector().dotProduct(u));
		// d = sqr(|u|^2-tm^2)
		double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm)));

		// if ( d >= r) there are no intersections
		if ((d - this.radius) >= 0) {
			return null;
		}

		// th=sqr(r^2-d^2)
		double th = Math.sqrt((this.radius * this.radius) - (d * d));
		// t1,t2=tm+/-th
		double t1 = alignZero(tm - th);
		double t2 = alignZero(tm + th);
		// take only t>0
		if (t1 <= 0 && t2 <= 0) {
			return null;
		}
		if (t1 > 0 && t2 > 0) {
			if (alignZero(maxDistance - t1) > 0 && alignZero(maxDistance - t2) > 0) {
				return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
			}
		}
		// take only t>0
		if (alignZero(maxDistance - t1) > 0 && t1 > 0) {
			// Pi=P0+Ti.v
			return List.of(new GeoPoint(this, ray.getPoint(t1)));
		}
		// take only t>0
		if (t2 > 0 && alignZero(maxDistance - t2) > 0) {
			// Pi=P0+Ti.v

			return List.of(new GeoPoint(this, ray.getPoint(t2)));

		}

		return null;

	}

}
