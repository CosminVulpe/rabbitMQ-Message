package rabbitMQ;

import model.Order;
import model.Stock;
import parser.JSONFileParser;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static enums.StatusOrderType.INSUFFICIENT_STOCKS;
import static enums.StatusOrderType.RESERVED;

public class Producer {


    private JSONFileParser jsonFileParser;

    public Producer(JSONFileParser jsonFileParser) {
        this.jsonFileParser=jsonFileParser;
    }

    public List<String> parseOutputFormat() {
        List<String> jsonObjectList = new ArrayList<>();

        for (Order order : jsonFileParser.getOrderList()) {
            for (Stock stock : order.getStockList()) {
                if (Objects.equals(order.getProductId(), Long.valueOf(stock.getId()))
                        && stock.getQuantity() > order.getQuantity()) {
                    order.setStatusOrder(RESERVED.name());
                } else {
                    order.setStatusOrder(INSUFFICIENT_STOCKS.name());
                }
                JsonObject jsonObjectBuilder = Json
                        .createObjectBuilder()
                        .add("order_id", order.getProductId())
                        .add("order_status", order.getStatusOrder())
                        .add("error_message",
                                (order.getStatusOrder().equals(INSUFFICIENT_STOCKS.name())
                                        ? "Not enough funds!" : "")
                        )
                        .build();

                System.out.println(
                        jsonObjectBuilder.toString()
                );

                jsonObjectList.add(jsonObjectBuilder.toString());
                break;
            }
        }
        return jsonObjectList;
    }
}
