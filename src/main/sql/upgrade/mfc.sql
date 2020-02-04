--
-- 看板
--
truncate imms.zhxh_rfid_card;
insert into imms.zhxh_rfid_card(kanban_no,rfid_no,card_type,card_status,production_id,workshop_id,issue_qty,stock_qty,outsource_qty,last_business_id,tower_no)
select ifnull(kanban_no,''),rfid_no,card_type,card_status,m.record_id as production_id,ws.record_id as workshop_id,issue_qty,stock_qty,outsource_qty,0,ifnull(tower_no,'')
from data_gather.rfid_card c0 join imms.zhxh_material m on c0.production_code = m.material_code
                              join imms.zhxh_org ws on c0.workshop_code = ws.org_code
;

--
-- 品质
--
truncate table imms.zhxh_defect;
insert into imms.zhxh_defect(defect_code,defect_name)
select defect_code,defect_name
from data_gather.defect
;

