package unittests.geometries;

import static org.junit.Assert.*;
import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Tube
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class TubeTests {
	public final Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
	public final Tube tube = new Tube(ray, 1);

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: The ray’s head creates a 90 degrees with the ray
		// First normalize the perpendicular vector (0,0,1) + (1,0,0) = (1,0,1)
		Vector tubeVector90 = tube.getNormal(new Point3D(1, 0, 1));
		// Get the correct Normal for the question
		Vector realVector90 = new Vector(1, 0, 0);
		assertEquals("Failed to get Normal", tubeVector90, realVector90);

		// =============== Boundary Values Tests ==================

		// TC02: when the connection between the point on the body
		Vector tubeVectorOn = tube.getNormal(new Point3D(1, 0, 0));
		Vector realVectorOn = new Vector(1, 0, 0);
		assertEquals("Failed to get Normal", tubeVectorOn, realVectorOn);

	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoints() {
		// bonus points
	}

}
