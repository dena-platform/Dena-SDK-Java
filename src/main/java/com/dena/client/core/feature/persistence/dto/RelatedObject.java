package com.dena.client.core.feature.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public final class RelatedObject {
    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "type", required = true)
    private String type;

    public RelatedObject() {
    }

    public RelatedObject(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatedObject that = (RelatedObject) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
