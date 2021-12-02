package primitives;

/**
 * class Vector is a fundamental object in geometry with direction and size,
 * defined by the end point
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Vector {
	/**
	 * head of the vector
	 */
	private Point3D head;

	/**
	 * constructor taking in 3 coordinates
	 * 
	 * @param pX first coordinate
	 * @param pY second coordinate
	 * @param pZ third coordinate
	 * @throws IllegalArgumentException vector cant be zero
	 */
	public Vector(Coordinate pX, Coordinate pY, Coordinate pZ) {
		this.head = new Point3D(pX, pY, pZ);
		checkZero(this.head);
	}

	/**
	 * constructor taking in 3 doubles
	 * 
	 * @param pX first coordinate
	 * @param pY second coordinate
	 * @param pZ third coordinate
	 * @throws IllegalArgumentException vector cant be zero
	 */
	public Vector(double pX, double pY, double pZ) {
		this.head = new Point3D(pX, pY, pZ);
		checkZero(this.head);
	}

	/**
	 * constructor taking in 3d point
	 * 
	 * @param p0 3d point
	 */
	public Vector(Point3D p0) {
		this.head = p0;
		// checkZero(this.head);
	}

	/**
	 * check if its the zero vector
	 * 
	 * @param point 3d point
	 * @throws IllegalArgumentException vector cant be zero
	 */
	public void checkZero(Point3D point) {
		if (this.head.equals(Point3D.ZERO)) {
			throw new IllegalArgumentException("vector can not zero vector");
		}
	}

	/**
	 * get head
	 * 
	 * @return this head
	 */
	public Point3D getHead() {
		return this.head;
	}

	/**
	 * add vectors
	 * 
	 * @param vec a vector
	 * @return new vector
	 */
	public Vector add(Vector vec) {
		return new Vector((this.head.x.coord + vec.head.x.coord), (this.head.y.coord + vec.head.y.coord),
				(this.head.z.coord + vec.head.z.coord));

	}

	/**
	 * subtract vectors
	 * 
	 * @param vec a vector
	 * @return new vector
	 */
	public Vector subtract(Vector vec) {
		return new Vector((this.head.x.coord - vec.head.x.coord), (this.head.y.coord - vec.head.y.coord),
				(this.head.z.coord - vec.head.z.coord));

	}

	/**
	 * scale a vector
	 * 
	 * @param d a scaler number
	 * @return new vector
	 */
	public Vector scale(double d) {
		return new Vector((this.head.x.coord * d), (this.head.y.coord * d), (this.head.z.coord * d));

	}

	/**
	 * cross product of two vectors
	 * 
	 * @param vec a vector
	 * @return the result
	 */
	public Vector crossProduct(Vector vec) {
		double tempX, tempY, tempZ;
		tempX = ((this.head.y.coord * vec.head.z.coord) - (this.head.z.coord * vec.head.y.coord));
		tempY = ((this.head.z.coord * vec.head.x.coord) - (this.head.x.coord * vec.head.z.coord));
		tempZ = ((this.head.x.coord * vec.head.y.coord) - (this.head.y.coord * vec.head.x.coord));
		return new Vector(tempX, tempY, tempZ);

	}

	/**
	 * dot product of two vectors
	 * 
	 * @param vec a vector
	 * @return the result
	 */
	public double dotProduct(Vector vec) {
		return ((this.head.x.coord * vec.head.x.coord) + (this.head.y.coord * vec.head.y.coord)
				+ (this.head.z.coord * vec.head.z.coord));

	}

	/**
	 * length of a vector squared
	 * 
	 * @return the result
	 */
	public double lengthSquared() {
		return ((this.head.x.coord * this.head.x.coord) + (this.head.y.coord * this.head.y.coord)
				+ (this.head.z.coord * this.head.z.coord));
	}

	/**
	 * length of a vector
	 * 
	 * @return the result
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * vector normalize
	 * 
	 * @return the result
	 */
	public Vector normalize() {
		this.head = this.scale(1 / this.length()).head;
		return this;
	}

	/**
	 * vector normalized
	 * 
	 * @return the result
	 */
	public Vector normalized() {
		Vector vec = new Vector(this.head.x, this.head.y, this.head.z);
		return vec.normalize();
	}

	/**
	 * get normal to vector
	 * 
	 * @return returns the normal to this vector
	 */
	public Vector getNormalToVector() {
		double coordinate;
		if (this.head.x.coord > 0) {
			coordinate = this.head.x.coord;
		} else {
			coordinate = -this.head.x.coord;
		}
		if (Math.abs(this.head.y.coord) < coordinate) {
			coordinate = 1;
			if (this.head.y.coord > 0) {
				coordinate = this.head.y.coord;
			} else {
				coordinate = -this.head.y.coord;
			}
		}
		if (Math.abs(this.head.z.coord) < coordinate) {
			coordinate = 2;
		}
		// check if x is the smallest
		if (coordinate == 0) {
			return new Vector(0, -this.head.z.coord, this.head.y.coord).normalize();
			// check if y is the smallest
		} else if (coordinate == 1)// y is the the smallest
			return new Vector(-this.head.z.coord, 0, this.head.x.coord).normalize();
		// else z is the smallest
		else {
			return new Vector(this.head.y.coord, -this.head.x.coord, 0).normalize();
		}
	}

	/**
	 * overloads the equals method to compare two vectors
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
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return this.head.equals(other.head);
	}

	/**
	 * return the string of the members in vector
	 * 
	 * @return string of the members in vector
	 */
	@Override
	public String toString() {
		return "Vector Head: " + this.head.toString();
	}
}
