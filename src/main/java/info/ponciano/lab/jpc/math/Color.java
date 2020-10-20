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
package info.ponciano.lab.jpc.math;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage rgb color with value are between 0 and 255
 *
 * @author jean-jacques
 */
public class Color implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1337691335265964074L;
    private int red;
    private int green;
    private int blue;

    /**
     * Creates new instance of @code{Color}that is a clone of the given color
     *
     * @param clone color to be cloned
     */
    public Color(final Color clone) {
        this.setRed(clone.getRed());
        this.setGreen(clone.getGreen());
        this.setBlue(clone.getBlue());
    }

    /**
     * Convert a color in a value corresponding to human perceptions
     *
     * @param p
     * @param accuracy 90 for human vew.
     * @return
     */
    public Color asHumanPerception(final int accuracy) {
        final Color newC = new Color(this.getRed() / accuracy * accuracy, this.getGreen() / accuracy * accuracy,
                this.getBlue() / accuracy * accuracy);
        return newC;
    }

    /**
     * Creates new instance of <code>Color</code>
     *
     * @param red   red chanel value between 0 and 255
     * @param green green chanel value between 0 and 255
     * @param blue  blue chanel value between 0 and 255
     */
    public Color(final int red, final int green, final int blue) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }

    public Color(final int rgb) {
        final int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = (rgb) & 0xFF;
        this.setRed(r);
        this.setGreen(g);
        this.setBlue(b);
    }

    public Color(final Color... color) {
        final Color meanCol = this.getAverageColor(List.of(color));
        if (meanCol != null) {
            this.red = (int) meanCol.getRed();
            this.green = (int) meanCol.getGreen();
            this.blue = (int) meanCol.getBlue();
        }
    }

    public int getRGB() {
        return new java.awt.Color(this.getRed(), this.getGreen(), this.getBlue()).getRGB();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.red;
        hash = 89 * hash + this.green;
        hash = 89 * hash + this.blue;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Color other = (Color) obj;
        if (this.red != other.red) {
            if (Math.abs(this.red - other.red) > 2)
                return false;
        }
        if (this.green != other.green) {
            if (Math.abs(this.green - other.green) > 2)
                return false;
        }
        if (this.blue != other.blue) {
            if (Math.abs(this.blue - other.blue) > 2)
                return false;
        }
        return true;
    }

    /**
     * Creates a new instance of <code>Color</code> which is the average color of
     * the color list.
     *
     * @param average list of color to create the average color
     */
    public Color(final List<? extends Color> average) {
        final Color meanCol = this.getAverageColor(average);
        if (meanCol != null) {
            this.red = (int) meanCol.getRed();
            this.green = (int) meanCol.getGreen();
            this.blue = (int) meanCol.getBlue();
        }
    }

    private int int2int(final int b) {
        if (b < 0) {
            return Byte.MAX_VALUE + 1 + (Byte.MAX_VALUE + 1 + b);
        } else {
            return b;
        }
    }

    /**
     * Gets red chanel value
     *
     * @return value between 0 and 255
     */
    public int getRed() {
        return this.int2int(this.red);
    }

    /**
     * Get red chanel value
     *
     * @return value between 0 and 1
     */
    public float getRedf() {
        return (float) (this.getRed() / 255.0f);
    }

    public final void setRed(final int red) {
        if (red > 255) {
            this.red = (int) 255;
        } else if (red < 0) {
            this.red = 0;
        } else {
            this.red = (int) red;
        }
    }

    /**
     * Gets green chanel value
     *
     * @return value between 0 and 255
     */
    public int getGreen() {
        return this.int2int(this.green);
    }

    /**
     * Get green chanel value
     *
     * @return value between 0 and 1
     */
    public float getGreenf() {
        return (float) (this.getGreen() / 255.0f);
    }

    public final void setGreen(final int green) {
        if (green > 255) {
            this.green = (int) 255;
        } else if (green < 0) {
            this.green = 0;
        } else {
            this.green = (int) green;
        }
    }

    /**
     * Gets blue chanel value
     *
     * @return value between 0 and 255
     */
    public int getBlue() {
        return this.int2int(this.blue);
    }

    /**
     * Get blue chanel value
     *
     * @return value between 0 and 1
     */
    public float getBluef() {
        return (float) (this.getBlue() / 255.0f);
    }

    public final void setBlue(final int blue) {
        if (blue > 255) {
            this.blue = (int) 255;
        } else if (blue < 0) {
            this.blue = 0;
        } else {
            this.blue = (int) blue;
        }
    }

    /**
     * Calculate euclidian distance between both color.
     *
     * @param p other color.
     * @return The distance between both color.
     */
    public double distance(final Color p) {
        return Math.sqrt(Math.pow(this.getRed() - p.getRed(), 2) + Math.pow(this.getGreen() - p.getGreen(), 2)
                + Math.pow(this.getBlue() - p.getBlue(), 2));
    }

    /**
     * Calculate deltaE distance between both color.
     *
     * @param p other color.
     * @return The distance between both color.
     */
    public double deltaE(final Color p) {
        final double[] lab = this.getLab();
        final double[] labp = p.getLab();
        return Math.sqrt(Math.pow(lab[0] - labp[0], 2) + Math.pow(lab[1] - labp[1], 2) + Math.pow(lab[2] - labp[2], 2));
    }

    /**
     * Calculate Taxicab geometry distance between both color.
     *
     * @param p other color.
     * @return The distance between both color.
     */
    public double distanceTaxicab(final Color p) {
        return (Math.abs(this.getRed() - p.getRed()) + Math.abs(this.getGreen() - p.getGreen())
                + Math.abs(this.getBlue() - p.getBlue()));
    }

    public final Color getAverageColor(final List<? extends Color> averageColors) {
        int ar = 0, ag = 0, ab = 0;
        if (averageColors.isEmpty()) {
            return null;
        }
        for (final Color averageColor : averageColors) {
            ar += averageColor.getRed();
            ag += averageColor.getGreen();
            ab += averageColor.getBlue();
        }
        ar /= averageColors.size();
        ag /= averageColors.size();
        ab /= averageColors.size();
        return new Color(ar, ag, ab);
    }

    @Override
    public String toString() {
        final String s = this.getRed() + "\t" + this.getGreen() + "\t" + this.getBlue();
        return s;
    }

    /**
     * Parses a string in color.
     *
     * @param s string composed of RGB values separated by white character (space or
     *          tabulation).
     * @return The color parsed
     */
    public static Color parseColor(final String s) {
        final String[] split = s.split("\\s");
        final int r = Integer.parseInt(split[0]);
        final int g = Integer.parseInt(split[1]);
        final int b = Integer.parseInt(split[2]);
        return new Color(r, g, b);
    }

    public double[] getLab() {

        double r, g, b, X, Y, Z, xr, yr, zr;

        // D65/2°
        final double Xr = 95.047;
        final double Yr = 100.0;
        final double Zr = 108.883;

        // --------- RGB to XYZ ---------//
        r = this.getRed() / 255.0;
        g = this.getGreen() / 255.0;
        b = this.getBlue() / 255.0;

        if (r > 0.04045) {
            r = Math.pow((r + 0.055) / 1.055, 2.4);
        } else {
            r = r / 12.92;
        }

        if (g > 0.04045) {
            g = Math.pow((g + 0.055) / 1.055, 2.4);
        } else {
            g = g / 12.92;
        }

        if (b > 0.04045) {
            b = Math.pow((b + 0.055) / 1.055, 2.4);
        } else {
            b = b / 12.92;
        }

        r *= 100;
        g *= 100;
        b *= 100;

        X = 0.4124 * r + 0.3576 * g + 0.1805 * b;
        Y = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        Z = 0.0193 * r + 0.1192 * g + 0.9505 * b;

        // --------- XYZ to Lab --------- //
        xr = X / Xr;
        yr = Y / Yr;
        zr = Z / Zr;

        if (xr > 0.008856) {
            xr = (float) Math.pow(xr, 1 / 3.);
        } else {
            xr = (float) ((7.787 * xr) + 16 / 116.0);
        }

        if (yr > 0.008856) {
            yr = (float) Math.pow(yr, 1 / 3.);
        } else {
            yr = (float) ((7.787 * yr) + 16 / 116.0);
        }

        if (zr > 0.008856) {
            zr = (float) Math.pow(zr, 1 / 3.);
        } else {
            zr = (float) ((7.787 * zr) + 16 / 116.0);
        }

        final double[] lab = new double[3];

        lab[0] = (116 * yr) - 16;
        lab[1] = 500 * (xr - yr);
        lab[2] = 200 * (yr - zr);

        return lab;

    }

    /**
     * Get the red value chanel normalized
     *
     * @return red/(red+green+blue)
     */
    public double getR() {
        return (double) this.getRed() / (double) (this.getRed() + this.getGreen() + this.getBlue());
    }

    public double getLuminance() {
        return 0.299 * this.red + 0.587 * green + 0.114 * blue;
    }

    /**
     * Get the green value chanel normalized
     *
     * @return green/(red+green+blue)
     */
    public double getG() {
        return (double) this.getGreen() / (double) (this.getRed() + this.getGreen() + this.getBlue());
    }

    /**
     * Get the blue value chanel normalized
     *
     * @return blue/(red+green+blue)
     */
    public double getB() {
        return (double) this.getBlue() / (double) (this.getRed() + this.getGreen() + this.getBlue());
    }

    private static List<Color> colors = new ArrayList<>();

    /**
     * Get a colors the most different as possible according to the number maximal
     * of colors
     *
     * @param colorIndex       index of the choosen color in the base of the total
     *                         number of color wanted (if you when to use 10 colors
     *                         in your project, the index is between 0 and 10)
     * @param maxNumberOfClass the total number of color wanted (if you when to use
     *                         10 the value is 10)
     * @return the colors asked, corresponding to the wanted index or null if the
     *         index of maxNumberOfClass is out the list.
     */
    public static Color getColor(final int colorIndex, final int maxNumberOfClass) {
        if (colorIndex < 0) {
            return null;
        }
        if (colors.isEmpty()) {
            initList();
        }
        if (maxNumberOfClass > colors.size() || colorIndex > colors.size()) {
            return null;
        }
        // transforms the index
        final int i = colorIndex * colors.size() / maxNumberOfClass;

        return colors.get(i);
    }

    public final static Color WHITE = new Color(255, 255, 255);
    public final static Color LIGHT_GRAY = new Color(192, 192, 192);
    public final static Color GRAY = new Color(128, 128, 128);
    public final static Color DARK_GRAY = new Color(64, 64, 64);
    public final static Color BLACK = new Color(0, 0, 0);
    public final static Color RED = new Color(255, 0, 0);
    public final static Color PINK = new Color(255, 175, 175);
    public final static Color ORANGE = new Color(255, 200, 0);
    public final static Color YELLOW = new Color(255, 255, 0);
    public final static Color GREEN = new Color(0, 255, 0);
    public final static Color MAGENTA = new Color(255, 0, 255);
    public final static Color CYAN = new Color(0, 255, 255);
    public final static Color BLUE = new Color(0, 0, 255);
    public final static Color LIGHT_BROWN = new Color(153, 130, 96);
    public final static Color maroon = new Color(128, 0, 0);
    public final static Color dark_red = new Color(139, 0, 0);
    public final static Color brown = new Color(165, 42, 42);
    public final static Color firebrick = new Color(178, 34, 34);
    public final static Color crimson = new Color(220, 20, 60);
    public final static Color tomato = new Color(255, 99, 71);
    public final static Color coral = new Color(255, 127, 80);
    public final static Color indian_red = new Color(205, 92, 92);
    public final static Color light_coral = new Color(240, 128, 128);
    public final static Color dark_salmon = new Color(233, 150, 122);
    public final static Color salmon = new Color(250, 128, 114);
    public final static Color light_salmon = new Color(255, 160, 122);
    public final static Color orange_red = new Color(255, 69, 0);
    public final static Color dark_orange = new Color(255, 140, 0);
    public final static Color orange = new Color(255, 165, 0);
    public final static Color gold = new Color(255, 215, 0);
    public final static Color dark_golden_rod = new Color(184, 134, 11);
    public final static Color golden_rod = new Color(218, 165, 32);
    public final static Color pale_golden_rod = new Color(238, 232, 170);
    public final static Color dark_khaki = new Color(189, 183, 107);
    public final static Color khaki = new Color(240, 230, 140);
    public final static Color olive = new Color(128, 128, 0);
    public final static Color yellow = new Color(255, 255, 0);
    public final static Color yellow_green = new Color(154, 205, 50);
    public final static Color dark_olive_green = new Color(85, 107, 47);
    public final static Color olive_drab = new Color(107, 142, 35);
    public final static Color lawn_green = new Color(124, 252, 0);
    public final static Color chart_reuse = new Color(127, 255, 0);
    public final static Color green_yellow = new Color(173, 255, 47);
    public final static Color dark_green = new Color(0, 100, 0);
    public final static Color forest_green = new Color(34, 139, 34);
    public final static Color lime = new Color(0, 255, 0);
    public final static Color lime_green = new Color(50, 205, 50);
    public final static Color light_green = new Color(144, 238, 144);
    public final static Color pale_green = new Color(152, 251, 152);
    public final static Color dark_sea_green = new Color(143, 188, 143);
    public final static Color medium_spring_green = new Color(0, 250, 154);
    public final static Color spring_green = new Color(0, 255, 127);
    public final static Color sea_green = new Color(46, 139, 87);
    public final static Color medium_aqua_marine = new Color(102, 205, 170);
    public final static Color medium_sea_green = new Color(60, 179, 113);
    public final static Color light_sea_green = new Color(32, 178, 170);
    public final static Color dark_slate_gray = new Color(47, 79, 79);
    public final static Color teal = new Color(0, 128, 128);
    public final static Color dark_cyan = new Color(0, 139, 139);
    public final static Color aqua = new Color(0, 255, 255);
    public final static Color light_cyan = new Color(224, 255, 255);
    public final static Color dark_turquoise = new Color(0, 206, 209);
    public final static Color turquoise = new Color(64, 224, 208);
    public final static Color medium_turquoise = new Color(72, 209, 204);
    public final static Color pale_turquoise = new Color(175, 238, 238);
    public final static Color aqua_marine = new Color(127, 255, 212);
    public final static Color powder_blue = new Color(176, 224, 230);
    public final static Color cadet_blue = new Color(95, 158, 160);
    public final static Color steel_blue = new Color(70, 130, 180);
    public final static Color corn_flower_blue = new Color(100, 149, 237);
    public final static Color deep_sky_blue = new Color(0, 191, 255);
    public final static Color dodger_blue = new Color(30, 144, 255);
    public final static Color light_blue = new Color(173, 216, 230);
    public final static Color sky_blue = new Color(135, 206, 235);
    public final static Color light_sky_blue = new Color(135, 206, 250);
    public final static Color midnight_blue = new Color(25, 25, 112);
    public final static Color navy = new Color(0, 0, 128);
    public final static Color dark_blue = new Color(0, 0, 139);
    public final static Color medium_blue = new Color(0, 0, 205);
    public final static Color royal_blue = new Color(65, 105, 225);
    public final static Color blue_violet = new Color(138, 43, 226);
    public final static Color indigo = new Color(75, 0, 130);
    public final static Color dark_slate_blue = new Color(72, 61, 139);
    public final static Color slate_blue = new Color(106, 90, 205);
    public final static Color medium_slate_blue = new Color(123, 104, 238);
    public final static Color medium_purple = new Color(147, 112, 219);
    public final static Color dark_magenta = new Color(139, 0, 139);
    public final static Color dark_violet = new Color(148, 0, 211);
    public final static Color dark_orchid = new Color(153, 50, 204);
    public final static Color medium_orchid = new Color(186, 85, 211);
    public final static Color purple = new Color(128, 0, 128);
    public final static Color thistle = new Color(216, 191, 216);
    public final static Color plum = new Color(221, 160, 221);
    public final static Color violet = new Color(238, 130, 238);
    public final static Color orchid = new Color(218, 112, 214);
    public final static Color medium_violet_red = new Color(199, 21, 133);
    public final static Color pale_violet_red = new Color(219, 112, 147);
    public final static Color deep_pink = new Color(255, 20, 147);
    public final static Color hot_pink = new Color(255, 105, 180);
    public final static Color light_pink = new Color(255, 182, 193);
    public final static Color pink = new Color(255, 192, 203);
    public final static Color antique_white = new Color(250, 235, 215);
    public final static Color beige = new Color(245, 245, 220);
    public final static Color bisque = new Color(255, 228, 196);
    public final static Color blanched_almond = new Color(255, 235, 205);
    public final static Color wheat = new Color(245, 222, 179);
    public final static Color corn_silk = new Color(255, 248, 220);
    public final static Color lemon_chiffon = new Color(255, 250, 205);
    public final static Color light_golden_rod_yellow = new Color(250, 250, 210);
    public final static Color light_yellow = new Color(255, 255, 224);
    public final static Color saddle_brown = new Color(139, 69, 19);
    public final static Color sienna = new Color(160, 82, 45);
    public final static Color chocolate = new Color(210, 105, 30);
    public final static Color peru = new Color(205, 133, 63);
    public final static Color sandy_brown = new Color(244, 164, 96);
    public final static Color burly_wood = new Color(222, 184, 135);
    public final static Color tan = new Color(210, 180, 140);
    public final static Color rosy_brown = new Color(188, 143, 143);
    public final static Color moccasin = new Color(255, 228, 181);
    public final static Color navajo_white = new Color(255, 222, 173);
    public final static Color peach_puff = new Color(255, 218, 185);
    public final static Color misty_rose = new Color(255, 228, 225);
    public final static Color lavender_blush = new Color(255, 240, 245);
    public final static Color linen = new Color(250, 240, 230);
    public final static Color old_lace = new Color(253, 245, 230);
    public final static Color papaya_whip = new Color(255, 239, 213);
    public final static Color sea_shell = new Color(255, 245, 238);
    public final static Color mint_cream = new Color(245, 255, 250);
    public final static Color slate_gray = new Color(112, 128, 144);
    public final static Color light_slate_gray = new Color(119, 136, 153);
    public final static Color light_steel_blue = new Color(176, 196, 222);
    public final static Color lavender = new Color(230, 230, 250);
    public final static Color floral_white = new Color(255, 250, 240);
    public final static Color alice_blue = new Color(240, 248, 255);
    public final static Color ghost_white = new Color(248, 248, 255);
    public final static Color honeydew = new Color(240, 255, 240);
    public final static Color ivory = new Color(255, 255, 240);
    public final static Color azure = new Color(240, 255, 255);
    public final static Color snow = new Color(255, 250, 250);
    public final static Color black = new Color(0, 0, 0);
    public final static Color dim_gray = new Color(105, 105, 105);
    public final static Color gray = new Color(128, 128, 128);
    public final static Color dark_gray = new Color(169, 169, 169);
    public final static Color silver = new Color(192, 192, 192);
    public final static Color light_gray = new Color(211, 211, 211);
    public final static Color gainsboro = new Color(220, 220, 220);
    public final static Color white_smoke = new Color(245, 245, 245);

    private static void initList() {
        colors.add(WHITE);
        colors.add(LIGHT_GRAY);
        colors.add(GRAY);
        colors.add(DARK_GRAY);
        colors.add(BLACK);
        colors.add(RED);
        colors.add(PINK);
        colors.add(ORANGE);
        colors.add(YELLOW);
        colors.add(GREEN);
        colors.add(MAGENTA);
        colors.add(CYAN);
        colors.add(BLUE);
        colors.add(LIGHT_BROWN);
        colors.add(maroon);
        colors.add(dark_red);
        colors.add(brown);
        colors.add(firebrick);
        colors.add(crimson);
        colors.add(tomato);
        colors.add(coral);
        colors.add(indian_red);
        colors.add(light_coral);
        colors.add(dark_salmon);
        colors.add(salmon);
        colors.add(light_salmon);
        colors.add(orange_red);
        colors.add(dark_orange);
        colors.add(orange);
        colors.add(gold);
        colors.add(dark_golden_rod);
        colors.add(golden_rod);
        colors.add(pale_golden_rod);
        colors.add(dark_khaki);
        colors.add(khaki);
        colors.add(olive);
        colors.add(yellow);
        colors.add(yellow_green);
        colors.add(dark_olive_green);
        colors.add(olive_drab);
        colors.add(lawn_green);
        colors.add(chart_reuse);
        colors.add(green_yellow);
        colors.add(dark_green);
        colors.add(forest_green);
        colors.add(lime);
        colors.add(lime_green);
        colors.add(light_green);
        colors.add(pale_green);
        colors.add(dark_sea_green);
        colors.add(medium_spring_green);
        colors.add(spring_green);
        colors.add(sea_green);
        colors.add(medium_aqua_marine);
        colors.add(medium_sea_green);
        colors.add(light_sea_green);
        colors.add(dark_slate_gray);
        colors.add(teal);
        colors.add(dark_cyan);
        colors.add(aqua);
        colors.add(light_cyan);
        colors.add(dark_turquoise);
        colors.add(turquoise);
        colors.add(medium_turquoise);
        colors.add(pale_turquoise);
        colors.add(aqua_marine);
        colors.add(powder_blue);
        colors.add(cadet_blue);
        colors.add(steel_blue);
        colors.add(corn_flower_blue);
        colors.add(deep_sky_blue);
        colors.add(dodger_blue);
        colors.add(light_blue);
        colors.add(sky_blue);
        colors.add(light_sky_blue);
        colors.add(midnight_blue);
        colors.add(navy);
        colors.add(dark_blue);
        colors.add(medium_blue);
        colors.add(royal_blue);
        colors.add(blue_violet);
        colors.add(indigo);
        colors.add(dark_slate_blue);
        colors.add(slate_blue);
        colors.add(medium_slate_blue);
        colors.add(medium_purple);
        colors.add(dark_magenta);
        colors.add(dark_violet);
        colors.add(dark_orchid);
        colors.add(medium_orchid);
        colors.add(purple);
        colors.add(thistle);
        colors.add(plum);
        colors.add(violet);
        colors.add(orchid);
        colors.add(medium_violet_red);
        colors.add(pale_violet_red);
        colors.add(deep_pink);
        colors.add(hot_pink);
        colors.add(light_pink);
        colors.add(pink);
        colors.add(antique_white);
        colors.add(beige);
        colors.add(bisque);
        colors.add(blanched_almond);
        colors.add(wheat);
        colors.add(corn_silk);
        colors.add(lemon_chiffon);
        colors.add(light_golden_rod_yellow);
        colors.add(light_yellow);
        colors.add(saddle_brown);
        colors.add(sienna);
        colors.add(chocolate);
        colors.add(peru);
        colors.add(sandy_brown);
        colors.add(burly_wood);
        colors.add(tan);
        colors.add(rosy_brown);
        colors.add(moccasin);
        colors.add(navajo_white);
        colors.add(peach_puff);
        colors.add(misty_rose);
        colors.add(lavender_blush);
        colors.add(linen);
        colors.add(old_lace);
        colors.add(papaya_whip);
        colors.add(sea_shell);
        colors.add(mint_cream);
        colors.add(slate_gray);
        colors.add(light_slate_gray);
        colors.add(light_steel_blue);
        colors.add(lavender);
        colors.add(floral_white);
        colors.add(alice_blue);
        colors.add(ghost_white);
        colors.add(honeydew);
        colors.add(ivory);
        colors.add(azure);
        colors.add(snow);
        colors.add(black);
        colors.add(dim_gray);
        colors.add(gray);
        colors.add(dark_gray);
        colors.add(silver);
        colors.add(light_gray);
        colors.add(gainsboro);
        colors.add(white_smoke);
    }
}
