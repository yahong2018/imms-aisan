drop table if exists zhxh_system_user;
drop table if exists zhxh_system_role;
drop table if exists zhxh_system_role_user;
drop table if exists zhxh_trace_info;
drop table if exists zhxh_system_param;
drop table if exists zhxh_system_program;
drop table if exists zhxh_program_privilege;
drop table if exists zhxh_role_privilege;

create table zhxh_system_user
(
    record_id            bigint auto_increment not null,
    user_code            varchar(20)           not null,
    display_name         varchar(50)           not null,
    pwd                  varchar(40)           not null,
    is_online            bit                   not null,
    last_login_time      datetime              null,
    account_status       varchar(20)           not null,
    record_creation_type varchar(20)           not null,
    email                varchar(255)          not null,

    primary key (record_id)
);

create table zhxh_system_role
(
    record_id            bigint auto_increment not null,
    role_code            varchar(20)           not null,
    role_name            varchar(50)           not null,
    account_status       varchar(20)           not null,
    record_creation_type varchar(20)           not null,

    primary key (record_id)
);

create table zhxh_system_role_user
(
    role_id bigint not null,
    user_id bigint not null,

    primary key (role_id, user_id)
);

create table zhxh_trace_info
(
    record_id    bigint auto_increment not null,
    business_id  bigint                not null,
    operator_id  bigint                not null,
    operate_time datetime              not null,
    operate_type varchar(20)           not null,
    class_name   varchar(255)          not null,
    target       varchar(4000)         not null,

    primary key (record_id)
);

create table zhxh_system_param
(
    record_id            bigint auto_increment not null,
    param_type           varchar(20)           not null,
    param_code           varchar(50)           not null,
    param_description    varchar(200)          not null,
    param_value          varchar(200)          not null,
    record_creation_type varchar(20)           not null,

    primary key (record_id)
);

create table zhxh_system_program
(
    record_id      bigint auto_increment  not null,
    program_code   varchar(50)  not null,
    program_name   varchar(120) not null,
    url            varchar(255) not null,
    glyph          varchar(100) null,
    show_order     int          not null,
    parameters     varchar(255) not null,
    parent_id      bigint       not null,
    program_status varchar(20)  not null,

    primary key (record_id)
);

create table zhxh_program_privilege
(
    record_id      bigint auto_increment not null,
    program_id     bigint                not null,
    privilege_code varchar(50)           not null,
    privilege_name varchar(120)          not null,

    primary key (record_id)
);

create table zhxh_role_privilege
(
    record_id            bigint auto_increment not null,
    program_privilege_id bigint                not null,
    role_id              bigint                not null,

    primary key (record_id)
);

insert into zhxh_system_user(record_id, user_code, display_name, pwd, is_online, last_login_time, account_status, record_creation_type, email) values (1, 'c00001', '刘永红', '123456', b'0', null, 'NORMAL', 'CUSTOM', 'yahong@zhxh.com');
insert into zhxh_system_user(record_id, user_code, display_name, pwd, is_online, last_login_time, account_status, record_creation_type, email) values (2, 'c00002', '徐斯珍', '123456', b'0', null, 'NORMAL', 'CUSTOM', 'sizhen@zhxh.com');

insert into zhxh_system_role(record_id, role_code, role_name, account_status, record_creation_type) values (1, 'admin', '管理员', 'EXPIRED', 'BUILD_IN');
insert into zhxh_system_role(record_id, role_code, role_name, account_status, record_creation_type) values (2, 'test', '测试', 'NORMAL', 'BUILD_IN');

insert into zhxh_system_role_user(role_id, user_id) values (1, 1);
insert into zhxh_system_role_user(role_id, user_id) values (2, 1);

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS01', '系统管理', '', '0xf013', 0, '', 0, 'NORMAL');
set @parentId = LAST_INSERT_ID ();
set @programId = @parentId;
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'RUN', '系统运行');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ( 'SYS01_01', '用户管理', 'app.view.admin.systemUser.SystemUser', '0xf007', 0, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'RUN', '系统运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'DELETE', '删除');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'STOP_USER', '暂停账户');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'START_USER', '启用账户');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'RESET_PASSWORD', '重设密码');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'ASSIGN_ROLE', '授权');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS01_02', '角色管理', 'app.view.admin.systemRole.SystemRole', '0xf233', 1, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '系统运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'ASSIGN_ROLE', '授权');

set @parentId = 1;
insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS01_03', '系统参数', 'app.view.admin.systemParam.SystemParam', '0xf085', 2, '',@parentId,  'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '系统运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'SYNC_WITH_ERP_WDB', '与万达宝同步');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');


insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS02', '组织架构', '', '0xf0e8', 1, '', 0, 'NORMAL');
set @parentId = LAST_INSERT_ID ();
set @programId = @parentId;
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS02_01', '车间与工位', 'app.view.imms.org.Organization', '0xf1ad', 0, '',@parentId , 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS02_02', '操作员管理', 'app.view.imms.org.operator.Operator', '0xf2be', 2, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03', '生产管理', '', '0xf0ae', 2, '', 0, 'NORMAL');
set @parentId = LAST_INSERT_ID ();
set @programId = @parentId;
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values (@programId, 'RUN', '运行');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_01', '产品管理', 'app.view.imms.material.material.Material', '0xf1d0', 1, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_02', '看板发卡', 'app.view.imms.mfc.rfidCard.RfidCard', '0xf2c3', 2, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'ExcelImport', '导入看板');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_03', '品质代码', 'app.view.imms.mfc.defectCode.DefectCode', '0xf02a', 3, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_04', '生产计划', 'app.view.imms.mfc.productionOrder.ProductionOrder', '0xf03a', 4, '', @parentId, 'STOPPED');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'UPDATE', '修改');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'DELETE', '删除');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_05', '生产报工', 'app.view.imms.mfc.productRecord.ProductRecord', '0xf0cb', 5, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_06', '生产移库', 'app.view.imms.mfc.productionMoving.ProductionMoving', '0xf218', 6, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS03_07', '生产品质', 'app.view.imms.mfc.qualityCheck.QualityCheck', '0xf00e', 7, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'INSERT', '新增');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'TO_ERP', '同步给ERP');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'BATCH_INSERT', '批量新增');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS04', '分析报表', '', '0xf02f', 3, '', 0, 'NORMAL');
set @parentId = LAST_INSERT_ID ();
set @programId = @parentId;
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS04_01', '每日生产进度表', 'app.view.imms.rpt.rptProductionOrderProgress.RptProductionOrderProgress', '0xf0ae', 1, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'PRINT', '打印');

insert into zhxh_system_program( program_code, program_name, url, glyph, show_order, parameters, parent_id, program_status) values ('SYS04_02', '生产现场库存表', 'app.view.imms.rpt.rptWip.RptWip', '0xf200', 2, '', @parentId, 'NORMAL');
set @programId = LAST_INSERT_ID ();
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'RUN', '运行');
insert into zhxh_program_privilege( program_id, privilege_code, privilege_name) values ( @programId, 'PRINT', '打印');

insert into zhxh_role_privilege (role_id, program_privilege_id)
select
    1,
    prv.record_id
from zhxh_program_privilege prv
where record_id not in (
    select program_privilege_id  from zhxh_role_privilege
);


insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('A001','LOG_LEVEL','日志级别','5','CUSTOM');  -- 5. Debug  4. Info  3. Warn  2. Error 1 Fatal
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','grant_type','grant_type','password','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','client_id','client_id','ZWUwN2E0NmItYjUzMC00YjRlLTlkYWMtZTY3YmExMDdkYzE0','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','client_secret','client_secret','YzUzMzMzOWUtZTk2MS00MDNiLTg4NjMtM2E3ZjU1OGMxZjk2','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','username','username','19101001','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','password','password','c8b3c62c7edb60d3c4c3ca488a9d81c564eb2329','CUSTOM');

insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','server_host','服务器','http://202.105.31.227','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','login_url','登录验证地址','jsf/rfws/oauth/token','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','progress_report_url','报工数据同步地址','jsf/rfws/erp/aisa/save/prodPw','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','moving_report_url','移库数据同步地址','jsf/rfws/erp/aisa/save/move','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','qualitycheck_report_url','品质数据同步地址','','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','bom_get_url','BOM查询地址','jsf/rfws/erp/aisa/common/getBomInfo','CUSTOM');

insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','account_id','账套Id','63','CUSTOM');
insert into zhxh_system_param(param_type,param_code,param_description,param_value,record_creation_type) values('B003','sync_cycle_minutes','自动同步间隔（分钟）','-1','CUSTOM');


create table zhxh_sync_data
(
    record_id           bigint          auto_increment      not null,
    business_id         bigint                              not null,
    class_type          varchar(200)                        not null,
    create_time         datetime                            not null,

    primary key (record_id)
);