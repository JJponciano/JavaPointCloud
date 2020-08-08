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
package info.ponciano.lab.jpc.pointcloud;

import info.ponciano.lab.jpc.algorithms.IoPointcloud;
import info.ponciano.lab.jpc.algorithms.segmentation.Segmentation;
import info.ponciano.lab.jpc.algorithms.segmentation.SegmentationEuclidean;
import info.ponciano.lab.jpc.algorithms.segmentation.SegmentationNormal;
import info.ponciano.lab.jpc.algorithms.segmentation.Regions;
import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.algorithms.segmentation.Region;
import info.ponciano.lab.jpc.pointcloud.stucture.octree.Octree;
import info.ponciano.lab.jpc.pointcloud.stucture.octree.Voxel;
import info.ponciano.lab.jpc.pointcloud.stucture.octree.Voxels;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.RandomColor;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 */
public class Pointcloud implements Serializable {

    public static final char IRREGULAR_K = 'i';
    public static final char REGULAR_K = 'r';
    public static final char ISOLATED_K = 's';
    public int maxPatchSize = 5000;
    protected Map<String, APointCloud> patches;
    protected Set<String> regular = new LinkedHashSet<>();
    protected Set<String> irregular = new LinkedHashSet<>();
    protected Set<String> isolated = new LinkedHashSet<>();

    public Pointcloud() {
        this.patches = new LinkedHashMap<>();
    }

    /**
     * Refactor the instance by recreating patches according to a points
     * contained, and two thresholds for the patches creation.
     *
     */
    public void refactor() {
        APointCloud points = this.getPoints();
        this.patches.clear();
        this.init(points);
    }

    /**
     * Initialization of the instance according to a Collection of points, and
     * two thresholds for the patches creation.
     *
     * @param pointcloud Point cloud use to init the cloud
     */
    private void init(APointCloud pointcloud) {
        // extract regular patch and return the rest
        PointCloudMap unknow = segmentPatch(pointcloud, true);
        // extract irregular patch and return isolated
        unknow = segmentPatch(unknow, false);
        //gather isolated in patch
        Voxels rawVoxels = Octree.getRawVoxels(unknow, maxPatchSize);
        Iterator<Voxel> it = rawVoxels.getVoxels();
        while (it.hasNext()) {
            Voxel next = it.next();
            String add = this.add(next.getPointsContained());
            this.isolated.add(add);
        }
    }

    private PointCloudMap segmentPatch(APointCloud pointcloud, boolean normal) {
        PointCloudMap unknow = new PointCloudMap();
        Voxels rawVoxels = Octree.getRawVoxels(pointcloud, maxPatchSize);

        rawVoxels.removeAlone();
        Iterator<Voxel> it = rawVoxels.getVoxels();
        while (it.hasNext()) {
            Voxel next = it.next();
            Regions splitedRegion;
            if (normal) {
                Segmentation segmentationNormal = new SegmentationNormal(next);
                segmentationNormal.run();
                splitedRegion = segmentationNormal.getResults();
            } else {
                Segmentation segmentationEuclidean = new SegmentationEuclidean(next);
                segmentationEuclidean.run();
                splitedRegion = segmentationEuclidean.getResults();
            }
            List<Region> regions = splitedRegion.getRegions();
            for (Region r : regions) {
                double minSize;
                if (normal) {
                    minSize = 0.01 * maxPatchSize;
                } else {
                    minSize = 10;
                }
                if (r.size() < minSize) {
                    unknow.add(r.getCloud());
                } else {

                    String add = this.add(r.getCloud());
                    if (normal) {
                        this.regular.add(add);
                    } else {
                        this.irregular.add(add);
                    }
                }
            }
        }
        return unknow;
    }

    public void add(String id, APointCloud cloud) {
        this.patches.put(id, cloud);
    }

    public Iterator<APointCloud> iterator() {
        return patches.values().iterator();
    }

    public Map<String, APointCloud> getPatches() {
        return patches;
    }

    /**
     * Returns {@code true} if this set contains the specified element. More
     * formally, returns {@code true} if and only if this set contains an
     * element {@code Patch} such that {@code Patch.id==id}.
     *
     * @param id id of the patch
     * @return {@code true} if this set contains the specified element
     */
    public boolean contains(String id) {
        return this.patches.containsKey(id);
    }

