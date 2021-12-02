package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class representing a point light
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class PointLight extends Light implements LightSource {

	/**
	 * location
	 */
	private Point3D position;
	/**
	 * Kc
	 */
	private double kC = 1;
	/**
	 * Kl
	 */
	private double kL = 0;
	/**
	 * Kq
	 */
	private double kQ = 0;

	/**
	 * constructor for point light
	 * 
	 * @param color color
	 * @param p     location
	 * @param c     direction kC
	 * @param l     direction kL
	 * @param q     direction kQ
	 */
	public PointLight(Color color, Point3D p, double c, double l, double q) {
		this.intensity = color;
		this.position = p;
		this.kC = c;
		this.kL = l;
		this.kQ = q;
	}

	/**
	 * constructor for point
	 * 
	 * @param color color
	 * @param p     location
	 */
	public PointLight(Color color, Point3D p) {
		this.intensity = color;
		this.position = p;
	}

	/**
	 * set Kc
	 * 
	 * @param c kc
	 * @return object
	 */
	public PointLight setKc(double c) {
		this.kC = c;
		return this;
	}

	/**
	 * set Kl
	 * 
	 * @param l kl
	 * @return object
	 */
	public PointLight setKl(double l) {
		this.kL = l;
		return this;
	}

	/**
	 * set Kq
	 * 
	 * @param q kq
	 * @return object
	 */
	public PointLight setKq(double q) {
		this.kQ = q;
		return this;
	}

	/**
	 * get the intensity
	 * 
	 * @param p Point 3d
	 * @return Color of the intensity
	 */
	@Override
	public Color getIntensity(Point3D p) {
		// il = (i0/(kc+kl*kq*d^2))
		return this.intensity
				.reduce(this.kC + this.kL * this.position.distance(p) + this.kQ * this.position.distanceSquared(p));
	}

	/**
	 * get the direction
	 * 
	 * @param p point 3d
	 * @return direction vector
	 */
	@Override
	public Vector getL(Point3D p) {
		if (p.equals(this.position)) {
			return null;
		}
		return p.subtract(this.position).normalized();
	}

	/**
	 * get the distance of a point
	 * 
	 * @param p point 3d
	 * @return distance
	 */
	@Override
	public double getDistance(Point3D p) {
		return position.distance(p);
	}

}