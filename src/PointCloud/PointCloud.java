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

/**
 *
 * @author Jean-Jacques Ponciano.
 */
public class PointCloud {

    protected ArrayList<PointColor> points;
    protected final String ext;

    /**
     * Creates a new instance of <code>PointCloud</code>.
     */
    public PointCloud() {
        this.ext = "pc";
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

    public void add(PointColor p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Iterable<Point> getPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public PointColor get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
