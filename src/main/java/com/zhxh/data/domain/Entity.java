package com.zhxh.data.domain;

import java.io.Serializable;
import java.util.*;

import org.checkerframework.checker.units.qual.kg;

public abstract class Entity implements Comparable<Entity> {
	private Long recordId;
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Entity() {
	}

	@Override
	public int compareTo(Entity o) {
		if (null == o || null == o.getRecordId() || null == this.getRecordId()) {
			return -1;
		}
		return this.getRecordId().compareTo(o.getRecordId());
	}
}
