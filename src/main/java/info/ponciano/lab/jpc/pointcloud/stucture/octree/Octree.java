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

import info.ponciano.lab.jpc.math.PiNode;
import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.math.PiMath;
import info.ponciano.lab.jpc.math.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jean-jacques Ponciano
 */
public class Octree {

    /**
     * Point cloud used to creates the tree at the origin.
     */
    protected APointCloud origin;

    /**
     * Maximum depth level that is already calculate.
     */
    private int depthLevel;

    /**
     * Deepest leaves of the tree.
     */
    private List<OctreeNode> leaves;
    /**
     * Root of the tree
     */
    private OctreeNode root;
    private int maxLevel;

    /**
     * Creates a new instance of @code{Octree}
     *
     * @param origin point cloud used to create the tree.
     */
    public Octree(APointCloud origin) {
        this.maxLevel = -1;
        this.depthLevel = 0;
        this.origin = origin;
        this.init();
        this.leaves = new ArrayList<>();
        this.leaves.add(root);
    }

    public Octree(Collection<Point> origin) {
        this.maxLevel = -1;
        this.depthLevel = 0;
        this.origin = new PointCloudMap();
        this.origin.add(origin);
        this.init();
        this.leaves = new ArrayList<>();
        this.leaves.add(root);
    }

    // ------------------------ PRIVATE ------------------------
    private void init() {
        // get the mazimum length un x y and Z

        // get the max of the § dimension size
        double max = PiMath.max(List.of(origin.getDx(), origin.getDy(), origin.getDz())) * 2;
        // create the max point
        Point pm = new Point(origin.getCentroid());

        // create the mother boxe
        Voxel motherBox = new Voxel(pm, max, max, max);
        this.origin.stream().forEach(p -> {
            if (!motherBox.add(p)) {
                System.err.println("point cannot be added");
            };
        });
        // set root with the motherbox as value
        this.root = new OctreeNode(motherBox);
    }

    private void split() {

        // if it is possible to split
        if (!this.maxLevelFound()) {
            this.depthLevel++;
            // List<Thread> workers = new ArrayList<>();
            // ExecutorService execute = Executors.newCachedThreadPool();
            // split in multithreading every node in the current leaves
//            ThreadManager manager = new ThreadManager();
            for (int i = 0; i < this.leaves.size(); i++) {
                OctreeNode leave = this.leaves.get(i);
                // execute.submit(leave);
               leave.run();
//                manager.addWorker(leave);
            }
            // wait until every node are splitted
//            if (this.leaves.size() > 0) {
//                manager.waitTheEnd();
//            }

            // init result list
            List<OctreeNode> newLeaves = new ArrayList<>();
            // fill the new leaves
            for (OctreeNode leaf : leaves) {
                List<PiNode<Voxel>> children = leaf.getChildren();
                if (children.isEmpty()) {
                    throw new InternalError("No children");
                }
                for (PiNode<Voxel> piNode : children) {
                    newLeaves.add((OctreeNode) piNode);
                }
            }

            // set new leaves if every things is ok
            if (newLeaves.size() < this.leaves.size()) {
                throw new InternalError("The octree split decrease the number of leaves");
            }
            // test if the split does not create any new leaf.
//            if (this.leaves.size() == newLeaves.size()) {
//                this.setMaxLevel();
//            } else {
                this.leaves = newLeaves;
//            }
        }
    }
   public static Voxels getRawVoxels(APointCloud pointcloud, int size) {
        Octree oct = new Octree(pointcloud);
        return oct.getVoxelsAtMaxSize(size);
    }
    private void setMaxLevel() {
        this.maxLevel = this.depthLevel;
    }

    protected boolean maxLevelFound() {
        return this.maxLevel > 0;
    }

    /**
     * getMinVolPC every node at the specific level
     *
     * @param level level specify
     * @return list of node at the specified depth
     */
    private List<OctreeNode> getNode(OctreeNode current, int level) {
        List<OctreeNode> res = new ArrayList<>();
        if (level == 0) {
            res.add(current);
        } else {
            // get childrens
            List<PiNode<Voxel>> children = current.getChildren();
            // if the node has no children, it is a leaf so it is added
            if (children.isEmpty()) {
                res.add(current);
            } else {
                current.getChildren().forEach((child) -> {
                    res.addAll(getNode((OctreeNode) child, level - 1));
                });
            }
        }
        return res;
    }

