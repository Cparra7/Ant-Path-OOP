package com.mycompany.a2;


public interface ICollection {

    // Add a GameObject to the collection
    void add(GameObject obj);

    // Return an iterator for looping through the collection
    IIterator getIterator();
}
