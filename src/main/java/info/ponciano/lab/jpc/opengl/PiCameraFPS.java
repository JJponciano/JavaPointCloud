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
package info.ponciano.lab.jpc.opengl;

import com.jogamp.opengl.GL2;

/**
 *
 * @author ponciano
 */
public class PiCameraFPS extends PiFPcamera implements IObjectGL {

    protected PiMire mire;
    protected boolean showMire;

    public PiCameraFPS(PiMire mire) {
        super();
        this.mire = mire;
        this.mire.setX((float) this.lookX);
        this.mire.setY((float) this.lookY);
        this.mire.setZ((float) this.lookZ);
        this.showMire = true;
    }

    @Override
    public void displayGL(GL2 gl) {
        if (showMire) {
            this.mire.setX((float) this.lookX);
            this.mire.setY((float) this.lookY);
            this.mire.setZ((float) this.lookZ);
            this.mire.displayGL(gl);
        }
    }

    public PiMire getMire() {
        return mire;
    }

    public void setMire(PiMire mire) {
        this.mire = mire;
    }

    public boolean isShowMire() {
        return showMire;
    }

    public void setShowMire(boolean showMire) {
        this.showMire = showMire;
    }

    public void setPosition(double x, double y, double z, double lx, double ly, double lz) {
        this.posx = x;
        this.posy = y;
        this.posz = z;
        this.lookX = lx;
        this.lookY = ly;
        this.lookZ = lz;
    }
}
