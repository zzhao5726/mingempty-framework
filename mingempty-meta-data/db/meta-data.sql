create schema if not exists meta_data collate utf8mb4_0900_ai_ci;

use meta_data;

drop table if exists t_meta_data_change_entry;

drop table if exists t_meta_data_entry;

drop table if exists t_meta_data_change_authorization;

drop table if exists t_meta_data_authorization;

drop table if exists t_meta_data_change_extra_field;

drop table if exists t_meta_data_extra_field;

drop table if exists t_meta_data_change_item;

drop table if exists t_meta_data_item;

drop table if exists t_meta_data_change_label;

drop table if exists t_meta_data_label;

drop table if exists t_meta_data_operation_history;

create table if not exists t_meta_data_change_entry
(
    entry_id        bigint unsigned                                  not null comment '条目ID'
    primary key,
    entry_code      varchar(64)                                      not null comment '条目编号',
    entry_version   int unsigned           default 1                 not null comment '条目版本（默认1）',
    entry_name      varchar(200)                                     not null comment '条目名称',
    entry_type      char                                             not null comment '条目类型
1：普通字典
2：树形字典
(同字典条目编码：dict_entry_type)',
    entry_sharding  char                   default '0'               not null comment '是否分表
0：否
1：是
(同字典条目编码：zero_or_one)',
    sort            double(10, 2) unsigned default 0.00              not null comment '条目排序（默认0）',
    delete_status   char                   default '0'               not null comment '是否已逻辑删除
0：否
1：是
(同字典条目：zero_or_one)',
    delete_time     datetime                                         null comment '删除时间',
    delete_operator varchar(32)                                      null comment '删除人',
    create_time     datetime               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                                      null comment '创建人',
    update_operator varchar(32)                                      null comment '更新人',
    constraint ui_ce_ec_ev
    unique (entry_code, entry_version)
    )
    comment '字典条目变化流水表';

create index i_ce_ev_ec
    on t_meta_data_change_entry (entry_version, entry_code);

create table if not exists t_meta_data_entry
(
    entry_id        bigint unsigned                                  not null comment '条目ID'
    primary key,
    entry_code      varchar(64)                                      not null comment '条目编号',
    entry_name      varchar(200)                                     not null comment '条目名称',
    entry_type      char                                             not null comment '条目类型
1：普通字典
2：树形字典
(同字典条目编码：dict_entry_type)',
    entry_sharding  char                   default '0'               not null comment '是否分表
0：否
1：是
(同字典条目编码：zero_or_one)',
    sort            double(10, 2) unsigned default 0.00              not null comment '条目排序（默认0）',
    delete_status   char                   default '0'               not null comment '是否已逻辑删除
0：否
1：是
(同字典条目：zero_or_one)',
    delete_time     datetime                                         null comment '删除时间',
    delete_operator varchar(32)                                      null comment '删除人',
    create_time     datetime               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                                      null comment '创建人',
    update_operator varchar(32)                                      null comment '更新人',
    constraint ui_e_ec
    unique (entry_code)
    )
    comment '字典条目表';

create table if not exists t_meta_data_change_authorization
(
    authorization_id   bigint unsigned                        not null comment '条目授权ID'
    primary key,
    entry_code         varchar(64)                            not null comment '条目编号',
    entry_version      int unsigned default 1                 not null comment '条目版本（默认1）',
    authorization_type varchar(64)  default '1'               not null comment '授权类型
1：角色编码
2：用户编码
(含义同条目：entry_authorization_type)',
    authorization_code varchar(64)                            not null comment '授权编码',
    delete_status      char         default '1'               not null comment '是否已逻辑删除
0：否
1：是
(同字典条目：zero_or_one)',
    delete_time        datetime                               null comment '删除时间',
    delete_operator    varchar(32)                            null comment '删除人',
    create_time        datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator    varchar(32)                            null comment '创建人',
    update_operator    varchar(32)                            null comment '更新人',
    constraint ui_ca_ec_lc_ic_ev
    unique (entry_code, authorization_type, authorization_code, entry_version)
    )
    comment '条目授权变化流水表';

create index i_ca_ev_ec
    on t_meta_data_change_authorization (entry_version, entry_code);


