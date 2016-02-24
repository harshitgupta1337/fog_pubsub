package org.sit.fog.pubsub.test;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Publisher {

    public static final String BROKER_URL = "tcp://10.14.96.58:1883";
    public static final String TOPIC = "test";
    
    private MqttClient client;


    public Publisher() {

        //We have to generate a unique Client id.
    	String clientId = "harshitgupta1337-pub";


        try {

            client = new MqttClient(BROKER_URL, clientId);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void start() {

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic("home/LWT"), "I'm gone :(".getBytes(), 0, false);

            client.connect(options);

            //Publish data forever
            while (true) {

                publish();

                Thread.sleep(500);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {	
            e.printStackTrace();
        }
    }

    private void publish() throws MqttException {
        final MqttTopic topic = client.getTopic(TOPIC);

        final int number = (int) (Math.random()*100);
        final String data = number + "";

        topic.publish(new MqttMessage(data.getBytes()));

        System.out.println("Published data. Topic: " + topic.getName() + "  Message: " + data);
    }

    public static void main(String... args) {
        final Publisher publisher = new Publisher();
        publisher.start();
    }
}
