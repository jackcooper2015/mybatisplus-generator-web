-- drop table if exists `db_config`;
create table if not exists `db_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `db_name` varchar(30) COMMENT '名字',
  `url` varchar(255) COMMENT 'url',
  `driver` varchar(150) COMMENT 'driver',
  `username` varchar(255) COMMENT '用户名',
  `password` varchar(255) COMMENT '密码',
  `schema` varchar(255),
  `catalog` varchar(255),
  `db_type` varchar(255),
  PRIMARY KEY (`id`)
) engine=innodb default charset=utf8;

-- drop table if exists `table_strategy_config`;
create table if not exists `table_strategy_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `db_id` int(11) COMMENT '数据库id',
  `table_name` varchar(255) COMMENT '数据库表名',
  `prefix` varchar(255),
  `model_name` varchar(255),
  `author` varchar(255),
  `entity_name` varchar(255),
  `mapper_name` varchar(255),
  `xml_name` varchar(255),
  `service_name` varchar(255),
  `service_impl_name` varchar(255),
  `controller_name` varchar(255),
  `entity_package` varchar(255),
  `service_package` varchar(255),
  `service_impl_package` varchar(255),
  `mapper_package` varchar(255),
  `controller_package` varchar(255),
  `template_set_id` int(11),
  PRIMARY KEY (`id`)
)  engine=innodb default charset=utf8;

--drop table if exists `template_set`;
create table if not exists `template_set`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `set_name` varchar(255) COMMENT '组名',
  `desc` varchar(255) comment '描述',
  `create_user` varchar(255) comment '创建人',
  PRIMARY KEY (`id`)
)  engine=innodb default charset=utf8;


-- drop table if exists `template`;
create table if not exists `template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `set_id`  int(11) NOT NULL comment '组id',
  `template_name` varchar(255) COMMENT '模板文件名',
  `content`  varchar(409600) comment '模板类容',
  `create_user` varchar(255) comment '创建人',
  `remark` varchar(255) comment '备注',
  PRIMARY KEY (`id`)
)  engine=innodb default charset=utf8;
