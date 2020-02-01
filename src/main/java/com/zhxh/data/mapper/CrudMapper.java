package com.zhxh.data.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.zhxh.data.DbQueryParameter;
import com.zhxh.data.domain.Entity;
import com.zhxh.data.domain.TraceInfo;

public interface CrudMapper<E extends Entity> {
	E get(Long id);

	List<E> getAll(DbQueryParameter query);

	int create(E item);

	int update(E item);

	int delete(Long id);

	int deleteAll(Long[] ids);

    int getPageTotal(DbQueryParameter query);

}
