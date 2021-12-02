package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Polygon extends Geometry {
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}

	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}

	@Override
	public String toString() {
		String outputVertices = "";

		for (Point3D i : vertices)
			outputVertices += i.toString() + " ";

		return "Plane: " + this.plane.toString() + "\nVertices: " + outputVertices;
	}

	/**
	 * get a ray that and check if it intersects with a objects
	 * 
	 * @return list of points of intersection
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		// list of all the points
		// List<Point3D> points = new ArrayList<Point3D>();
		Point3D p0 = ray.getP0();

		// get all Vi in the polygon
		List<Vector> vec = new ArrayList<Vector>();
		int numPoints = vertices.size();
		for (int i = 0; i < numPoints; i++) {
			vec.add(vertices.get(i).subtract(p0));
		}

		// Check if the intersection point with its plane is inside the polygon/triangle
		List<GeoPoint> planeResult = plane.findGeoIntersections(ray, maxDistance);
		if (planeResult != null) {
			// signs
			boolean sign = false;
			// normal
			Vector n;

			for (int i = 0; i < numPoints; i++) {
				n = vec.get(i).crossProduct(vec.get((i + 1) % numPoints)).normalize();
				// Constraint compromise: if one or more are 0.0 no intersection
				if (isZero(ray.getVector().dotProduct(n))) {
					return null;
				}
				// The point is inside if all v . Ni have the same sign (+/-)
				double inside = ray.getVector().dotProduct(n);
				// dont update on the first pass through
				if (i == 0) {
					sign = (inside > 0);
				} else {
					if (sign != (inside > 0)) {
						return null;
					}
				}
			}
			planeResult.get(0).geometry = this;
			return planeResult;
		}
		return null;
	}

}
