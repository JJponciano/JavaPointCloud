/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.jpc.math.vector;

import info.ponciano.lab.jpc.math.Coord3D;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jean-jacques
 */
public class Vector3d extends Coord3D implements Cloneable {
   @Override
    public Vector3d clone() throws CloneNotSupportedException{
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        return (Vector3d) o;
    }
    public Vector3d(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3d(List<Normal> averagcoord) {
        super(averagcoord);
    }

    public Vector3d(Coord3D p1, Coord3D p2) {
        super(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());
    }

    public Vector3d() {
super(0, 0, 0);
    }

    public Vector3d(String sx, String sy, String sz) {
        super(sx, sy, sz);
    }
    

//    @Override
//    public Vector3d clone()  {
//        Vector3d clone= null;
//        try {
//             clone= (Vector3d) super.clone();
//        } catch (CloneNotSupportedException ex) {
//            Logger.getLogger(Vector3d.class.getName()).log(Level.SEVERE, null, ex);
//        }//here clone attribute that is not in the super class 
//       return clone;
//    }
    /**
     * Test if both vectors are collinear. Vectors are collinear if
     *
     * @param other vector to be tested
     * @param delta approximation of the equals test.
     * @return true if both vectors are collinear, false otherwise.
     */
    public boolean isCollinear(Vector3d other, double delta) {
        // vector u and v are collinear if u.x*v.y=v.x*u.y=u.x*v.z=v.x*u.z
        double p1 = this.getX() * other.getY();
        double p2 = other.getX() * this.getY();

        double p3 = this.getX() * other.getZ();
        double p4 = other.getX() * this.getZ();
        return ((Math.abs(p1 - p2) < delta) && (Math.abs(p3 - p4) < delta));
    }

    public boolean isOrthogonal(Vector3d other, double delta) {
        double dot = this.dotProduct(other);
        return Math.abs(dot) < delta;
    }

    public double dotProduct(Vector3d other) {
        return this.x * other.getX() + this.y * other.getY() + this.z * other.getZ();
    }

    public double cosAngle(Vector3d other) {
        double dot = this.dotProduct(other);
        return (dot / (this.getNorm() * other.getNorm()));
    }

    public double angle(Vector3d other) {
        return Math.acos(cosAngle(other));
    }

    public double getNorm() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

    }

    /**
     * Computes the cross product of v1 and v2 and places the result in this
     * vector.
     *
     * @param v1 left-hand vector
     * @param v2 right-hand vector
     */
    public void cross(Vector3d v1, Vector3d v2) {
        double tmpx = v1.y * v2.z - v1.z * v2.y;
        double tmpy = v1.z * v2.x - v1.x * v2.z;
        double tmpz = v1.x * v2.y - v1.y * v2.x;

        x = tmpx;
        y = tmpy;
        z = tmpz;
    }

    /**
     * Sets the elements of this vector to uniformly distributed random values
     * in a specified range, using a supplied random number generator.
     *
     * @param lower lower random value (inclusive)
     * @param upper upper random value (exclusive)
     * @param generator random number generator
     */
    public void setRandom(double lower, double upper, Random generator) {
        double range = upper - lower;

        x = generator.nextDouble() * range + lower;
        y = generator.nextDouble() * range + lower;
        z = generator.nextDouble() * range + lower;
    }

    /**
     * Scales the elements of this vector by <code>s</code>.
     *
     * @param s scaling factor
     */
    public void scale(double s) {
        x = s * x;
        y = s * y;
        z = s * z;
    }
    /**
	 * Adds vector v1 to v2 and places the result in this vector.
	 *
	 * @param v1 left-hand vector
	 * @param v2 right-hand vector
	 */
	public void add (Vector3d v1, Vector3d v2)
	 {
	   x = v1.x + v2.x;
	   y = v1.y + v2.y;
	   z = v1.z + v2.z;
	 }

	/**
	 * Adds this vector to v1 and places the result in this vector.
	 *
	 * @param v1 right-hand vector
	 */
	public void add (Vector3d v1)
	 {
	   x += v1.x;
	   y += v1.y;
	   z += v1.z;
     }
     	/**
	 * Multiply this vector to v1 and places the result in this vector.
	 *
	 * @param v1 right-hand vector
	 */
	public void times (Vector3d v1)
    {
      x *= v1.x;
      y *= v1.y;
      z *= v1.z;
    }
      	/**
	 * Multiply this vector to the double given and places the result in this vector.
	 *
	 * @param v1 value for multiplication
	 */
	public void times (double v1)
    {
      x *= v1;
      y *= v1;
      z *= v1;
    }


	/**
	 * Subtracts vector v1 from v2 and places the result in this vector.
	 *
	 * @param v1 left-hand vector
	 * @param v2 right-hand vector
	 */
	public void sub (Vector3d v1, Vector3d v2)
	 {
	   x = v1.x - v2.x;
	   y = v1.y - v2.y;
	   z = v1.z - v2.z;
	 }

	/**
	 * Subtracts v1 from this vector and places the result in this vector.
	 *
	 * @param v1 right-hand vector
	 */
	public void sub (Vector3d v1)
	 {
	   x -= v1.x;
	   y -= v1.y;
	   z -= v1.z;
	 }
/**
	 * Returns the squared of the Euclidean distance between this vector
	 * and vector v.
	 *
	 * @return squared distance between this vector and v
	 */
	public double distanceSquared(Vector3d v)
	 {
	   double dx = x - v.x;
	   double dy = y - v.y;
	   double dz = z - v.z;

	   return (dx*dx + dy*dy + dz*dz);
	 }
        
	/**
	 * Sets the elements of this vector to zero.
	 */
	public void setZero()
	 {
	   x = 0;
	   y = 0;
	   z = 0;
	 }
        /**
	 * Normalizes this vector in place.
	 */
	public void normalize()
	 {	
             double DOUBLE_PREC = 2.2204460492503131e-16;
	   double lenSqr = x*x + y*y + z*z;
	   double err = lenSqr - 1;
	   if (err > (2*DOUBLE_PREC) ||
	       err < -(2*DOUBLE_PREC))
	    { double len = Math.sqrt(lenSqr);
	      x /= len;
	      y /= len;
	      z /= len;
	    }
	 }
        /**
	 * Returns the square of the 2 norm of this vector. This
	 * is the sum of the squares of the elements.
	 *
	 * @return square of the 2 norm
	 */
	public double normSquared()
	 { 
	   return x*x + y*y + z*z;
	 }
	


}
