package com.kroger.digital.receipts.queue.example;

import com.teamten.beanstalk.BeanstalkClient;
import com.teamten.beanstalk.BeanstalkClientImpl;
import com.teamten.beanstalk.Job;

import java.io.*;
import java.net.SocketTimeoutException;

/**
 * Created by kon3982 on 1/26/16.
 */
public class BeanstalkMessageConsumer {

    public static void main(String[] args) throws Exception {
        BeanstalkClient client = null;
        try {
            client = createClient();

            while(true) {
                try {
                    Job job = client.reserve(null);
                    if (job != null) {
                        Object data = deserialize(job.getData());
                        QueueMessage message = (QueueMessage)data;
                        System.out.println(message);
                        client.delete(job.getJobId());
                    }

                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
            }


        } catch (Exception clientException) {
            clientException.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    public static BeanstalkClient createClient() throws Exception {
        BeanstalkClient client = new BeanstalkClientImpl();
        return client;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }



}
