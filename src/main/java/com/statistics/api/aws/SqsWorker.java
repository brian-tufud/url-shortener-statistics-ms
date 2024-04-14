package com.statistics.api.aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.statistics.api.service.EventService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SqsWorker {

    private final SqsClient sqs = SqsClient.builder()
            .region(Region.US_EAST_1)
            .build();

    @Autowired
    private Environment env;

    @Autowired
    private EventService eventService;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    work();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    public void work() throws InterruptedException, JSONException {
        String fifoQueueUrl = env.getProperty("statistics_queue");

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(fifoQueueUrl)
                .waitTimeSeconds(5)
                .maxNumberOfMessages(10)
                .build();

        while (true) {
            try {
                ReceiveMessageResponse response = sqs.receiveMessage(receiveMessageRequest);
                List<Message> sqsMessages = response.messages();
                System.out.println(sqsMessages.size() + " received messages...");

                for (Message message : sqsMessages) {
                    String body = message.body();
                    System.out.println("message received: " + body);

                    JSONObject json = new JSONObject(body);

                    try {
                        eventService.processEvent(json);
                        System.out.println("deleting message from queue...");
                        sqs.deleteMessage(DeleteMessageRequest.builder()
                                .queueUrl(fifoQueueUrl)
                                .receiptHandle(message.receiptHandle())
                                .build());
                    } catch (Exception e) {
                        System.out.println("Error processing message, " + e.getMessage());
                    }
                }
            } catch (SqsException e) {
                e.printStackTrace();
            } finally {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
