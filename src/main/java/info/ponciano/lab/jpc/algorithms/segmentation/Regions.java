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
package info.ponciano.lab.jpc.algorithms.segmentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Dr. Jean-Jacques Ponciano
 */
public class Regions {

    private List<Region> regions;

    public Regions() {
        super();
        regions = new ArrayList<>();
    }

    /*
     * public boolean add(Point e) { nr++; return regions.add(new
     * RegionEuclidean(nr, List.of(e))); }
     * 
     * public boolean add(List<Point> e) { nr++; return regions.add(new
     * RegionEuclidean(nr, e)); }
     */
    public boolean add(Region e) {
        return regions.add(e);
    }

    public int size() {
        return regions.size();
    }

    public boolean addAll(Collection<Region> e) {
        return regions.addAll(e);
    }

    public boolean addAll(Regions e) {
        return regions.addAll(e.getRegions());
    }

    public void sort() {
        this.regions.sort((Region o1, Region o2) -> o2.size() - o1.size());
    }

    public void sortX() {
        this.regions.sort((Region o1, Region o2) -> Double.compare(o2.getCentroid().getX(), o1.getCentroid().getX()));
    }

    // protected <EuclidianRegion> regions;
    protected void calculate_adjacent_regions(boolean fast) {
        if (this.regions.size() > 1) {
            // ThreadManager manager = new ThreadManager();
            // compute adjoined regions
            // System.out.println("calculate_adjacent_regions:" + (this.regions.size() - 1)
            // * this.regions.size());
            this.sortX();
            for (int i = 0; i < this.regions.size() - 1; i++) {
                // System.out.println(i + "/" + this.size());
                int start = i;
                // manager.addWorker(() -> {
                for (int j = start + 1; j < this.regions.size(); j++) {
                    // get both this
                    Region reg1 = this.regions.get(start);
                    Region reg2 = this.regions.get(j);
                    boolean closeX = reg1.isCloseX(reg2);
                    if (!closeX) {
                        break;
                    }
                    boolean canMerge = reg1.canMerge(reg2);
                    // test if the distance is under the maximal neighbor distance.
                    if (canMerge) {
                        // this are adjoining
                        reg1.addAdjoinedRegion(reg2);
                        reg2.addAdjoinedRegion(reg1);
                    }
                }
                // });
            }
            // wait all this are computed.
            // manager.waitTheEnd();
        }
    }

    /**
     * Merges regions when possible
     *
     * @return Regions after merging.
     */
    public Regions merges(boolean fast) {
//        Benchmark ben = new Benchmark();
//        ben.go();
        this.calculate_adjacent_regions(fast);
//        ben.stop();
//        System.out.println("calculate_adjacent_regions:" + ben.showValue());
        Regions finalRegions = new Regions();
        this.regions.stream().map(region -> region.mergeLinkedRegion()).filter(ml -> ml != null)
                .forEach(ml -> finalRegions.add(ml));
        return finalRegions;
    }

    /**
     * Merges regions that are bigger than "minSize" and ignores the others.
     *
     * @param minSize minimum size to take into account regions
     * @return the sum of the merged regions and the isolated regions
     */
    public Regions merge(int minSize) {
        Regions biggest = new Regions();
        Regions issolated = new Regions();
        this.regions.stream().forEach(r -> {
            if (r.size() < minSize) {
                issolated.add(r);
            } else {
                biggest.add(r);
            }
        });
        // System.out.println("biggest: " + biggest.size());
        // System.out.println("Issolate: " + issolated.size());

        // boolean fast =(biggest.size()<=10000);
        Regions mergesBiggest = biggest.merges(false);
        // System.out.println("After merging: " + mergesBiggest.size());
        issolated.addAll(mergesBiggest);
        return issolated;
    }

    /**
     * Removes every small regions.
     *
     * @param regions regions to be cleaned.
     * @param count minimum number of points that a region should has to be
     * kept.
     * @return Only region with a number of point upper than "count".
     */
    protected void clean(int count) {
        Iterator<Region> itr = this.regions.iterator();
        while (itr.hasNext()) {
            Region next = itr.next();
            if (next.size() <= count) {
                itr.remove();
            }
        }
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public Stream<Region> stream() {
        return this.regions.stream();
    }

}
