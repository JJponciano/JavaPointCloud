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

package info.ponciano.lab.jpc.pointcloud.bounding;

/**
 * Provides methods for comparing doubleing point variables using a precision {@code delta}.
 */
public class EstimatedCompare {

	/**
	 * @param f1 is the first double of the comparison
	 * @param f2 is the second double of the comparison
	 * @param precision is the error tolerated when comparing doubleing point numbers
	 * @return 1 if f1 is greater than f2, 0 if f1 is equal to f2, -1 otherwise
	 */
	public static int compareFloat(double f1, double f2, double precision) {
		if (Math.abs(f1 - f2) < precision) {
			return 0;
		} else {
			if (f1 > f2) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	/**
	 * @param d1 is the first double of the comparison
	 * @param d2 is the second double of the comparison
	 * @param precision is the error tolerated when comparing doubleing point numbers
	 * @return 1 if d1 is greater than d2, 0 if d1 is equal to d2, -1 otherwise
	 */
	public static int compareDouble(double d1, double d2, double precision) {
		if (Math.abs(d1 - d2) < precision) {
			return 0;
		} else {
			if (d1 > d2) {
				return 1;
			} else {
				return -1;
			}
		}
	}


}
