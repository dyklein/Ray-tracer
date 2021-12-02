package unittests.primitives;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import primitives.*;

/**
 * Testing Ray
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class RayTests {
	/**
	 * test to get the closet point tot he ray
	 * {@link primitives.Ray#findClosestPoint(primitives.Point3D)}
	 */
	@Test
	public void testDistance() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, 1));
		List<Point3D> list = null;

		// =============== Boundary Values Tests ==================
		// TC01: empty list
		assertEquals("TC01: supposed to be null", null, ray.findClosestPoint(list));
		list = new ArrayList<Point3D>();
		list.add(new Point3D(100, 100, 100));
		list.add(new Point3D(10, 10, 10));
		list.add(new Point3D(0, 0, 1));
		list.add(new Point3D(-10, -10, -10));
		list.add(new Point3D(-100, -100, -100));
		// TC02: first point is the closest
		ray = new Ray(new Point3D(99, 99, 99), new Vector(0, 0, 1));
		assertEquals("TC02: supposed to be first point", list.get(0), ray.findClosestPoint(list));
		// TC03: last point is the closest
		ray = new Ray(new Point3D(-99, -99, -99), new Vector(0, 0, 1));
		assertEquals("TC03: supposed to be first point", list.get(4), ray.findClosestPoint(list));
		// ============ Equivalence Partitions Tests ==============
		// TC04: middle of the list
		ray = new Ray(new Point3D(4, 4, 4), new Vector(0, 0, 1));
		assertEquals("TC04: supposed to be first point", list.get(2), ray.findClosestPoint(list));
	}

}