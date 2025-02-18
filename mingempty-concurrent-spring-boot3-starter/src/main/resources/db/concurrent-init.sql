drop table if exists t_thread_task_record;
create table if not exists t_thread_task_record
(
    task_id         varchar(32)                           not null comment '线程任务ID'
    primary key,
    pool_name       varchar(32)                           not null comment '线程池名称',
    task_class      varchar(120)                          not null comment '提交任务类',
    thread_data     json                                  not null comment '线程数据',
    task_status     char(1)     default '0'               not null comment '任务状态
0：初始化
1：已处理
2：已撤销
(同字典条目编码：thread_status)',
    record_time     datetime    default CURRENT_TIMESTAMP not null comment '记录时间',
    completion_time datetime                              null comment '完成时间',
    trace_id        varchar(64) default ''                not null comment '链路ID',
    span_id         varchar(64) default ''                not null comment 'spanID',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                           null comment '创建人',
    update_operator varchar(32)                           null comment '更新人'
    )
    comment '线程池任务记录表';

create index i_ttr_tc
    on t_thread_task_record (task_class);

create index i_ttr_ti
    on t_thread_task_record (trace_id);