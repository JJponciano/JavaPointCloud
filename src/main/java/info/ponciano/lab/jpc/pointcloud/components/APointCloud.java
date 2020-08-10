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

import info.ponciano.lab.jpc.pointcloud.bounding.MinOrientedBoundingBoxComputer2D;
import info.ponciano.lab.jpc.pointcloud.stucture.octree.Voxel;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.math.vector.Normal;
import info.ponciano.lab.jpc.math.vector.Vector3d;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class is used to manage 3D point cloud.
 *
 * @author Dr. Jean-Jacques Ponciano.
 */
public abstract class APointCloud implements Serializable {

    protected Coord3D centroid;
    protected Normal meanNormal;
    protected double dx;// vector of dimension X
    protected double dy; // vector of dimension Y
    protected double dz;// vector of dimension Z 
    protected Voxel obb;  // oriented bouding box
    protected double minZ; // minz
    protected double maxZ;// maxz
    protected double volume;
    protected double area;
    protected Point maxPoint;
    protected Point minPoint;

    /**
     * Create new instance of <code>PointCloud</code>
     */
    public APointCloud() {
        this.obb = null;
        this.dz = -1;
        this.volume = -1;
        this.area = -1;
        this.centroid = null;
    }

    /**
     * Add a point in the point cloud.
     *
     * @param p point to be added.
     */
    public abstract void add(Point p);

    /**
     * Add points in the point cloud.
     *
     * @param points points to be added.
     */
    public abstract void add(Collection<Point> points);

    public void add(APointCloud cloud) {
        cloud.stream().forEach(p -> this.add(p));
    }

    /**
     * Size of the point cloud
     *
     * @return The number of points in the point cloud.
     */
    public abstract int size();

    /**
     * Remove the point
     *
     * @param noisePoint point to be removed
     */
    public abstract void remove(Point noisePoint);

    /**
     * Tests if the point cloud has at least one point.
     *
     * @return true if the point cloud has points, false otherwise.
     */
    public abstract boolean isEmpty();

    /**
     * Returns maximum Euclidean distance to consider normals as similar.
     *
     * @return maximum Euclidean distance to consider normals as similar.
     */
    public double getOptimalNormal() {
        if (this.iterator().hasNext()) {
            throw new InternalError("Point cloud empty for getOptimalNormal");
        }
        Normal n = this.getMean().getNormal();
        List<Double> d = new ArrayList<>();
        Iterator<Point> iterator = this.iterator();
        while (iterator.hasNext()) {
            Point next = iterator.next();
            d.add(next.getNormal().distance(n));
        }
        double mean = 0.0;
        for (int i = 0; i < d.size(); i++) {
            mean += d.get(i);
        }
        mean /= d.size();
        int variance = 0;
        for (int i = 0; i < d.size(); i++) {
            variance += (d.get(i) - mean) * (d.get(i) - mean);
        }
        variance /= d.size();
        var std = Math.sqrt(variance);
        return (std) * 0.65;
    }

