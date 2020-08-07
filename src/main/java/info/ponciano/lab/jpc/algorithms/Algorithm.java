/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.jpc.algorithms;

/**
 *
 * @author Jean-Jacques Ponciano
 * @param <T> Algorithm results type
 */
public interface Algorithm<T> extends Runnable {

    /**
     * Get the algorithm results.
     *
     * @return the results of the algorithm execution or null if the algorithm
     * was not run before.
     */
    public T getResults();

}
