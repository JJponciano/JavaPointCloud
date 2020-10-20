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

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

/**
 *
 * @author Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
 */
public class PiMath {

    public static double min(final double[] values) {
        final OptionalDouble min = DoubleStream.of(values).min();
        return min.getAsDouble();
    }

    public static double getMean(final double[] values) {
        final double mean = PiMath.sum(values) / values.length;
        return mean;

    }

    public static double getMean(final List<Double> values) {
        final double mean = PiMath.sum(values) / values.size();
        return mean;

    }
   private static double sum(final double[] values) {
        double sum = 0;
        for (final double value : values) {
            sum += value;
        }
        return sum;
    }
    
    private static double sum(final List<Double> values) {
        double sum = 0;
        sum = values.stream().map((value) -> value).reduce(sum, (accumulator, _item) -> accumulator + _item);
        return sum;
    }
    public static double max(final double[] values) {
        final OptionalDouble max = DoubleStream.of(values).max();
        return max.getAsDouble();
    }

    public static double min(final List<Double> values) {
        return min(toDoubleArray(values));
    }

    public static double max(final List<Double> values) {
        return max(toDoubleArray(values));
    }

    public static double[] toDoubleArray(final List<Double> values) {
        final double[] a = new double[values.size()];
        for (int i = 0; i < a.length; i++) {
            a[i] = values.get(i);
        }
        return a;
    }
}
