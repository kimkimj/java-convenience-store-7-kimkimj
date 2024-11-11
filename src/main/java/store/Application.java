package store;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Order;
import store.domain.Stock;
import store.service.OrderService;
import store.view.FileReader;
import store.view.InputView;
import store.view.OutputView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args)  {
        FileReader fileReader = new FileReader();
        InputView inputView = new InputView();
        Map<String, Stock> stockMap = fileReader.readFile();
        OrderService orderService = new OrderService(stockMap);
        boolean morePurchase = true;

        while (morePurchase) {
            OutputView outputView = new OutputView(stockMap);
            outputView.printProducts();

            List<Order> orders = inputView.readItem();;
            orderService.order(orders);

            morePurchase = inputView.promptForMorePurchase();
        }
    }
}
