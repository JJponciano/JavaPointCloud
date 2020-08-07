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

import info.ponciano.lab.jpc.algorithms.Algorithm;
import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.pointcloud.stucture.octree.Voxel;

/**
 *
 * @author Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 */
public abstract class Segmentation implements Algorithm<Regions> {

    final APointCloud region;
    final double distance;
    private Regions merges;

    public Segmentation(APointCloud region, double distance) {
        this.merges = null;
        this.region = region;
        this.distance = distance;
    }

    public Segmentation(Voxel next) {
        this.merges = null;
        this.region = next.getPointsContained();
        this.distance = next.getOptimalPointSpace();
    }

    protected abstract Region createsRegion(APointCloud points, double distance);

    @Override
    public void run() {
        /* Transforms every point of the cloud in a single region. */
        final Regions rgs = new Regions();
        this.region.stream().forEach(point -> {
            APointCloud rpc = new PointCloudMap();
            rpc.add(point);
            rgs.add(new RegionNormal(rpc, this.distance));
        });
        //merge all regions when it is possible
        this.merges = rgs.merges(true);
    }

    @Override
    public Regions getResults() {
        return merges;
    }

    /*private static List<Segment> byNormal(Pointcloud instance) {
        Benchmark ben = new Benchmark();
        APointCloud points = instance.getPoints();

        //Creation of a voxel based on patch center point
        Voxel v = new Voxel(points.getMinPoint(), points.getMaxPoint());
        List<Double> ds = new ArrayList<>();
        Map<Integer, APointCloud> patches = instance.getPatches();

        patches.forEach((k, next) -> {
            Point mean = next.getMean();
            v.add(mean);
            Point min = next.getMinPoint();
            Point max = next.getMaxPoint();
            ds.add(min.getCoords().distance(max.getCoords()));
        });
        //Segments the voxel into regions
        double d = PiMath.getMean(ds) * 10;
        ben.go();
        System.out.println(d);
        Regions segmentVoxel = Segmentation1.splitRegion(v.getPointsContained(), d);
        ben.stop();
        System.out.println("segmentation:" + ben.showValue());
        ben.go();
        List<Segment> segments = new ArrayList<>();
        //get each regions
        segmentVoxel.getRegions().forEach(r -> {
            //for each region get all patches
            APointCloud cloud = r.getCloud();
            Segment seg = new Segment();
            cloud.stream().forEach(p -> {
                //extract patch's id
                seg.add(instance.get(p.hashCode()));
            });
            segments.add(seg);
        });
        ben.stop();
        System.out.println("Getting:" + ben.showValue());
        return segments;
    }*/
}
