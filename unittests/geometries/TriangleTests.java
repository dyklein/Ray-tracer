package unittests.geometries;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Triangle
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class TriangleTests {
	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Triangle triangle = new Triangle(new Point3D(0, 0, 0), new Point3D(1, 1, 0), new Point3D(2, 1, 0));
		Vector triangleVector = triangle.getNormal(new Point3D(0, 0, 0));
		Vector vecPos = new Vector(0, 0, 1);
		Vector VecNeg = new Vector(0, 0, -1);
		assertTrue("Wrong normal for plane vector.", vecPos.equals(triangleVector) || VecNeg.equals(triangleVector));
	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoints() {
		Triangle triangle = new Triangle(new Point3D(0, 1, 0), new Point3D(0, 3, 0), new Point3D(1, 2, 0));
		List<Point3D> result = new ArrayList<Point3D>();
		// ============ Equivalence Partitions Tests ==============

		// TC01: Inside polygon/triangle
		result.add(new Point3D(0.5, 2, 0));
		assertEquals("Ray should not cross the triangle", result,
				triangle.findIntersections(new Ray(new Point3D(0.5, 2, 2), new Vector(0, 0, -3))));
		// TC02: Outside against edge
		assertEquals("Ray should not cross the triangle", null,
				triangle.findIntersections(new Ray(new Point3D(1, 3, -1), new Vector(0, 0, 3))));
		// TC03: Outside against vertex
		assertEquals("Ray should not cross the triangle", null,
				triangle.findIntersections(new Ray(new Point3D(2, 2, -1), new Vector(0, 0, 3))));

		// =============== Boundary Values Tests ==================
		// **** the ray begins "before" the plane

		// TC04: On edge
		assertEquals("Ray should not cross the triangle", null,
				triangle.findIntersections(new Ray(new Point3D(0, 2, -1), new Vector(0, 0, 1))));
		// TC05: In vertex
		assertEquals("Ray should not cross the triangle", null,
				triangle.findIntersections(new Ray(new Point3D(0, 1, -1), new Vector(0, 0, 1))));
		// TC06: On edge's continuation
		assertEquals("Ray should not cross the triangle", null,
				triangle.findIntersections(new Ray(new Point3D(0, 4, -1), new Vector(0, 0, 1))));

	}
}
