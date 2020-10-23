
/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.ponciano.lab.jpc.math;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jean-jacques Ponciano
 */
public class Polygon {

    protected Color color;
    protected List<Point> ptlist;

    public Polygon() {
        color = Color.WHITE;
        this.ptlist = new ArrayList<>();
    }

    public Polygon(Color color, List<Point> ptlist) {
        this.ptlist = ptlist;
        this.setColor(color);
    }

    /**
     * Set the color of the polygon and color every point of the polygon
     *
     * @param color new color to be applied.
     */
    public final void setColor(Color color) {
        this.color = color;
        ptlist.forEach((point) -> {
            point.setColor(color);
        });
    }

    /**
     * Adds news point to the polygon.
     *
     * @param p point to be added
     */
    public void add(Point p) {
        this.ptlist.add(p);
    }

    public List<Point> getPtlist() {
        return ptlist;
    }

    public Point get(int i) {
        if (i < 0 || i > this.ptlist.size()) {
            throw new IndexOutOfBoundsException(i + "/" + this.ptlist.size());
        }
        return ptlist.get(i);
    }

    public void setPtlist(List<Point> ptlist) {
        this.ptlist = ptlist;
    }

    public Color getColor() {
        return color;
    }

  
}
