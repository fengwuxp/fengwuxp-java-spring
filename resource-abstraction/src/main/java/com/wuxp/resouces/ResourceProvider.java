package com.wuxp.resouces;

import java.util.List;

/**
 * resource provider
 * <p>
 *    responsible for resource maintenance and screening
 * </p>
 */
public interface ResourceProvider<R extends Resource<ID>, ID> {

    /**
     * persistence resource
     */
    ID persistence(R resource);

    int update(R resource);

    default boolean deleted(R resource) {
        if (resource == null) {
            return false;
        }
        return this.deletedById(resource.getUniqueIdentifier());
    }


    default int deleted(ResourceFindCondition condition) {

        List<R> list = this.findList(condition);
        int count = 0;
        for (R r : list) {
            if (this.deleted(r)) {
                count++;
            }
        }
        return count;
    }

    boolean deletedById(ID id);

    /**
     * find resource instance by unique identifier
     *
     * @param id
     * @return
     */
    R findById(ID id);

    List<R> findList(ResourceFindCondition condition);
}
