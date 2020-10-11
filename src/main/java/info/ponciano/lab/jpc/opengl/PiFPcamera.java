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

import java.awt.event.KeyEvent;

/**
 * First person camera is use to manage a camera move like at the first
 * person.
 *
 * @author ponciano
 */
public class PiFPcamera extends PiCamera {

    /**
     * status of the camera
     */
    private boolean status;
    private int movingForward;
    private int movingBack;
    private int movingLeft;
    private int movingRight;
    private int jump;
    private int unjump;
    private int sensibilityINC;
    private int sensibilityDEC;
    private int resetZ;

    public PiFPcamera() {
        super();
        this.status = false;
        this.keyBordEN();

    }

    /**
     * Enable the camera and calibrate its
     *
     * @param x x coordinate in the screen to be used for the calibration.
     * @param y y coordinate in the screen to be used for the calibration.
     */
    public void enable(double x, double y) {
        this.status = true;
        this.setOldCoord(x, y);
    }

    /**
     * Disable the camera
     */
    public void diseable() {
        this.status = false;
    }

    /**
     * Switch the camera status. Enable the camera if it is disable and vise
     * versa.
     *
     * @param x x coordinate in the screen to be used or the calibration.
     * @param y y coordinate in the screen to be used for the calibration.
     */
    public void switchOnOff(double x, double y) {
        if (this.status) {
            this.diseable();
        } else {
            this.enable(x, y);
        }
    }

    /**
     * Target of the camera to follow.
     *
     * @param x x coordinate in the screen
     * @param y y coordinate in the screen
     */
    public void taget(double x, double y) {
        //if the mouse is catched, the camera look at the mouse location.
        if (this.status) {
            this.lookat(x, y);
        }
    }

    /**
     * Moving the camera according to the code of movement.
     *
     * By default the moves codes used the english keyboord:
     * <ul>
     * <li>w: moving forward</li>
     * <li>s:moving back</li>
     * <li>d: moving rigth</li>
     * <li>a: moving left</li>
     * </ul>
     *
     * @param code move code.
     *
     */
    public void move(int code) {
        if (code == this.resetZ) {
            this.resetZoom();
        } else  if (code == movingForward) {
            this.avancer();
        } else if (code == movingBack) {
            this.reculer();
        } else if (code == movingRight) {
            this.droite();
        } else if (code == movingLeft) {
            this.gauche();
        } else if (code == jump) {
            this.jump();
        } else if (code == unjump) {
            this.unjump();
        } else if (code == sensibilityINC ||code == KeyEvent.VK_PLUS || code == 107) {
            
            this.addSensibility(+0.1);
            System.out.println("sensibility: " + this.sensibility);
        } else if (code == sensibilityDEC||code == KeyEvent.VK_MINUS || code == 109) {
            
            this.addSensibility(-0.1);
            System.out.println("sensibility: " + this.sensibility);
        }
    }

    /**
     * Set the moving key code in germany keyboord.
     */
    protected final void keyBordEN() {
         this.sensibilityINC = KeyEvent.VK_B;
        this.sensibilityDEC = KeyEvent.VK_N;
        this.movingForward = KeyEvent.VK_W;
        this.movingBack = KeyEvent.VK_S;
        this.movingRight = KeyEvent.VK_D;
        this.movingLeft = KeyEvent.VK_A;
        this.jump = KeyEvent.VK_E;
        this.unjump = KeyEvent.VK_Q;
        this.resetZ= KeyEvent.VK_R;
    }

    public int getJump() {
        return jump;
    }

    public int getUnjump() {
        return unjump;
    }

    public int getMovingForward() {
        return movingForward;
    }

    public int getMovingBack() {
        return movingBack;
    }

    public int getMovingLeft() {
        return movingLeft;
    }

    public int getMovingRight() {
        return movingRight;
    }

    public int getSensibilityINC() {
        return sensibilityINC;
    }

    public void setSensibilityINC(int sensibilityINC) {
        this.sensibilityINC = sensibilityINC;
    }

    public int getSensibilityDEC() {
        return sensibilityDEC;
    }

    public void setSensibilityDEC(int sensibilityDEC) {
        this.sensibilityDEC = sensibilityDEC;
    }

    private void jump() {
        this.posz+=this.sensibility;
    }

    private void unjump() {
        this.posz-=this.sensibility;
    }

    @Override
    public String toString() {
        String s = "";
         s += KeyEvent.getKeyText(this.getSensibilityINC()) + ": camera go back\n";
        s += KeyEvent.getKeyText(this.getMovingForward()) + ": camera go forward\n";
        s += KeyEvent.getKeyText(this.getMovingBack()) + ": camera go back\n";
        s += KeyEvent.getKeyText(this.getMovingForward()) + ": camera go forward\n";
        s += KeyEvent.getKeyText(this.getMovingLeft()) + ": camera go left\n";
        s += KeyEvent.getKeyText(this.getMovingRight()) + ": camera go rigth\n";
        s += KeyEvent.getKeyText(this.getJump()) + ": camera up\n";
        s += KeyEvent.getKeyText(this.getUnjump()) + ": camera down\n";
        s += "mouse scroll button: camera target the mouse cursor\n";
        s +=KeyEvent.getKeyText(KeyEvent.VK_PLUS)+" or "+ KeyEvent.getKeyText(this.getSensibilityINC())+ ": increase camera sensibility\n";
        s += KeyEvent.getKeyText(KeyEvent.VK_MINUS)+" or "+ KeyEvent.getKeyText(this.getSensibilityDEC())+ ": decrease camera sensibility\n";
         s += KeyEvent.getKeyText(KeyEvent.VK_R)+ ": reset zoom\n";
        return s;
    }
      public String positionToString() {
        String s = "";
        s+=this.posx+","+this.posy+","+this.posz+",";
        s+=this.lookX+","+this.lookY+","+this.lookZ+"\n";     
        return s;
    }

}
