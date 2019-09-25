package com.reapal.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName Speak.java
 * @Description 聊天对象
 * @createTime 2019年09月20日 19:15:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speak {

    private String username; //发送人

    private String msg; //发送消息

}
