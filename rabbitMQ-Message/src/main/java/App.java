import parser.JSONFileParser;
import parser.XMLFileParser;
import rabbitMQ.Consumer;
import rabbitMQ.Producer;


public class App {

    private static final int MAX_THREADS = 5;

    public static void main(String[] args) {

        for (int i = 1; i <= MAX_THREADS; i++) {

            XMLFileParser xmlFileParser = new XMLFileParser();
            xmlFileParser.parseFile("stock_new"
                    .concat(String.valueOf(i))
                    .concat(".xml"));

            JSONFileParser jsonFileParser = new JSONFileParser(xmlFileParser);

            Consumer consumer = new Consumer("ORDERS-queue"
                    .concat(String.valueOf(i))
                    , (long) i);
            Thread consumerThread = new Thread(consumer);

            consumerThread.start();

            jsonFileParser.parseFile(consumer.getEndPoint());

            Producer producer = new Producer(jsonFileParser);

            producer.parseOutputFormat();
        }

    }
}
