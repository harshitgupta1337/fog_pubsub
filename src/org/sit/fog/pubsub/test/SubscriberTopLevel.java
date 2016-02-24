package org.sit.fog.pubsub.test;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class SubscriberTopLevel {

	public static final String BROKER_URL = "tcp://localhost:1883";
    

    //We have to generate a unique Client id.
    String clientId = "harshit-top-sub";
    private MqttClient mqttClient;

    public SubscriberTopLevel() {

        try {
            mqttClient = new MqttClient(BROKER_URL, clientId);


        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() throws IOException {
        try {

            mqttClient.setCallback(new SubscribeCallback("yoyo", true));
            mqttClient.connect();

            final String topic = Utils.TOP_TOPIC;
            
            mqttClient.subscribe(topic);

            System.out.println("Subscriber is now listening to "+topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String... args) throws IOException {
        final SubscriberTopLevel subscriber = new SubscriberTopLevel();
        subscriber.start();
    }

}
