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
package PointCloud;

import pointcloud.model.base.Point3D;

/**
 * A 3D geometric point that represents the x, y, z coordinates
 *
 * @author Jean-Jacques Ponciano
 */
public class Point {

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
    public double distance(geometry.Point p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) + Math.pow(z - p.getZ(), 2));
    }

    /**
     * Get the point calculate by the linear interpolation.
     *
     * @param p next extremity.
     * @param t value of the interpolation(usualy between 0 and 1).
     * @return The point at the specifique value in the linear interpolation.
     */
    public geometry.Point linearInterpolation(geometry.Point p, double t) {
        float px = (float) ((1.f - t) * this.x + t * p.getX());
        float py = (float) ((1.f - t) * this.y + t * p.getY());
        float pz = (float) ((1.f - t) * this.z + t * p.getZ());
        return new Point3D(px, py, pz);
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
}
