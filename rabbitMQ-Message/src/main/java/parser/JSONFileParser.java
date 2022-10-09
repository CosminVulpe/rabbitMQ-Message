package parser;

import model.Order;
import model.Stock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static enums.StatusOrderType.INSUFFICIENT_STOCKS;
import static enums.StatusOrderType.RESERVED;

public class JSONFileParser extends FileParser {

    private final List<Order> orderList;
    private final XMLFileParser xmlFileParser;

    private Order order;


    public JSONFileParser(XMLFileParser xmlFileParser) {
        super(Logger.getLogger(JSONFileParser.class.getName()));
        this.orderList = new ArrayList<>();
        this.xmlFileParser = xmlFileParser;
    }


    @Override
    public void parseFile(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(
                            new FileReader(getFilePath("orders"
                                    .concat("/")
                                    .concat(fileName)
                                    .concat(".json")))
                    );
            printLogInfo("The JSON File"
                    .concat(fileName)
                    .concat(" was processed"));

            String clientName = (String) jsonObject.get("client_name");
            JSONArray items = (JSONArray) jsonObject.get("items");

            for (Object item : items) {
                Long productId = (Long) ((JSONObject) item).get("product_id");
                Long quantity = (Long) ((JSONObject) item).get("quantity");
                this.order = new Order(clientName, productId, quantity, xmlFileParser.getStockList());
                appendOrderToList(order);
            }
        } catch (IOException | ParseException e) {
            printLogWarn(e.getMessage());
        }
    }

    public XMLFileParser getXmlFileParser() {
        return xmlFileParser;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    private void appendOrderToList(Order order) {
        orderList.add(order);
    }

    public Order getOrder() {
        return order;
    }

    @Override
    protected String getFilePath(String typeOfFile) {
        return "src/main/resources/".concat(typeOfFile);
    }

}
