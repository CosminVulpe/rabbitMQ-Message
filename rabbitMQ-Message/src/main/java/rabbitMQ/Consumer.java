package rabbitMQ;

import com.rabbitmq.client.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer implements Runnable {

    private final Long threadNumber;

    private final String endPoint;

    public Consumer(String endPoint, Long threadNumber) {
        this.endPoint = endPoint;
        this.threadNumber = threadNumber;
    }

    public void connect() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(endPoint, true, false, false, null);

        DeliverCallback deliverCallback = (s, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            FileWriter fileWriter = new FileWriter("src/main/resources/orders/"
                    .concat(endPoint)
                    .concat(".json"));
            fileWriter.write(message);

            fileWriter.flush();
            fileWriter.close();
        };

        channel.basicConsume(endPoint, true, deliverCallback, (CancelCallback) null);
    }

    @Override
    public void run() {
        try {
            connect();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getEndPoint() {
        return endPoint;
    }
}
