package com.kroger.digital.receipts.queue.example;

import com.teamten.beanstalk.BeanstalkClient;
import com.teamten.beanstalk.BeanstalkClientImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kon3982 on 1/26/16.
 */
public class BeanstalkMessageProducer {

    private static final String TUBE_NAME = "sim-tube";
    private static final int DEFAULT_PRIORITY = 1;
    private static final int DEFAULT_DELAY_SECONDS = 0;
    private static final int DEFAULT_TTR_SECONDS = 120;


    public static void main(String[] args) throws Exception {

        //Every second spin up 10 threads that will create 32 messages each. 320 a second)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    doTheThingNTimes(32);
                }
            }
        }, 0, 1000);
    }


    public static void createMessage(BeanstalkClient client) {
        if (client == null) throw new RuntimeException("Unable to create client.  Aborting");
        try {
            client.put(DEFAULT_PRIORITY, DEFAULT_DELAY_SECONDS, DEFAULT_TTR_SECONDS, serialize(QueueMessage.randomMessage()));
        } catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }

    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }


    public static BeanstalkClient createClient()  {
        try {

        } catch (Exception e) {
            System.out.println("Unable to connect to server: " +  e);
        }

        return null;
    }

    public static void doTheThingNTimes(final int messages) {
        Runnable r = new Runnable() {
            public void run() {
                BeanstalkClient client = createClient();
                if (client == null)
                    throw new RuntimeException("Unable to create client.  Aborting. ");
                try {
                    for (int i=0; i<messages; i++) {
                        createMessage(client);
                    }

                } finally
                {
                    client.close();
                }
            }
        };

        new Thread(r).start();
    }

}
