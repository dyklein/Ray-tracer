package elements;

import primitives.Vector;
import primitives.Color;
import primitives.Point3D;

/**
 * interface that represent a light source
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public interface LightSource {
	/**
	 * get the intensity
	 * 
	 * @param p point 3d
	 * @return color of the intensity
	 */
	public Color getIntensity(Point3D p);

	/**
	 * get the direction
	 * 
	 * @param p point 3d
	 * @return direction vector
	 */
	public Vector getL(Point3D p);

	/**
	 * get the distance of a point
	 * 
	 * @param p point 3d
	 * @return distance
	 */
	public double getDistance(Point3D p);

}
