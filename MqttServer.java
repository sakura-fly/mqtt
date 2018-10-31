

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

@Component
public class MqttServer {

    private final String HOST = "tcp://192.168.1.107:1883";
    // private   final String HOST = "tcp://192.168.1.107:61613";
    private final String TOPIC = "bp/#";
    private final String clientid = "bbfe";

    private MqttClient client;

    public MqttServer() throws MqttException {
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    // 链接
    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        // options.setUserName(userName);
        // options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new Callback());
            client.connect(options);
            client.subscribe(TOPIC);
            System.out.println("###################  mqtt connect  ###################");
        } catch (Exception e) {
            System.out.println("###################err###################");
            e.printStackTrace();
        }
    }


    // 发送信息
    public void publish(String topic, String msg) {
        MqttMessage message = new MqttMessage();
        message.setQos(2);
        message.setPayload(msg.getBytes());
        // MqttTopic topic = new MqttTopic("");
        try {
            client.publish(topic, message);
            System.out.println("message is published completely! "
                    // + token.isComplete()
            );
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // MqttDeliveryToken token = topic.publish(message);
        // token.waitForCompletion();

    }


    // 添加订阅
    public void sub(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 取消订阅
    public void unsub(String topic) {
        try {
            client.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