    /**
     * Returns {@code Patch} that has the {@code id}
     *
     * @param id id of the patch
     * @return {@code Patch} found or null if the patch is not found
     */
    public APointCloud get(String id) {
        return this.patches.get(id);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     *
     * @param patch element to be added
     * @return the key assign at the element to retrieve it with
     * {@code Pointcloud.get}
     * @throws UnsupportedOperationException if the {@code add} operation is not
     * supported by this set
     * @throws ClassCastException if the class of the specified element prevents
     * it from being added to this set
     * @throws NullPointerException if the specified element is null and this
     * set does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     * element prevents it from being added to this set
     */
    public String add(APointCloud patch) {
        String hashCode = patch.getMean().hash();
        this.patches.put(hashCode, patch);
        return hashCode;
    }

    /**
     * Returns the number of point contained in the {@code Pointcloud}.
     *
     * @return the sum of all patches' size.
     */
    public int size() {
        int size = 0;
        Iterator<APointCloud> iterator = this.iterator();
        while (iterator.hasNext()) {
            APointCloud next = iterator.next();
            size += next.size();
        }
        return size;
    }

    /**
     * Returns all points that are contained in all patches.
     *
     * @return {@code APointCloud} contained in all patches
     */
    public APointCloud getPoints() {
        APointCloud points = new PointCloudMap();
        Iterator<APointCloud> iterator = this.iterator();
        while (iterator.hasNext()) {
            APointCloud next = iterator.next();
            next.stream().forEach(points::add);
        }
        return points;
    }

    /**
     * Assign a random colour at each patch.
     */
    public void randomColorizesPatches() {
        RandomColor rc = new RandomColor();
        Iterator<APointCloud> patches1 = this.iterator();
        while (patches1.hasNext()) {
            APointCloud next = patches1.next();
            next.setColor(rc.getColor());
        }
    }

    /**
     * Assign a random colour at each patch.
     */
    public void typeColorizesPatches() {
        Iterator<String> patches1 = this.irregular.iterator();
        while (patches1.hasNext()) {
            String next = patches1.next();
            this.get(next).setColor(Color.BLUE);
        }
        patches1 = this.regular.iterator();
        while (patches1.hasNext()) {
            String next = patches1.next();
            this.get(next).setColor(Color.GREEN);
        }
        patches1 = this.isolated.iterator();
        while (patches1.hasNext()) {
            String next = patches1.next();
            this.get(next).setColor(Color.RED);
        }
    }

    /**
     * Gets the value of the variable {@code maxPatchSize} used for patches
     * creation
     *
     * @return the maximum size that a patch can have.
     */
    public int getMaxPatchSize() {
        return maxPatchSize;
    }

    /**
     * Gets all index of patches identified as regular. A patch is identified as
     * regular if it is composed of homogeneous normals
     *
     * @return all index of patches identified as regular.
     */
    public Set<String> getRegular() {
        return regular;
    }

    /**
     * Gets all index of patches identified as irregular. A patch is identified
     * as irregular if it is composed of heterogeneous normals.
     *
     * @return all index of patches identified as irregular.
     */
    public Set<String> getIrregular() {
        return irregular;
    }

    /**
     * Gets all index of patches identified as isolated. A patch is identified
     * as isolated if it is composed of point without any common normals or
     * proximity.
     *
     * @return all index of patches identified as isolated.
     */
    public Set<String> getIsolated() {
        return isolated;
    }

    /**
     * Sets the value of the variable {@code maxPatchSize} used for patches
     * creation
     *
     * @param maxPatchSize the maximum size that a patch can have.
     */
    public void setMaxPatchSize(int maxPatchSize) {
        this.maxPatchSize = maxPatchSize;
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
        final Pointcloud other = (Pointcloud) obj;
        if (this.patches.size() != other.patches.size()) {
            return false;
        }
        Iterator<String> iterator = this.patches.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (!other.contains(next)) {
                return false;
            }
            if (!other.get(next).equals(this.get(next))) {
                return false;
            }
        }
        if (!Objects.equals(this.regular, other.regular)) {
            return false;
        }
        if (!Objects.equals(this.irregular, other.irregular)) {
            return false;
        }
        return Objects.equals(this.isolated, other.isolated);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.maxPatchSize;
        hash = 97 * hash + Objects.hashCode(this.regular);
        hash = 97 * hash + Objects.hashCode(this.irregular);
        hash = 97 * hash + Objects.hashCode(this.isolated);
        return hash;
    }

    /**
     * Sets the value of the patches {@code Map} {@code patches}
     *
     * @param patches new patches {@code Map}
     */
    public void setPatches(Map<String, APointCloud> patches) {
        this.patches = patches;
    }

    /**
     * Sets the value of the regular key {@code Set} {@code regular}
     *
     * @param patches new {@code Set} of key for {@code regular} patches
     */
    public void setRegular(Set<String> regular) {
        this.regular = regular;
    }

    /**
     * Sets the value of the irregular key {@code Set} {@code irregular}
     *
     * @param patches new {@code Set} of key for {@code irregular} patches
     */
    public void setIrregular(Set<String> irregular) {
        this.irregular = irregular;
    }

    /**
     * Sets the value of the isolated key {@code Set} {@code isolated}
     *
     * @param patches new {@code Set} of key for {@code isolated} patches
     */
    public void setIsolated(Set<String> isolated) {
        this.isolated = isolated;
    }

    /**
     * Initialise the {@code PointCloud} instance by loading point in a file
     * formatted in X Y Z R G B Nx Nz Nz KEY(optional). If the file has no key
     * defined of if all key are similar, the point cloud is automatically
     * refactored to create patches.
     *
     * @param path path of the file.
     * @throws FileNotFoundException if the file is not found.
     */
    public void loadASCII(String path) throws FileNotFoundException {
        IoPointcloud.loadASCII(path, this);
        int size = this.patches.size();
        //refactor the pointcloud to creates patches
        if (size == 1) {
            this.refactor();
        }
    }

    /**
     * Saves the point cloud in a file at the format X Y Z R G B Nx Nz Nz KEY
     * for each line and ending with index for {@code regular},{@code irregular}
     * and {@code isolated} patches.
     *
     * @param path path of the file.
     * @return true if the point cloud is correctly saved, false otherwise.
     */
    public boolean saveASCII(String path) {
        return IoPointcloud.saveASCII(path, this);
    }
}
