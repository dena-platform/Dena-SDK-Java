# Working With Object #

----------

## Object ##
To Save object in Dena use simple class type. class need not extend or implement any class or interface. those class should have following requirements:

1.It should have public fields or have getter and setter for private fields (that follow the naming design patterns outlined in the JavaBeans Specification)
3. pojo should have default public, no-argument constructor. 
4. Static or transient fields will not serialized in operation (save, update)
5. Field name can not be 'object_id','created', 'updated'.
6. Field type should be primitive, primitive wrapper, String or Collection of those type . other type will be ignore  
6. Optional requirement: Dena platform automatically assigns a unique ID to every persisted object. If the application needs to have access to the assigned ID, the class must declare the following field: 

    public String object_id;

7. Optional requirement: in addition to denaObjectId, Dena platform maintains two other properties for every persisted object - created and updated. The former contains the timestamp when the object was initially created in the Dena Platform. The latter is updated every time the object is updated. To get access to these values, the class must declare the following fields:
 
    public Date createdTime;

    public Date updatedTime;  
    

## Save Or Update Object To Dena ##

This API used for two purposed:

1. Creating new objects in Dena Platform
2. Updating existing objects.
 

The save operation checks if the object has denaObjectId assigned by the server. In that case, the object is updated, otherwise it is created in the Dena Platform data store. 

The denaObjectId property is automatically assigned to all objects in the database when they are initially saved. 

**Class Hierarchy**

1. When an class extend another class all public fields or getter, setter of parent class also save or update in dena platform.
2. Interface are not include in hierarchy and do not include in this operation.


**Method:**

    public T DENA.saveOrUpdate(T object) throws DenaFault

**Return Value:**

Returns the saved object.

