package com.dena.client.service.web.HttpClient.dto.response;

import com.dena.client.utils.DenaStringUtils;
import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DenaObjectResponse {
    private Map<String, Object> fields = new LinkedHashMap<>();

    @JsonProperty(value = "object_id")
    private String objectId;

    @JsonProperty(value = "related_objects")
    private List<RelatedObject> relatedObjects = new ArrayList<>();

    @JsonProperty(value = "object_uri")
    private String objectURI;

    @JsonAnyGetter
    public Map<String, Object> getAllFields() {
        return fields;
    }


    @JsonAnySetter
    public void addProperty(String name, Object value) {
        if (DenaStringUtils.isNoneBlank(name) && value != null) {
            fields.put(name, value);
        }
    }


    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public String getObjectURI() {
        return objectURI;
    }

    public void setObjectURI(String objectURI) {
        this.objectURI = objectURI;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public List<RelatedObject> getRelatedObjects() {
        return relatedObjects;
    }

    public void setRelatedObjects(List<RelatedObject> relatedObjects) {
        this.relatedObjects = relatedObjects;
    }
}
