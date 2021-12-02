package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import static java.lang.Math.max;

/**
 * class representing a spot light
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class SpotLight extends PointLight {
	/**
	 * direction
	 */
	private Vector direction;

	/**
	 * spot light constructor
	 * 
	 * @param color color
	 * @param p     point 3d
	 * @param c     kc
	 * @param l     kl
	 * @param q     kq
	 * @param d     direction
	 */
	public SpotLight(Color color, Point3D p, double c, double l, double q, Vector d) {
		super(color, p, c, l, q);
		this.direction = d.normalize();
	}

	/**
	 * spot light constructor
	 * 
	 * @param color color
	 * @param p     point 3d
	 * @param d     direction
	 */
	public SpotLight(Color color, Point3D p, Vector d) {
		super(color, p);
		this.direction = d.normalize();
	}

	/**
	 * get the intensity
	 * 
	 * @param p Point 3d
	 * @return Color of the intensity
	 */
	@Override
	public Color getIntensity(Point3D p) {
		// dot product cos theta dir * l
		double dirL = this.direction.dotProduct(this.getL(p));
		if (dirL < 0) {
			return Color.BLACK;
		}
		// il = (i0 * dir DOT L /(kc+kl*kq*d^2))
		return super.getIntensity(p).scale(max(0, dirL));
	}
}
