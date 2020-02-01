package com.zhxh.data.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.data.domain.Entity;
import com.zhxh.data.domain.SyncData;
import com.zhxh.data.mapper.SyncDataMapper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    private static final String SQL_GET_SYNC_DATA = "com.zhxh.data.mapper.SyncDataMapper.getRecordsTobeSync";
}
