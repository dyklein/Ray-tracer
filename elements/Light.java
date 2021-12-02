package elements;

import primitives.*;

/**
 * abstract class representing a light
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public abstract class Light {
	/**
	 * how strong the ambient light is
	 */
	protected Color intensity;

	/**
	 * Construct intensity
	 * 
	 * @param color color
	 */
	public Light(Color color) {
		intensity = color;
	}

	/**
	 * default constructor
	 */
	public Light() {
		this.intensity = Color.BLACK;
	}

	/**
	 * get the intensity
	 * 
	 * @return color of the intensity
	 */
	public Color getIntensity() {
		return this.intensity;
	}

}