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
package info.ponciano.lab.jpc.pointcloud.stucture.octree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;

import info.ponciano.lab.jpc.math.Point;

import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.opengl.IObjectGL;
import java.io.Serializable;

/**
 * Voxel which contains many points.
 *
 * @author jean-jacques.ponciano
 */
public class Voxel extends Box implements Serializable{

    protected PointCloudMap cloud;
    protected Point mean;
    private Color color;

    /**
     * Creates a new instance of <code>Voxel</code>.
     *
     * @param p1 first point of the diagonal.
     * @param p2 second point of the diagonal.
     */
    public Voxel(final Point p1, final Point p2) {
        super(p1, p2);
        this.cloud = new PointCloudMap();
        if (p1.isColored() && p2.isColored()) {
            this.color = new Color(p1.getColor(), p2.getColor());
        } else {
            this.color = Color.WHITE;
        }
    }

    public Voxel(final List<Point> p) {
        super(p);
        this.cloud = new PointCloudMap();
        if (p.get(0).isColored()) {
            this.color = new Color(p.get(0).getColor(), p.get(1).getColor(), p.get(2).getColor(), p.get(3).getColor(),
                    p.get(4).getColor(), p.get(5).getColor(), p.get(6).getColor(), p.get(7).getColor());
        } else {
            this.color = Color.WHITE;
        }
    }

    /**
     * Creates a new instance of <code>Voxel</code>.
     *
     * @param center center of the voxel
     * @param width distance used to compute the X size of the voxel.
     * @param height distance used to compute the Y size of the voxel.
     * @param lenght distance used to compute the Z size of the voxel.
     */
    public Voxel(final Point center, final double width, final double height, final double lenght) {
        super(new Point(center.getCoords().getX() - width / 2.0, center.getCoords().getY() - height / 2.0,
                center.getCoords().getZ() - lenght / 2.0),
                new Point(center.getCoords().getX() + width / 2.0, center.getCoords().getY() + height / 2.0,
                        center.getCoords().getZ() + lenght / 2.0));
        this.cloud = new PointCloudMap();
        if (center.isColored()) {
            this.color = center.getColor();
        } else {
            this.color = Color.WHITE;
        }
    }

    /**
     * Adds a point a the voxel if the point is inside the voxel.
     *
     * @param point point to be added.
     * @return True of the point could be added, false if the point is not
     * inside the voxel.
     */
    public boolean add(final Point point) {
        final boolean inside = this.isInside(point);
        if (inside) {
            // add the point
            this.cloud.add(point);
            // calcule the mean
            if (this.mean == null) {
                this.mean = point;
            } else {
                final Point mean1 = point.getMean(mean, this.pointCount() - 1);
                this.mean = mean1;
            }
            if (mean.isColored()) {
                this.color = mean.getColor();
            }
        }
        return inside;
    }

    public void add(final Collection<Point> points) {
//        this.cloud.add(points);
//        this.calculatesMean();
        points.forEach(p -> {
           if(!this.add(p)) System.err.println("point cannot be added");
        });
    }

    /**
     * Get points contained in the Box.
     *
     * @return all points inside the voxel.
     */
    public PointCloudMap getPointsContained() {
        return cloud;
    }

    /**
     * Calculates the mean point from points contained into the voxel
     */
    private void calculatesMean() {
        this.mean = cloud.getMean();
        if (mean.isColored()) {
            this.color = this.mean.getColor();
        }
    }