create table if not exists t_meta_data_authorization
(
    authorization_id   bigint unsigned                       not null comment '条目授权ID'
    primary key,
    entry_code         varchar(64)                           not null comment '条目编号',
    authorization_type varchar(64) default '1'               not null comment '授权类型
1：角色编码
2：用户编码
(含义同条目：entry_authorization_type)',
    authorization_code varchar(64)                           not null comment '授权编码',
    create_time        datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator    varchar(32)                           null comment '创建人',
    update_operator    varchar(32)                           null comment '更新人',
    constraint ui_a_ec_lc_ic
    unique (entry_code, authorization_type, authorization_code)
    )
    comment '条目授权表';

create table if not exists t_meta_data_change_extra_field
(
    extra_field_id   bigint unsigned                                  not null comment '条目扩展字段关系ID'
    primary key,
    entry_code       varchar(64)                                      not null comment '条目编号',
    entry_version    int unsigned           default 1                 not null comment '条目版本（默认1）',
    extra_field_name varchar(32)                                      not null comment '扩展字段名称',
    extra_field_code varchar(128)                                     not null comment '扩展字段编码',
    other_dict_flag  char                   default '0'               not null comment '是否为其余字典项
0：否
1：是
(同字典条目编码：zero_or_one)',
    other_entry_code varchar(128)                                     null comment '其余字典项条目编号
(同字典条目编码：entry_code)',
    extra_field_sort double(10, 2) unsigned default 0.00              not null comment '扩展字段排序（默认0）',
    create_time      datetime               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator  varchar(32)                                      null comment '创建人',
    update_operator  varchar(32)                                      null comment '更新人',
    constraint ui_cef_ec_erc_ev
    unique (entry_code, extra_field_code, entry_version)
    )
    comment '字典扩展字段信息变化流水表';

create index i_cef_ev_ec
    on t_meta_data_change_extra_field (entry_version, entry_code);

create table if not exists t_meta_data_extra_field
(
    extra_field_id   bigint unsigned                                  not null comment '条目扩展字段关系ID'
    primary key,
    entry_code       varchar(64)                                      not null comment '条目编号',
    extra_field_name varchar(32)                                      not null comment '扩展字段名称',
    extra_field_code varchar(128)                                     not null comment '扩展字段编码',
    other_dict_flag  char                   default '0'               not null comment '是否为其余字典项
0：否
1：是
(同字典条目编码：zero_or_one)',
    other_entry_code varchar(128)                                     null comment '其余字典项条目编号
(同字典条目编码：entry_code)',
    extra_field_sort double(10, 2) unsigned default 0.00              not null comment '扩展字段排序（默认0）',
    create_time      datetime               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator  varchar(32)                                      null comment '创建人',
    update_operator  varchar(32)                                      null comment '更新人',
    constraint ui_ef_ec_erc
    unique (entry_code, extra_field_code)
    )
    comment '字典扩展字段信息表';

create table if not exists t_meta_data_change_item
(
    item_id          bigint unsigned                                  not null comment '字典ID'
    primary key,
    entry_code       varchar(64)                                      not null comment '条目编号',
    entry_version    int unsigned           default 1                 not null comment '条目版本（默认1）',
    item_parent_code varchar(64)            default '#'               not null comment '字典父编号',
    item_code        varchar(64)                                      not null comment '字典项编号',
    item_name        varchar(128)                                     not null comment '字典项名称',
    item_sort        double(10, 2) unsigned default 0.00              not null comment '字典排序（默认0）',
    item_level       int unsigned           default 1                 not null comment '字典层级（默认1）',
    item_extra_field json                                             null comment '扩展字段
(以json格式进行存储)',
    delete_status    char                   default '1'               not null comment '是否已逻辑删除
0：否
1：是
(同字典条目：zero_or_one)',
    delete_time      datetime                                         null comment '删除时间',
    delete_operator  varchar(32)                                      null comment '删除人',
    create_time      datetime               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator  varchar(32)                                      null comment '创建人',
    update_operator  varchar(32)                                      null comment '更新人',
    constraint ui_ci_ec_ic_ev
    unique (entry_code, item_code, entry_version),
    constraint ui_ci_ec_ipc_ic_ev
    unique (entry_code, item_parent_code, item_code, entry_version)
    )
    comment '字典项变化流水表';

create index i_ci_ev_ec
    on t_meta_data_change_item (entry_version, entry_code);

