-- 密码加密方式sha1
insert ignore into db_config (id,db_name,url,driver,username,password,schema)
values(1,'test','jdbc:mysql://test/test?serverTimezone=UTC','com.mysql.jdbc.Driver','test','test','test');
