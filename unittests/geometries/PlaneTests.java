package unittests.geometries;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Plane
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class PlaneTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// =============== Boundary Values Tests ==================

		// TC1: The first and second points are equal

		try {
			new Plane(new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 1));
			fail("Cant accept points on the same line");
		} catch (IllegalArgumentException e) {

		}
		// TC1: The points are all on the same line
		try {
			new Plane(new Point3D(0, 0, 0), new Point3D(0, 0, 1), new Point3D(0, 0, 2));
			fail("Cant accept points on the same line");
		} catch (IllegalArgumentException e) {

		}

	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test normal for any of the directions.

		Plane plane = new Plane(new Point3D(0, 0, 0), new Point3D(1, 1, 0), new Point3D(2, 1, 0));
		Vector planeVector = plane.getNormal(new Point3D(0, 0, 0));
		Vector vecPos = new Vector(0, 0, 1);
		Vector VecNeg = new Vector(0, 0, -1);
		assertTrue("Wrong normal for plane vector.", vecPos.equals(planeVector) || VecNeg.equals(planeVector));

	}

	/**
	 * Test method for {@link geometries.Plane#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoints() {
		Plane plane = new Plane(new Point3D(0, 1, 0), new Point3D(0, 3, 0), new Point3D(1, 2, 0));
		List<Point3D> result = new ArrayList<Point3D>();
		// ============ Equivalence Partitions Tests ==============

		// **** EP: The Ray must be neither orthogonal nor parallel to the plane
		// TC01: Ray intersects the plane
		result = plane.findIntersections(new Ray(new Point3D(1, 1, 2), new Vector(0, 0, -1)));
		assertEquals("Ray should cross the plane", 1, result.size());
		// TC02: Ray does not intersect the plane
		result = plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 0, 2)));
		assertNull("Ray should not cross the plane", result);

		// =============== Boundary Values Tests ==================
		// TC03: Ray is parallel to the plane not included in the plane
		result = plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(2, 2, 1)));
		assertNull("Ray should not cross the plane", result);
		// TC04: Two cases the ray included
		result = plane.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(2, 2, 0)));
		assertNull("Ray should not cross the plane", result);
		// TC05: Ray is orthogonal to the plane in the plane
		result = plane.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1)));
		assertNull("Ray should not cross the plane", result);
		// TC06: Three cases according to p0 after the plane
		result = plane.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(0, 0, -1)));
		assertEquals("Ray should not cross the plane", null, result);
		// TC07: Three cases according to p0 before the plane
		result = plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)));
		assertEquals("Ray should not cross the plane", null, result);
		// TC08: Ray is neither orthogonal nor parallel to and begins at the plane p0is
		// in the plane, but not the ray)
		result = plane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0)));
		assertEquals("Ray should not cross the plane", null, result);
		// TC09: Ray is neither orthogonal nor parallel to the plane and begins in the
		// same point which appears as reference point in the plane ( Q
		result = plane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 2, 0)));
		assertEquals("Ray should not cross the plane", null, result);
	}

}
