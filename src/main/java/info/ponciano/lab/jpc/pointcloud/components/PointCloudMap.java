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
package info.ponciano.lab.jpc.pointcloud.components;

import info.ponciano.lab.jpc.math.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

/**
 * This class is used to manage 3D point cloud.
 *
 * @author Dr. Jean-Jacques Ponciano.
 */
public class PointCloudMap extends APointCloud {

    protected LinkedHashMap<String, Point> points;

    public PointCloudMap() {
        super();
        this.points = new LinkedHashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        points.values().forEach((point) -> {
            buff.append(point.toString()).append("\n");
        });
        return buff.toString();
    }

    @Override
    public void add(Point p) {
        if (p != null) {
            String hashCode = p.hash();
            if (!this.points.containsKey(hashCode)) {
                this.points.put(hashCode, p);
            } 
//            else {
//                Point get = this.points.get(hashCode);
//                System.out.println("**************************");
//                System.out.println(get.toString());
//                System.out.println(p.toString());
//                System.out.println(get.hash() + " = " + p.hash());
//            }

        }
    }

    @Override
    public void add(Collection<Point> points) {
        points.forEach(p -> this.add(p));
    }

    @Override
    public int size() {
        return this.points.size();
    }

    @Override
    public void remove(Point noisePoint) {
        Point remove = this.points.remove(noisePoint.hashCode());
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
        return Point.getMean(this.points.values());
    }

    @Override
    public Iterator<Point> iterator() {
        return this.points.values().iterator();
    }

    @Override
    public Stream<Point> stream() {
        return this.points.values().stream();
    }

    public Point get(String i) {
        return this.points.get(i);
    }

}