    @Override
    @Deprecated
    public int hashCode() {
        return this.getCentroid().hashCode();
    }
    public String hash() {
        return this.getCentroid().hash();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        try {

            final APointCloud other = (APointCloud) obj;

            if (this.size() != other.size()) {
                return false;
            }
            boolean ctd = this.getCentroid().equals(other.getCentroid());
            return ctd;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Test if the point cloud is coloured
     *
     * @return true if the point cloud is coloured.
     */
    public boolean isColored() {
        if (this.isEmpty()) {
            return false;
        }
        return (this.iterator().next().isColored());
    }

    public boolean isNormalized() {
        if (this.isEmpty()) {
            return false;
        }
        return (this.iterator().next().isNormalized());
    }

    public void setColor(Color color) {
        stream().forEach((point) -> {
            point.setColor(color);
        });
    }

    public abstract void clear();

    public double getMinZ() {
        if (dz < 0) {
            computeMinMaxZ();
        }
        return minZ;
    }

    public double getMaxZ() {
        if (dz < 0) {
            computeMinMaxZ();
        }
        return maxZ;
    }

    /**
     * Get the Oriented Bounding Box of the point cloud and compute it if
     * necessary.
     *
     * @return a Voxel representing the OBB of the point cloud.
     */
    public Voxel getOBB() {
        if (obb == null) {
            computesOBB();
        }
        return this.obb;
    }

    public double getDx() {
        if (obb == null) {
            computesOBB();
        }
        return dx;
    }

    public double getDy() {
        if (obb == null) {
            computesOBB();
        }
        return dy;
    }

    public double getDz() {
        if (dz < 0) {
            computeMinMaxZ();
        }
        return dz;
    }

    public double getVolume() {
        if (obb == null) {
            computesOBB();
        }
        return volume;
    }

    public double getArea() {
        if (obb == null) {
            computesOBB();
        }
        return area;
    }

    private void computesOBB() {
        if (dz < 0) {
            computeMinMaxZ();
        }
        if (this.size() > 4) {
            Coord3D center = this.center();
            List<Point2D> p2d = new ArrayList();
            this.stream().forEach(point -> {
                double x = point.getCoords().getX();
                double y = point.getCoords().getY();
                p2d.add(new Point2D.Double(x, y));
            });

            List<Point2D> minObbCorners = MinOrientedBoundingBoxComputer2D.computeCorners(p2d);
            List<Point> vpoints = new ArrayList();
            for (Point2D p : minObbCorners) {
                vpoints.add(new Point(p.getX(), p.getY(), this.minZ));
                vpoints.add(new Point(p.getX(), p.getY(), this.maxZ));
            }
            this.obb = new Voxel(vpoints);
            this.obb.move(new Coord3D(-center.getX(), -center.getY(), 0));
            this.uncenter(center);

            computeSpatialValues();
        } else {
            this.obb = new Voxel(this.getMinPoint(), this.getMaxPoint());
        }
    }

    private void computeSpatialValues() {
        Coord3D coordsMin = this.obb.getMin().getCoords();
        Coord3D coordsMax = this.obb.getMax().getCoords();
        this.dx = Math.abs(coordsMax.getX() - coordsMin.getX());
        this.dy = Math.abs(coordsMax.getY() - coordsMin.getY());
        this.area = this.dx * this.dy;
        this.volume = this.area * this.dz;
    }

    private void computeMinMaxZ() {
        if (this.size() <= 1) {
            this.dz = 0;
        } else {
            boolean notSet = true;
            this.minZ = -1;
            this.maxZ = -1;
            // extract min and max Z
            Iterator<Point> iterator = iterator();
            while (iterator.hasNext()) {
                Point p = iterator.next();
                if (notSet) {
                    this.minZ = p.getCoords().getZ();
                    this.maxZ = p.getCoords().getZ();
                    notSet = false;
                } else {
                    if (p.getCoords().getZ() < minZ) {
                        minZ = p.getCoords().getZ();
                    } else if (p.getCoords().getZ() > maxZ) {
                        maxZ = p.getCoords().getZ();
                    }
                }
            }
            this.dz = Math.abs(this.maxZ - this.minZ);
        }
    }

    /**
     * Get the centroid of the point cloud.
     *
     * @return the centroid of the point cloud.
     */
    public Coord3D getCentroid() {
        if (this.centroid == null) {
            double x = 0;
            double y = 0;
            double z = 0;
            for (Iterator<Point> iterator = this.iterator(); iterator.hasNext();) {
                Point point = iterator.next();
                x += point.getCoords().getX();
                y += point.getCoords().getY();
                z += point.getCoords().getZ();
            }
            x /= this.size();
            y /= this.size();
            z /= this.size();
            this.centroid = new Coord3D(x, y, z);
        }
        return this.centroid;
    }

    public Color getMeanColor() {
        List<Color> average = new ArrayList<>();
        for (Iterator<Point> iterator = this.iterator(); iterator.hasNext();) {
            Point point = iterator.next();
            average.add(point.getColor());
        }
        return new Color(average);
    }

    public Normal getMeanNormal() {
        if (this.meanNormal == null) {
            double x = 0;
            double y = 0;
            double z = 0;
            for (Iterator<Point> iterator = this.iterator(); iterator.hasNext();) {
                Point point = iterator.next();
                x += point.getNormal().getX();
                y += point.getNormal().getY();
                z += point.getNormal().getZ();
            }
            x /= this.size();
            y /= this.size();
            z /= this.size();
            this.meanNormal = new Normal(x, y, z);
        }
        return meanNormal;
    }

    public abstract Point getMean();

    /**
     * Get the nearest point of the point cloud to the given point distance with
     * another pointcloud
     *
     * @param p point considered for searching its nearest.
     * @return the nearest point of the cloud to p.
     */
    public Point getNearest(Point p) {
        Double globalMinDistance;
        List<Double> localMinDistances = new ArrayList<>();
        Point nearest = null;
        double localMinDistance = Double.MAX_VALUE;
        for (Iterator<Point> iterator = this.iterator(); iterator.hasNext();) {
            Point pointA = iterator.next();
            double distance = p.getCoords().distance(pointA.getCoords());
            if (distance < localMinDistance) {
                localMinDistance = distance;
                nearest = pointA;
            }
        }
        return nearest;

    }

    /**
     * Lock every point of the point cloud
     */
    public void lock() {
        this.stream().forEach((p) -> p.setLock(true));
    }

    /**
     * Unlock every point of the point cloud
     */
    public void unlock() {
        this.stream().forEach((p) -> p.setLock(false));
    }

    /**
     * return the two nearest points of the both cloud
     *
     * @param cloud cloud to be processed
     * @return the two nearest points of the both cloud
     */
    public Point[] getNearests(APointCloud cloud) {
        Point[] ps = new Point[2];
        double localMinDistance = Double.MAX_VALUE;

        for (Iterator<Point> iterator = this.iterator(); iterator.hasNext();) {
            Point pointA = iterator.next();
            for (Iterator<Point> iterator2 = cloud.iterator(); iterator2.hasNext();) {
                Point pointB = iterator2.next();
                Vector3d v = new Vector3d(pointA.getCoords(), pointB.getCoords());
                Double distance = v.getNorm();
                if (distance < localMinDistance) {
                    localMinDistance = distance;
                    ps[0] = pointA;
                    ps[1] = pointB;
                }
            }
        }
        return ps;
    }

    /**
     * Get the distance between the centroid of the both point cloud
     *
     * @param other other point cloud
     * @return the distance between both centroid.
     */
    public double getCentroidDistance(APointCloud other) {
        return this.getCentroid().distance(other.getCentroid());
    }

    /**
     * Get the density of the point cloud according to its volume and its number
     * of point.
     *
     * @return the density of the point cloud.
     */
    public double getDensity() {
        return this.getOBB().getVolume() / this.size();
    }

    /**
     * Set the oriented bounding box
     *
     * @param obb
     */
    void setOBB(Voxel obb) {
        this.obb = obb;
        computeSpatialValues();
    }

    private double area() {
        Point next = this.iterator().next();

        //compute the size of the cloud
        double[] min = {next.getCoords().getX(), next.getCoords().getY(), next.getCoords().getZ()};
        double[] max = {next.getCoords().getX(), next.getCoords().getY(), next.getCoords().getZ()};
        //test each point of the point cloud to seen if a point coordinates is a extremum of the cloud.
        this.stream().forEach((Point p) -> {
            if (p.getCoords().getX() < min[0]) {
                min[0] = p.getCoords().getX();
            } else if (p.getCoords().getX() > max[0]) {
                max[0] = p.getCoords().getX();
            }

            if (p.getCoords().getY() < min[1]) {
                min[1] = p.getCoords().getY();
            } else if (p.getCoords().getY() > max[1]) {
                max[1] = p.getCoords().getY();
            }
            if (p.getCoords().getZ() < min[2]) {
                min[2] = p.getCoords().getZ();
            } else if (p.getCoords().getZ() > max[2]) {
                max[2] = p.getCoords().getZ();
            }
        });
        //Calculate the area of the cloud 
        this.maxPoint = new Point(max[0], max[1], max[2]);
        this.minPoint = new Point(min[0], min[1], min[2]);
        this.maxPoint.setColor(next.getColor());
        this.minPoint.setColor(next.getColor());
        return (max[0] - min[0]) * (max[1] - min[1]) * (max[2] - min[2]);
    }

    public Point getMaxPoint() {
        if (this.maxPoint == null) {
            this.area();
        }
        return this.maxPoint;
    }

    public Point getMinPoint() {
        if (this.minPoint == null) {
            this.area();
        }
        return this.minPoint;
    }

    /**
     * Center the point cloud
     *
     * @return the centroid use to centre the point cloud or null if the point
     * cloud is empty.
     */
    public Coord3D center() {
        if (this.size() > 0) {
            this.move(this.getCentroid());
            return this.getCentroid();
        }
        return null;
    }

    /**
     * Reset the point cloud at its original place Center the point cloud
     *
     * @param centroid centroid of the point cloud used for centering
     */
    public void uncenter(Coord3D centroid) {
        this.stream().forEach((Point point) -> {
            point.getCoords().setX(point.getCoords().getX() + centroid.getX());
            point.getCoords().setY(point.getCoords().getY() + centroid.getY());
            point.getCoords().setZ(point.getCoords().getZ() + centroid.getZ());
        });
    }

    /**
     * Moves the point cloud according to a given vector
     *
     * @param movingVector moving vector
     */
    public void move(Coord3D movingVector) {
        this.stream().forEach((Point point) -> {
            point.getCoords().setX(point.getCoords().getX() - movingVector.getX());
            point.getCoords().setY(point.getCoords().getY() - movingVector.getY());
            point.getCoords().setZ(point.getCoords().getZ() - movingVector.getZ());
        });
    }

    public abstract Iterator<Point> iterator();

    public abstract Stream<Point> stream();

}
