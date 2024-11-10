package store.view;

import store.domain.Promotion;
import store.domain.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class FileReader {
    Map<String, Promotion> promotionMap;
    Map<String, Stock> stockMap;

    public FileReader() {
        stockMap = new HashMap<>();
        promotionMap = new HashMap<>();
    }

    public Map<String, Stock> readFile() {
        readPromotionsFile();
        readProductsFile();
        return stockMap;
    }

    public void readPromotionsFile() {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader("src/main/resources/promotions.md"))) {
            String[] header = br.readLine().split(",");
            String line;
            while ((line = br.readLine()) != null) {
                Promotion promotion = parsePromotion(line);
                promotionMap.put(promotion.getName(), promotion);
            }
        } catch (IOException e) {
            System.err.println("[ERROR] promotions 파일 읽기에 실패했습니다. " + e.getMessage());
        }
    }

    public void readProductsFile() {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader("src/main/resources/products.md"))) {
            String[] header = br.readLine().split(",");
            String line;
            while ((line = br.readLine()) != null) {
                Stock stock = parseProducts(line);
                stockMap.put(stock.getProductName(), stock);
            }
        } catch (IOException e) {
            System.err.println("[ERROR] products 파일 읽기에 실패했습니다. " + e.getMessage());
        }
    }

    private Promotion parsePromotion(String input) {
        String[] line = input.split(",");
        String promotionName = line[0];
        int minimum = Integer.parseInt(line[1]);
        int extra = Integer.parseInt(line[2]);
        LocalDate startDate = LocalDate.parse(line[3]);
        LocalDate endDate = LocalDate.parse(line[4]);
        return new Promotion(promotionName, minimum, extra, startDate, endDate);
    }

    private Stock parseProducts(String input) {
        String[] line = input.split(",");
        String productName = line[0];
        int price = Integer.parseInt(line[1]);
        int quantity = Integer.parseInt(line[2]);
        String promotionName = line[3];
        return createStock(productName, promotionName, price, quantity);
    }

    private Stock createStock(String productName, String promotionName, int price, int quantity) {
        // check if the product exists
        if (stockMap.containsKey(productName)) {
            Stock stock = stockMap.get(productName);
            if (stock.hasPromotion()) {
                Promotion promotion = stock.getPromotion();
                return new Stock(productName, quantity, price, promotion, stock.getPromotionQuantity());
            }
            // the processed stock does not have a promotion
            return new Stock(productName, stock.getQuantity(), price, promotionMap.get(promotionName), quantity);
        }
        // product does not exist in the database yet
        // no promotion
        if (promotionName.equals("null")) {
            return new Stock(productName, quantity, price, null, 0);
        }
        Promotion promo = promotionMap.get(promotionName);
        return new Stock(productName, 0, price, promo, quantity);
    }
}
