package unittests.geometries;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
			fail("Constructed a polygon with wrong order of vertices");
		} catch (IllegalArgumentException e) {
		}

		// TC03: Not in the same plane
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 2, 2));
			fail("Constructed a polygon with vertices that are not in the same plane");
		} catch (IllegalArgumentException e) {
		}

		// TC04: Concave quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
			fail("Constructed a concave polygon");
		} catch (IllegalArgumentException e) {
		}

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
			fail("Constructed a polygon with vertices on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC11: Last point = first point
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
			fail("Constructed a polygon with vertices on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC12: Colocated points
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 1, 0));
			fail("Constructed a polygon with vertices on a side");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
				new Point3D(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to triangle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoints() {
		Polygon firstPolygon = new Polygon(new Point3D(0, 1, 0), new Point3D(0, 3, 0), new Point3D(1, 2, 0));
		Polygon secondPolygon = new Polygon(new Point3D(0, 1, 0), new Point3D(0, 3, 0), new Point3D(1, 3, 0),
				new Point3D(1, 1, 0));
		List<Point3D> result = new ArrayList<Point3D>();
		// ============ Equivalence Partitions Tests ==============

		// TC01: Inside polygon/triangle
		result.add(new Point3D(0.5, 2, 0));
		assertEquals("Ray should not cross the triangle", result,
				firstPolygon.findIntersections(new Ray(new Point3D(0.5, 2, 2), new Vector(0, 0, -3))));
		assertEquals("Ray should not cross the square", result,
				secondPolygon.findIntersections(new Ray(new Point3D(0.5, 2, 2), new Vector(0, 0, -3))));
		// TC02: Outside against edge
		assertEquals("Ray should not cross the triangle", null,
				firstPolygon.findIntersections(new Ray(new Point3D(1, 3, -1), new Vector(0, 0, 3))));
		assertEquals("Ray should not cross the square", result,
				secondPolygon.findIntersections(new Ray(new Point3D(0.5, 2, 2), new Vector(0, 0, -3))));
		// TC03: Outside against vertex
		assertEquals("Ray should not cross the triangle", null,
				firstPolygon.findIntersections(new Ray(new Point3D(2, 2, -1), new Vector(0, 0, 3))));
		assertEquals("Ray should not cross the square", result,
				secondPolygon.findIntersections(new Ray(new Point3D(0.5, 2, 2), new Vector(0, 0, -3))));

		// =============== Boundary Values Tests ==================
		// **** the ray begins "before" the plane

		// TC04: On edge
		assertEquals("Ray should not cross the triangle", null,
				firstPolygon.findIntersections(new Ray(new Point3D(0, 2, -1), new Vector(0, 0, 1))));
		assertEquals("Ray should not cross the square", null,
				secondPolygon.findIntersections(new Ray(new Point3D(0, 2, -1), new Vector(0, 0, 1))));
		// TC05: In vertex
		assertEquals("Ray should not cross the triangle", null,
				firstPolygon.findIntersections(new Ray(new Point3D(0, 1, -1), new Vector(0, 0, 1))));
		assertEquals("Ray should not cross the square", null,
				secondPolygon.findIntersections(new Ray(new Point3D(0, 1, -1), new Vector(0, 0, 1))));
		// TC06: On edge's continuation
		assertEquals("Ray should not cross the triangle", null,
				firstPolygon.findIntersections(new Ray(new Point3D(0, 4, -1), new Vector(0, 0, 1))));
		assertEquals("Ray should not cross the square", null,
				secondPolygon.findIntersections(new Ray(new Point3D(-1, 1, -1), new Vector(0, 0, 1))));
	}
}
