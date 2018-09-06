package com.dena.client.common.exception;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public interface ErrorCode {
    String OBJECT_NOT_PRESENT = "Entity can not be null";

    String RELATION_NOT_PRESENT = "Relation can not be null";

    String OBJECT_ID_NOT_SET = "Object id is not present";

    String OBJECTS_IS_NOT_SAME_TYPE = "Objects is not of same type";

    String USER_CREDIT_INVALID = "Email or password is invalid";

    String USER_IS_NOT_LOGGEDED = "User is not logged in";


}
