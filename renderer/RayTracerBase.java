package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class that renders the image
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public abstract class RayTracerBase {
	/**
	 * protected scene
	 */
	protected Scene scene;

	/**
	 * constructor that receives a scene
	 * 
	 * @param scene scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * a public abstract function for tracing rays that receives a ray and return s
	 * color
	 * 
	 * @param ray ray
	 * @return color
	 */
	public abstract Color traceRay(Ray ray);
}
