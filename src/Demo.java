
import algorithms.basic.DisplayCloud;
import algorithms.io.ReadPCfromTXT;
import java.util.Scanner;

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
/**
 *
 * @author Jean-Jacques Ponciano
 */
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Demo DisplayCloud
        Demo demo = new Demo();
        demo.demoDisplayCloud();
        System.out.println("Display table");
        Scanner sc = new Scanner(System.in);
        String str = "";
        while (!str.toLowerCase().contains("q")) {
            System.out.println("Exit(Q)");
            str = sc.nextLine();
        }
        System.exit(0);
    }

    /**
     * Demonstration of the algorithm <code>DisplayCloud</code>
     */
    public void demoDisplayCloud() {
        String pathfile = "E:\\Data\\table.txt";
        //read the point cloud in a windows.
        ReadPCfromTXT reader = new ReadPCfromTXT(pathfile);
        reader.run();
        //display the point cloud
        DisplayCloud instance = new DisplayCloud(reader.getCloud(), 1000, 1000);
        instance.run();
    }

}