create table if not exists t_meta_data_item
(
    item_id          bigint unsigned                                  not null comment '字典ID'
    primary key,
    entry_code       varchar(64)                                      not null comment '条目编号',
    item_parent_code varchar(64)            default '#'               not null comment '字典父编号',
    item_code        varchar(64)                                      not null comment '字典项编号',
    item_name        varchar(128)                                     not null comment '字典项名称',
    item_sort        double(10, 2) unsigned default 0.00              not null comment '字典排序（默认0）',
    item_level       int unsigned           default 1                 not null comment '字典层级（默认1）',
    item_extra_field json                                             null comment '扩展字段
(以json格式进行存储)',
    delete_status    char                   default '1'               not null comment '是否已逻辑删除
0：否
1：是
(同字典条目：zero_or_one)',
    delete_time      datetime                                         null comment '删除时间',
    delete_operator  varchar(32)                                      null comment '删除人',
    create_time      datetime               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator  varchar(32)                                      null comment '创建人',
    update_operator  varchar(32)                                      null comment '更新人',
    constraint ui_i_ec_ic
    unique (entry_code, item_code),
    constraint ui_i_ec_ipc_ic
    unique (entry_code, item_parent_code, item_code)
    )
    comment '字典项表';

create table if not exists t_meta_data_change_label
(
    label_id        bigint unsigned                        not null comment '字典项标签ID'
    primary key,
    entry_code      varchar(64)                            not null comment '条目编号',
    entry_version   int unsigned default 1                 not null comment '条目版本（默认1）',
    label_code      varchar(64)  default '#'               not null comment '标签编号
(含义同条目：dict_label)',
    item_code       varchar(64)                            not null comment '字典项编号',
    delete_status   char         default '1'               not null comment '是否已逻辑删除
0：否
1：是
(同字典条目：zero_or_one)',
    delete_time     datetime                               null comment '删除时间',
    delete_operator varchar(32)                            null comment '删除人',
    create_time     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                            null comment '创建人',
    update_operator varchar(32)                            null comment '更新人',
    constraint ui_cl_ec_lc_ic_ev
    unique (entry_code, label_code, item_code, entry_version)
    )
    comment '字典项标签变化流水表';

create index i_cl_ec_ic_lc_ev
    on t_meta_data_change_label (entry_code, item_code, label_code, entry_version);

create index i_cl_ev_ec
    on t_meta_data_change_label (entry_version, entry_code);

create table if not exists t_meta_data_label
(
    label_id        bigint unsigned                    not null comment '字典项标签ID'
    primary key,
    entry_code      varchar(64)                        not null comment '条目编号',
    label_code      varchar(64)                        not null comment '标签编号
(含义同条目：dict_label)',
    item_code       varchar(64)                        not null comment '字典项编号',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator varchar(32)                        null comment '创建人',
    update_operator varchar(32)                        null comment '更新人',
    constraint ui_l_ec_lc_ic
    unique (entry_code, label_code, item_code)
    )
    comment '字典项标签表';

create index i_l_ec_ic_lc
    on t_meta_data_label (entry_code, item_code, label_code);

create table if not exists t_meta_data_operation_history
(
    operation_history_id bigint unsigned                        not null comment '操作历史ID'
    primary key,
    entry_code           varchar(64)                            not null comment '条目编号',
    operation_type       char(2)                                not null comment '操作类型
01.条目-新增
02.条目-修改
03.条目-逻辑删除
04.条目权限-新增
05.条目权限-逻辑删除
06.扩展字段-新增
07.扩展字段-修改
08.字典项-新增
09.字典项-修改
10.字典项-逻辑删除
11.导入-excel
12.导入-zip
(同字典条目编码：dict_operation_type)',
    entry_version        int unsigned default 1                 not null comment '条目版本（默认1）',
    operator_code        varchar(32)  default '1'               not null comment '操作人',
    operation_time       datetime     default CURRENT_TIMESTAMP not null comment '操作时间',
    batch_id             bigint unsigned                        null comment '批次ID
(使用Excel或Zip导入时有值)',
    create_time          datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time          datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_operator      varchar(32)                            null comment '创建人',
    update_operator      varchar(32)                            null comment '更新人',
    constraint ui_oh_ec_ev
    unique (entry_code, entry_version)
    )
    comment '字典操作历史表';

create index i_oh_ec_ot
    on t_meta_data_operation_history (entry_code, operation_type);
