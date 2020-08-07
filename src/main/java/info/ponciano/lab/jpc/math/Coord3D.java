/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.jpc.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.StorelessCovariance;

/**
 * 3D coordinates with a 4 decimal accuracy.
 *
 * @author jean-jacques Ponciano
 */
public class Coord3D implements Cloneable, java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3502280727283881631L;
    protected double x;
    protected double y;
    protected double z;
    public final static double ACCURACY = 0.0001;
    private String key;

    /**
     * Creates a new instance of <code>Coord3D</code>that is a clone of the
     * given <code>Coord3D</code>.
     *
     * @param clone <code>Coord3D</code> to be cloned
     */
    public Coord3D(final Coord3D clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.z = clone.z;
        computeKey();

    }

    /**
     * Creates a new instance of <code>Coord3D</code>.
     *
     * @param x The x coordinate of the coordinates.
     * @param y The y coordinate of the coordinates.
     * @param z The z coordinate of the coordinates.
     */
    public Coord3D(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        computeKey();
    }

    public Coord3D(final float x, final float y, final float z) {
        this.x = (double) x;
        this.y = (double) y;
        this.z = (double) z;
        computeKey();
    }

    /**
     * Creates a new instance of <code>Coord3D</code> which is the average
     * coordinates of the coordinates list.
     *
     * @param average list of coordinates to create the average coordinates
     */
    public Coord3D(final List<? extends Coord3D> average) {
        final Coord3D averageCoord3D = Coord3D.getMean(average);
        if (averageCoord3D != null) {
            this.x = averageCoord3D.getX();
            this.y = averageCoord3D.getY();
            this.z = averageCoord3D.getZ();
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
        computeKey();
    }

    public Coord3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        computeKey();
    }

    /**
     * Creates a new instance of <code>Coord3D</code>
     *
     * @param coordToString String from string provide by the
     * <code>toString()</code>
     */
    public Coord3D(final String coordToString) {
        List<String> split = new ArrayList<>();
        String[] splited = coordToString.split("\\s");
        for (String string : splited) {
            if (!string.isEmpty()) {
                split.add(string);
            }
        }
        final String sx = split.get(0);
        final String sy = split.get(1);
        final String sz = split.get(2);
        this.format(sx, sy, sz);
        computeKey();
    }

    public Coord3D(final String sx, final String sy, final String sz) {
        this.format(sx, sy, sz);
        computeKey();
    }

    @Override
    public Coord3D clone() throws CloneNotSupportedException {
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = super.clone();
        } catch (final CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        return (Coord3D) o;
    }

    /**
     * Gets the average coordinates of the coordinates list
     *
     * @param coords list of coordinates
     * @return the average coordinates or null if the list is empty.
     */
    public static final Coord3D getMean(final List<? extends Coord3D> coords) {
        if (coords.isEmpty()) {
            return null;
        }
        double ax = 0, ay = 0, az = 0;

        for (final Coord3D average : coords) {
            ax += average.getX();
            ay += average.getY();
            az += average.getZ();
        }
        ax /= coords.size();
        ay /= coords.size();
        az /= coords.size();

        return new Coord3D(ax, ay, az);
    }

    /**
     * Calculate euclidian distance between both coordinates.
     *
     * @param p other coordinates.
     * @return The distance between both coordinates.
     */
    public double distance(final Coord3D p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) + Math.pow(z - p.getZ(), 2));
    }

    /**
     * Calculate euclidian Absolute distance between both coordinates.
     *
     * @param p other coordinates.
     * @return The distance between both coordinates.
     */
    public double distanceABS(final Coord3D p) {
        return Math.sqrt(Math.pow(Math.abs(x) - Math.abs(p.getX()), 2) + Math.pow(Math.abs(y) - Math.abs(p.getY()), 2)
                + Math.pow(Math.abs(z) - Math.abs(p.getZ()), 2));
    }

    /**
     * Calculate Taxicab geometry distance between both coordinates.
     *
     * @param p other coordinates.
     * @return The distance between both coordinates.
     */
    public double distanceTaxicab(final Coord3D p) {
        return (Math.abs(x - p.getX()) + Math.abs(y - p.getY()) + Math.abs(z - p.getZ()));
    }

    /**
     * Gets the standard deviation of the coordinates list.
     *
     * @param coords list of coordinates.
     * @return The standard deviation of the coordinates list.
     */
    public static Coord3D getStandardDeviation(final List<? extends Coord3D> coords) {
        if (coords.isEmpty()) {
            return null;
        }
        // get the mean of the coord list.
        final Coord3D mean = Coord3D.getMean(coords);

        double ax = 0, ay = 0, az = 0;
        for (final Coord3D coord : coords) {
            ax += Math.pow(coord.getX() - mean.getX(), 2);
            ay += Math.pow(coord.getY() - mean.getY(), 2);
            az += Math.pow(coord.getZ() - mean.getZ(), 2);
        }
        ax /= coords.size() - 1;
        ay /= coords.size() - 1;
        az /= coords.size() - 1;
        final Coord3D diff = new Coord3D(Math.sqrt(ax), Math.sqrt(ay), Math.sqrt(az));

        return diff;
    }

    /**
     * Gets the Mahalanobis distance between the current coordinates and the
     * coordinates list.
     *
     * @param coords list of coordinates.
     * @return Mahalanobis distance between the current coordinates and the
     * coordinates list or -1 if the list is empty.
     */
    public double getMahalanobisDistance(final List<? extends Coord3D> coords) {
        // calculate the covariance matrix
        final StorelessCovariance sc = new StorelessCovariance(3);
        for (int i = 0; i < coords.size(); i++) {
            sc.increment(coords.get(i).getArrayD());
        }
        return this.getMahalanobisDistance(sc, coords);
    }

    /**
     * Gets the Mahalanobis distance between the current coordinates and the
     * coordinates list.
     *
     * @param sc the covariance matrix used.
     * @param coords list of coordinates.
     * @return Mahalanobis distance between the current coordinates and the
     * coordinates list or -1 if the list is empty.
     */
    public double getMahalanobisDistance(final StorelessCovariance sc, final List<? extends Coord3D> coords) {
        if (coords.isEmpty()) {
            return -1;
        }
        final Coord3D mean = Coord3D.getMean(coords);
        // find the inverse of the covariance matrix
        final RealMatrix invcov = new LUDecomposition(sc.getCovarianceMatrix()).getSolver().getInverse();

        // subtract the mean from the current coordinates
        final Coord3D vm = new Coord3D(x, y, z);
        vm.subtract(mean);
        final double[] preMultiply = invcov.preMultiply(vm.getArrayD());
        final Coord3D tmp = new Coord3D(preMultiply[0], preMultiply[1], preMultiply[2]);

        final double interdist = tmp.matrixMultiplication(vm);
        return Math.sqrt(interdist);
    }

    /**
     * Calculate Chebyshev distance between both coordinates.
     *
     * @param p other coordinates.
     * @return The distance between both coordinates.
     */
    public double distanceChebyshev(final Coord3D p) {
        final double abs1 = Math.abs(x - p.getX());
        final double abs2 = Math.abs(y - p.getY());
        final double abs3 = Math.abs(z - p.getZ());
        final double max = Math.max(abs1, abs2);
        return Math.max(max, abs3);
    }

    /**
     * Get the coordinates calculate by the linear interpolation parametric.
     *
     * @param p next extremity.
     * @param t value of the interpolation(usually between 0 and 1).
     * @return The coordinates at the specific value in the linear
     * interpolation.
     */
    public Coord3D linearInterpolationT(final Coord3D p, final double t) {
        final double px = (double) ((1.f - t) * this.x + t * p.getX());
        final double py = (double) ((1.f - t) * this.y + t * p.getY());
        final double pz = (double) ((1.f - t) * this.z + t * p.getZ());
        return new Coord3D(px, py, pz);
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
        computeKey();
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
        computeKey();
    }

    public double getZ() {
        return z;
    }

    public void setZ(final double z) {
        this.z = z;
        computeKey();
    }

    @Override
    public String toString() {
        return x + "\t" + y + "\t" + z;
    }

    /**
     * Indicates if both object are equals.
     *
     * @param other object to be compared.
     * @param delta the maximum delta between each coordinate for which both
     * coordinates are still considered equal.
     * @return true if all coordinates difference is less than delta.
     */
    public boolean equals(final Coord3D other, final double delta) {
        if (Math.abs(this.x - other.x) > delta) {
            return false;
        }
        if (Math.abs(this.y - other.y) > delta) {
            return false;
        }
        return Math.abs(this.z - other.z) <= delta;
    }

    public double getCloudResolution() {
        return ACCURACY;
    }

    /**
     * Get coordinates coordinates in a array.
     *
     * @return a array of Double representing coordinates coordinates.
     * <ul>
     * <li>0:x</li>
     * <li>1:y</li>
     * <li>2:z</li>
     * </ul>
     *
     */
    public double[] getArray() {
        final double[] array = {this.x, this.y, this.z};
        return array;
    }

    /**
     * Get coordinates coordinates in a array.
     *
     * @return a array of Double representing coordinates coordinates.
     * <ul>
     * <li>0:x</li>
     * <li>1:y</li>
     * <li>2:z</li>
     * </ul>
     *
     */
    public double[] getArrayD() {
        final double[] array = {this.x, this.y, this.z};
        return array;
    }

    /**
     * Set coordinates coordinates in a array.
     *
     * @param a index of the coordinate
     * <ul>
     * <li>0:x</li>
     * <li>1:y</li>
     * <li>2:z</li>
     * </ul>
     * @param value new value of the coordinate
     *
     */
    public void setArray(final int a, final double value) {
        switch (a) {
            case 0:
                this.x = value;
                break;
            case 1:
                this.y = value;
                break;
            default:
                this.z = value;
                break;
        }
        computeKey();
    }

    /**
     * Sets the elements of this vector to uniformly distributed random values
     * in a specified range, using a supplied random number generator.
     *
     * @param lower lower random value (inclusive)
     * @param upper upper random value (exclusive)
     * @param generator random number generator
     */
    protected void setRandom(final double lower, final double upper, final Random generator) {
        final double range = upper - lower;

        x = generator.nextDouble() * range + lower;
        y = generator.nextDouble() * range + lower;
        z = generator.nextDouble() * range + lower;
        computeKey();
    }

    public int compareTo(final Coord3D o) {
        if (this.x < o.getX()) {
            return -1;
        } else if (this.x > o.getX()) {
            return 1;
        } else if (this.y < o.getY()) {
            return -1;
        } else if (this.y > o.getY()) {
            return 1;
        } else if (this.z < o.getZ()) {
            return -1;
        } else if (this.z > o.getZ()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Return the distance with another coordinate only about the first priority
     * coordinate in the @code{compareTo} function.
     *
     * @param o other coordinate
     * @return the distance between the both priority coordinate (X)
     * @see compareTo
     */
    public double distanceCompareTo(final Coord3D o) {
        return Math.abs(this.x - o.getX());
    }

    /**
     * Test is this instance of coordinates is under a specific coordinates
     *
     * @param o specific coordinates
     * @return true if this instance is under the coordinates specify, false
     * otherwise.
     */
    public boolean isUnder(final Coord3D o) {
        return this.x < o.getX() && this.y < o.getY() && this.z < o.getZ();
    }

    /**
     * Test is this instance of coordinates is upper a specific coordinates
     *
     * @param o specific coordinates
     * @return true if this instance is upper the coordinates specify, false
     * otherwise.
     */
    public boolean isUpper(final Coord3D o) {
        return this.x > o.getX() && this.y > o.getY() && this.z > o.getZ();
    }

    /**
     * Return the medium coordinates between both coordinates.
     *
     * @param coordinates other coordinates used
     * @return The medium coordinates between both coordinates.
     */
    public Coord3D medium(final Coord3D coordinates) {
        final double xm = (this.x + coordinates.getX()) / 2.0f;
        final double ym = (this.y + coordinates.getY()) / 2.0f;
        final double zm = (this.z + coordinates.getZ()) / 2.0f;
        return new Coord3D(xm, ym, zm);
    }

    /**
     * Test if a coordinates is between to coordinates.
     *
     * <p>
     * The order is not relevant, thus <code>new Coordinate(0,0,0).isBetween(new
     * Coordinate(-1,0,0),new Coordinate(0,0,1)</code> provides the same result
     * as      <code>new Coordinate(0,0,0).isBetween(new Coordinate(0,0,1),new
     * Coordinate(-1,0,0)</code>.
     * </p>
     *
     * @param coordinates a coordinate
     * @param coordinates2 another coordinate
     * @return true if the coordinate is between both given, false otherwise.
     *
     *
     */
    public boolean isBetween(final Coord3D coordinates, final Coord3D coordinates2) {
        return (this.isBetweenX(coordinates, coordinates2) && this.isBetweenY(coordinates, coordinates2)
                && this.isBetweenZ(coordinates, coordinates2));
    }

    public boolean isBetweenX(final Coord3D coordinates, final Coord3D coordinates2) {
        final double minX = Math.min(coordinates.getX(), coordinates2.getX());

        final double maxX = Math.max(coordinates.getX(), coordinates2.getX());
        return (this.x >= minX) && (this.x <= maxX);
    }

    public boolean isBetweenY(final Coord3D coordinates, final Coord3D coordinates2) {
        final double minY = Math.min(coordinates.getY(), coordinates2.getY());
        final double maxY = Math.max(coordinates.getY(), coordinates2.getY());
        return (this.y >= minY) && (this.y <= maxY);
    }

    public boolean isBetweenZ(final Coord3D coordinates, final Coord3D coordinates2) {
        final double minZ = Math.min(coordinates.getZ(), coordinates2.getZ());
        final double maxZ = Math.max(coordinates.getZ(), coordinates2.getZ());
        return (this.z >= minZ) && (this.z <= maxZ);
    }

    /**
     * Subtract coordinates from the current coordinates
     *
     * @param coord coordinates to be used.
     */
    public void subtract(final Coord3D coord) {
        this.x -= coord.getX();
        this.y -= coord.getY();
        this.z -= coord.getZ();
    }

    /**
     * Add coordinates from the current coordinates
     *
     * @param coord coordinates to be used.
     */
    public void add(final Coord3D coord) {
        this.x += coord.getX();
        this.y += coord.getY();
        this.z += coord.getZ();
    }

    private double matrixMultiplication(final Coord3D vm) {
        final double dist = this.x * vm.getX() + this.y * vm.getY() + this.z * vm.getZ();
        return dist;
    }

    /**
     * Calculate euclidian distance between both coordinates.
     *
     * @param coords other coordinates.
     * @param nomalize true to normilize the coordinate before study the
     * distance
     * @return The distance between both coordinates.
     */
    public double distance(final Coord3D coords, final boolean nomalize) {
        if (nomalize) {
            final double xn = x / (x + y + z);
            final double yn = y / (x + y + z);
            final double zn = z / (x + y + z);
            final double pxn = coords.getX() / (coords.getX() + coords.getY() + coords.getZ());
            final double pyn = coords.getY() / (coords.getX() + coords.getY() + coords.getZ());
            final double pzn = coords.getZ() / (coords.getX() + coords.getY() + coords.getZ());
            return Math.sqrt(Math.pow(xn - pxn, 2) + Math.pow(yn - pyn, 2) + Math.pow(zn - pzn, 2));

        } else {
            return this.distance(coords);
        }
    }

    /**
     * Sets a single element of these coordinates. Elements 0, 1, and 2
     * correspond to x, y, and z.
     *
     * @param i element index
     * @param value element value
     * @throws ArrayIndexOutOfBoundsException if i is not in the range 0 to 2.
     */
    public void set(final int i, final double value) {
        switch (i) {
            case 0: {
                x = value;
                break;
            }
            case 1: {
                y = value;
                break;
            }
            case 2: {
                z = value;
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(i);
            }
        }
        computeKey();
    }

    /**
     * Sets the values of these coordinates to those of v1.
     *
     * @param v1 vector whose values are copied
     */
    public void set(final Coord3D v1) {
        x = v1.x;
        y = v1.y;
        z = v1.z;
        computeKey();
    }

    /**
     * Sets the elements of this vector to the prescribed values.
     *
     * @param x value for first element
     * @param y value for second element
     * @param z value for third element
     */
    public void set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        computeKey();
    }

    /**
     * Gets a single element of these coordinates. Elements 0, 1, and 2
     * correspond to x, y, and z.
     *
     * @param i element index
     * @return element value
     * @throws ArrayIndexOutOfBoundsException if i is not in the range 0 to 2.
     */
    public double get(final int i) {
        switch (i) {
            case 0: {
                return x;
            }
            case 1: {
                return y;
            }
            case 2: {
                return z;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(i);
            }
        }
    }

    public static Coord3D fromString(final String string) {
        final String[] split = string.split("\t");
        return new Coord3D(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

    private void format(final String sx, final String sy, final String sz) {
        try {
            this.x = Double.parseDouble(sx);
            this.y = Double.parseDouble(sy);
            this.z = Double.parseDouble(sz);
        } catch (final NumberFormatException e) {
            try {
                this.x = (double) Double.parseDouble(sx);
                this.y = (double) Double.parseDouble(sy);
                this.z = (double) Double.parseDouble(sz);
            } catch (final NumberFormatException e2) {
                this.x = (double) Integer.parseInt(sx);
                this.y = (double) Integer.parseInt(sy);
                this.z = (double) Integer.parseInt(sz);
            }
        }
    }

    /**
     * Test if both vectors are collinear. Vectors are collinear if
     *
     * @param other vector to be tested
     * @param delta approximation of the equals test.
     * @return true if both vectors are collinear, false otherwise.
     */
    public boolean isCollinear(final Coord3D other, final double delta) {
        // vector u and v are collinear if u.x*v.y=v.x*u.y=u.x*v.z=v.x*u.z
        final double p1 = this.getX() * other.getY();
        final double p2 = other.getX() * this.getY();

        final double p3 = this.getX() * other.getZ();
        final double p4 = other.getX() * this.getZ();
        return ((Math.abs(p1 - p2) < delta) && (Math.abs(p3 - p4) < delta));
    }

    public boolean isOrthogonal(final Coord3D other, final double delta) {
        final double dot = this.dotProduct(other);
        return Math.abs(dot) < delta;
    }

    public double dotProduct(final Coord3D other) {
        return this.x * other.getX() + this.y * other.getY() + this.z * other.getZ();
    }

    public double cosAngle(final Coord3D other) {
        final double dot = this.dotProduct(other);
        return (dot / (this.getNorm() * other.getNorm()));
    }

    public double angle(final Coord3D other) {
        return Math.acos(cosAngle(other));
    }

    public double getNorm() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

    }

    /**
     * Computes the cross product of v1 and v2 and returns the result.
     *
     * @param v1 left-hand vector
     * @param v2 right-hand vector
     * @return
     */
    public static Coord3D cross(final Coord3D v1, final Coord3D v2) {
        final double tmpx = v1.y * v2.z - v1.z * v2.y;
        final double tmpy = v1.z * v2.x - v1.x * v2.z;
        final double tmpz = v1.x * v2.y - v1.y * v2.x;

        return new Coord3D(tmpx, tmpy, tmpz);
    }

    /**
     * Scales the elements of this vector by <code>s</code>.
     *
     * @param s scaling factor
     */
    public void scale(final double s) {
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
    public void add(final Coord3D v1, final Coord3D v2) {
        x = v1.x + v2.x;
        y = v1.y + v2.y;
        z = v1.z + v2.z;
    }

    /**
     * Subtracts vector v1 from v2 and places the result in this vector.
     *
     * @param v1 left-hand vector
     * @param v2 right-hand vector
     */
    public void sub(final Coord3D v1, final Coord3D v2) {
        x = v1.x - v2.x;
        y = v1.y - v2.y;
        z = v1.z - v2.z;
        v1.computeKey();
    }

    /**
     * Subtracts v1 from this vector and places the result new vector.
     *
     * @param v1 right-hand vector
     * @return new vector corresponding to the substract of both vector
     */
    public Coord3D sub(final Coord3D v1) {
        final double nx = x - v1.x;
        final double ny = y - v1.y;
        final double nz = z - v1.z;
        return new Coord3D(nx, ny, nz);
    }

    /**
     * Divides v1 from this vector and places the result new vector.
     *
     * @param v1 right-hand vector
     * @return new vector corresponding to the divides of both vector
     */
    public Coord3D divide(final Coord3D v1) {
        final double nx = x / v1.x;
        final double ny = y / v1.y;
        final double nz = z / v1.z;
        return new Coord3D(nx, ny, nz);
    }

    /**
     * Divides the vector by the value.
     *
     * @param v1 value to divide the vector
     * @return new divided vector
     */
    public Coord3D divide(final double v1) {
        final double nx = x / v1;
        final double ny = y / v1;
        final double nz = z / v1;
        return new Coord3D(nx, ny, nz);
    }

    public static void sortByMinDist(final List<Coord3D> ps, final Coord3D d) {
        Collections.sort(ps, (final Coord3D o1, final Coord3D o2) -> {
            final double d1 = o1.distance(d);
            final double d2 = o2.distance(d);
            return (int) (d1 - d2);
        });
    }

    /**
     * Returns the squared of the Euclidean distance between this vector and
     * vector v.
     *
     * @return squared distance between this vector and v
     */
    public double distanceSquared(final Coord3D v) {
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return (dx * dx + dy * dy + dz * dz);
    }

    /**
     * Sets the elements of this vector to zero.
     */
    public void setZero() {
        x = 0;
        y = 0;
        z = 0;
        computeKey();
    }

    /**
     * Normalizes this vector in place.
     */
    public void normalize() {
        final double norm = this.getNorm();
        if (norm > 0) {
            this.x /= norm;
            this.y /= norm;
            this.z /= norm;
        }
    }

    /**
     * Returns the square of the 2 norm of this vector. This is the sum of the
     * squares of the elements.
     *
     * @return square of the 2 norm
     */
    public double normSquared() {
        return x * x + y * y + z * z;
    }

    @Deprecated
    @Override
    public int hashCode() {
        throw new IllegalAccessError("Do not use hashCode but use hash");
        //        final int prime = 31;
//        int result = 1;
//        long temp;
//        temp = Double.doubleToLongBits(x);
//        result = prime * result + (int) (temp ^ (temp >>> 32));
//        temp = Double.doubleToLongBits(y);
//        result = prime * result + (int) (temp ^ (temp >>> 32));
//        temp = Double.doubleToLongBits(z);
//        result = prime * result + (int) (temp ^ (temp >>> 32));
//        return Objects.hash(rx, ry, rz);F
    }

    public String hash() {
        return key;

    }

    private void computeKey() {
        long rx = Math.round(x / Coord3D.ACCURACY);
        long ry = Math.round(y / Coord3D.ACCURACY);
        long rz = Math.round(z / Coord3D.ACCURACY);
        this.key = rx + "" + ry + "" + rz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Coord3D other = (Coord3D) obj;
        if (Math.abs(x - other.x) > Coord3D.ACCURACY) {
            return false;
        }
        if (Math.abs(y - other.y) > Coord3D.ACCURACY) {
            return false;
        }
        if (Math.abs(z - other.z) > Coord3D.ACCURACY) {
            return false;
        }
        return true;
    }
    /*
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass() && this == obj) {
            return true;
        }

        try {
            final Coord3D other = (Coord3D) obj;
            return this.equals(other, Coord3D.ACCURACY);
        } catch (final Exception e) {
            return false;
        }
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Double.hashCode(this.x);
        hash = 97 * hash + Double.hashCode(this.y);
        hash = 97 * hash + Double.hashCode(this.z);
        return hash;
    }
     */

}
