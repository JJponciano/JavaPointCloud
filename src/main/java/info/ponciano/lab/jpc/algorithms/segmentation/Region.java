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
package info.ponciano.lab.jpc.algorithms.segmentation;

import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.vector.Normal;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * @author Dr. Jean-Jacques Ponciano
 */
public abstract class Region {

    private boolean closed;
    private final UUID id;
    protected final PointCloudMap cloud;
    private final Set<Region> adjoinedRegions;

    public Region(final APointCloud points) {
        this.id = UUID.randomUUID();
        this.closed = false;
        this.adjoinedRegions = new LinkedHashSet<>();
        this.cloud = new PointCloudMap();
        this.cloud.add(points);
    }

    public UUID getId() {
        return id;
    }

    public PointCloudMap getCloud() {
        return cloud;
    }

    Coord3D getCentroid() {
        return this.cloud.getCentroid();
    }

    synchronized void addAdjoinedRegion(final Region reg2) {
        this.adjoinedRegions.add(reg2);
    }

    public Set<Region> getAdjoinedRegions() {
        return adjoinedRegions;
    }

    public void close() {
        this.closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public Normal getMeanNormal() {
        return this.cloud.getMeanNormal();
    }
    public Color getMeanColor() {
        return this.cloud.getMeanColor();
    }

    public int size() {
        return this.cloud.size();
    }

    /**
     * Test if the regions can merge with another
     *
     * @param reg2 second region to test
     * @return true if both region can be merge in one, false otherwise.
     */
    public boolean canMerge(Region reg2) {
        return isClose(reg2) && isSimilar(reg2);
    }

    /**
     * Test if the region is close to another
     *
     * @param reg2 second region to test
     * @return true if both region are close, false otherwise.
     */
    protected abstract boolean isClose(Region reg2);

    /**
     * Test if the region is close to another in X value
     *
     * @param reg2 second region to test
     * @return true if both region are close, false otherwise.
     */
    protected abstract boolean isCloseX(Region reg2);

    protected abstract boolean isSimilar(Region reg2);

    /**
     * Gets the maximum distance to consider two points as closed
     *
     * @return the distance use for the merging.
     */
    public abstract double getDistance();

    /**
     * Merge recurrsivelly all linked region.
     *
     * @param id id of the new region.
     * @return the new region created.
     */
    public Region mergeLinkedRegion() {
        if (this.isClosed()) {
            return null;
        }
        final Collection<Region> regions = this.mergeRegionsAdjoined();
        return this.merge(regions);
    }

    /**
     * Merges collection of regions in a new one
     *
     * @param regions regions to be merged
     * @param id id of the new regions created
     * @return the region created by the merge of all given regions.
     */
    protected Region merge(Collection<Region> regions) {
        PointCloudMap points = new PointCloudMap();
      regions.stream().map(region -> region.getCloud()).forEach(rpc -> {
            points.add(rpc);
        });
        return this.createRegion(points);
    }

    /**
     * Merge regions and close there
     *
     * @param region region to be analysed
     * @return the list of all regions to merge (all regions adjoined to the
     * region or adjoined to an adjoined region of the region).
     */
    private Collection<Region> mergeRegionsAdjoined() {

        // if the current regions is closed, return null
        if (this.isClosed()) {
            return null;
        }
        final TreeMap<UUID, Region> superRegion = new TreeMap<>();
        // get all adjoineds region
        final LinkedList<Region> toBExplored = new LinkedList<>();
        toBExplored.push(this);
        while (!toBExplored.isEmpty()) {
            final Region pop = toBExplored.pop();
            // add the adjoined region to the current regions
            // if the regions is not already added
            if (!superRegion.containsKey(pop.getId())) {
                // adds it
                superRegion.put(pop.getId(), pop);
            }
            // close the region
            pop.close();
            // test if the regions is not closed to run recursivity trategy
            pop.getAdjoinedRegions().stream().filter(r -> !r.isClosed()).forEach(r -> toBExplored.push(r));
        }
        // return the region
        return superRegion.values();
    }

    protected abstract Region createRegion(APointCloud points);

}
