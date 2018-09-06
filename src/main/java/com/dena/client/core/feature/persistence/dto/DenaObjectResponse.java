package com.dena.client.core.feature.persistence.dto;

import com.dena.client.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class DenaObjectResponse {
    private Map<String, Object> fields = new LinkedHashMap<>();

    @JsonProperty(value = "object_id")
    private String objectId;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("create_time")
    private Long createTime;

    @JsonProperty(value = "object_uri")
    private String objectURI;

    @JsonProperty(value = "related_objects")
    private List<RelatedObject> relatedObjects = new ArrayList<>();

    @JsonAnyGetter
    public Map<String, Object> getAllFields() {
        return fields;
    }


    @JsonAnySetter
    public void addProperty(String name, Object value) {
        if (StringUtils.isNoneBlank(name) && value != null) {
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