    /**
     * Split the voxel according to the dimension (4 voxel in 2D, 8 voxel in 3D)
     * and remove all sub-voxel which are empty.
     *
     * @return Sub voxels created and filled or null if the split voxels have
     * diagonal length less than the <code>cloudResolution</code>.
     */
    @Override
    public ArrayList split() {
        // create the number of voxel corresponding to the number of points
        final ArrayList<Voxel> voxels = new ArrayList<>();
        // -------Split
        // get the center point of the cube of voxel.
        final Point centre = new Point(this.points);
        for (final Point point : this.points) {
            voxels.add(new Voxel(point, centre));
        }
        // -------Fill
        // fill subBox with points to be splitted
        this.cloud.stream().forEach((Point point) -> {
            int j = 0;
            boolean added = false;
            while (!added && j < voxels.size()) {
                added = voxels.get(j).add(point);
                j++;
            }
            if (!added) {
//                boolean k = this.isInside(point);
//                Display d = new Display(null, true);
//                Voxel voxel = new Voxel(point, 1, 1, 1);
//                Coord3D coord3D = new Coord3D(413387.67820, 5318067.80870, 308.50620);
//                voxel.move(coord3D);
//                voxel.setColor(Color.RED);
//                d.addObject(voxel.getView());
//                voxels.forEach(v -> {
//                    v.move(coord3D);
//                    d.addObject(v.getView());
//                });
//                Iterator<Voxel> iterator = voxels.iterator();
//                int size = voxels.size();
//                int o = 0;
//                while (iterator.hasNext()) {
//                    Voxel next = iterator.next();
//                    next.move(coord3D);
//                    next.setColor(Color.getColor(o++, size));
//                    d.addObject(voxels.get(0).getView());
//                }
//                d.run();
                 throw new InternalError("Point not added!");
                // throw new InternalError("A point is lost...");
            }
        });
        // remove empty voxel
        final ArrayList<Voxel> finalBoxes = new ArrayList<>();
        voxels.stream().filter((voxele) -> (!voxele.isEmpty())).forEachOrdered((voxele) -> {
            finalBoxes.add(voxele);
        });
        if (finalBoxes.isEmpty()) {
            throw new InternalError("The split of a voxel does not produce any sub-voxel!");
//finalBoxes.add(this);
        }
        return finalBoxes;
    }

    /**
     * Returns <tt>true</tt> if this voxel contains no elements.
     *
     * @return <tt>true</tt> if this voxel contains no elements
     */
    public boolean isEmpty() {
        return this.cloud.isEmpty();
    }

    /**
     * Return the number of point inside the voxel.
     *
     * @return the number of point inside the voxel.
     */
    public int pointCount() {
        return this.cloud.size();
    }

    public Point getMean() {
        if (this.mean == null) {
            this.calculatesMean();
        }
        return this.mean;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    /**
     * Colors every points contained in the cloud
     *
     * @param color color to be used to color points.
     */
    public void colorPoints(final Color color) {
        cloud.stream().forEach((point) -> {
            point.setColor(color);
        });
    }

    /**
     * Gets voxel view to be display with openGL
     *
     * @return return an openGL view.
     */
    public IObjectGL getView() {
        return new VoxelView(this);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Voxel other = (Voxel) obj;
        return Objects.equals(this.mean, other.mean);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.mean);
        return hash;
    }

    /**
     * Get the density of the voxel ( the voxel volume divided by number of
     * point)
     *
     * @return the density found for the voxels.
     */
    public double getDensity() {
        return this.getVolume() / (double) (this.pointCount());
    }
      /**
     * Get the optimal distance between two points in the voxel to determine if they are close 
     * @return distance maximum distance between to point to consider both point
     * as close.
     */
    public double getOptimalPointSpace() {
        double density = this.getVolume() / (double) (this.pointCount());
        return Math.cbrt(density)*2;
    }
    /**
     * Returns maximum Euclidean distance to consider normals as
     * similar.
     * @return maximum Euclidean distance to consider normals as
     * similar.
     */
     public double getOptimalNormal() {
         return this.cloud.getOptimalNormal();
    }

    /**
     * Move the voxel according to the given vector
     *
     * @param movingVector representing vector translation
     */
    public void move(final Coord3D movingVector) {
        for (final Point point : points) {
            point.getCoords().setX(point.getCoords().getX() - movingVector.getX());
            point.getCoords().setY(point.getCoords().getY() - movingVector.getY());
            point.getCoords().setZ(point.getCoords().getZ() - movingVector.getZ());
        }
    }

    public List<Point> getListPoints() {
        final List<Point> res = new ArrayList<>();
        res.addAll(Arrays.asList(this.points));
        return res;
    }

    @Override
    public String toString() {
        String s = "";
        for (final Point point : this.points) {
            s += point.toString() + "\n";
        }
        return s;
    }

    /**
     * Get the number of point contained in the voxel.
     *
     * @return the number of point contained in the voxel.
     */
    public int size() {
        return this.cloud.size();
    }

}
