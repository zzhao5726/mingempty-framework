drop table if exists t_mq_record;
create table if not exists t_mq_record
(
    mq_id           varchar(32)                           not null comment 'ID'
    primary key,
    message_id      varchar(32)                           not null comment '消息ID',
    record_type     char(1)     default '0'               not null comment '消息记录类型
0：发送者
1：消费者',
    component_name  varchar(32)                           not null comment '绑定元件名称',
    bean_name       varchar(32) default ''                not null comment '绑定元件名称',
    message_payload json                                  not null comment 'mq消息数据',
    message_headers json                                  not null comment 'mq消息头数据',
    message_status  char(1)     default '0'               not null comment '任务状态
0：初始化
1：已处理
2：已撤销
(同字典条目编码：message_status)',
    record_time     datetime    default CURRENT_TIMESTAMP not null comment '记录时间',
    completion_time datetime                              null comment '完成时间',
    trace_id        varchar(64) default ''                not null comment '链路ID',
    span_id         varchar(64) default ''                not null comment 'spanID',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                           null comment '创建人',
    update_operator varchar(32)                           null comment '更新人'
    )
    comment 'MQ消息记录表';

create index i_mr_rt_cn
    on t_mq_record (record_type, component_name);

create index i_mr_bn
    on t_mq_record (bean_name);