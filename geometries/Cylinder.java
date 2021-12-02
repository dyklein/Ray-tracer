package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;

/**
 * class Cylinder is cylinder in a 3d space
 * 
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Cylinder extends Tube {
	/**
	 * height of the tube
	 */
	private double height;

	/**
	 * constructor of a cylinder
	 * 
	 * @param a ray of the cylinder
	 * @param r radius of the cylinder
	 * @param h height of the cylinder
	 */
	public Cylinder(Ray a, double r, double h) {
		super(a, r);
		this.height = h;
	}

	/**
	 * get height of the cylinder
	 * 
	 * @return this height
	 */
	public double getHeight() {
		return this.height;
	}

	/**
	 * get the normal of a cylinder
	 * 
	 * @param point point 3d
	 * @return return the normal
	 */
	@Override
	public Vector getNormal(Point3D point) {

		// if t == 0 | t == h than the point is on the base else do the tube one.
		// Check if the dot product will return 0
		try {
			alignZero(this.axisRay.getVector().dotProduct(point.subtract(this.axisRay.getP0())));
		} catch (Exception e) {
			return this.axisRay.getVector().normalize();
		}
		double t = alignZero(this.axisRay.getVector().dotProduct(point.subtract(this.axisRay.getP0())));
		if (isZero(t) | isZero(t - this.height)) {
			return this.axisRay.getVector().normalize();
		}

		Vector o = new Vector(this.axisRay.getP0()).add(this.axisRay.getVector().scale(t));
		return new Vector((point.subtract(o.getHead())).getHead()).normalize();

	}

	// use zero.
	/**
	 * get the values of the members in cylinder
	 * 
	 * @return return a string of the members
	 */
	@Override
	public String toString() {
		return "Height" + this.height + "\nAxis Ray: " + this.axisRay.toString() + "\nRadius: " + this.radius;
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
		// bonus points
		return null;
	}
}
