create table zhxh_rfid_card
(
    record_id              bigint  auto_increment      not null,

    kanban_no              varchar(20)                 not null,
    rfid_no                varchar(20)                 not null,
    card_type              int                         not null,       --  卡类别: 1.员工卡     2.数量卡    3.委外加工卡
    card_status            int                         not null,       --  0. 未使用   1. 已派发   2.已退回   3.已绑定   10. 已报工   20. 已移库收货(已外发)   30.已回厂     255.已作废

    production_id          bigint                      not null,       -- （工艺数量卡）所代表的产品
    workshop_id            bigint                      not null,       -- （工艺数量卡）所代表的工艺
    issue_qty              int                         not null,       -- 派发数量
    stock_qty              int                         not null,       -- 库存数量
    outsource_qty          int                         not null,       -- 外发数量
    last_business_id       bigint                      not null,       -- 最后一笔绑定的业务单据

    tower_no               varchar(20)                 not null default '',  -- 塔的号码

    PRIMARY KEY(record_id)
);

create table zhxh_product_summary
(
    record_id             bigint          auto_increment     not null,
    product_date          datetime                           not null,

    workshop_id           bigint                             not null,
    production_id         bigint                             not null,

    qty_good_0            int                                not null,
    qty_defect_0          int                                not null,

    qty_good_1            int                                not null,
    qty_defect_1          int                                not null,

    primary key(record_id)
);


create table zhxh_product_record
(
    record_id              bigint   auto_increment   not null,

    workshop_id            bigint                    not null, -- 车间Id
    workstation_id         bigint                    not null, -- 工位Id
    production_id          bigint                    not null, -- 产品编号

    gid                    int                       null, -- 机器号
    did                    int                       null, -- 组号

    time_of_origin         datetime                  not null, -- 报告时间: 日历日
    time_of_origin_work    datetime                  not null, -- 报告时间: 工作日
    shift_id               int                       not null, -- 班次: 0.白班(08:30:00 ~ 19:29:59)  1.夜班(20:00:00 ~ 08:29:59)

    rfid_card_id           bigint                    null, -- RFID卡记录号
    rfid_card_no           varchar(20)               null, -- RFID卡号
    card_qty               int                       null, -- 卡的派发数量

    qty                    int                       not null, -- 报工数量
    operator_id            bigint                    null,

    report_type            int                       not null, -- 报工类型
    remark                 varchar(250)              null,

    primary key(record_id)
);

create table zhxh_production_moving
(
    record_id                  bigint                    auto_increment    not null,

    production_id              bigint                    not null,
    rfid_no                    varchar(20)               null,
    rfid_card_id               bigint                    null,

    gid                        int                       null,
    did                        int                       null,
    operator_id                bigint                    null,
    operator_id_from           bigint                    null,
    qty                        int                       not null,

    time_of_origin             datetime                  not null,
    time_of_origin_work        datetime                  not null, -- 报告时间: 工作日
    shift_id                   int                       not null, -- 班次: 0.白班(08:30:00 ~ 19:29:59)  1.夜班(20:00:00 ~ 08:29:59)

    workshop_id_from           int                       not null,
    workstation_id             int                       null,
    workshop_id                bigint                    not null,

    PRIMARY KEY(record_id)
);

create table zhxh_defect(
   record_id                   bigint     auto_increment      not null,
   defect_code                 varchar(20)                    not null,
   defect_name                 varchar(50)                    not null,

   primary key(record_id)
);


create table zhxh_quality_check
(
    record_id                      bigint     auto_increment   not null,
    production_id                  bigint                      not null,
    workshop_id                    bigint                      not null,
    wocg_code                      varchar(20)                 not null,   -- 工作中心组
    loc_code                       varchar(20)                 not null,

    defect_id                      long                        not null,
    time_of_origin_work            datetime                    not null, -- 报告时间: 工作日
    shift_id                       int                         not null, -- 班次: 0.白班(08:30:00 ~ 19:29:59)  1.夜班(20:00:00 ~ 08:29:59)

    qty                            int                         not null,

    PRIMARY KEY(record_id)
);


create table zhxh_workstation_bind
(
    record_id               bigint       auto_increment   not null,
    outsource_card_id       bigint                        not null,
    workstation_id          bigint                        not null,
    workshop_id             bigint                        not null,
    attach_time             datetime                      not null,       -- 绑定时间
    out_time                datetime                      null,           -- 外发时间
    back_time               datetime                      null,           -- 回厂时间
    bind_status             int                           not null,       -- 3.已绑定   4.已外发

    primary key(record_id)
);

create table zhxh_card_bind(
     record_id               bigint       auto_increment   not null,
     outsource_card_id       bigint                        not null,       -- 外发卡
     qty_report_id           bigint                        not null,       -- 报工记录号
     qty_card_id             bigint                        not null,       -- 数量卡
     attach_time             datetime                      not null,       -- 绑定时间
     workstation_bind_id     bigint                        not null,

     primary key(record_id)
);

