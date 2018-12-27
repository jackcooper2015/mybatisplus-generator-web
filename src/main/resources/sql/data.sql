-- 密码加密方式sha1
insert ignore into db_config (id,db_name,url,driver,username,password,schema)
values(1,'chjmall_op','jdbc:mysql://devdb-mysql-dev.chj.cloud:30111/chjmall_op?serverTimezone=UTC','com.mysql.jdbc.Driver','amos_dbuser','amos_dbuser','chjmall_op');