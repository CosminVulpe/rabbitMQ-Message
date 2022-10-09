package rabbitMQ;

import com.rabbitmq.client.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {

    private static final String QUEUE_NAME = "ORDERS-queue";

    public void connect() throws IOException, TimeoutException {
        final AtomicInteger counter = new AtomicInteger(1);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                FileWriter fileWriter = new FileWriter("src/main/resources/orders/" + "orders" + counter.getAndIncrement() + ".json");
                fileWriter.write(message);

                fileWriter.flush();
                fileWriter.close();
            }
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, (CancelCallback) null);
    }
}
