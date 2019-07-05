package com.fengyaodong.bloan.dao.config;

import java.io.Serializable;
import java.util.List;

/**
 * GenericService
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/4 17:24
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/4 17:24
 */
public interface GenericService<T, Pk extends Serializable> {

    /**
     * 添加数据
     *
     * @param entity
     * @return
     */
    void add(T entity);

    /**
     * 修改数据
     *
     * @param entity
     * @return
     */
    int updateById(T entity);

    /**
     * 删除数据
     *
     * @param pk
     * @return
     */
    int deleteById(Pk pk);

    /**
     * 批量删除
     *
     * @param pkList
     * @return
     */
    int deleteBatchIds(List<? extends Pk> pkList);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> findAll();

    /**
     * 根据条件进行查询
     *
     * @param entity
     * @return
     */
    List<T> find(T entity);

    /**
     * 根据主键获取详细数据
     *
     * @param pk
     * @return
     */
    T findOneById(Pk pk);
}
