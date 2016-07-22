/*
 * Copyright (C) 2016 Jean-Jacques Ponciano.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package algorithms;

import PointCloud.PointCloud;
import PointCloud.PointCloudView;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JDialog;
import opengl.DrawingScene;

/**
 * A algorithm to display a Point cloud in no modal windows.
 *
 * @author Jean-Jacques Ponciano.
 */
public class DisplayCloud implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected int width;
    protected int height;

    /**
     * Creates a new instance of <code>CenterPC</code>.
     *
     * @param cloud cloud to be centered.
     */
    public DisplayCloud(PointCloud cloud, int width, int height) {
        this.isReady = true;
        this.cloud = cloud;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    @Override
    public void run() {
        this.isReady = false;
        DrawingScene scene = new DrawingScene();
        PointCloudView view = new PointCloudView(this.cloud);
        scene.addObject(view);
        JDialog jd = new JDialog();
        jd.setSize(this.width, this.height);
        GLCanvas canvas = scene.getCanvas(this.width, this.height);
        jd.add(canvas);
        jd.setModal(false);
        jd.setVisible(true);
        this.isReady = true;
    }

}
