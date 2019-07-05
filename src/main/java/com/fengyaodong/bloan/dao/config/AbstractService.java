package com.fengyaodong.bloan.dao.config;

import com.fengyaodong.bloan.dao.page.IPagination;
import com.fengyaodong.bloan.dao.page.PageableService;
import com.fengyaodong.bloan.dao.page.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 通常抽象Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/4 17:22
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/4 17:22
 */
public interface AbstractService<T, Pk extends Serializable> extends GenericService<T, Pk>, PageableService<T, T> {

    /**
     * @return
     */
    GenericMapper<T, Pk> getMapper();

    @Override
    default void add(T entity) {
        getMapper().add(entity);
    }

    @Override
    default int updateById(T entity) {
        return getMapper().updateById(entity);
    }

    @Override
    default int deleteById(Pk pk) {
        return getMapper().deleteById(pk);
    }

    @Override
    default int deleteBatchIds(List<? extends Pk> pkList) {
        return getMapper().deleteBatchIds(pkList);
    }

    @Override
    default List<T> findAll() {
        return getMapper().findAll();
    }

    @Override
    default T findOneById(Pk pk) {
        return getMapper().findOneById(pk);
    }

    @Override
    default List<T> find(T condition) {
        return getMapper().find(condition);
    }

    @Override
    default List<T> find(T condition, IPagination pagination) {
        return getMapper().find(condition, (Pagination) pagination);
    }

    @Override
    default List<T> findAllByMsgType(T condition, IPagination pagination) {
        return getMapper().findAllByMsgType(condition, (Pagination) pagination);
    }

    @Override
    default Long count(T condition) {
        return getMapper().count(condition);
    }

    @Override
    default Long countByMsgType(T condition) {
        return getMapper().countByMsgType(condition);
    }

    @Override
    default IPagination buildPagination(int offset, int size) {
        return new Pagination(offset, size);
    }

}
