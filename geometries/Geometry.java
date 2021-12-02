package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * abstract class of Geometry
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public abstract class Geometry implements Intersectable {

	/**
	 * emission color
	 */
	protected Color emission = Color.BLACK;
	/**
	 * material
	 */
	private Material material = new Material();

	/**
	 * get the emission
	 * 
	 * @return color
	 */
	public Color getEmission() {
		return this.emission;
	}

	/**
	 * set the emission
	 * 
	 * @param color color
	 * @return Geometry object
	 */
	public Geometry setEmission(Color color) {
		this.emission = color;
		return this;
	}

	/**
	 * get the material
	 * 
	 * @return material
	 */
	public Material getMaterial() {
		return this.material;
	}

	/**
	 * set material
	 * 
	 * @param mat material
	 * @return Geometry object
	 */
	public Geometry setMaterial(Material mat) {
		this.material = mat;
		return this;
	}

	/**
	 * get the normal of a shape
	 * 
	 * @param p0 point 3d
	 * @return return normalized vector at a point 3d
	 */
	public abstract Vector getNormal(Point3D p0);

}
