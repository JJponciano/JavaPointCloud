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

import java.awt.Color;

/**
 * A 3D geometric point that represents the x, y, z coordinates and a color.
 *
 * @author Jean-Jacques Ponciano
 */
public class PointColor extends Point {

    protected Color color;

    /**
     * Creates a new instance of PointColor with the white color.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     */
    public PointColor(float x, float y, float z) {
        super(x, y, z);
        this.color = Color.WHITE;
    }

    /**
     *
     * Creates a new instance of PointColor.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @param color The color of the point.
     */
    public PointColor(float x, float y, float z, Color color) {
        super(x, y, z);
        this.color = color;
    }

    /**
     * Create a white point at the origin.
     */
    public PointColor() {
        super(0, 0, 0);
        this.color = Color.WHITE;
    }

    /**
     * Creates a new instance of <code>PointColor</code>.
     *
     * @param point point contained the coordinate of the new instance.
     */
    public PointColor(Point point) {
        super(point.getX(), point.getY(), point.getZ());
        this.color = Color.WHITE;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String s = x + "\t" + y + "\t" + z + "\t"
                + this.color.getRed() + "\t" + this.color.getGreen() + "\t" + this.color.getBlue();
        return s;
    }

    /**
     * Modification of the value of the coordinate represented by c1.
     *
     * @param c1 the representations of the coordinate
     * <ol>
     * <li>0:x</li>
     * <li>1:y</li>
     * <li>2:z</li>
     * </ol>
     * @param value new value of the coordinate.
     */
    public void set(int c1, float value) {
        switch (c1) {
            case 0:
                this.x = value;
                break;
            case 1:
                this.y = value;
                break;
            case 2:
                this.z = value;
                break;
            default:
                break;
        }

    }
}