    @SuppressWarnings("empty-statement")
    private void goDeepEnough(int level) {
        // split the tree until the depth level is right or until the max level is
        // found.
        while (this.depthLevel < level && !this.maxLevelFound()) {
            this.split();
        }

    }

    /**
     * * Gets box that its mean point is the given point
     *
     * @param point mean of the box searched
     * @param current current not to search the point
     * @return the box found of null if the box are not found.
     */
    private Voxel getBox(Point point, OctreeNode current) {
        Voxel box = null;
        // if the current is the searched box
        if (current.getData().getMean().equals(point)
                || (current.getData().isInside(point) && current.getData().getPointsContained().size() < 2)) {
            box = current.getData();
        } else {
            // if the current node has no child
            int childrenNumber = current.getChildren().size();
            if (childrenNumber == 0) {
                // test if the point cloud could be again splited
                if (maxLevelFound()) {
                    this.split();
                    // update the new childrenNumber
                    childrenNumber = current.getChildren().size();
                }
            }
            // get the child for whose the point is contained inside
            OctreeNode child = null;
            int i = 0;
            boolean isInside = false;
            while (!isInside && i < childrenNumber) {
                child = (OctreeNode) current.getChildren().get(i);
                isInside = child.getData().isInside(point);
                i++;
            }
            // if the point is realy contained in a child
            if (isInside && child != null) {
                // run the function for the child
                box = this.getBox(point, child);
            }

        }
        return box;
    }

    // ---------------------- END PRIVATE ----------------------
    public APointCloud getOrigin() {
        return this.origin;
    }

    /**
     * Get a point cloud composed of point representing the mean of each voxel
     * at a specific level.
     *
     * @param level Octree level.
     * @return PointCloud composed of the voxels' mean points.
     */
    public PointCloudMap getAtLevel(int level) {
        Voxels nodes = this.getVoxels(level);
        PointCloudMap result = nodes.getMeanPoints();
        return result;
    }

    /**
     * Gets voxels at the specific level in the tree
     *
     * @param level Level in the tree
     * @return list of cubes at the given level.
     */
    public Voxels getVoxels(int level) {
        Voxels result = new Voxels();
        if (level >= 0) {
            // if the requiered level is upper the max level, the level is reset to the max.
            if (this.maxLevel > 0 && level > this.maxLevel) {
                level = maxLevel;
            }
            goDeepEnough(level);

            // get node at the right depth level
            List<OctreeNode> nodes = this.getNode(root, level);

            nodes.stream().map((node) -> node.getData()).forEachOrdered((box) -> {
                // get the data
                // get the mean point for the cloud
                result.add(box);
            });
        }
        return result;
    }

    /**
     * Gets voxels according to their number of point.
     *
     * @param size maximum number of point allowed for each voxel
     * @return list of cubes at the right level.
     */
    public Voxels getVoxelsPointCount(int size) {
        Voxels result = new Voxels();
        // if the requiered level is upper the max level, the level is reset to the max.
        while (!this.leavesAverage(size)) {
            this.split();
        }
        this.leaves.stream().map((node) -> node.getData()).forEachOrdered((box) -> {
            // get the data
            // get the mean point for the cloud
            result.add(box);
        });
        return result;
    }

    /**
     * Gets the max level if it is known, -1 otherwise.
     *
     * @return the max level of the tree or -1 if the max level is not yet found
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Gets list of boxes that their mean point is contained in the given list
     *
     * @param points list of points corresponding to the mean of boxes points
     * @return List of boxes.
     */
    public List<Voxel> getVoxels(List<Point> points) {
        List<Voxel> boxes = new ArrayList<>();
        // foreach point given
        points.stream().map((point) -> this.getBox(point, this.root)).filter((box) -> (box != null))
                .forEachOrdered((box) -> {
                    boxes.add(box);
                }); // search the box in the tree

        if (boxes.size() < points.size()) {
            throw new InternalError("Octree corrupted!");
        }
        return boxes;
    }

    /**
     * Gets point cloud corresponding to point contained in octree boxes that
     * their mean point is contained in the given list.
     *
     * @param points list of points corresponding to the mean of boxes points.
     * @return point cloud in full resolution.
     */
    public PointCloudMap getSubPointCloud(List<Point> points) {
        List<Voxel> boxes = this.getVoxels(points);
        PointCloudMap result = new PointCloudMap();
        boxes.forEach((boxe) -> {
            boxe.getPointsContained().stream().forEach(p -> {
                result.add(p);
            });
        });
        return result;
    }

