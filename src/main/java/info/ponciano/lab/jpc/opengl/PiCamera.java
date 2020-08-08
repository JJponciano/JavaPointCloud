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
package info.ponciano.lab.jpc.opengl;

import com.jogamp.opengl.glu.GLU;
import info.ponciano.lab.jpc.math.Point;
/**
 *
 * @author ponciano
 */
public class PiCamera {

    //possition de la camera 
    protected double posx;
    protected double posy;
    protected double posz;
    //point de visé de la camera 
    protected double lookX;
    protected double lookY;
    protected double lookZ;

    //angle de visé de la camera 
    protected double aX;
    protected double aY;
    //angle de Zoome de la caméra
    protected float zoom;
    // vitesse de déplacement de la caméra
    protected int vit;
    //coordinate of old look at.
    protected double oldX;
    protected double oldY;
    protected double sensibility;

    public PiCamera() {
        this.sensibility = 1;
        //possition de la camera
        this.posz = 4;
        this.posy = -4;
        this.posx =4;
        //point de visé de la camera
        this.lookZ = 0;
        this.lookY = 0;
        this.lookX = 0;
        this.aX = 0;
        this.aY = 0;
        //angle de Zoome de la caméra
      this.resetZoom();
        // vitesse de déplacement de la caméra
        this.vit = 1;
        this.oldX = 0;
        this.oldY = 0;
    }

    /**
     * Increases the zoom 
     */
    public void zoom() {
        this.zoom-=sensibility;
    }
     /**
     * Decreases the zoom 
     */
    public void unzoom() {
        this.zoom+=sensibility;
    }

    /**
     * Reset the zoom 
     */
    public void resetZoom() {
        //angle de Zoome de la caméra
        this.zoom = 45;
    }

    public void reculer() {
        calculedeplacement(-this.sensibility);
    }

    public void avancer() {
        calculedeplacement(this.sensibility);
    }

    public void gauche() {// TODO 9.2 this does not work fine
        //rotation vectoriel
        double newx = posx, newy = posy;
        
        newx -= (this.lookX / 9.0) * Math.cos(Math.PI / 2.0) - (this.lookY / 9.0) * Math.sin(
                Math.PI / 2.0);
        newy -= (this.lookX / 9.0) * Math.sin(Math.PI / 2.0) - (this.lookY / 9.0) * Math.cos(
                Math.PI / 2.0);

        //get the diference
        this.lookX+=(posx-newx);
        
        this.lookY+=(posy-newy);
        posy = newy;
        posx = newx;
    }

    public void droite() {
        //rotation vectoriel
        double newx = posx, newy = posy;

        newx += (this.lookX / 9.0) * Math.cos(Math.PI / 2.0) - (this.lookY / 9.0) * Math.sin(
                Math.PI / 2.0);
        newy += (this.lookX / 9.0) * Math.sin(Math.PI / 2.0) - (this.lookY / 9.0) * Math.cos(
                Math.PI / 2.0);

        posy = newy;
        posx = newx;
    }

    public void init(GLU glu) {
        glu.gluLookAt(posx, posy, posz, lookX, lookY, lookZ, 0.0, 0.0, 1.0);
    }

    /**
     * Modify the point of observation of the camera according to the location
     * of the cursor
     *
     * @param x coordinate of the position of the cursor
     * @param y coordinate of the position of the cursor
     */
    public void lookat(double x, double y) {

        if (this.oldX != 0 || this.oldY != 0) {
            double n = (x - oldX) * sensibility;
            this.aX += n;
            double lz= (y - oldY) * sensibility/10.0;
            this.lookZ -=lz;
            this.calculeRotation();
        }
        //update the coordinate of the lookat
        this.oldX = x;
        this.oldY = y;
    }
 
    

    protected void calculeRotation() {
        // ====================rotation horizontal====================
        //calcule du rayon
        double ray = Math.sqrt(Math.pow(lookX - posx,2) +Math.pow(lookY - posy,2));
        
        //calcule des coordonnées du nouveau point
        this.lookX = ray * Math.sin((aX / 360.0) * Math.PI) + this.posx;
        this.lookY = ray * Math.cos((aX / 360.0) * Math.PI) + this.posy;
    }

    /**
     * fait un pas en avant ou en arriere dans la trajectoire de visé dep =1
     * pour avancer, -1 pour reculer
     */
    protected void calculedeplacement(double dep) {
        double t = 0.1 * dep;
        // interpolation linéaire entre la position et le points visée
        double newx = t * lookX + (1 - t) * posx;
        double newy = t * lookY + (1 - t) * posy;

        // la distance entre px et lx, réciproquement entre pz et lz s'est réduite
        // On calcule de combient pour la combler
        lookX += newx - posx;
        lookY += newy - posy;
        posy = newy;
        posx = newx;

//        double newy = t * lookY + (1 - t) * posy;
//        lookY += newy - posy;
//        posy = newy;
    }

    /**
     * Reset the coordinate of the old look at coordinate to calibrate the
     * camera
     *
     * @param x mouse coordinate in x.
     * @param y mouse coordinate in y.
     */
    public void setOldCoord(double x, double y) {
        this.oldX = x;
        this.oldY = y;
    }

    /**
     * Add a value to the camera move sensibility.
     *
     * @param value value to be added.
     */
    public void addSensibility(double value) {
        this.sensibility += value;
        if (sensibility <= 0) {
            sensibility = 0.001;
        }
    }

    public double getSensibility() {
        return sensibility;
    }

    public Point getLocate() {
        return new Point(this.posx, this.posy, this.posz);
    }

    public float getZoom() {
        return zoom;
    }

}
