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
package info.ponciano.lab.jpc.pointcloud.stucture.octree;

import info.ponciano.lab.jpc.math.PiNode;
import java.util.ArrayList;

/**
 *
 * @author jean-jacques Ponciano
 */
class OctreeNode extends PiNode<Voxel> implements Runnable {

    public OctreeNode(final Voxel data) {
        super(data);
    }

    @Override
    public void run() {
        // split the data
        final ArrayList<Box> split = this.data.split();
        // this.childsplit = split.size();
        //for each new part
        if (split == null||split.isEmpty()) {
            throw new InternalError("The split of the OctreeNode does not produce any leaf!");
        }
        split.stream().map((newData) -> new OctreeNode((Voxel) newData)).forEachOrdered((newNode) -> {
            //create a new node
            //add the new node as child of the current node
            this.addChild(newNode);
        });

    }

}
