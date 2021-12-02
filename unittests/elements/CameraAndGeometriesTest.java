package unittests.elements;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import elements.Camera;
import geometries.*;
import primitives.*;

/**
 * class to check integration between creating rays form the camera and
 * calculating intersections between rays and geometric bodies
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class CameraAndGeometriesTest {

	public int getIntersection(Camera cam, Intersectable obj, int column, int row, int pixColumn, int pixRow) {
		List<Point3D> points;
		int total = 0;
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				points = obj.findIntersections(cam.constructRayThroughPixel(column, row, j, i));
				if (points != null) {
					total += points.size();
				}
			}

		}
		return total;
	}

	/**
	 * Test method for
	 * {@link geometries.Triangle#findIntersections(primitives.Ray)}. Test method
	 * for {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testCameraRaysWithTriangle() {
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		int result = 0;
		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
		result = getIntersection(camera, triangle, 3, 3, 3, 3);
		// TC01: Triangle: 1 intersection points
		assertEquals("TC01: Bad Camera Integration", 1, result);

		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
		result = getIntersection(camera, triangle, 3, 3, 3, 3);
		// TC02: Triangle: 2 intersection points
		assertEquals("TC02: Bad Camera Integration", 2, result);
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testCameraRaysWithPlane() {
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		int result = 0;
		Plane plane = new Plane(new Point3D(0, 0, -3), new Point3D(1, 0, -3), new Point3D(2, 2, -3));
		result = getIntersection(camera, plane, 3, 3, 3, 3);
		// TC01: Plane: 9 intersection points
		assertEquals("TC01: Bad Camera Integration", 9, result);

		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		plane = new Plane(new Point3D(0, 0, -3.1), new Point3D(1, 0, -3.2), new Point3D(2, 2, -3.6));
		result = getIntersection(camera, plane, 3, 3, 3, 3);
		// TC02: Plane: 9 intersection points
		assertEquals("TC02: Bad Camera Integration", 9, result);

		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		plane = new Plane(new Point3D(-1, 0, 0), new Point3D(-1, 1, 0), new Point3D(0, 0, -2));
		result = getIntersection(camera, plane, 3, 3, 3, 3);
		// TC03: Plane: 6 intersection points
		assertEquals("TC03: Bad Camera Integration", 6, result);

		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		plane = new Plane(new Point3D(0, 0, 3), new Point3D(1, 0, 3), new Point3D(2, 2, 3));
		result = getIntersection(camera, plane, 3, 3, 3, 3);
		// TC04: Plane: 0 intersection points
		assertEquals("TC04: Bad Camera Integration", 0, result);
	}

	/**
	 * Test method for {@link geometries.SPhere#findIntersections(primitives.Ray)}.
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testCameraRaysWithSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		int result = 0;
		Sphere sphere = new Sphere(1, new Point3D(0, 0, -3));
		result = getIntersection(camera, sphere, 3, 3, 3, 3);
		// TC01: Sphere: 2 intersection points
		assertEquals("TC01: Bad Camera Integration", 2, result);

		camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		sphere = new Sphere(2.5, new Point3D(0, 0, -2.5));
		result = getIntersection(camera, sphere, 3, 3, 3, 3);
		// TC02: Sphere: 18 intersection points
		assertEquals("TC02: Bad Camera Integration", 18, result);

		camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		sphere = new Sphere(2, new Point3D(0, 0, -2));
		result = getIntersection(camera, sphere, 3, 3, 3, 3);
		// TC03: Sphere: 10 intersection points
		assertEquals("TC03: Bad Camera Integration", 10, result);

		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		sphere = new Sphere(4, new Point3D(0, 0, 0));
		result = getIntersection(camera, sphere, 3, 3, 3, 3);
		// TC04: Sphere: 9 intersection points
		assertEquals("TC04: Bad Camera Integration", 9, result);

		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1);
		camera.setViewPlaneSize(3, 3);
		sphere = new Sphere(0.5, new Point3D(0, 0, 1));
		result = getIntersection(camera, sphere, 3, 3, 3, 3);
		// TC05: Sphere: 0 intersection points
		assertEquals("TC05: Bad Camera Integration", 0, result);

	}

}