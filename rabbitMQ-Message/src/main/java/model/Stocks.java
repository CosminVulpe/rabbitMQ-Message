package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stocks {

    @XmlElement(name="stock")
    private List<Stock> stockList;

    public Stocks() {
    }

    public List<Stock> getStocksList() {
        return stockList;
    }


    public void setProductList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public String toString() {
        return "Stocks{" +
                "productList=" + stockList +
                '}';
    }
}