    /**
     * Gets point cloud corresponding to point contained in octree boxes that
     * their mean point is the given point.
     *
     * @param point point corresponding to the mean of boxes points.
     * @return point cloud in full resolution.
     */
    public PointCloudMap getSubPointCloud(Point point) {
        List<Voxel> boxes = this.getVoxels(List.of(point));
        PointCloudMap result = new PointCloudMap();
        boxes.forEach((boxe) -> {
            boxe.getPointsContained().stream().forEach(p -> {
                result.add(p);
            });
        });
        return result;
    }

    /**
     * Gets point cloud composed of the mean of every voxel which the minimum
     * area is under the area provided.
     *
     * @param area minimum resolution
     * @return point cloud for the area resolution
     */
    public PointCloudMap getMinVolPC(double area) {
        Voxels result = getMinVolVoxels(area);
        return result.getMeanPoints();
    }

    /**
     * Split the octree until the maximum volume of every box is under the given
     * volume.
     *
     * @param volume maximum volume of boxes
     * @return boxes at the right level in the octree.
     */
    public Voxels getMinVolVoxels(double volume) {
        int level = 0;
        Voxels result = this.getVoxels(level);
        double minVolume = result.getMinVolume();
        while (level != this.getMaxLevel() && minVolume > volume) {
            level++;
            result = this.getVoxels(level);
            minVolume = result.getMinVolume();
        }
        return result;
    }

    /**
     * Gets voxels of the octree leaves and split the octree until the maximum
     * number of point on the leaf is under the number given.
     *
     * @param maxNumberofPoint maximum number of point per box
     * @return Voxels at the right level in the octree.
     */
    public Voxels getVoxelsAtMaxSize(int maxNumberofPoint) {
       Voxels result = new Voxels();
        // if the requiered level is upper the max level, the level is reset to the max.
        while (!this.allleavesUnder(maxNumberofPoint)&&!maxLevelFound()) {
            this.split();
        }
        this.leaves.stream().map((node) -> node.getData()).forEachOrdered((box) -> {
            // get the data
            // get the mean point for the cloud
            result.add(box);
        });
        return result;
    }

    /**
     * Gets points of the octree leaves and split the octree until the maximum
     * number of point on the leaf is under the number given. specific level.
     *
     * @param maxNumberofPoint maximum number of point per box
     * @return PointCloud composed of the voxels' mean points.
     */
    public PointCloudMap getAtMaxSize(int maxNumberofPoint) {
        Voxels nodes = this.getVoxelsAtMaxSize(maxNumberofPoint);
        PointCloudMap result = nodes.getMeanPoints();
        return result;
    }

    /**
     * Test if each leaves has less number of point than the size.
     *
     * @param size size to test.
     * @return true if all leaves have a size under the size given.
     */
    private boolean allleavesUnder(int size) {
        Iterator<OctreeNode> iterator = this.leaves.iterator();
        if (!iterator.hasNext()) {
            return false;
        }
        while (iterator.hasNext()) {
            OctreeNode next = iterator.next();
            if (next.getData().size() > size) {
                return false;
            }
        }
        return true;
    }

    private boolean leavesAverage(int size) {
        Iterator<OctreeNode> iterator = this.leaves.iterator();
        if (!iterator.hasNext()) {
            return false;
        }
        List<Double> av = new ArrayList<>();
        while (iterator.hasNext()) {
            OctreeNode next = iterator.next();
            int size1 = next.getData().size();
//            if (size1 > 1) {
                av.add((double) size1);
//            }
        }
        return (PiMath.getMean(av) <= size);
    }

    /**
     * Test if one leaf has less number of point than the size.
     *
     * @param size size to test.
     * @return true if at least one leaf has a size under the size given.
     */
    private boolean oneLeafUpper(int size) {
        Iterator<OctreeNode> iterator = this.leaves.iterator();
        if (!iterator.hasNext()) {
            return false;
        }
        while (iterator.hasNext()) {
            OctreeNode next = iterator.next();
            if (next.getData().size() <= size) {
                return true;
            }
        }
        return false;
    }
}
