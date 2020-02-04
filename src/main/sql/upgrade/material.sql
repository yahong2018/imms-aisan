--
-- 物料
--
truncate table imms.zhxh_material;
insert into imms.zhxh_material(material_code,material_name,description,auto_finished_progress)
select material_code,material_name,description,auto_finished_progress
from data_gather.material;

--
-- BOM
--
truncate table imms.zhxh_bom;
insert into imms.zhxh_bom(bom_no,bom_type,bom_status,material_id,component_id,material_qty,component_qty,effect_date)
select bom_no,bom_type,bom_status,
       m.record_id as material_id,
       c.record_Id as component_id,
       material_qty,component_qty,effect_date
from data_gather.bom b join imms.zhxh_material m on b.material_code = m.material_code
                       join imms.zhxh_material c on b.component_code = c.material_code
;

--
-- Material Stock
--
truncate table imms.zhxh_material_stock;
insert into imms.zhxh_material_stock(store_id,material_id,qty_stock,qty_move_in,qty_back_in,qty_back_out,qty_consume_good,qty_consume_defect,qty_good,qty_defect,qty_move_out)
select m.record_id as material_id, ws.record_id as store_id,
       st0.qty_stock,st0.qty_move_in,st0.qty_back_in,st0.qty_back_out,st0.qty_consume_good,
       st0.qty_consume_defect,st0.qty_good,st0.qty_defect,st0.qty_move_out
from data_gather.material_stock st0 join imms.zhxh_material m on st0.material_code = m.material_code
                                    join imms.zhxh_org ws on st0.store_code = ws.org_code
;



