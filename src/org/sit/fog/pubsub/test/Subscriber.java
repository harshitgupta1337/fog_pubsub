package org.sit.fog.pubsub.test;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

	public static final String BROKER_URL = "tcp://localhost:1883";
    

    //We have to generate a unique Client id.
    String clientId = "harshit-sub";
    private MqttClient mqttClient;

    public Subscriber() {

        try {
            mqttClient = new MqttClient(BROKER_URL, clientId);


        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() throws IOException {
        try {

            mqttClient.setCallback(new SubscribeCallback("harshit-top-pub", false));
            mqttClient.connect();

            //Subscribe to all subtopics of home
            //final String topic = "home/#";
            final String topic = Utils.TOPIC;
            
            mqttClient.subscribe(topic);

            System.out.println("Subscriber is now listening to "+topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String... args) throws IOException {
        final Subscriber subscriber = new Subscriber();
        subscriber.start();
    }

}
