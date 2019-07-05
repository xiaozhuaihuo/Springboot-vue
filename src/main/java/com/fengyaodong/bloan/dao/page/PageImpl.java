package com.fengyaodong.bloan.dao.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageImpl<T> implements Page<T>, Serializable {

    private static final long serialVersionUID = 867755909294344406L;

    @ApiModelProperty("当前页面数据")
    private List<T> content = new ArrayList<T>();

    @ApiModelProperty("总记录数")
    private long total;

    private int number;

    private int size;

    public PageImpl(List<T> content, long total, int number, int size) {
        this.content.addAll(content);
        this.total = total;
        this.number = number;
        this.size = size;
    }

    public PageImpl(List<T> content, IPagination pagination, long total) {
        this.content.addAll(content);
        this.total = !content.isEmpty() && pagination != null && pagination.getOffset() + pagination.getLimit() > total
                ? pagination.getOffset() + content.size()
                : total;
        this.number = pagination == null ? 0 : pagination.getOffset() / pagination.getLimit() + 1;
        this.size = pagination == null ? 0 : pagination.getLimit();
    }

    public PageImpl(List<T> content) {
        this(content, null, null == content ? 0 : content.size());
    }

    public PageImpl() {

    }

    @Override
    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getNumberOfElements() {
        return content.size();
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    @JsonIgnore
    public boolean hasContent() {
        return !content.isEmpty();
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Override
    @JsonIgnore
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    @JsonIgnore
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    public void setTotalElements(long total) {
        this.total = total;
    }

    @Override
    @JsonIgnore
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    @JsonIgnore
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return new PageImpl<S>(getConvertedContent(converter), total, number, size);
    }

    /**
     *
     * @param converter
     * @param <S>
     * @return
     */
    protected <S> List<S> getConvertedContent(Converter<? super T, ? extends S> converter) {
        Assert.notNull(converter, "Converter must not be null!");
        List<S> result = new ArrayList<S>(content.size());
        for (T element : this) {
            result.add(converter.convert(element));
        }
        return result;
    }

}
