package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing GeometriesTest
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class GeometriesTest {
	/**
	 * test for a empty collection
	 */
	@Test
	public void testEmptyCollection() {
		// =============== Boundary Values Tests ==================
		// TC01: Collection of no items
		Geometries test1 = new Geometries();
		assertTrue("collection is not empty", test1.getList().isEmpty());

	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoints() {
		Triangle triangle = new Triangle(new Point3D(0, 1, 5), new Point3D(0, 3, 5), new Point3D(1, 2, 5));
		Sphere sphere = new Sphere(2, new Point3D(0, -2, 10));
		Plane plane = new Plane(new Point3D(0, 1, 20), new Point3D(0, 3, 20), new Point3D(1, 2, 20));
		Geometries geometries = new Geometries(triangle, sphere, plane);

		// ============ Equivalence Partitions Tests ==============
		// TC01: some shapes but not all intersects
		assertEquals("TC01 wrong number of intersections", 2,
				geometries.findIntersections(new Ray(new Point3D(.5, 2, -1), new Vector(0, 0, 1))).size());

		// =============== Boundary Values Tests ==================
		// TC02: no shape intersects with a body
		assertNull("TC02 wrong number of intersections",
				geometries.findIntersections(new Ray(new Point3D(.5, 2, 1), new Vector(0, 0, -1))));

		// TC03: only one shape intersects
		assertEquals("TC03 wrong number of intersections", 1,
				geometries.findIntersections(new Ray(new Point3D(-1, 2, -1), new Vector(0, 0, 1))).size());
		assertEquals("TC03.2 wrong number of intersections", 2,
				geometries.findIntersections(new Ray(new Point3D(0, -2, 13), new Vector(0, 0, -1))).size());
		// TC04: all shapes intersects (BVA)
		triangle = new Triangle(new Point3D(0, 1, 5), new Point3D(0, 3, 5), new Point3D(1, 2, 5));
		sphere = new Sphere(6, new Point3D(0, -2, 10));
		plane = new Plane(new Point3D(0, 1, 20), new Point3D(0, 3, 20), new Point3D(1, 2, 20));
		geometries = new Geometries(triangle, sphere, plane);
		assertEquals("TC04 wrong number of intersections", 4,
				geometries.findIntersections(new Ray(new Point3D(.5, 2, -1), new Vector(0, 0, 1))).size());
		// TC05: an empty collection
		Geometries geometriesEmpty = new Geometries();
		assertNull("TC05 wrong number of intersections",
				geometriesEmpty.findIntersections(new Ray(new Point3D(.5, 2, 1), new Vector(0, 0, -1))));

		// TC06: test intersection of circle and triangle
		triangle = new Triangle(new Point3D(-4, 0, 5), new Point3D(-1, 0, 5), new Point3D(-2.5, 2, 5));
		sphere = new Sphere(1, new Point3D(-2.5, 2, 5));
		plane = new Plane(new Point3D(0, 1, 20), new Point3D(0, 3, 20), new Point3D(1, 2, 20));
		geometries = new Geometries(triangle, sphere, plane);
		assertEquals("TC06 wrong number of intersections", 3,
				geometries.findIntersections(new Ray(new Point3D(-2.5, 1.5, 13), new Vector(0, 0, -1))).size());
		assertEquals("TC07 wrong number of intersections", 2,
				geometries.findIntersections(new Ray(new Point3D(-2.5, 2.5, 13), new Vector(0, 0, -1))).size());
	}

}
