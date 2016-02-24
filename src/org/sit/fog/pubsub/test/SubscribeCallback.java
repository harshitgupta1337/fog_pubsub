package org.sit.fog.pubsub.test;

import java.io.IOException;
import java.util.Calendar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class SubscribeCallback implements MqttCallback {

	boolean isTopLevel;
	Publisher publisher;
	public SubscribeCallback(String clientId, boolean isTopLevel) throws IOException, MqttSecurityException, MqttException{
		this.isTopLevel = isTopLevel;
		this.publisher = new Publisher(clientId, "tcp://localhost:1883", Utils.TOP_TOPIC);
		this.publisher.setUp();
	}
	
    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message){
    	
    	System.out.println(topic + " " + message.toString()+ " " + Calendar.getInstance().getTimeInMillis());
    	
    	if(!isTopLevel){
    		final MqttTopic _topic = publisher.getClient().getTopic(Utils.TOP_TOPIC);

            try {
				_topic.publish(new MqttMessage(message.toString().getBytes()));
			} catch (MqttException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

            //System.out.println("Published data to TOP Topic: " + _topic.getName() + "  Message: " + message.toString());
    	}
    	
        if ("home/LWT".equals(topic)) {
            System.err.println("Sensor gone!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
}
