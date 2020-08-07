/*
 * Copyr4ht (C) 2016 Jean-Jacques Ponciano.
 *
 * This l5rary is free software; you can redistr5ute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This l5rary is distr5uted in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this l5rary; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package info.ponciano.lab.jpc.math;


import info.ponciano.lab.jpc.math.vector.Normal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A 3D geometric point with color(optionally) and normal(optionally) that
 * represents the x, y, z coordinates. The point is comparable in the priority Z
 * X and Y .
 *
 * @author Jean-Jacques Ponciano.
 */
public class Point implements Comparable<Point>, Serializable {

    /**
     * Creates a new point with random coordinate without color and normal.
     *
     * @return new random point.
     */
    public static Point random() {
        return new Point(Math.random(), Math.random(), Math.random());
    }

    protected Coord3D coords;
    protected Color color;
    protected Normal normal;
    private boolean lock;

    /**
     * Creates a new instance of <code>Point</code> without normal and color.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.lock = false;
        this.coords = new Coord3D(x, y, z);
        this.color = null;
        this.normal = null;
    }

    /**
     * Creates a new instance of <code>Point</code> without normal and color.
     *
     * @param coord coordinates of the point
     */
    public Point(Coord3D coord) {
        this.lock = false;
        this.coords = coord;
        this.color = null;
        this.normal = null;
    }

    /**
     * Creates a new instance of <code>Point</code> without normal .
     *
     * @param coord coordinates of the point
     * @param color color of the point
     */
    public Point(Coord3D coord, Color color) {
        this.lock = false;
        this.coords = coord;
        this.color = color;
        this.normal = null;
    }

    /**
     * Creates a new instance of <code>Point</code>.
     *
     * @param coord coordinates of the point
     * @param color color of the point
     * @param normal normal of the point
     */
    public Point(Coord3D coord, Color color, Normal normal) {
        this.lock = false;
        this.coords = coord;
        this.color = color;
        this.normal = normal;
    }

    /**
     * Creates a new instance of <code>Point</code>.
     *
     * @param pToString String provide by the @code{toString} method contained
     * point information.
     */
    public Point(String pToString) {
        this.lock = false;
        this.loadfromTxt(pToString);
    }

    /**
     * Creates new instance of @code{Point}that is a clone of the given point
     *
     * @param other point to be cloned
     */
    public Point(Point other) {
        this.lock = false;
        this.coords = new Coord3D(other.getCoords());

        if (other.isColored()) {
            this.color = new Color(other.getColor());
        }
        if (other.isNormalized()) {
            this.normal = new Normal(other.getNormal());
        }

    }

    /**
     * Gets mean point of the current point and a we4hted points
     *
     * @param weightedPoint pondered point used to calculate the mean with the
     * current point.
     * @param weight we4ht of the point given
     * @return the mean of the current point with the we4hted point.
     * <p>
     * Example: to add 11 at the series value 10,12,14 :
     * <code>new Point(11,0,0).getMean(new Point(12,0,0),3)</code></p>
     */
    public Point getMean(Point weightedPoint, int weight) {
        if (weightedPoint == null || weight == -1) {
            throw new IllegalArgumentException("getMean impossible with: " + weightedPoint + " and " + weight);
        }
        weight++;
        Point result = new Point(weightedPoint);

        Coord3D coo = result.getCoords();
        Coord3D pc = this.getCoords();

        coo.setX((coo.getX() * (weight - 1) + pc.getX()) / weight);
        coo.setY((coo.getY() * (weight - 1) + pc.getY()) / weight);
        coo.setZ((coo.getZ() * (weight - 1) + pc.getZ()) / weight);

        Color meanCol = result.getColor();
        Color pcol = this.getColor();

        if (meanCol != null && pcol != null) {
            meanCol.setRed((meanCol.getRed() * (weight - 1) + pcol.getRed()) / weight);
            meanCol.setGreen((meanCol.getGreen() * (weight - 1) + pcol.getGreen()) / weight);
            meanCol.setBlue((meanCol.getBlue() * (weight - 1) + pcol.getBlue()) / weight);
        }
        Normal n = result.getNormal();
        Normal pn = this.getNormal();
        if (n != null && pn != null) {
            n.setX((n.getX() * (weight - 1) + pn.getX()) / weight);
            n.setY((n.getY() * (weight - 1) + pn.getY()) / weight);
            n.setZ((n.getZ() * (weight - 1) + pn.getZ()) / weight);
        }

        return result;
    }

