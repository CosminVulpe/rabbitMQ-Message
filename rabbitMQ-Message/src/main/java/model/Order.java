package model;


import java.util.List;

public class Order {

    private List<Stock> stockList;

    private Long quantity;

    private Long productId;

    private String clientName;

    private String statusOrder;

    public Order(String clientName, Long productId, Long quantity, List<Stock> stockList) {
        this.clientName = clientName;
        this.productId = productId;
        this.quantity = quantity;
        this.stockList = stockList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "stockList=" + stockList +
                ", quantity=" + quantity +
                ", productId=" + productId +
                ", clientName='" + clientName + '\'' +
                ", statusOrder='" + statusOrder + '\'' +
                '}';
    }
}
