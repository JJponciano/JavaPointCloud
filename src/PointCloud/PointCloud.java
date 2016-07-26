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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jean-Jacques Ponciano.
 */
public class PointCloud {

    protected ArrayList<PointColor> points;

    /**
     * Creates a new instance of <code>PointCloud</code>.
     */
    public PointCloud() {
        this.points = new ArrayList<>();
    }

    public void setColor(Color color) {
        for (int i = 0; i < this.points.size(); i++) {
            this.points.get(i).setColor(color);
        }
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        for (PointColor point : points) {
            buff.append(point.toString()).append("\n");
        }
        return buff.toString();
    }

    /**
     * Add a point in the point cloud.
     *
     * @param p point to be added.
     */
    public void add(PointColor p) {
        if (p != null) {
            this.points.add(p);
        }
    }

    /**
     * Get all points in a <code>PointColor[]</code>
     *
     * @return all points in the point cloud in a array.
     */
    public PointColor[] getPoints() {
        PointColor[] parray = new PointColor[this.points.size()];
        parray = this.points.toArray(parray);
        return parray;
    }

    /**
     * Size of the point cloud
     *
     * @return The number of points in the point cloud.
     */
    public int size() {
        return this.points.size();
    }

    /**
     * Get the ith point in the point cloud.
     *
     * @param i point position in the point cloud
     * @return the point locate at the ith position in the point cloud or null
     * if no point is found.
     */
    public PointColor get(int i) {
        if (i >= 0 && i < this.size()) {
            return this.points.get(i);
        } else {
            return null;
        }
    }

}
