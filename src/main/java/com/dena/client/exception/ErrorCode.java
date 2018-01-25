package com.dena.client.exception;

/**
 * Each error code contain two part:
 * <li>message code that resolve to an message</li>
 * <li>error code that include two part: error_code - http_response_code  </li>
 *
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public enum ErrorCode {
    // Input type & format exception - range 1000-1999
    INVALID_REQUEST("com.dena.platform.restapi.exception.INVALID_REQUEST", "1000-400"),
    INVALID_MEDIA_TYPE("com.dena.platform.restapi.exception.INVALID_MEDIA_TYPE", "1001-400"),


    // Data store exception - range 2000-2999
    GENERAL_DATA_STORE_EXCEPTION("com.dena.platform.restapi.exception.GENERAL_DATASTORE", "2000-500"),
    RELATION_INVALID_EXCEPTION("com.dena.platform.restapi.exception.RELATION_INVALID", "2001-400"),
    ObjectId_INVALID_EXCEPTION("com.dena.platform.restapi.exception.ObjectId_INVALID", "2002-400"),
    ObjectId_NOT_FOUND_EXCEPTION("com.dena.platform.restapi.exception.ObjectId_NOT_FOUND", "2003-400"),


    GENERAL("com.dena.platform.restapi.exception.GENERAL", "999-500");

    private String messageCode;
    private String errorCode;


    ErrorCode(String messageCode, String errorCode) {
        this.messageCode = messageCode;
        this.errorCode = errorCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
