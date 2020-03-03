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
-- 转换工位
--
alter table zhxh_org
    drop column workstation_type;

alter table zhxh_org
    add    can_report             bit                         not null default 0,
    add    can_move_in            bit                         not null default 0,
    add    can_issue_card         bit                         not null default 0,
    add    can_outsource_out      bit                         not null default 0,
    add    can_outsource_back     bit                         not null default 0
;

update zhxh_org
set   can_report  = 1,
      can_move_in  = 1,
      can_issue_card =1
where org_type = 'ORG_WORKSTATION';


update zhxh_org wk join zhxh_org ws on wk.parent_id = ws.record_id
set   wk.can_report  = 1,
      wk.can_move_in  = 1,
      wk.can_issue_card =1 ,
      wk.can_outsource_out = 1,
      wk.can_outsource_back = 1
where wk.org_type = 'ORG_WORKSTATION'
  and ws.workshop_type = 4


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

--
-- 转换存储区域
--

