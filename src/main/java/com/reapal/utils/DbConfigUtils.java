package com.reapal.utils;

import com.reapal.model.DbConfig;

import java.io.*;
import java.util.*;

/**
 * Created by jack-cooper on 2017/1/20.
 */
public class DbConfigUtils {

    private static Properties properties ;
    private static File file  = new File("database.properties");

    static{
        if(!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DbConfigUtils(){
        properties = new Properties();
        System.out.println("database.properties路径==================> " + file.getAbsolutePath());
    }

    /**
     * 初始化
     * @param file
     * @throws IOException
     */
    public void init(File file) throws IOException {
        InputStream fis = new FileInputStream(file);
        properties.load(fis);
    }

    /**
     * 添加配置信息
     * @param dbConfig
     * @return
     */
    public boolean addDbconfig(DbConfig dbConfig) {
        try {
            this.init(this.file);
            boolean b = properties.containsKey(dbConfig.getDbName());
            if (b) {
                //修改
                return modifyDbconfig(dbConfig);
            }
            properties.put(dbConfig.getDbName(),dbConfig.getUrl()+"|"+ dbConfig.getDriver() + "|" + dbConfig.getUsername() + "|" + dbConfig.getPassword() + "|" + dbConfig.getSchema());
            properties.store(new FileOutputStream(file), new Date().toString());
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改配置信息
     * @param dbConfig
     * @return
     */
    public boolean modifyDbconfig(DbConfig dbConfig) throws IOException {
        this.init(file);
        boolean b = properties.containsKey(dbConfig.getDbName());
        if(b){
            properties.setProperty(dbConfig.getDbName(),dbConfig.getUrl()+"|"+dbConfig.getDriver()+"|"+dbConfig.getUsername()+"|"+dbConfig.getPassword()+"|"+dbConfig.getSchema());
            properties.store(new FileOutputStream(file),new Date().toString());
            return true;
        }else {
            return addDbconfig(dbConfig);
        }
    }


    /**
     * 删除配置信息
     * @param key
     * @return
     */
    public boolean deleteDbconfig(String key) {
        try {
            this.init(file);
            properties.remove(key);
            properties.store(new FileOutputStream(file), new Date().toString());
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询所有配置信息
     * @param key dbName
     * @return
     */
    public DbConfig getDbconfigByKey(String key) throws IOException {
        this.init(file);
        String  str = (String)properties.get(key);
        if(null != str && !"".equals(str)) {
            String[] splits = str.split("\\|");
            DbConfig dc = new DbConfig();
            dc.setDbName(key);
            dc.setUrl(splits[0]);
            dc.setDriver(splits[1]);
            dc.setUsername(splits[2]);
            dc.setPassword(splits[3]);
            dc.setSchema(splits[4]);
            return dc;
        }
        return null;
    }



    /**
     * 查询所有配置信息
     * @param
     * @return
     */
    public List<DbConfig> getAllDbconfig() {
        try {
            this.init(file);
            List<DbConfig> list = new ArrayList<DbConfig>();
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> temp : entries) {
                DbConfig dc = new DbConfig();
                dc.setDbName((String) temp.getKey());
                String value = (String) temp.getValue();
                String[] splits = value.split("\\|");
                dc.setUrl(splits[0]);
                dc.setDriver(splits[1]);
                dc.setUsername(splits[2]);
                dc.setPassword(splits[3]);
                dc.setSchema(splits[4]);
                list.add(dc);
            }
            return list;
        }catch ( IOException e){
            e.printStackTrace();
            return null;
        }
    }


}
