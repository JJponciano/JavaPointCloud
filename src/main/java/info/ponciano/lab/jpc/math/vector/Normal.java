/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.jpc.math.vector;

import info.ponciano.lab.jpc.math.Coord3D;
import java.util.List;
import info.ponciano.lab.jpc.math.Color;

/**
 *
 * @author jean-jacques
 */
public class Normal extends Coord3D {

    /**
     *
     */
    private static final long serialVersionUID = -2305202659567077967L;

    /**
     * Creates a new instance of <code>Normal</code>that is a clone of the given
     * <code>Normal</code>.
     *
     * @param clone Normal to be cloned
     */
    public Normal(Normal clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.z = clone.z;
    }

    public Normal(double x, double y, double z) {
        super(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Normal(String sx, String sy, String sz) {
        super(sx.replaceAll("-", ""), sy.replaceAll("-", ""), sz.replaceAll("-", ""));
    }

    public Normal(List<Normal> averagcoord) {
        super(averagcoord);
    }

    @Override
    public void setX(double x) {
        this.x = Math.abs(x);
    }

    @Override
    public void setY(double y) {
        this.y = Math.abs(y);
    }

    @Override
    public void setZ(double z) {
        this.z = Math.abs(z);
    }

    public Color toColor() {
//        int r = (int) ((this.x + 1) / 2.0 * 255);
//        int g = (int) ((this.y + 1) / 2.0 * 255);
//        int b = (int) ((this.z + 1) / 2.0 * 255);
        int r = Math.round((float)(Math.abs(this.x) * 255));
        int g = Math.round((float)(Math.abs(this.y) * 255));
        int b = Math.round((float)(Math.abs(this.z) * 255));
        return new Color(r, g, b);
    }

    /**
     * Scale the vector with the specific value
     *
     * @param value value of the scale
     */
    public void scale(double value) {
        this.x *= value;
        this.y *= value;
        this.z *= value;
    }

    /**
     * Translate the coordinates according to the vector coordinates
     *
     * @param c coordinates to be translated
     * @return new coordinates after the vector translation
     */
    public Coord3D translate(Coord3D c) {
        return new Coord3D(c.getX() + this.x, c.getY() + this.y, c.getZ() + this.z);
    }

    /**
     * Translate the coordinates according to the inverse vector coordinates
     *
     * @param c coordinates to be translated
     * @return new coordinates after the vector translation
     */
    public Coord3D translateInv(Coord3D c) {
        return new Coord3D(c.getX() - this.x, c.getY() - this.y, c.getZ() - this.z);
    }

}
