package parser;

import model.Stock;
import model.Stocks;

import javax.xml.bind.JAXB;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class XMLFileParser extends FileParser {

    private final List<Stocks> stocksList;

    public XMLFileParser() {
        super(Logger.getLogger(XMLFileParser.class.getName()));
        this.stocksList = new ArrayList<>();
    }

    @Override
    public void parseFile() {
        File folder = new File(getFilePath("input"));
        File[] files = folder.listFiles();

        if (doFoldersExist(files)) {
            printLogWarn("The folder is empty! Check if there is XML file");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                printLogInfo("The ".concat(fileName).concat(" was processed"));

                File inputFile = new File(getFilePath("input/").concat(fileName));
                Stocks stocks = JAXB.unmarshal(inputFile, Stocks.class);
                appendStockToList(stocks);
            }
        }
    }

    @Override
    protected String getFilePath(String typeOfFile) {
        return "src/main/resources/" + typeOfFile;
    }

    public List<Stock> getStockList() {
        return stocksList
                .stream()
                .map(Stocks::getStocksList)
                .collect(Collectors.toList())
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private boolean doFoldersExist(File[] files) {
        return files.length == 0;
    }

    private void appendStockToList(Stocks stocks) {
        stocksList.add(stocks);
    }

}
