package com.dena.client.core.feature.persistence;

import com.dena.client.common.utils.ClassUtils;
import com.dena.client.core.feature.persistence.dto.RelatedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public final class Relation<T> {
    private final static Logger log = LoggerFactory.getLogger(Relation.class);

    private Set<RelatedObject> relatedObjects = new HashSet<>();

    public void addRelatedObject(T object) {
        String type = ClassUtils.findTypeName(object);
        Optional<String> objectId = DenaSerializer.findObjectId(object);

        if (objectId.isPresent()) {
            RelatedObject relatedObject = new RelatedObject(type, objectId.get());
            relatedObjects.add(relatedObject);
        } else {
            log.debug("object_id not set for object [{}]", object);
        }
    }

    public Set<RelatedObject> getAllRelatedObjects() {
        return relatedObjects;
    }


}
