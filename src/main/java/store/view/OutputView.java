package store.view;

import store.domain.Stock;

import java.util.Map;

public class OutputView {
    private final Map<String, Stock> stocks;

    public OutputView(Map<String, Stock> stocks) {
        this.stocks = stocks;
    }

    public void printProducts() {
        for (String name: stocks.keySet()) {
            stocks.get(name).printStock();
        }
    }
}
