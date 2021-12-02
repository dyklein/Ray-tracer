package primitives;

/**
 * Point3D (point in space) - a fundamental object in geometry - a point with 3
 * coordinates
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Point3D {
	/**
	 * coordinates of a point in 3D space represented by 3 points of the x y z axis
	 */
	final Coordinate x;
	final Coordinate y;
	final Coordinate z;

	/**
	 * Zero static constant containing a period (0,0,0)
	 */
	public static final Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

	/**
	 * constructor that take in coordinates
	 * 
	 * @param x first coordinate
	 * @param y second coordinate
	 * @param z third coordinate
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * constructor that takes in doubles
	 * 
	 * @param x first coordinate
	 * @param y second coordinate
	 * @param z third coordinate
	 */
	public Point3D(double x, double y, double z) {
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}

	/**
	 * get x coordinates
	 * 
	 * @return x value
	 */
	public double getX() {
		return this.x.coord;
	}

	/**
	 * get y coordinates
	 * 
	 * @return y value
	 */
	public double getY() {
		return this.y.coord;
	}

	/**
	 * get z coordinates
	 * 
	 * @return z value
	 */
	public double getZ() {
		return this.z.coord;
	}

	/**
	 * Adds a vector to a point
	 * 
	 * @param vec VECTOR
	 * @return a new point
	 */
	public Point3D add(Vector vec) {
		return new Point3D((this.x.coord + vec.getHead().x.coord), (this.y.coord + vec.getHead().y.coord),
				(this.z.coord + vec.getHead().z.coord));

	}

	/**
	 * vector subtraction - receives a second point in the parameter,
	 * 
	 * @param point in 3d space
	 * @return a vector from the second point to the point on which the operation is
	 *         performed
	 */
	public Vector subtract(Point3D point) {
		return new Vector((this.x.coord - point.x.coord), (this.y.coord - point.y.coord),
				(this.z.coord - point.z.coord));

	}

	/**
	 * distance between two points in 3d space squared
	 * 
	 * @param point point 3d
	 * @return the result
	 */
	public double distanceSquared(Point3D point) {
		return (((this.x.coord - point.x.coord) * (this.x.coord - point.x.coord))
				+ ((this.y.coord - point.y.coord) * (this.y.coord - point.y.coord))
				+ ((this.z.coord - point.z.coord) * (this.z.coord - point.z.coord)));

	}

	/**
	 * distance between to points in 3d space
	 * 
	 * @param point 3d
	 * @return the result
	 */
	public double distance(Point3D point) {
		return Math.sqrt(distanceSquared(point));
	}

	/**
	 * overloads the equals method to compare two points 3d
	 * 
	 * @param obj point 3d
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D other = (Point3D) obj;
		return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
	}

	/**
	 * print out the members of point 3d
	 * 
	 * @return return the string of the members
	 */
	@Override
	public String toString() {
		return "(" + this.x.toString() + "," + this.y.toString() + "," + this.z.toString() + ")";
	}

}
