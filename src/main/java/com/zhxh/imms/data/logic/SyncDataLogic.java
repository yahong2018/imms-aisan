package com.zhxh.imms.data.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.domain.SyncData;
import org.springframework.stereotype.Component;

@Component
public class SyncDataLogic extends CrudLogic<SyncData> {
//    public List getRecordsTobeSync(Class clazz, String tableName, String resultMapId) {
//        Map map = new HashMap();
//        map.put("resultType", clazz);
//        map.put("tableName", tableName);
//        map.put("classType", clazz.getCanonicalName());
//        map.put("resultMapId", resultMapId);
//
//        SqlSession session = this.getSqlSessionFactory().openSession();
//        List result = session.selectList(SQL_GET_SYNC_DATA,map);
//        return result;
//
//       // return ((SyncDataMapper) this.getMapper()).getRecordsTobeSync(map);
//    }

//    private static final String SQL_GET_SYNC_DATA = "com.zhxh.imms.data.mapper.SyncDataMapper.getRecordsTobeSync";
}
