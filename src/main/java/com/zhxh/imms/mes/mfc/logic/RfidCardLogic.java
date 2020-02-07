package com.zhxh.imms.mes.mfc.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.mfc.mapper.RfidCardMapper;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RfidCardLogic extends CrudLogic<RfidCard> {
    public RfidCard getByRfidNo(String rfidNo) {
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere("rfid.rfid_no ='" + rfidNo + "'");
        List<RfidCard> cardList = this.getAll(query);
        if (cardList.size() == 0) {
            return null;
        }

        return cardList.get(0);
    }

    public RfidCard getByKanbanAndMaterial(String kanbanNo, String materialCode) {
        FilterExpression[] expressions = new FilterExpression[2];
        expressions[0] = new FilterExpression("kanbanNo", "=", kanbanNo);
        expressions[1] = new FilterExpression("productionCode", "=", materialCode);
        DbQueryParameter query = new DbQueryParameter(RfidCard.class,expressions);
        return this.get(query);
    }

    public List<RfidCard> getOutsourceBindCard(Long workstationId, Long outsourceCardId) {
        return ((RfidCardMapper) this.getMapper()).getOutsourceBindCard(workstationId, outsourceCardId);
    }

    public Map<String, Integer> getIssuedTotalCountAndQty(Long productionId) {
        return ((RfidCardMapper) this.getMapper()).getIssuedTotalCountAndQty(productionId);
    }
}
