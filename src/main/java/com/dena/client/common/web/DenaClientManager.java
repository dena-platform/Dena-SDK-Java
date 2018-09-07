package com.dena.client.common.web;

import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.utils.JSONMapper;
import com.dena.client.common.utils.StringUtils;
import com.dena.client.common.utils.URLUtils;
import com.dena.client.common.web.HttpClient.HttpClient;
import com.dena.client.common.web.HttpClient.dto.request.*;
import com.dena.client.core.feature.persistence.dto.DenaResponse;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */

public class DenaClientManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final static String loginUserURL = "/users/login";

    private final static String logoutUserURL = "/users/logout";

    private final static String registerNewUserURL = "/users/register";

    private final static Logger log = getLogger(DenaClientManager.class);

    private static final HttpClient DENA_HTTP_CLIENT = HttpClient.getInstance();

    public static DenaResponse findDenaObject(FindObjectRequest findObjectRequest) throws DenaFault {
        String fullURL;
        // child type name is empty. find object by id
        if (StringUtils.isBlank(findObjectRequest.getChildTypeName())) {
            fullURL = findObjectRequest.getBaseURL() + findObjectRequest.getAppId() + findObjectRequest.getParentTypeName() +
                    findObjectRequest.getParentObjectId();
        } else {
            fullURL = findObjectRequest.getBaseURL() + findObjectRequest.getAppId() + findObjectRequest.getParentTypeName() +
                    findObjectRequest.getParentObjectId() + "/relation" + findObjectRequest.getChildTypeName();
        }

        DenaResponse denaResponse = DENA_HTTP_CLIENT.getData(fullURL, findObjectRequest.getParameterList());
        log.debug("Find object, address [{}], parameters {}", fullURL, findObjectRequest.getParameterList());

        return denaResponse;
    }

    public static DenaResponse createNewDenaObject(CreateObjectRequest createObjectRequest) throws DenaFault {
        String fullURL = createObjectRequest.getBaseURL() + createObjectRequest.getAppId() + createObjectRequest.getTypeName();
        RequestBody requestBody = RequestBody.create(JSON, JSONMapper.createJSONFromObject(createObjectRequest.getRequestBodyMap()));
        Headers headers = Headers.of(createObjectRequest.getHeaders());
        DenaResponse denaResponse = DENA_HTTP_CLIENT.postData(fullURL, headers, createObjectRequest.getParameterList(), requestBody);
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

    public static DenaResponse deleteRelation(DeleteRelationRequest deleteRelationRequest) throws DenaFault {
        String fullURL;

        // child object id is empty. remove relation with type
        if (StringUtils.isBlank(deleteRelationRequest.getChildObjectId())) {
            fullURL = deleteRelationRequest.getBaseURL() + deleteRelationRequest.getAppId()
                    + deleteRelationRequest.getParentTypeName() + deleteRelationRequest.getParentObjectId()
                    + "/relation" + deleteRelationRequest.getChildTypeName();
        } else { // child object id is not empty. remove relation with other type with specified object_id
            fullURL = deleteRelationRequest.getBaseURL() + deleteRelationRequest.getAppId()
                    + deleteRelationRequest.getParentTypeName() + deleteRelationRequest.getParentObjectId()
                    + "/relation" + deleteRelationRequest.getChildTypeName() + deleteRelationRequest.getChildObjectId();
        }

        DenaResponse denaResponse = DENA_HTTP_CLIENT.deleteData(fullURL, deleteRelationRequest.getParameterList());
        log.debug("Successfully delete relation, address [{}]", fullURL);

        return denaResponse;
    }

    public static DenaResponse loginUser(GeneralRequest generalRequest) {
        String fullURL = URLUtils.makeURLString(generalRequest.getBaseURL(), generalRequest.getAppId(), loginUserURL);
        RequestBody requestBody = RequestBody.create(JSON, JSONMapper.createJSONFromObject(generalRequest.getRequestBodyMap()));
        Headers headers = Headers.of(generalRequest.getHeaders());
        DenaResponse denaResponse = DENA_HTTP_CLIENT.postData(fullURL, headers, null, requestBody);
        log.debug("Successfully calling login user address [{}]", fullURL);

        return denaResponse;
    }

    public static DenaResponse logOutUser(GeneralRequest generalRequest) {
        String fullURL = URLUtils.makeURLString(generalRequest.getBaseURL(), generalRequest.getAppId(), logoutUserURL);
        RequestBody requestBody = RequestBody.create(JSON, JSONMapper.createJSONFromObject(generalRequest.getRequestBodyMap()));
        Headers headers = Headers.of(generalRequest.getHeaders());
        DenaResponse denaResponse = DENA_HTTP_CLIENT.postData(fullURL, headers, null, requestBody);
        log.debug("Successfully calling logout user address [{}]", fullURL);

        return denaResponse;
    }


    public static DenaResponse registerUser(GeneralRequest generalRequest) {
        String fullURL = URLUtils.makeURLString(generalRequest.getBaseURL(), generalRequest.getAppId(), registerNewUserURL);
        RequestBody requestBody = RequestBody.create(JSON, JSONMapper.createJSONFromObject(generalRequest.getRequestBodyMap()));
        Headers headers = Headers.of(generalRequest.getHeaders());
        DenaResponse denaResponse = DENA_HTTP_CLIENT.postData(fullURL, headers, null, requestBody);
        log.debug("Successfully calling login register address [{}]", fullURL);

        return denaResponse;
    }


}
