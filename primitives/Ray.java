package primitives;

import java.util.ArrayList;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;

/**
 * Ray - a foundational object in geometry the group of points on a straight
 * line that are on one relative side To a given point on the line called the
 * beginning of the foundation. Defined by point and direction (unit vector) ...
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Ray {
	/**
	 * point in 3d space tail
	 */
	private Point3D p0;
	/**
	 * direction of the ray head
	 */
	private Vector dir;
	/**
	 * A constant field for the size of moving ray’s heads for shadow rays
	 */
	private static final double DELTA = 0.1;

	/**
	 * constructor that take in coordinates
	 * 
	 * @param vec   vector
	 * @param point point
	 */
	public Ray(Point3D point, Vector vec) {
		this.dir = vec.normalized();
		this.p0 = point;
	}

	/**
	 * constructor that take in coordinates
	 * 
	 * @param point     point
	 * @param direction direction of ray
	 * @param normal    normal
	 */
	public Ray(Point3D point, Vector direction, Vector normal) {
		// The sign + or is according to the sign of direction·normal
		// The real ray head will be head + normal.scale DELTA)
		double d = direction.dotProduct(normal) >= 0 ? DELTA : -DELTA;
		p0 = point.add(normal.scale(d));
		dir = direction.normalized();
	}

	/**
	 * get the vector of the ray
	 * 
	 * @return vector of the array
	 */
	public Vector getVector() {
		return this.dir;

	}

	/**
	 * get the point of the ray
	 * 
	 * @return point3d of the ray
	 */
	public Point3D getP0() {
		return this.p0;

	}

	/**
	 * Return the p0 + the ray vector times a scaler
	 * 
	 * @param t t is a scaler
	 * @return return the string of the members
	 */
	public Point3D getPoint(double t) {
		// P=P0+t∙v
		return this.p0.add(this.dir.scale(t));
	}

	/**
	 * find the point with minimal distance from the ray head point and return it
	 * 
	 * @param points List of points
	 * @return points that is the closest
	 */
	public Point3D findClosestPoint(List<Point3D> points) {
		Point3D result = null;
		// check if its a empty list
		if (points == null) {
			return result;
		}
		double distance = Double.MAX_VALUE;
		for (Point3D point : points) {
			double temp = point.distance(this.p0);
			if (temp < distance) {
				if (result == null) {
					result = new Point3D(point.x, point.y, point.z);

				} else {
					result = point;
				}
				distance = temp;
			}
		}

		return result;
	}

	public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoint) {
		GeoPoint result = null;
		// check if its a empty list
		if (geoPoint == null) {
			return result;
		}
		double distance = Double.MAX_VALUE;
		for (GeoPoint geo : geoPoint) {
			double temp = geo.point.distance(this.p0);
			if (temp < distance) {
				if (result == null) {
					result = new GeoPoint(geo.geometry, geo.point);

				} else {
					result = geo;
				}
				distance = temp;
			}
		}

		return result;
	}

	/**
	 * create a beam of rays
	 * 
	 * @param n   normal
	 * @param dis distance
	 * @param num number of rays
	 * @return list of rays
	 */
	public List<Ray> beamOfRays(Vector n, double dis, int num) {
		if (num == 1) {
			return List.of(this);
		}
		List<Ray> beam = new ArrayList<>();

		Vector w = this.getVector().getNormalToVector();
		Vector v = this.getVector().crossProduct(w).normalize();

		Point3D center = this.getPoint(dis);
		Point3D ranP = Point3D.ZERO;
		double ranX;
		double ranY;
		double random;
		double normalDot = alignZero(n.dotProduct(this.getVector()));
		double r = Math.abs(Math.tan(Math.acos(w.dotProduct(v))));
		for (int i = 1; i < num; i++) {
			ranX = randomNumber(-1, 1);
			ranY = Math.sqrt(1 - Math.pow(ranX, 2));
			random = randomNumber(-r, r);
			if (!isZero(ranX))
				ranP = center.add(w.scale(random));
			if (!isZero(ranY))
				ranP = center.add(v.scale(random));
			Vector t = ranP.subtract(this.p0);
			double normalDotT = alignZero(n.dotProduct(t));
			if (normalDot * normalDotT > 0)
				beam.add(new Ray(this.p0, t));
		}
		return beam;
	}

	/**
	 * get random number
	 * 
	 * @param min min number
	 * @param max max number
	 * @return random number[min, max)
	 */
	public static double randomNumber(double min, double max) {
		double random = Math.random() * (max - min) + min;
		return random;
	}

	/**
	 * overloads the equals method to compare two rays
	 * 
	 * @param obj second ray
	 * @return return tru if obj are the same and false if they are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return this.p0.equals(other.p0) && this.dir.equals(other.dir);
	}

	/**
	 * print out the members of the ray
	 * 
	 * @return return the string of the members
	 */
	@Override
	public String toString() {
		return "Point: " + this.p0.toString() + " Vector: " + this.dir.toString();
	}

}
