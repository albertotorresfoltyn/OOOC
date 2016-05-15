#OOOC

A simple, lightweight, OODBMS for Java

##What is OOOC?

OOOCis a free and lightweight object-oriented database system for use in Java development.

##The idea

The idea of OOOC was to create a streamlined database system that provides object storage and access via similar methods of the standard OODBMS (i.e. retrieval via a unique object identifier, use of a single-level, collection-like storage etc.). 
It was important to ensure the system is robust, simple and that the database data is easily accessible and portable.

##Why use OOOC?

Provide developers with a quick and easy programming interface
Provide a portable database system that doesn’t rely on any background services
Provide a platform independent database system
TODO: WEB API

How does OOOC work?

The databases created by OOOC follow a two tier structure; clusters being the first point of access, and then the objects being the children.
A cluster groups objects together in order to create a more manageable database (similar to collections in mongo); 
for example if you needed to store graphical objects and authentication objects it would be more logical to put them each in their own
cluster as they share no semantic relation.

To actually put OOOC to use one must simply include the required files in their Java project and create a OOOC object like in the example below:
(you can create a JAR and use it!)
import OOOC.*;

public class OOOCTest {
  public static void main(String[] args) {
    // Create and store an object in the cluster named "cluster01"
    String objectToStore = "Hello world!";
    test.storeObject("cluster01", objectToStore);

    // Retrieve object 2 from cluster01 and output it to the console
    String retrievedObject = (String)test.getObject("cluster01", 2);
    System.out.println(retrievedObject);
  }
}