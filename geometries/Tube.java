package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class tube is Tube in a 3d space
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Tube extends Geometry {
	/**
	 * axis of the tube
	 */
	protected Ray axisRay;
	/**
	 * radius of the tube
	 */
	protected double radius;

	/**
	 * constructor of a tube
	 * 
	 * @param a ray of the tube
	 * @param r radius of the tube
	 */
	public Tube(Ray a, double r) {
		this.axisRay = a;
		if (r == 0) {
			throw new IllegalArgumentException("tube radius can not be zero");
		}
		this.radius = r;
	}

	/**
	 * get the normal of a tube
	 * 
	 * @param point point 3d
	 * @return return the normal
	 */
	@Override
	public Vector getNormal(Point3D point) {
		// t = v . (p - p0)
		double t = this.axisRay.getVector().dotProduct(point.subtract(this.axisRay.getP0()));
		// o = p0 + V * t
		// check if the scale vector will be the zero vector.
		if (t == 0) {
			Vector o = new Vector(new Point3D(0, 0, 0));
			// n = normalize(p -o)
			return new Vector((point.subtract(o.getHead())).getHead()).normalize();
		} else {
			Vector o = new Vector(this.axisRay.getP0()).add(this.axisRay.getVector().scale(t));
			// n = normalize(p -o)
			return new Vector((point.subtract(o.getHead())).getHead()).normalize();
		}

	}

	/**
	 * get the values of the members in tube
	 * 
	 * @return return a string of the members
	 */
	@Override
	public String toString() {
		return "Axis Ray: " + this.axisRay.toString() + "\nRadius: " + this.radius;
	}

	/**
	 * get a ray that and check if it intersects with a objects
	 * 
	 * @return list of points of intersection
	 * @param maxDistance distance
	 * @param ray         ray
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		// bonus points
		return null;
	}
}
