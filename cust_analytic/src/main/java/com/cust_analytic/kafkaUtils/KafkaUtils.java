package com.cust_analytic.kafkaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;

public class KafkaUtils {
    Properties properties = new Properties();
    Admin admin = null;

    public KafkaUtils() {
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        admin = AdminClient.create(properties);
    }

    public boolean deleteTopics(String[] topics) {
        List<String> to = Arrays.asList(topics);
        DeleteTopicsResult r = admin.deleteTopics(to);
        KafkaFuture<Void> fut = r.all();
        try {
            fut.get();
        } catch (ExecutionException ee) {
            // the computation threw an exception
            System.out.println("Execution exception " + ee);
            return false;
        } catch (InterruptedException ie) {
            // the current thread was interrupted while waiting
            System.out.println("interrupted exception " + ie);
            return false;
        }
        System.out.println("deleteTopics return true");
        return true;
    }

    public boolean createTopics(String[] topics) {
        List<NewTopic> crTopics = new ArrayList<NewTopic>();
        for (String name : topics) {
            NewTopic nt = new NewTopic(name, 1, (short) 1);
            crTopics.add(nt);
        }
        CreateTopicsResult r = admin.createTopics(crTopics);
        KafkaFuture<Void> fut = r.all();
        try {
            fut.get();
        } catch (ExecutionException ee) {
            // the computation threw an exception
            System.out.println("Execution exception " + ee);
            return false;
        } catch (InterruptedException ie) {
            // the current thread was interrupted while waiting
            System.out.println("interrupted exception " + ie);
            return false;
        }
        System.out.println("createTopics return true");
        return true;
    }

    public String[] listTopics() {
        String[] results = null;
        Set<String> topics = null;
        ListTopicsResult tr = admin.listTopics(new ListTopicsOptions());
        KafkaFuture<Set<String>> lr = tr.names();
        try {
            topics = lr.get();
        } catch (ExecutionException ee) {
            // the computation threw an exception
            System.out.println("Execution exception " + ee);
            return null;
        } catch (InterruptedException ie) {
            // the current thread was interrupted while waiting
            System.out.println("interrupted exception " + ie);
            return null;
        }
        int len = topics.size();
        results = new String[len];
        topics.toArray(results);
        return results;
    }

}
