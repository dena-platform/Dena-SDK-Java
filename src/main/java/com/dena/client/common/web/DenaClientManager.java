package com.dena.client.common.web;

import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.utils.StringUtils;
import com.dena.client.common.web.HttpClient.HttpClient;
import com.dena.client.common.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.common.web.HttpClient.dto.request.DeleteObjectRequest;
import com.dena.client.common.web.HttpClient.dto.request.DeleteRelationRequest;
import com.dena.client.common.web.HttpClient.dto.request.GetObjectRequest;
import com.dena.client.common.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.common.utils.JSONMapper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class DenaClientManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final static Logger log = getLogger(DenaClientManager.class);

    private static final HttpClient DENA_HTTP_CLIENT = HttpClient.getInstance();

    public static DenaResponse getData(GetObjectRequest getObjectRequest) throws DenaFault {
        String URL = getObjectRequest.getURL();
        return DENA_HTTP_CLIENT.getData(URL, getObjectRequest.getParameterList());

    }

    public static DenaResponse createNewDenaObject(CreateObjectRequest createObjectRequest) throws DenaFault {
        String fullURL = createObjectRequest.getBaseURL() + createObjectRequest.getAppId() + createObjectRequest.getTypeName();
        RequestBody requestBody = RequestBody.create(JSON, JSONMapper.createJSONFromObject(createObjectRequest.getRequestBodyMap()));
        DenaResponse denaResponse = DENA_HTTP_CLIENT.postData(fullURL, createObjectRequest.getParameterList(), requestBody);
        log.debug("Successfully posted data to address [{}], parameters {}, body [{}]", fullURL, createObjectRequest.getParameterList(), createObjectRequest.getRequestBodyMap());

        return denaResponse;
    }


    public static DenaResponse updateDenaObject(CreateObjectRequest updateObjectRequest) throws DenaFault {
        String fullURL = updateObjectRequest.getBaseURL() + updateObjectRequest.getAppId() + updateObjectRequest.getTypeName();
        RequestBody requestBody = RequestBody.create(JSON, JSONMapper.createJSONFromObject(updateObjectRequest.getRequestBodyMap()));
        DenaResponse denaResponse = DENA_HTTP_CLIENT.putData(fullURL, updateObjectRequest.getParameterList(), requestBody);
        log.debug("Successfully put data to address [{}], parameters {}, body [{}]", fullURL, updateObjectRequest.getParameterList(), updateObjectRequest.getRequestBodyMap());

        return denaResponse;
    }

    public static DenaResponse deleteDenaObject(DeleteObjectRequest deleteObjectRequest) throws DenaFault {
        String fullURL = deleteObjectRequest.getBaseURL() + deleteObjectRequest.getAppId()
                + deleteObjectRequest.getTypeName() + deleteObjectRequest.getObjectIds();

        DenaResponse denaResponse = DENA_HTTP_CLIENT.deleteData(fullURL, deleteObjectRequest.getParameterList());
        log.debug("Successfully delete data, address [{}], parameters {}", fullURL, deleteObjectRequest.getParameterList());

        return denaResponse;
    }

    public static DenaResponse DeleteRelation(DeleteRelationRequest deleteRelationRequest) throws DenaFault {
        if(StringUtils.isNoneBlank()deleteRelationRequest.getChildObjectId())

        String fullURL = deleteRelationRequest.getBaseURL() + deleteRelationRequest.getAppId()
                + deleteRelationRequest.getTypeName() + deleteRelationRequest.getObjectIds();

        DenaResponse denaResponse = DENA_HTTP_CLIENT.deleteData(fullURL, deleteRelationRequest.getParameterList());
        log.debug("Successfully delete data, address [{}], parameters {}", fullURL, deleteRelationRequest.getParameterList());

        return denaResponse;
    }


}
