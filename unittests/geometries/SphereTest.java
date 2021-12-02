package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Sphere
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class SphereTest {
	/**
	 * Test method for {@link geometries.Sphere#Sphere(primitives.Point3D, double)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct sphere
		try {
			new Sphere(2.5, new Point3D(0, 0, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct sphere");
		}
	}

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalSphere() {
		// ============ Equivalence Partitions Tests ==============

		// TC00: returning a normal
		Sphere sphere = new Sphere(1, new Point3D(0, 0, 0));
		Vector sphereNorm = sphere.getNormal(new Point3D(0, 0, 1));
		Vector vec = new Vector(new Point3D(0, 0, 1));
		assertEquals("TC00:incorrect normal", sphereNorm, vec);
	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		assertNull("TC01:Ray's line out of sphere",
				sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
		Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
		List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals("TC02:Wrong number of points", 2, result.size());
		if (result.get(0).getX() > result.get(1).getX()) {
			result = List.of(result.get(1), result.get(0));
		}
		assertEquals("TC02:Ray crosses sphere", List.of(p1, p2), result);

		Sphere sphere2;
		List<Point3D> result2;

		// TC03: Ray starts inside the sphere (1 point)
		result = sphere.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(3, 1, 0)));
		assertEquals("TC03:Ray crosses sphere", List.of(p2), result);

		sphere2 = new Sphere(2, new Point3D(0, 0, 0));
		// TC04: Ray starts after the sphere (0 points)
		assertNull("TC04:Ray starts after sphere",
				sphere2.findIntersections(new Ray(new Point3D(3, 3, 3), new Vector(5, 5, 5))));

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)

		// TC11: Ray starts at sphere and goes inside (1 points)
		result = sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0)));
		assertEquals("C11:Ray goes inside sphere", List.of(new Point3D(2, 0, 0)), result);

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull("TC12:Ray starts at sphere and goes outside",
				sphere2.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(3, 3, 3))));

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		p1 = new Point3D(2, 0, 0);
		p2 = new Point3D(-2, 0, 0);
		result2 = sphere2.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(-3, 0, 0)));
		assertEquals("TC13:Wrong number of points", 2, result2.size());
		assertEquals("TC13:Ray starts before sphere", List.of(p1, p2), result2);

		// TC14: Ray starts at sphere and goes inside (1 points)
		p1 = new Point3D(-2, 0, 0);
		result2 = sphere2.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-5, 0, 0)));
		assertEquals("TC14:Wrong number of points", 1, result2.size());
		assertEquals("TC14:Ray starts at sphere and goes inside", List.of(p1), result2);

		// TC15: Ray starts inside (1 points)
		p1 = new Point3D(-2, 0, 0);
		result2 = sphere2.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-5, 0, 0)));
		assertEquals("TC15:Wrong number of points", 1, result2.size());
		assertEquals("TC15:Ray starts inside sphere", List.of(p1), result2);

		// TC16: Ray starts at the center (1 points)
		p1 = new Point3D(-2, 0, 0);
		result2 = sphere2.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-5, 0, 0)));
		assertEquals("TC16:Wrong number of points", 1, result2.size());
		assertEquals("TC16:Ray starts at the center of the sphere", List.of(p1), result2);

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull("TC17:Ray starts at sphere and goes outside",
				sphere2.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));

		// TC18: Ray starts after sphere (0 points)
		assertNull("TC18:Ray starts after sphere",
				sphere2.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull("TC19:Ray starts after sphere",
				sphere2.findIntersections(new Ray(new Point3D(2, 0, -3), new Vector(0, 0, 6))));

		// TC20: Ray starts at the tangent point
		assertNull("TC20:Ray starts at tangent point",
				sphere2.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 3))));

		// TC21: Ray starts after the tangent point
		assertNull("TC21:Ray starts after tangent point",
				sphere2.findIntersections(new Ray(new Point3D(2, 0, 3), new Vector(0, 0, 2))));

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull("TC22:Ray's line is outside, ray is orthogonal to ray start to sphere's center line",
				sphere2.findIntersections(new Ray(new Point3D(0, 0, 3), new Vector(2, 0, 0))));

	}

}