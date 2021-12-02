package elements;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.*;

/**
 * object use to view the object in the scene
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Camera {

	/**
	 * a location of Point3D
	 */
	private Point3D p0;
	/**
	 * a vector stemming forward from p0
	 */
	private Vector vTo;
	/**
	 * a vector stemming up from p0
	 */
	private Vector vUp;
	/**
	 * a vector stemming right from p0
	 */
	private Vector vRight;
	/**
	 * screen width
	 */
	private double width;
	/**
	 * screen height
	 */
	private double height;
	/**
	 * screen distance
	 */
	private double distance;

	/**
	 * construct the camera
	 * 
	 * @param point location of the camera
	 * @param up    vector up
	 * @param to    vector to
	 * @throws IllegalArgumentException vector needs to be orthogonal to each other
	 * 
	 */
	public Camera(Point3D point, Vector to, Vector up) {
		// check if the two vectors are orthogonal
		if (isZero(up.dotProduct(to))) {
			this.p0 = point;
			this.vUp = up.normalized();
			this.vTo = to.normalized();
			this.vRight = to.crossProduct(up).normalize();

		} else {
			throw new IllegalArgumentException("vectors must be orthogonal to each other");

		}

	}

	/***
	 * get point p0
	 * 
	 * @return point3d of the camera
	 */
	public Point3D getP0() {
		return this.p0;
	}

	/***
	 * getter vTo
	 * 
	 * @return vector Vto
	 */
	public Vector getVTo() {
		return this.vTo;
	}

	/***
	 * getter vUp
	 * 
	 * @return vector vUp
	 */
	public Vector getVUo() {
		return this.vUp;
	}

	/***
	 * getter vRight
	 * 
	 * @return vector vRight
	 */
	public Vector getVRight() {
		return this.vRight;
	}

	/**
	 * A set function for the View Plane size which receives two parameters
	 * 
	 * @param width  width
	 * @param height height
	 * @return this camera
	 * @throws IllegalArgumentException must have positive numbers
	 */
	public Camera setViewPlaneSize(double width, double height) {
		// check for zero or negative plane size
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("must be positive number for plane size");
		}
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * A set function for the View Plane distance from the camera
	 * 
	 * @param distance distance
	 * @return this camera
	 * @throws IllegalArgumentException distant cant be zero
	 */
	public Camera setDistance(double distance) {
		// check if the distance is zero
		if (isZero(distance)) {
			throw new IllegalArgumentException("distance cant be zero");
		}
		this.distance = distance;
		return this;
	}

	/**
	 * A function construct Ray Through Pixel
	 * 
	 * @param nX Nx represents the number of columns
	 * @param nY Ny represents the number of rows
	 * @param j  rows of pixel
	 * @param i  column of pixel
	 * @return ray through screen
	 * @throws IllegalArgumentException distant cant be zero
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

		if (isZero(this.distance)) {
			throw new IllegalArgumentException("distance cant be zero");
		}
		// Image center Pc = P0 + d . Vto
		Point3D pC = this.p0.add(this.vTo.scale(this.distance));
		// Ratio (pixel width & height)
		// ry = h/ny
		double rY = this.height / nY;
		// rx = w/nx
		double rX = this.width / nX;
		// Pixel[i,j ] center
		// yi=-(i–(ny−1)/2).ry
		double yI = -(i - (nY - 1) / 2d) * rY;
		// (j-(nx-1)/2).rx
		double xJ = (j - (nX - 1) / 2d) * rX;

		// check for zero Vector Zero
		Point3D pIJ = null;
		if (!isZero(xJ) && !isZero(yI)) {
			// regular formula
			pIJ = pC.add((vRight.scale(xJ)).add((vUp.scale(yI))));
		} else if (isZero(xJ) && !isZero(yI)) {
			// pIJ = pIJ.add vUp.scale yI
			pIJ = pC.add(vUp.scale(yI));
		} else if (!isZero(xJ) && isZero(yI)) {
			// pIJ = pIJ.add vRight.scale xJ
			pIJ = pC.add(vRight.scale(xJ));
		} else {
			pIJ = pC;
		}
		// vij = pIJ -p0
		// ray (po,vij)
		return new Ray(p0, pIJ.subtract(p0));

	}

	/**
	 * print out the members of the camera
	 * 
	 * @return return the string of the members
	 */
	@Override
	public String toString() {
		return "Point: " + this.p0.toString();

	}
}
