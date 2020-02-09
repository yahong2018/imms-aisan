package com.zhxh.imms.data;

import java.util.List;

import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.data.domain.Entity;

public interface CrudMapper<E extends Entity> {
	E get(Long id);

	List<E> getAll(DbQueryParameter query);

	int create(E item);

	int update(E item);

	int delete(Long id);

	int deleteAll(Long[] ids);

    int getPageTotal(DbQueryParameter query);

}
