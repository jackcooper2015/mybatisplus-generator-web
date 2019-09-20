package com.reapal.chat;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName User.java
 * @Description 在线聊天用户
 * @createTime 2019年09月20日 19:10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String name;
    String ip;
}
