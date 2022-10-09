package parser;

import model.Stock;
import model.Stocks;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class XMLFileParser extends FileParser {

    private final List<Stocks> stocksList;

    public XMLFileParser() {
        super(Logger.getLogger(XMLFileParser.class.getName()));
        this.stocksList = new ArrayList<>();
    }

    @Override
    public void parseFile(String fileName) {
        File folder = new File(getFilePath("input"));
        File[] files = folder.listFiles();

        if (doFoldersExist(files)) {
            printLogWarn("The folder is empty! Check if there is XML file");
            return;
        }

        printLogInfo("The XML"
                .concat(fileName)
                .concat(" was processed"));

        File inputFile = new File(getFilePath("input/")
                .concat(fileName));
        Stocks stocks = JAXB.unmarshal(inputFile, Stocks.class);

        appendStockToList(stocks);
        moveFolder(fileName);
    }


    private void moveFolder(String fileName) {
        File destinationFile = new File("src/main/resources/processed/"
                .concat(fileName));

        File fromFile = new File(getFilePath("input/")
                .concat(fileName));

        try {
            Files.copy(Paths.get(fromFile.toURI())
                    , Paths.get(destinationFile.toURI()), REPLACE_EXISTING);

            Files.move(Paths.get(fromFile.toURI())
                    , Paths.get(destinationFile.toURI()), REPLACE_EXISTING);
        } catch (IOException e) {
            printLogWarn(e.getMessage());
        }
    }

    @Override
    protected String getFilePath(String typeOfFile) {
        return "src/main/resources/".concat(typeOfFile);
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
