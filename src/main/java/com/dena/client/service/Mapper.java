package com.dena.client.service;

import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public interface Mapper {
    Map<String, Object> findAllFields(Object object);
}