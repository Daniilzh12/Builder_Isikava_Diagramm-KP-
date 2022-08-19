package com.daniilzyravlev.fishbone;

/**
 * Class for coordinates
 * @param <X> coordinate
 * @param <Y> coordinate
 */
public class PointLiner<X,Y> {
    public X item1;
    public Y item2;

    public PointLiner(X item1, Y item2) {
        this.item1 = item1;
        this.item2 = item2;
    }
}