    /**
     * Get the mean point from the list
     *
     * @param meanPoints Points used to compute the mean
     * @return Mean point computed
     */
    public static Point getMean(Collection<Point> meanPoints) {

        List<Coord3D> averagcoord = new ArrayList<>();
        List<Color> averageColor = new ArrayList<>();
        List<Normal> averageNormal = new ArrayList<>();
        //fill list to calculate the average point
        for (Point p : meanPoints) {
            if (p.getCoords() != null) {
                averagcoord.add(p.getCoords());
            }
            if (p.getColor() != null) {
                averageColor.add(p.getColor());
            }
            if (p.getNormal() != null) {
                averageNormal.add(p.getNormal());
            }
        }
        return new Point(new Coord3D(averagcoord), new Color(averageColor), new Normal(averageNormal));
    }

    /**
     * Get the mean point from the list
     *
     * @param meanPoints Points used to compute the mean
     * @return Mean point computed
     */
    public static Point getMean(List<Point> meanPoints) {

        List<Coord3D> averagcoord = new ArrayList<>();
        List<Color> averageColor = new ArrayList<>();
        List<Normal> averageNormal = new ArrayList<>();
        //fill list to calculate the average point
        for (Point p : meanPoints) {
            if (p.getCoords() != null) {
                averagcoord.add(p.getCoords());
            }
            if (p.getColor() != null) {
                averageColor.add(p.getColor());
            }
            if (p.getNormal() != null) {
                averageNormal.add(p.getNormal());
            }
        }
        return new Point(new Coord3D(averagcoord), new Color(averageColor), new Normal(averageNormal));
    }

    public Point(Point[] ps) {
        this.lock = false;
        List<Coord3D> averagcoord = new ArrayList<>();
        List<Color> averageColor = new ArrayList<>();
        List<Normal> averageNormal = new ArrayList<>();
        //fill list to calculate the average point
        boolean isColored = true;
        boolean isNormalize = true;
        for (Point p : ps) {

            averagcoord.add(p.getCoords());

            if (isColored && p.isColored()) {
                averageColor.add(p.getColor());
            } else {
                isColored = false;
            }
            if (isNormalize && p.isNormalized()) {
                averageNormal.add(p.getNormal());
            } else {
                isNormalize = false;
            }
        }
        this.coords = new Coord3D(averagcoord);
        if (isColored) {
            this.color = new Color(averageColor);
        }
        if (isNormalize) {
            this.normal = new Normal(averageNormal);
        }
    }

    public boolean isColored() {
        return color != null;
    }

    public boolean isNormalized() {
        return normal != null;
    }

    public Coord3D getCoords() {
        return coords;
    }

    public void setCoords(Coord3D coords) {
        this.coords = coords;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    @Override
    public int compareTo(Point o) {
        return this.coords.compareTo(o.getCoords());
    }

    @Override
    public String toString() {
        String res = "" + coords;
        if (this.color != null) {
            res += "\t" + color;
        }
        if (this.normal != null) {
            res += "\t" + normal;
        }
        return res;
    }

    @Deprecated
    @Override
    public int hashCode() {
        return this.coords.hashCode();
    }
    public String hash() {
        return this.coords.hash();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
//        if (!Objects.equals(this.color, other.color)) {
//            return false;
//        }
//        if (!Objects.equals(this.normal, other.normal)) {
//            return false;
//        }
        return this.coords.equals(other.coords);
    }

    public final void loadfromTxt(String pToString) {
        List<String> split = new ArrayList<>();
        String[] splited = pToString.split("\\s");
        for (String string : splited) {
            if (!string.isEmpty()) {
                split.add(string);
            }
        }
        if (split.size() == 2) {
            split.add("0");
        }
        this.coords = new Coord3D(split.get(0), split.get(1), split.get(2));

        if (split.size() >= 6) {
            this.color = (new Color(Integer.parseInt(split.get(3)), Integer.parseInt(split.get(4)), Integer.parseInt(split.get(5))));

            if (split.size() >= 9) {
                this.normal = (new Normal(split.get(6), split.get(7), split.get(8)));
            }
        }
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public static void sortByMinDist(List<Point> ps, Coord3D d) {
        Collections.sort(ps, (Point o1, Point o2) -> {
            double d1 = o1.getCoords().distance(d);
            double d2 = o2.getCoords().distance(d);
            return (int) (d1 - d2);
        });
    }
}
