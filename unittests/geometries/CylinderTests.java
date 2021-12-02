package unittests.geometries;

import static org.junit.Assert.*;
import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Cylinder
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class CylinderTests {
	public final Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
	public final Cylinder cylinder = new Cylinder(ray, 1, 4);

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D3D)}.
	 */
	@Test
	public void testGetNormal() {
		// =============== Boundary Values Tests ==================
		// TC04: test a point3D on the center of the base
		Vector cylinderCenterBase = cylinder.getNormal(new Point3D(0, 0, 0));
		Vector realCylinderCenterBase = new Vector(0, 0, 1);
		assertEquals("Failed to get Normal on the center of the base", cylinderCenterBase, realCylinderCenterBase);

		// TC04: test a point3D on the opposite center of the base
		Vector cylinderCenterBase2 = cylinder.getNormal(new Point3D(0, 0, 4));
		Vector realCylinderCenterBase2 = new Vector(0, 0, 1);
		assertEquals("Failed to get Normal on the center of the base", cylinderCenterBase2, realCylinderCenterBase2);

		// TC05: test a point3D on the side of the base
		Vector cylinderSideBase = cylinder.getNormal(new Point3D(1, 0, 2));
		Vector realCylinderSideBase = new Vector(1, 0, 0);
		assertEquals("Failed to get Normal side of cylinder", cylinderSideBase, realCylinderSideBase);

		// ============ Equivalence Partitions Tests ==============

		// TC01: test a point3D on the side of the cylinder
		Vector cylinderSide = cylinder.getNormal(new Point3D(4, 0, 0));
		Vector realCylinderSide = new Vector(0, 0, 1);
		assertEquals("Failed to get Normal on the side of the cylinder", cylinderSide, realCylinderSide);

		// TC02: test a point3D on one of the bases
		Vector cylinderAbove = cylinder.getNormal(new Point3D(.5, 0, 0));
		Vector realCylinderAbove = new Vector(0, 0, 1);
		assertEquals("Failed to get Normal on one of the bases", cylinderAbove, realCylinderAbove);

		// TC03: test a point3D on the opposite side of the first base
		Vector cylinderBelow = cylinder.getNormal(new Point3D(2, 0, 5));
		Vector realCylinderBelow = new Vector(1, 0, 0);
		assertEquals("Failed to get Normal on the opposite side of the first base", cylinderBelow, realCylinderBelow);

	}

	/**
	 * Test method for {@link geometries.Cylinder#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoints() {
		// bonus points
	}
}