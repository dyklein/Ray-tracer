package unittests.primitives;

import static org.junit.Assert.*;
import org.junit.Test;

import primitives.*;

/**
 * Testing Point3D
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class Point3DTests {
	/**
	 * test adding in the vector class
	 * {@link primitives.Vector#add(primitives.Vector)}
	 */
	@Test
	public void testAdding() {
		Point3D p1 = new Point3D(1, 2, 3);
		assertTrue("ERROR: Point + Vector does not work correctly",
				Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))));
	}

	/**
	 * test subtraction in the vector class
	 * {@link primitives.Vector#subtract(primitives.Vector)}
	 */
	@Test
	public void testSubtraction() {
		Point3D p1 = new Point3D(1, 2, 3);
		// assertTrue("ERROR: Point + Vector does not work correctly",
		// !Point3D.ZERO.equals(p1.add(new Vector(-1,-2,-3))));
		assertTrue("ERROR: Point - Point does not work correctly",
				new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)));
	}

	/**
	 * test subtraction in the vector class
	 * {@link primitives.Point3D#distance(primitives.Point3D)}
	 */
	@Test
	public void testDistance() {
		Point3D p1 = new Point3D(1, 2, 3);
		Point3D p2 = new Point3D(3, 3, 5);
		double result = 3;
		assertEquals(result, p2.distance(p1), 0.001);
	}
}
