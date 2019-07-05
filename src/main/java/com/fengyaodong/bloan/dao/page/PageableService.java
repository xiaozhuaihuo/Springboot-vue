package com.fengyaodong.bloan.dao.page;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface PageableService<C, R> {

    /**
     * 分页查询 <br/>
     * 先查询总记录数据，然后再获取当前页数据<br/>
     *
     * @param condition 查询条件
     * @param page      从1开始的页码
     * @param page
     * @return 分页结果
     */
    default Page<R> findByPage(C condition, int page, int size) {

        Long totalCnt = count(condition);
        if (null == totalCnt || totalCnt.intValue() < 0) {
            return null;
        }
        List<R> list = null;
        int offset = (page - 1) * size;
        IPagination pagination = buildPagination(offset, size);
        if (null != totalCnt && totalCnt.intValue() > 0 && offset < totalCnt) {
            list = find(condition, pagination);
        } else {
            list = new ArrayList<>();
        }
        return new PageImpl<>(list, pagination, totalCnt);

    }

    /**
     * @param offset
     * @param size
     * @return
     */
    IPagination buildPagination(int offset, int size);

    /**
     * 根据查询条件，获得总记录数
     *
     * @param condition 查询条件
     * @return 总记录数
     */
    Long count(C condition);

    /**
     * 根据查询条件，获得数据列表
     *
     * @param condition  查询条件
     * @param pagination
     * @return 结果集
     */
    List<R> find(C condition, IPagination pagination);

    List<R> findAllByMsgType(C condition, IPagination pagination);

    Long countByMsgType(C condition);
}
