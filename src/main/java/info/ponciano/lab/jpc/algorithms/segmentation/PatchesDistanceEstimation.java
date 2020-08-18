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

import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 */
public abstract class PatchesDistanceEstimation implements Runnable {

    @Override
    public void run() {
        try {
            //get all patches
            Map<String, APointCloud> patches = this.getPatches();
            //calculate the minimum distance between all patches in multi-threading
            APointCloud[] patchestoArray = patches.values().toArray(new APointCloud[patches.size()]);
            /*Brut force method for distances computing. This method should be improved */
            List<info.ponciano.lab.jpc.algorithms.segmentation.MinPatchesDistanceEstimation> workers = breakingStrategy(patchestoArray,100);
            //executes all thread
            ExecutorService execute = Executors.newCachedThreadPool();
            workers.forEach(w -> execute.submit(w));
            execute.shutdown();
            execute.awaitTermination(10, TimeUnit.DAYS);

            this.postprocessing(workers, patches);
        } catch (InterruptedException ex) {
            Logger.getLogger(PatchesDistanceEstimation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set workers for computing patches distances in a brut force way. This
     * method should be improved
     *
     * @param patchestoArray array of patches
     * @param workers list of workers created
     */
    protected List<MinPatchesDistanceEstimation> brutForce(APointCloud[] patchestoArray) {
        List<info.ponciano.lab.jpc.algorithms.segmentation.MinPatchesDistanceEstimation> workers = new ArrayList<>();
        for (int i = 0; i < patchestoArray.length - 1; i++) {
            for (int j = i + 1; j < patchestoArray.length; j++) {
                workers.add(new info.ponciano.lab.jpc.algorithms.segmentation.MinPatchesDistanceEstimation(patchestoArray[i], patchestoArray[j]));
            }
        }
        return workers;
    }

    /**
     * Set workers for computing patches distances in a breaking way.
     *
     * @param patchestoArray array of patches
     * @param workers list of workers created
     */
    protected List<MinPatchesDistanceEstimation> breakingStrategy(APointCloud[] patchestoArray, double maxdistance) {
        //sort the patches in x then in y then in z
        Arrays.sort(patchestoArray);

        List<info.ponciano.lab.jpc.algorithms.segmentation.MinPatchesDistanceEstimation> workers = new ArrayList<>();
        for (int i = 0; i < patchestoArray.length - 1; i++) {
            APointCloud pi = patchestoArray[i];

            for (int j = i + 1; j < patchestoArray.length; j++) {
                APointCloud pj = patchestoArray[j];
                if (pi.getCentroid().distance(pj.getCentroid()) > maxdistance) {
                    break;
                } else {
                    workers.add(new info.ponciano.lab.jpc.algorithms.segmentation.MinPatchesDistanceEstimation(pi, pj));
                }
            }
        }
        return workers;
    }

    protected abstract Map<String, APointCloud> getPatches();

    protected abstract void postprocessing(List<MinPatchesDistanceEstimation> workers, Map<String, APointCloud> patches);
}
