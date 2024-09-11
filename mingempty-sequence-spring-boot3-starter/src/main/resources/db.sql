create table if not exists t_sequence_generated
(
    biz_tag     varchar(128)                        not null comment '业务key'
    primary key,
    max_id      bigint    default 0                 not null comment '当前已经分配了的最大id',
    step        int       default 50                not null comment '步长',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '序列生成';