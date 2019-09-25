package com.reapal.chat;

import com.alibaba.fastjson.JSON;
import com.reapal.utils.WebsocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * WebSocket 聊天服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Slf4j
@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();


    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineSessions.put(session.getId(), session);
        final List<User> users = onlineSessions.values().stream().map(t -> new User(getHostFromSession(t), "")).collect(Collectors.toList());
        String data = JSON.toJSONString(users);
        sendMessageToAll(Message.jsonStr(Message.ENTER, data));
    }



    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        Speak speak = JSON.parseObject(jsonStr, Speak.class);
        speak.setUsername(getHostFromSession(session));
        log.info("=======> :{}",JSON.toJSONString(speak));
        sendSpeakToAll(speak,session.getId());
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session) {
        onlineSessions.remove(session.getId());
        String host = getHostFromSession(session);
        String data = JSON.toJSONString(new User(host, host));
        sendMessageToAll(Message.jsonStr(Message.QUIT, data));
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        onlineSessions.remove(session.getId());
        String host = getHostFromSession(session);
        String data = JSON.toJSONString(new User(host, host));
        sendMessageToAll(Message.jsonStr(Message.QUIT, data));
//        log.error("链接断开",error);
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendMessageToAll(String msg) {
        onlineSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendSpeakToAll(Speak speak,String sessionId) {
        final Message message = Message.builder().type(Message.SPEAK).data(JSON.toJSONString(speak)).build();
        onlineSessions.forEach((id, session) -> {
            try {
                message.setMe(id.equals(sessionId));
                session.getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String getHostFromSession(Session session) {
        final InetSocketAddress remoteAddress = WebsocketUtil.getRemoteAddress(session);
        return remoteAddress.getHostString()+":"+remoteAddress.getPort();
    }

}
