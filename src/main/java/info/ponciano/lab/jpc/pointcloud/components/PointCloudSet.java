/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
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
package info.ponciano.lab.jpc.pointcloud.components;

import info.ponciano.lab.jpc.math.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

public class PointCloudSet extends APointCloud {

    protected Set<Point> points;

    public PointCloudSet() {
        super();
        this.points = new LinkedHashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        points.forEach((point) -> {
            buff.append(point.toString()).append("\n");
        });
        return buff.toString();
    }

    @Override
    public void add(Point p) {
        if (p != null) {
            this.points.add(p);
        }
    }

    @Override
    public void add(Collection<Point> points) {
        this.points.addAll(points);
    }

    @Override
    public int size() {
        return this.points.size();
    }

    @Override
    public void remove(Point noisePoint) {
        this.points.remove(noisePoint);
    }

    @Override
    public boolean isEmpty() {
        return this.points.isEmpty();
    }

    @Override
    public void clear() {
        this.points.clear();
    }

    @Override
    public Point getMean() {
        return Point.getMean(this.points);
    }

    @Override
    public Iterator<Point> iterator() {
        return this.points.iterator();
    }

    @Override
    public Stream<Point> stream() {
        return this.points.stream();
    }

}
