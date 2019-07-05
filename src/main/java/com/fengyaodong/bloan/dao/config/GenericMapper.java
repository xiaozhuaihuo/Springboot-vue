package com.fengyaodong.bloan.dao.config;


import com.fengyaodong.bloan.dao.page.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * GenericMapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/28 9:16
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/28 9:16
 */
public interface GenericMapper<T, Pk extends Serializable> {

    /**
     * 添加数据
     *
     * @param entity
     */
    void add(T entity);

    /**
     * 修改数据
     *
     * @param entity
     * @return 更新影响行数
     */
    int updateById(T entity);

    /**
     * 删除数据
     *
     * @param pk
     * @return 删除影响行数
     */
    int deleteById(Pk pk);

    /**
     * 批量删除功能
     *
     * @param pkList
     * @return 删除影响行数
     */
    int deleteBatchIds(List<? extends Pk> pkList);

    /**
     * 查询所有数据功能
     *
     * @return
     */
    List<T> findAll();

    /**
     * 根据ID查询详情
     *
     * @param pk
     * @return
     */
    T findOneById(Pk pk);

    /**
     * 根据查询条件统计总记录数据
     *
     * @param condition
     * @return
     */
    Long count(T condition);

    Long countByMsgType(T condition);


    /**
     * 根据查询条件获取数据(不带分页功能)
     *
     * @param condition 查询条件
     * @return
     */
    List<T> find(T condition);

    /**
     * 根据查询条件获取数据(带分页功能)
     *
     * @param condition 查询条件
     * @param pagination 分页信息
     * @return
     */
    List<T> find(T condition, Pagination pagination);

    /**
     * 根据特定条件获取数据（待分页功能）
     * @param condition
     * @param pagination
     * @return
     */
    List<T> findAllByMsgType(T condition, Pagination pagination);

}
