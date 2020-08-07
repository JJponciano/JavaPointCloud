/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.jpc.math;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ponciano
 */
public class RandomColor {

    protected List<Color> alreadyUsed;

    public RandomColor() {
        this.alreadyUsed = new ArrayList<>();
    }

    public Color getColor() {
        Color col = null;
        boolean isknow =true ;
        while (isknow) {
            col = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            isknow = alreadyUsed.contains(col);
        }
        return col;

    }

}
