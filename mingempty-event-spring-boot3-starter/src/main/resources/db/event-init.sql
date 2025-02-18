drop table if exists t_event_listener_record;

drop table if exists t_event_publisher_record;

create table if not exists t_event_publisher_record
(
    event_id        varchar(32)                           not null comment '事件ID'
    primary key,
    event_type      varchar(32)                           not null comment '事件类型',
    biz_no          varchar(32) default ''                not null comment '业务号码',
    event_data      json                                  not null comment '事件数据',
    event_class     varchar(120)                          not null comment '事件类',
    event_status    char(1)     default '0'               not null comment '事件是否已处理
0：初始化
1：已处理
2：已撤销
(同字典条目编码：event_status)',
    record_time     datetime    default CURRENT_TIMESTAMP not null comment '记录时间',
    completion_time datetime                              null comment '完成时间',
    trace_id        varchar(64)                           not null comment '链路ID',
    span_id         varchar(128)                          not null comment '跨度ID',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                           null comment '创建人',
    update_operator varchar(32)                           null comment '更新人'
    )
    comment '事件发布记录表';

create index i_epr_et_es_bn
    on t_event_publisher_record (event_type, event_status, biz_no);

create index i_epr_bn_et_es
    on t_event_publisher_record (biz_no, event_type, event_status);

create table if not exists t_event_listener_record
(
    event_listener_id    varchar(32)                           not null comment '事件监听ID'
    primary key,
    event_id             varchar(32)                           not null comment '事件ID',
    event_type           varchar(32)                           not null comment '事件类型',
    biz_no               varchar(32) default ''                not null comment '业务号码',
    event_listener_class varchar(120)                          not null comment '事件监听处理类',
    event_status         char(1)     default '0'               not null comment '事件是否已处理
0：初始化
1：已处理
2：已撤销
(同字典条目编码：event_status)',
    record_time          datetime    default CURRENT_TIMESTAMP not null comment '记录时间',
    completion_time      datetime                              null comment '完成时间',
    trace_id             varchar(64)                           not null comment '链路ID',
    span_id              varchar(128)                          not null comment '跨度ID',
    create_time          datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time          datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator      varchar(32)                           null comment '创建人',
    update_operator      varchar(32)                           null comment '更新人'
    )
    comment '事件监听记录表';

alter table t_event_listener_record
    add constraint ui_elr_ei_elc
        unique (event_id, event_listener_class);

create index i_elr_bn
    on t_event_listener_record (biz_no);