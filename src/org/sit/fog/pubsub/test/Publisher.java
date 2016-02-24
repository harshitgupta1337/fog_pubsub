package org.sit.fog.pubsub.test;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Publisher {
    
    private MqttClient client;
    private String _topic;

    public Publisher(String clientId, String brokerUrl, String topic) {

        try {

            client = new MqttClient(brokerUrl, clientId);
            this._topic = topic;

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setUp() throws MqttSecurityException, MqttException{
    	MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setWill(client.getTopic("home/LWT"), "I'm gone :(".getBytes(), 0, false);

        client.connect(options);
    }
    void start() {

        try {
            setUp();
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
        final MqttTopic topic = client.getTopic(_topic);

        final int number = (int) (Math.random()*100);
        final String data = number + "";

        topic.publish(new MqttMessage(data.getBytes()));

        System.out.println("Published data. Topic: " + topic.getName() + "  Message: " + data);
    }

    public MqttClient getClient() {
		return client;
	}

	public void setClient(MqttClient client) {
		this.client = client;
	}

	public static void main(String... args) {
        final Publisher publisher = new Publisher("harshit-pub", "tcp://10.14.96.58:1883", Utils.TOPIC);
        publisher.start();
    }
}
