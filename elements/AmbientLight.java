package elements;

import primitives.Color;

/**
 * An ambient light source represents a fixed intensity and fixed color light
 * source that affects all objects in the scene equally.
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class AmbientLight extends Light {

	/**
	 * Constructor of ambient light
	 * 
	 * @param iA color
	 * @param kA intensity
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA.scale(kA));
	}

	/**
	 * default constructor
	 */
	public AmbientLight() {
		super();
	}

}
