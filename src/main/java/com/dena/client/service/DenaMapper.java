package com.dena.client.service;

import com.dena.client.service.web.HttpClient.dto.response.DenaResponse;

import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public interface DenaMapper {
    Map<String, Object> findAllFields(Object object);

    String findTypeName(Object object);

    <T> T updateObjectFromResponse(Object object, DenaResponse denaResponse);
}
