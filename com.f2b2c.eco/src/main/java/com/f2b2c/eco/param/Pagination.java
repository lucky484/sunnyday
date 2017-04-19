package com.f2b2c.eco.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
/**
 * 用于包装数据进行datatable分页
 * @author color.wu
 *
 * @param <T>
 */
public class Pagination<T> implements Page<T>, Serializable{

	private static final long serialVersionUID = 4285109174551362580L;
	private final List<T> content = new ArrayList<T>();
	private final Pageable pageable;
	private final long recordsTotal;
	private final long recordsFiltered;
	/**
	 * 构造函数
	 * @param content 数据list集合
	 * @param pageable 分页pageable
	 * @param recordsTotal 总记录数
	 */
	public Pagination(List<T> content, Pageable pageable, long recordsTotal) {

		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}

		this.content.addAll(content);
		this.recordsTotal = recordsTotal;
		this.pageable = pageable;
		this.recordsFiltered=recordsTotal;
	}
	
	/**
	 * 构造函数
	 * @param content 数据list集合
	 */
	public Pagination(List<T> content) {
		this(content, null, null == content ? 0 : content.size());
	}
	
	public long getRecordsFiltered(){
		return this.recordsFiltered;
	}
	
	public long getRecordsTotal(){
		return this.recordsTotal;
	}
	
	@Override
	public int getNumber() {
		return pageable == null ? 0 : pageable.getPageNumber();
	}

	@Override
	public int getSize() {
		return pageable == null ? 0 : pageable.getPageSize();
	}

	@Override
	public int getTotalPages() {
		return getSize() == 0 ? 1 : (int) Math.ceil((double) recordsTotal / (double) getSize());
	}

	@Override
	public int getNumberOfElements() {
		return content.size();
	}

	@Override
	public long getTotalElements() {
		return recordsTotal;
	}

	@Override
	public boolean hasPreviousPage() {
		return getNumber() > 0;
	}

	@Override
	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	@Override
	public boolean hasNextPage() {
		return getNumber() + 1 < getTotalPages();
	}

	@Override
	public boolean isLastPage() {
		return !hasNextPage();
	}

	@Override
	public Pageable nextPageable() {
		return hasNextPage() ? pageable.next() : null;
	}

	@Override
	public Pageable previousPageable() {
		return hasPreviousPage()?pageable.previousOrFirst():null;
	}

	@Override
	public Iterator<T> iterator() {
		return content.iterator();
	}

	@Override
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	@Override
	public boolean hasContent() {
		return !content.isEmpty();
	}

	@Override
	public Sort getSort() {
		return pageable == null ? null : pageable.getSort();
	}
}
