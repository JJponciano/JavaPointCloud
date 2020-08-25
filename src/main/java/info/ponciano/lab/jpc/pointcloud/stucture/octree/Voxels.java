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

import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.math.PiMath;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.opengl.Display;
import info.ponciano.lab.jpc.opengl.IObjectGL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class Voxels {

    private final List<Voxel> voxels;
    /**
     * min area found among voxels
     */
    private double minVolume;

    public Voxels() {
        this.minVolume = Integer.MAX_VALUE;
        this.voxels = new LinkedList<>();
    }

    public Voxels(List<Voxel> voxels) {
        this.minVolume = Integer.MAX_VALUE;
        this.voxels = new LinkedList<>();
        this.voxels.addAll(voxels);
    }

    /**
     * Add a box.
     *
     * @param box box to be added
     */
    public void add(Voxel box) {
        if (this.isEmpty()) {
            this.minVolume = box.getVolume();
        } else {
            // campute the min area of voxels.
            this.minVolume = Double.min(box.getVolume(), this.minVolume);
        }
        this.voxels.add(box);
    }

    /**
     * Get the minimum area among all voxels.
     *
     * @return the minimum area among all voxels.
     */
    public double getMinVolume() {
        return minVolume;
    }

    public Stream<Voxel> stream() {
        return voxels.stream();
    }

    public Iterator<Voxel> getVoxels() {
        return this.voxels.iterator();
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    public boolean isEmpty() {
        return this.voxels.isEmpty();
    }

    public int size() {
        return this.voxels.size();
    }

    /**
     * Adds a point in the right voxels
     *
     * @param point point to be added
     * @return true if the point is added, false otherwise.
     */
    public boolean addInsideFast(Point point) {
        int i = 0;
        boolean notAdded = true;
        Iterator<Voxel> iterator = this.voxels.iterator();
        while (iterator.hasNext()&& notAdded) {
            Voxel voxel = iterator.next();
            notAdded = !point.getCoords().isBetween(voxel.getMin().getCoords(), voxel.getMax().getCoords());
            if (!notAdded) {
                voxel.add(point);
            } else {
                i++;
            }
        }
        return i < this.size();
    }

    /**
     * Adds a point in the right voxels
     *
     * @param point point to be added
     * @return true if the point is added, false otherwise.
     */
    public boolean addInside(Point point) {
        int i = 0;
        Iterator<Voxel> iterator = this.voxels.iterator();
        boolean added=true;
        while (iterator.hasNext()&& !added) {
            Voxel voxel = iterator.next();
            added=voxel.add(point);
        }
        return added;
    }

    /**
     * Gets the point cloud composed of the mean point of each leaf.
     *
     * @return Point cloud composed of the mean leaves points
     */
    public PointCloudMap getMeanPoints() {
        PointCloudMap result = new PointCloudMap();
        this.voxels.stream().map((box) -> box.getMean()).forEachOrdered((p) -> {
            // get the mean point for the cloud
            // center or Mean
            result.add(p);
        });
        return result;
    }

    /**
     *
     * Get voxels view to be draw.
     *
     *
     *
     * <h2>Example to draw voxels:</h2> <code>
     * <p>
     * Display dis = new Display(width, height);
     * </p>
     * <p>
     * voxels.getView().forEach((boxView) -> { dis.addObject(boxView); });
     * </p>
     * <p>
     * dis.run();
     * </p></code>
     *
     * @return array of voxels views.
     *
     */
    public ArrayList<IObjectGL> getView() {
        ArrayList<IObjectGL> views = new ArrayList<>();
        this.voxels.forEach((boxe) -> {
            views.add(new VoxelView(boxe));
        });
        return views;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.voxels);
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
        final Voxels other = (Voxels) obj;
        return Objects.equals(this.voxels, other.voxels);
    }

    /**
     * Get the maximum density of point ( the voxel volume divided by number of
     * point)
     *
     * @return the maximum density found among all voxels.
     */
    public double getMaxDensity() {
        List<Double> densities = new ArrayList<>();
        this.voxels.forEach((voxel) -> {
            densities.add(voxel.getDensity());
        });
        return PiMath.max(densities);
    }

    public double getMaxSize() {
        List<Double> pcount = new ArrayList<>();
        this.voxels.forEach((voxel) -> {
            pcount.add((double) voxel.pointCount());
        });
        return PiMath.max(pcount);
    }

    public Voxel getFirst() {
        if (this.voxels.isEmpty()) {
            return null;
        }
        return this.voxels.iterator().next();
    }

    public void setColor(Color color) {
        for (Voxel voxel : voxels) {
            voxel.setColor(color);
        }
    }

    /**
     * Display voxels in an new Window
     * <pre><code>
     *  Voxel v = new Voxel(new Point(minx, miny, minz), new Point(maxx, maxy, maxz));
     * Voxels voxels=new Voxels();
     * voxels.add(v);
     * final Display display = new Display(null, true);
     * voxels.forEach(v -> display.addObject(v.getView()));
     * display.run();
     * </code></pre>
     *
     * @param title Window title
     */
    public void show(String title) {
        /*   Voxel v = new Voxel(new Point(minx, miny, minz), new Point(maxx, maxy, maxz));
        Voxels voxels=new Voxels();
        voxels.add(v); */
        final Display display = new Display(null, true);
        voxels.forEach(v -> display.addObject(v.getView()));
        display.run();
    }

    public void removeAlone() {
        this.voxels.removeIf((t) -> {
            return  t.size()<2; 
        });
    }

}
