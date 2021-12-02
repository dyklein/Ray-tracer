package scene;

import primitives.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import elements.*;

/**
 * the scene name, also initializes an empty collection of geometries
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class Scene {
	/**
	 * name of the scene
	 */
	public String name;
	/**
	 * background color
	 */
	public Color background;
	/**
	 * ambient light
	 */
	public AmbientLight ambientLight;
	/**
	 * collection of geometries
	 */
	public Geometries geometries;
	/**
	 * list of lights sources
	 */
	public List<LightSource> lights;

	/**
	 * constructor
	 * 
	 * @param n name of the scene
	 */
	public Scene(String n) {
		this.name = n;
		this.background = Color.BLACK; // initialize to black
		this.ambientLight = new AmbientLight(this.background, 0);
		this.geometries = new Geometries();
		this.lights = new LinkedList<LightSource>();
	}

	/**
	 * set background color
	 * 
	 * @param b color of the background
	 * @return object of the scene
	 */
	public Scene setBackground(Color b) {
		this.background = b;
		return this;
	}

	/**
	 * set the ambient light
	 * 
	 * @param a ambient light
	 * @return object of the scene
	 */
	public Scene setAmbientLight(AmbientLight a) {
		this.ambientLight = a;
		return this;
	}

	/**
	 * set the collection of geometries
	 * 
	 * @param g collection of geometries
	 * @return object of the scene
	 */
	public Scene setGeometries(Geometries g) {
		this.geometries = g;
		return this;
	}

	/**
	 * set lights
	 * 
	 * @param source list of lights
	 * @return object of the scene
	 */
	public Scene setLights(List<LightSource> source) {
		this.lights = source;
		return this;
	}
}
