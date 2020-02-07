package com.zhxh.imms.data;

import com.google.gson.Gson;
import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.data.domain.TraceInfo;
import com.zhxh.imms.data.mapper.CrudMapper;
import com.zhxh.imms.data.mapper.TraceInfoMapper;
import com.zhxh.imms.utils.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(transactionManager = "immsTransactionManager", rollbackFor = RuntimeException.class)
public abstract class CrudLogic<E extends Entity> implements Generic<E> {
    @Autowired
    private TraceInfoMapper traceMapper;

    @Autowired
    private CrudMapper<E> mapper;

    protected Class itemClass = createItemClass();

    private static transient HashMap<String, Map<String, String>> allFieldsMap = new HashMap<>();

    static void fillFieldsMap(Class clazz, Map<String, String> fieldsMap) {
        fieldsMap.keySet().forEach(k -> {
            fieldsMap.put(k, fieldsMap.get(k));
        });
        String key = clazz.getCanonicalName();
        allFieldsMap.put(key, fieldsMap);
    }

    private Class createItemClass() {
        Type t = this.getClass().getGenericSuperclass();
        return (Class<E>) ((ParameterizedType) t).getActualTypeArguments()[0];
    }

    public static Map<String, String> getFieldsMap(Class clazz) {
        String key = clazz.getCanonicalName();
        return allFieldsMap.get(key);
    }

    public Class getItemClass() {
        return itemClass;
    }

    protected Gson getGson() {
        Gson gson = GlobalConstants.getGson("handler", "traceInfos", "allFieldsMap");
        return gson;
    }

    public List<TraceInfo> getTraceInfos(E item) {
        Map map = new HashMap();
        map.put("businessId", item.getRecordId());
        map.put("className", item.getClass().getCanonicalName());
        DbQueryParameter query = new DbQueryParameter();
        return this.traceMapper.getTraceInfos(map);
    }

    public int create(E item) {
        if (this.exists(item)) {
            throw new BusinessException("数据已存在！");
        }
        int result = this.getMapper().create(item);
        if (!(item instanceof TraceInfo)) {
            this.createTraceInfo(DataOperateType.INSERT, item);
        }

        return result;
    }

    public boolean exists(E item) {
        return this.get(item.getRecordId()) != null;
    }

    private void createTraceInfo(DataOperateType operateType, E item) {
        return;
//
//        暂时停止历史记录
//
//        TraceInfo traceInfo = new TraceInfo();
//        traceInfo.setBusinessId(item.getRecordId());
//        traceInfo.setOperateTime(LocalDateTime.now());
//        traceInfo.setOperateType(operateType);
//        traceInfo.setClassName(item.getClass().getCanonicalName());
//        traceInfo.setTarget(this.getGson().toJson(item));
//
//        SystemUser currentUser = GlobalConstants.getCurrentUser();
//        if (currentUser != null) {
//            traceInfo.setOperatorId(currentUser.getRecordId());
//        } else {
//            traceInfo.setOperatorId(0L);
//        }
//        traceMapper.create(traceInfo);
    }

    public int update(E item) {
        if (!this.exists(item)) {
            throw new BusinessException("需要更新的数据不存在！");
        }
        int result = this.getMapper().update(item);
        if (!(item instanceof TraceInfo)) {
            this.createTraceInfo(DataOperateType.UPDATE, item);
        }

        return result;
    }

    public int delete(Long id) {
        E item = this.get(id);
        if (item == null) {
            throw new BusinessException("需要删除的数据不存在！");
        }
        int result = this.getMapper().delete(id);
        if (!(item instanceof TraceInfo)) {
            this.createTraceInfo(DataOperateType.DELETE, item);
        }
        return result;
    }

    public int deleteAll(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        List<E> deletedItems = null;
        if (itemClass != TraceInfo.class) {
            deletedItems = this.getDeletedItems(ids);
            if (deletedItems.size() != ids.length) {
                throw new BusinessException("需要删除的数据不存在！");
            }
        }
        int result = this.getMapper().deleteAll(ids);
        if (itemClass != TraceInfo.class) {
            for (E item : deletedItems) {
                this.createTraceInfo(DataOperateType.DELETE, item);
            }
        }
        return result;
    }

    private List<E> getDeletedItems(Long[] ids) {
        DbQueryParameter query = new DbQueryParameter();
        String idColumn = getFieldsMap(this.getItemClass()).get("recordId");
        StringBuilder builder = new StringBuilder();
        builder.append(idColumn).append(" in (");
        for (long id : ids) {
            builder.append(id).append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append(")");
        query.setWhere(builder.toString());
        return this.getMapper().getAll(query);
    }

    public E get(Long id) {
        return this.getMapper().get(id);
    }

    public E get(DbQueryParameter query) {
        List<E> list = this.getAll(query);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<E> getAll(DbQueryParameter query) {
        return this.getMapper().getAll(query);
    }

    public List<E> getAll() {
        DbQueryParameter query = new DbQueryParameter();
        return this.getAll(query);
    }

    public int getPageTotal(DbQueryParameter query) {
        return this.getMapper().getPageTotal(query);
    }

    public CrudMapper<E> getMapper() {
        return mapper;
    }

    public void setMapper(CrudMapper<E> mapper) {
        this.mapper = mapper;
    }
}
