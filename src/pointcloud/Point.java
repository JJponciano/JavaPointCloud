/*
 * Copyright (C) 2016 Jean-Jacques Ponciano.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package pointcloud;

/**
 * A 3D geometric point that represents the x, y, z coordinates. The point is
 * comparable in the priority Z X and Y .
 *
 * @author Jean-Jacques Ponciano.
 */
public class Point implements Comparable<Point> {

    protected float x;
    protected float y;
    protected float z;

    /**
     * Creates a new instance of Point.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     */
    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculate the distance between both points.
     *
     * @param p other point.
     * @return The distance between both points.
     */
    public double distance(Point p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) + Math.pow(z - p.getZ(), 2));
    }

    /**
     * Get the point calculate by the linear interpolation.
     *
     * @param p next extremity.
     * @param t value of the interpolation(usualy between 0 and 1).
     * @return The point at the specifique value in the linear interpolation.
     */
    public Point linearInterpolation(geometry.Point p, double t) {
        float px = (float) ((1.f - t) * this.x + t * p.getX());
        float py = (float) ((1.f - t) * this.y + t * p.getY());
        float pz = (float) ((1.f - t) * this.z + t * p.getZ());
        return new Point(px, py, pz);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return x + "\t" + y + "\t" + z;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Float.floatToIntBits(this.x);
        hash = 97 * hash + Float.floatToIntBits(this.y);
        hash = 97 * hash + Float.floatToIntBits(this.z);
        return hash;
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
        final Point other = (Point) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
            return false;
        }
        return true;
    }

    /**
     * Get point's coordinates in a array.
     *
     * @return a array of Float representing point's coordinates.
     * <ul>
     * <li>0:x</li>
     * <li>1:y</li>
     * <li>2:z</li>
     * </ul>
     *
     */
    public float[] getArray() {
        float[] array = {this.x, this.y, this.z};
        return array;
    }

    @Override
    public int compareTo(Point o) {
        if (this.z < o.getZ()) {
            return -1;
        } else if (this.z > o.getZ()) {
            return 1;
        } else if (this.x < o.getX()) {
            return -1;
        } else if (this.x > o.getX()) {
            return 1;
        } else if (this.y < o.getY()) {
            return -1;
        } else if (this.y > o.getY()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Test is this instance of point is under a specific point
     *
     * @param o specific point
     * @return true if this instance is under the point specify, false
     * otherwise.
     */
    public boolean isUnder(Point o) {
        if (this.x < o.getX()
                && this.y < o.getY()
                && this.z < o.getZ()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Test is this instance of point is upper a specific point
     *
     * @param o specific point
     * @return true if this instance is upper the point specify, false
     * otherwise.
     */
    public boolean isUpper(Point o) {
        if (this.x > o.getX()
                && this.y > o.getY()
                && this.z > o.getZ()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the medium point between both points.
     *
     * @param point other point used
     * @return The mediun point between both points.
     */
    public Point medium(Point point) {
        float xm = (this.x + point.getX()) / 2.0f;
        float ym = (this.y + point.getY()) / 2.0f;
        float zm = (this.z + point.getZ()) / 2.0f;
        return new Point(xm, ym, zm);
    }
}
