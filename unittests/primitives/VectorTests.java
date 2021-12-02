package unittests.primitives;

import static org.junit.Assert.*;
import org.junit.Test;

import primitives.*;
import static primitives.Util.*;

/**
 * Testing Vector
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class VectorTests {
	public final Vector v1 = new Vector(1, 2, 3);
	public final Vector v2 = new Vector(-2, -4, -6);
	public final Vector v3 = new Vector(0, 3, -2);
	public final Vector v4 = new Vector(1, 1, 1);
	public final Vector v5 = new Vector(2, 2, 2);
	public final Vector v6 = new Vector(3, 3, 3);

	/**
	 * Test method for
	 * {@link primitives.Vector#Vector(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testZeroVec() {
		try {
			new Vector(0, 0, 0);
			fail("ERROR: zero vector does not throw an exception");
		} catch (IllegalArgumentException e) {

		}

	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}
	 */
	@Test
	public void testAdding() {
		assertTrue("ERROR: Vector + Vector does not work correctly", v4.add(v5).equals(v6));
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}
	 */
	@Test
	public void testSubtraction() {
		assertTrue("ERROR: Vector - Vector does not work correctly", v6.subtract(v5).equals(v4));
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}
	 */
	@Test
	public void testScaling() {
		assertTrue("ERROR: scaling() wrong value", v4.scale(3).equals(v6));
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}
	 */
	@Test
	public void testDotProduct() {

		assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));

		assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}
	 */
	@Test
	public void testLength() {
		assertTrue("ERROR: length() wrong value", isZero(new Vector(0, 3, 4).length() - 5));

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}
	 */
	@Test
	public void testLengthSquare() {
		Vector v1 = new Vector(1, 2, 3);
		assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));

	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}
	 */
	@Test
	public void testNormalize() {

		Vector v = new Vector(1, 2, 3);
		Vector vCopy = new Vector(v.getHead());
		Vector vCopyNormalize = vCopy.normalize();
		assertTrue("ERROR: normalize() function creates a new vector", vCopy == vCopyNormalize);
		assertTrue("ERROR: normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
		Vector u = v.normalized();
		assertTrue("ERROR: normalized() function does not create a new vector", u != v);
	}

	/**
	 * Test method for {@link}
	 */
	@Test
	public void testNormalized() {

	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}
	 */
	@Test
	public void testCrossProduct() {
		try {
			v1.crossProduct(v2);
			fail("ERROR: crossProduct() for parallel vectors does not throw an exception");
		} catch (IllegalArgumentException e) {

		}

		Vector vr = v1.crossProduct(v3);
		assertTrue("ERROR: crossProduct() wrong result length", isZero(vr.length() - v1.length() * v3.length()));
		assertTrue("ERROR: crossProduct() result is not orthogonal to its operands",
				isZero(vr.dotProduct(v1)) || isZero(vr.dotProduct(v3)));

	}

}
