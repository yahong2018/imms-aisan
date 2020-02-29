--
-- 看板
--
truncate imms.zhxh_rfid_card;
insert into imms.zhxh_rfid_card(kanban_no,rfid_no,card_type,card_status,production_id,workshop_id,issue_qty,stock_qty,outsource_qty,last_business_id,tower_no)
select ifnull(kanban_no,''),rfid_no,card_type,card_status,m.record_id as production_id,ws.record_id as workshop_id,issue_qty,stock_qty,ifnull(outsource_qty,0),0,ifnull(tower_no,'')
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

--
-- 报工、移库、品质记录不需要导入
/*
truncate table imms.zhxh_product_record;

insert into imms.zhxh_product_record(workshop_id,workstation_id,production_id,rfid_card_id,operator_id,gid,did,time_of_origin,time_of_origin_work,shift_id,rfid_card_no,card_qty,qty,report_type,remark)

select ws.record_id as workshop_id,
       wk.record_id as workstation_id,
       m.record_id as production_id,
       rfid.record_id as rfid_card_id,
       o.record_id as operator_id,
       p.rfid_controller_id,p.rfid_terminator_id,p.time_of_origin,p.time_of_origin_work,p.shift_id,p.rfid_card_no,p.card_qty,p.qty,p.report_type,p.remark
from data_gather.production_order_progress p left join imms.zhxh_org ws on p.workshop_code = ws.org_code
                                             left join imms.zhxh_org wk on p.workstation_code = wk.org_code
                                             left join imms.zhxh_material m on p.production_code = m.material_code
                                             left join imms.zhxh_rfid_card rfid on p.rfid_card_no = rfid.rfid_no
                                             left join imms.zhxh_operator o on p.employee_id = o.employee_id
where wk.record_id is not null
;
*/