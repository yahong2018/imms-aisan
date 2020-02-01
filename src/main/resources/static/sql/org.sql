create table zhxh_org
(
    record_id              bigint    auto_increment    not null,
    org_code               varchar(50)                 not null,
    org_name               varchar(50)                 not null,
    org_type               varchar(50)                 not null,
    description            varchar(250)                null,

    parent_id              bigint                      not null,

    op_index               int                         not null default 0,   -- 对于车间来说，工序编号必须唯一
    prev_index             int                         not null default 0,
    workshop_type          int                         not null default 0,   -- 车间类型: 1. 内部车间   3.外发前工程车间   4.外发车间   5.外发后工程车间

    gid                    int                         not null default 0,
    did                    int                         not null default 0,
    did_template           int                         not null default 8,  --  工位机的缺省显示模板，默认为8，如果是手持机，则要设为4

    wocg_code              varchar(20)                 null,     -- 工作中心编码
    auto_report_count      int                         not null default 0,  -- 自动报工的工序个数

    loc_code               varchar(20)                 null,    -- 储位编码
    workstation_type       int                         not null default 0,  -- 工位类别:0.

    primary key(record_id)
);

create table zhxh_operator
(
    record_id              bigint    auto_increment    not null,
    employee_id            varchar(20)                 not null,
    employee_name          varchar(50)                 not null,
    employee_card_no       varchar(20)                 not null,
    org_id                 bigint                      null,

    primary key(record_id)
);

create table zhxh_operator_workshop
(
    operator_id            bigint                      not null,
    workshop_id            bigint                      not null,

    primary key(operator_id,workshop_id)
);
