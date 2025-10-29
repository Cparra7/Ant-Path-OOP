package com.mycompany.a3;

public interface ICollider {
	
	// if an object collides with another object
	boolean collidesWith(GameObject otherObject);
	
	//  handles the collision 
	void handleCollision(GameObject otherObject);

}