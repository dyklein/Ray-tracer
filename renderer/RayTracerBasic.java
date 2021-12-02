package renderer;

import java.util.ArrayList;
import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Find the intersection and the scenes geometries and renders the image
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * max color level
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**
	 * min color value
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	/**
	 * initial k
	 */
	private static final double INITIAL_K = 1.0;
	/**
	 * number of rays
	 */
	private int numOfRays;
	/**
	 * distance of sampling
	 */
	private double rayDistance;

	/**
	 * set number of rays
	 * 
	 * @param n number of rays
	 */
	public void setNumOfRays(int n) {
		if (n < 1)
			throw new IllegalArgumentException("need at least one ray");
		this.numOfRays = n;
	}

	/**
	 * set the the distance of sampling
	 * 
	 * @param d distance
	 */
	public void setRayDistance(double d) {
		if (d < 0)
			throw new IllegalArgumentException("distance cannot be less than zorro ");
		this.rayDistance = d;
	}

	/**
	 * super constructor
	 * 
	 * @param scene scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * get the color of the intersected geometries
	 * 
	 * @param ray ray
	 * @return color of ray traced
	 */
	@Override
	public Color traceRay(Ray ray) {
		// search intersections between the ray and the 3DModel of the scene
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? Color.BLACK : calcColor(closestPoint, ray);
	}

	/**
	 * receives a point as a parameter and returns the color.
	 * 
	 * @param point point in 3d space
	 * @return color
	 */
	public Color calcColor(Point3D point) {
		// function shall return the ambient light of the sce
		return this.scene.ambientLight.getIntensity();
	}

	/**
	 * receives a point as a parameter and returns the color.
	 * 
	 * @param intersection geo points
	 * @param ray          ray
	 * @return color
	 */
	public Color calcColor(GeoPoint intersection, Ray ray) {
		return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculate the color
	 * 
	 * @param intersection intersection points
	 * @param ray          ray
	 * @param level        level
	 * @param k            k level
	 * @return Color
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
		Color color = intersection.geometry.getEmission();
		color = color.add(calcLocalEffects(intersection, ray, k));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	/**
	 * Calculate the global effects
	 * 
	 * @param geoPoint intersection point
	 * @param ray      ray of light
	 * @param level    level
	 * @param k        k level
	 * @return color
	 */
	private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double k) {
		Color color = Color.BLACK;
		List<Ray> beam = new ArrayList<>();

		Material material = geoPoint.geometry.getMaterial();
		double kr = material.kR;
		double kkr = k * kr;
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, geoPoint.point, ray);
			if (this.numOfRays == 0) {
				GeoPoint reflectedPoint = findClosestIntersection(ray);
				if (reflectedPoint != null) {
					color = color.add(calcColor(reflectedPoint, ray, level - 1, kkr).scale(kr));
				}
			} else {
				if (isZero(this.numOfRays) || this.rayDistance < 0) {
					beam.add(reflectedRay);
				} else {
					beam = reflectedRay.beamOfRays(geoPoint.geometry.getNormal(geoPoint.point), this.rayDistance,
							this.numOfRays);
				}
				for (Ray ray2 : beam) {
					GeoPoint reflectedPoint = findClosestIntersection(ray2);
					if (reflectedPoint != null) {
						color = color.add(calcColor(reflectedPoint, ray2, level - 1, kkr).scale(kr));
					}
				}
			}

		}
		double kt = material.kT;
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, geoPoint.point, ray);
			if (this.numOfRays == 0) {
				GeoPoint refractedPoint = findClosestIntersection(ray);
				if (refractedPoint != null) {
					color = color.add(calcColor(refractedPoint, ray, level - 1, kkt).scale(kt));
				}
			} else {
				if (isZero(this.numOfRays) || this.rayDistance < 0) {
					beam.add(refractedRay);
				} else {
					beam = refractedRay.beamOfRays(geoPoint.geometry.getNormal(geoPoint.point), this.rayDistance,
							this.numOfRays);
				}
				for (Ray ray2 : beam) {
					GeoPoint refractedPoint = findClosestIntersection(ray2);
					if (refractedPoint != null) {
						color = color.add(calcColor(refractedPoint, ray2, level - 1, kkt).scale(kt));
					}

				}
			}

		}
		return color;
	}

	/**
	 * Adding diffusion/specular calculation
	 * 
	 * @param intersection geo points
	 * @param ray          ray
	 * @param k            k level
	 * @return Color
	 */
	public Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getVector();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (isZero(nv)) {
			return Color.BLACK;
		}
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD;
		double ks = intersection.geometry.getMaterial().kS;

		Color color = Color.BLACK;
		for (LightSource lightSource : this.scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			// check sign(nl) == sign(nv)
			if (nl * nv > 0) {
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * find the closet intersection point
	 * 
	 * @param ray ray
	 * @return geo point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		// search intersections between the ray and the 3DModel of the scene
		List<GeoPoint> geo = this.scene.geometries.findGeoIntersections(ray);
		if (geo == null) {
			// search intersections between the ray and the 3DModel of the scene
			return null;
		}
		// find the closest point to the ray’s head and find the color of the point
		return ray.findClosestGeoPoint(geo);
	}

	/**
	 * construction of reflected ray
	 * 
	 * @param normal   normal of the ray
	 * @param geoPoint intersection point
	 * @param ray      ray
	 * @return ray that is constructed
	 */
	public Ray constructReflectedRay(Vector normal, Point3D geoPoint, Ray ray) {
		Vector v = ray.getVector();
		double vn = v.dotProduct(normal);
		if (vn == 0) {
			return null;
		}
		Vector r = v.subtract(normal.scale(2 * vn));
		return new Ray(geoPoint, r, normal);
	}

	/**
	 * Calculate the refracted ray
	 * 
	 * @param normal normal
	 * @param point  point
	 * @param ray    ray
	 * @return ray
	 */
	public Ray constructRefractedRay(Vector normal, Point3D point, Ray ray) {
		return new Ray(point, ray.getVector(), normal);
	}

	/**
	 * calculate the spectra
	 * 
	 * @param ks             ks
	 * @param l              l
	 * @param n              n
	 * @param v              v
	 * @param nShininess     ^Nsh
	 * @param lightIntensity il
	 * @return color
	 */
	public Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		// l dot n
		double ln = l.dotProduct(n);
		// r = l - 2 * (l dot n) * n
		Vector r = l.subtract(n.scale(ln * 2)).normalize();
		// -v dot r
		double vr = -1 * v.dotProduct(r);
		// the max value between 0 and vr
		double max;
		if (vr > 0) {
			max = vr;
		} else
			max = 0;
		// ks * max (0 , vr) ^ nsh * il
		return lightIntensity.scale(Math.pow(max, nShininess) * ks);
	}

	/**
	 * calculate the diffusion
	 * 
	 * @param kd             kd
	 * @param l              l
	 * @param n              n
	 * @param lightIntensity il
	 * @return color of the diffusion
	 */
	public Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		// l dot n
		double ln = l.dotProduct(n);
		// | l dot n |
		if (ln < 0) {
			ln *= -1;
		}
		// kd * | l dot n | * in
		return lightIntensity.scale(ln * kd);
	}

	/**
	 * old javaDoc (needs to be replaced) check un-shadowing between a point and the
	 * light source
	 * 
	 * @param light    light source
	 * @param l        vector
	 * @param n        vector
	 * @param geoPoint geo point
	 * @return false if light is block and true otherwise
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move
		double lightDistance = light.getDistance((geoPoint.point));
		var intersection = scene.geometries.findGeoIntersections(lightRay, light.getDistance(lightRay.getP0()));
		if (intersection == null) {
			return 1.0;
		}
		double ktr = 1.0;
		for (GeoPoint gp : intersection) {
			if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
				ktr *= gp.geometry.getMaterial().kT;
				if (ktr < MIN_CALC_COLOR_K) {
					return 0.0;
				}
			}
		}
		return ktr;
	}
}
