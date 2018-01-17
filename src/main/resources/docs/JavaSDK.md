# Working With Object #

----------

## Object ##
To Save object in Dena we can use a simple pojo. pojo need not extend or implement any class or interface. those pojo should have following requirements:

1. The pojo class should have public fields or have getter and setter.
2. Optional requirement: Dena platform automatically assigns a unique ID to every persisted object. If the application needs to have access to the assigned ID, the class must declare the following field:


## Save Object To Dena ##

This API used for two purposed:

1. Creating new objects in Dena Platform
2. Updating existing objects.
 

If an object has been previously saved, it is updated in the database, otherwise it is created. The save operation checks if the object has denaObjectId assigned by the server. In that case, the object is updated, otherwise it is created in the Dena Platform data store. 

The denaObjectId property is automatically assigned to all objects in the database when they are initially saved. 

**Method:**

    public T DENA.save(T object) throws DenaError

**Return Value:**

Returns the saved object.

