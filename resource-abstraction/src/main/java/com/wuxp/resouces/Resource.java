package com.wuxp.resouces;


/**
 * resource
 * <p>
 * all can be considered as resources, this is a logical abstraction
 * </p>
 */
public interface Resource<ID> {

    /**
     * an instance used to distinguish resources
     *
     * @return resource unique identifier
     */
    ID getUniqueIdentifier();

    /**
     * @return resource type
     */
    ResourceType getResourceType();

    /**
     * @return class type of resource
     */
    Class<ID> getClassType();
}
