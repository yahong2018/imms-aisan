create table zhxh_kocheer_station_info  (
  record_id                     bigint          auto_increment     not null,
  station_code                  varchar(30)                        not null,
  station_name                  varchar(50)                        not null,
  station_position              varchar(100)                       not null,
  software_name                 varchar(30)                        not null,
  software_version              varchar(30)                        not null,
  station_ip                    varchar(20)                        not null,
  station_login_state           int                                not null,
  login_user_id                 int                                not null,
  first_login_time              datetime                           null,
  last_login_time               datetime                           null,
  last_logout_time              datetime                           null,
  last_state_update_time        datetime                           null,
  is_use                        int                                not null,

  primary key (record_id)
);

create table zhxh_kocheer_conter_info  (
   record_id                   bigint          auto_increment      not null ,
   station_id                  bigint                              not null,
   gid                         int                                 not null,
   conter_name                 varchar(20)                         not null,
   start_did                   int                                 not null,
   end_did                     int                                 not null,
   ip                          varchar(20)                         not null,
   port                        int                                 not null,
   position                    varchar(50)                         not null,
   is_use                      int                                 not null,
   wiress_power                int                                 not null default 3,
   remark                      varchar(50)                         null,
   primary key (record_id)
);

create table zhxh_kocheer_device_raw_data
(
    record_id                bigint          auto_increment       not null,
    station_id               bigint                               not null,
    gid                      int                                  not null,
    did                      int                                  not null,
    is_new_data              int                                  not null,
    data_content             varchar(2000)                        not null,
    create_date              datetime                             not null default Now(),

    primary key (record_id)
);


create table zhxh_workstation_session
(
    record_id              bigint      auto_increment      not null,
    workstation_id         bigint                          not null,
    session_type           int                             not null,
    current_step           int                             not null, -- 当前步骤：-1.新建 0.确定session_type  1~250. 当前步骤   253.已过期  254.已取消   255.已完成
    operator_id            bigint                          null,     -- 操作员Id
    gid                    int                             not null,
    did                    int                             not null,

    create_time            datetime                        not null,
    last_process_time      datetime                        not null,   -- 最后处理时间

    primary key(record_Id)
);


create table zhxh_workstation_session_step
(
    record_id                    bigint       auto_increment     not null,
    workstation_session_id       bigint                          not null,
    step                         int                             not null,
    req_time                     datetime                        not null,
    req_data_type                int                             not null, -- 1. 工卡    2.数量卡   3.委外加工卡    4.键盘输入
    req_data                     varchar(20)                     not null, -- 请求的数据
    resp_data                    varchar(2000)                   not null, -- 从服务器返回的结果
    resp_time                    datetime                        not null, -- 返回时间

    primary key(record_id)
);







