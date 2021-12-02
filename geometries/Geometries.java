package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Ray;

/**
 * Class of Geometries
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Geometries implements Intersectable {

	/**
	 * center of the sphere
	 */
	private ArrayList<Intersectable> geometries;

	/**
	 * default constructor of a Geometries
	 * 
	 */
	public Geometries() {
		this.geometries = new ArrayList<Intersectable>();
	}

	/**
	 * constructor of a Geometries
	 * 
	 * @param otherGeometries list of Intersectable
	 */
	public Geometries(Intersectable... otherGeometries) {
		this.geometries = new ArrayList<Intersectable>();
		for (Intersectable intersectable : otherGeometries) {
			this.geometries.add(intersectable);

		}
	}

	/**
	 * adds an object to the collection
	 * 
	 * @param otherGeometries list of Intersectable
	 */
	public void add(Intersectable... otherGeometries) {
		for (Intersectable intersectable : otherGeometries) {
			this.geometries.add(intersectable);

		}
	}

	/**
	 * get list of geometries
	 * 
	 * @return list of Intersectable
	 */
	public ArrayList<Intersectable> getList() {
		return this.geometries;
	}

	/**
	 * given a ray check if it intersects with the objects in the list
	 * 
	 * @param ray         ray
	 * @param maxDistance max distance
	 * @return list of points of intersection
	 * 
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> result = null;
		// check if list is empty
		if (!geometries.isEmpty()) {
			for (Intersectable intersectable : geometries) {
				List<GeoPoint> tempList = intersectable.findGeoIntersections(ray, maxDistance);
				if (tempList != null) {
					if (result == null) {
						result = new ArrayList<GeoPoint>();
					}
					result.addAll(tempList);
				}

			}
		}
		return result;
	}

}
