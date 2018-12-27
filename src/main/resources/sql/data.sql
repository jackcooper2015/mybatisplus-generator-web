-- 密码加密方式sha1
insert ignore into db_config (id,db_name,url,driver,username,password,schema)
values(1,'aaa','jdbc:mysql://ip:port/test?serverTimezone=UTC','com.mysql.jdbc.Driver','root','root','test');
