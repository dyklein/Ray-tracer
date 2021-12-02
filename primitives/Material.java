package primitives;

/**
 * class of material and there shininess
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class Material {
	/**
	 * kd
	 */
	public double kD;
	/**
	 * ks
	 */
	public double kS;
	/**
	 * shininess
	 */
	public int nShininess = 0;
	/**
	 * Perfect mirror has a kr=1 and matt surface has a kr=0
	 */
	public double kR = 0.0;
	/**
	 * kt=1 when object is translucent, kt=0 when the object is opaque.
	 */
	public double kT = 0.0;

	/**
	 * set kd
	 * 
	 * @param d kd
	 * @return This obj
	 */
	public Material setKd(double d) {
		this.kD = d;
		return this;
	}

	/**
	 * set ks
	 * 
	 * @param s ks
	 * @return this obj
	 */
	public Material setKs(double s) {
		this.kS = s;
		return this;
	}

	/**
	 * set shininess
	 * 
	 * @param n shininess
	 * @return this obj
	 */
	public Material setShininess(int n) {
		this.nShininess = n;
		return this;
	}

	/**
	 * set kr
	 * 
	 * @param r kr
	 * @return this obj
	 */
	public Material setKr(double r) {
		this.kR = r;
		return this;
	}

	/**
	 * set kt
	 * 
	 * @param t kt
	 * @return this obj
	 */
	public Material setKt(double t) {
		this.kT = t;
		return this;
	}
}
