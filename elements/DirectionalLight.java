package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class representing a directional light
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class DirectionalLight extends Light implements LightSource {
	/**
	 * direction of the light
	 */
	private Vector direction;

	/**
	 * constructor for direct light
	 * 
	 * @param color color
	 * @param dir   intensity
	 */
	public DirectionalLight(Color color, Vector dir) {
		super(color);
		this.direction = dir;
	}

	/**
	 * get the intensity
	 * 
	 * @param p Point 3d
	 * @return Color of the intensity
	 */
	@Override
	public Color getIntensity(Point3D p) {
		return new Color(this.intensity);
	}

	/**
	 * get the direction
	 * 
	 * @param p point 3d
	 * @return direction vector
	 */
	@Override
	public Vector getL(Point3D p) {
		return this.direction;
	}

	/**
	 * get the distance of a point
	 * 
	 * @param p point 3d
	 * @return distance
	 */
	@Override
	public double getDistance(Point3D p) {
		return Double.POSITIVE_INFINITY;
	}

}
