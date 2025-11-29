package com.mycompany.a3;

public interface IIterator {

    // Returns true if another object exists in the collection
    boolean hasNext();

    // Return the next GameObject
    GameObject getNext();
}