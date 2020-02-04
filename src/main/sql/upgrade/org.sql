--
-- 转换组织结构
--
truncate table imms.zhxh_org;
insert into imms.zhxh_org(org_code,org_name,org_type,description,parent_id,op_index,prev_index,workshop_type,gid,did,did_template,wocg_code,loc_code)
select org_code,
       org_name,
       case org_type when 'ORG_WORK_SHOP' then 'ORG_WORKSHOP' when 'ORG_WORK_STATION' then 'ORG_WORKSTATION' end,
       description,
       parent_id,
       operation_index as op_index,
       prev_operation_index as prev_index,
       workshop_type,
       rfid_controller_id as gid,
       rfid_terminator_id as did,
       rfid_template_index as did_template,
       wocg_code,
       loc_code
from data_gather.work_organization_unit;

update data_gather.work_organization_unit p join imms.zhxh_org o on p.record_id = o.parent_id
    join imms.zhxh_org pp on p.org_code = pp.org_code
set o.parent_id = pp.record_id
where o.parent_id >0;

update imms.zhxh_org
set parent_id = 0
where parent_id < 0;

--
-- 转换操作人员
--
truncate imms.zhxh_operator;
insert into imms.zhxh_operator(employee_id,employee_name,employee_card_no)
select employee_id,employee_name,employee_card_no
from data_gather.operator;

--
-- 人员所属部门:不需要转换，目前没有
--
