create table zhxh_material
(
    record_id              bigint     auto_increment   not null,
    material_code          varchar(20)                 not null,
    material_name          varchar(50)                 not null,
    description            varchar(250)                null,

    auto_finished_progress int                         not null default 0,  -- 是否自动报工

    primary key(record_id)
);

create table zhxh_bom
(
    record_id             bigint    auto_increment     not null,
    bom_no                varchar(20)                  not null,
    bom_type              int                          not null,
    bom_status            int                          not null,   -- 0.生效    1.未启用   2.已作废

    material_id           bigint                       not null,
    component_id          bigint                       not null,

    material_qty          int                          not null,
    component_qty         int                          not null,

    effect_date           datetime                     not null,  -- 出现第二条相同记录的时候，要注意对以前的记录进行判断，将以前记录的状态改变，要做成任务，每天检查系统中的BOM

    primary key(record_id)
);

create table zhxh_material_stock
(
    record_id               bigint    auto_increment    not null,
    store_id                bigint                      not null,
    material_id             bigint                      not null,

    qty_stock               int                         not null,  -- 在库
    qty_move_in             int                         not null,  -- 转入（半成品） *
    qty_back_in             int                         not null,  -- 退回（从下部门退回到本部门）
    qty_back_out            int                         not null,  -- 退出（从本部门退回到上部门）
    qty_consume_good        int                         not null,  -- 良品消耗
    qty_consume_defect      int                         not null,  -- 不良消耗
    qty_good                int                         not null,  -- 良品数
    qty_defect              int                         not null,  -- 不良品数
    qty_move_out            int                         not null,  -- 转出（完成品） *

    primary key(record_id)
);



