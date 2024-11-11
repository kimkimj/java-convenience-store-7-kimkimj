package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.*;
import store.exception.ErrorMessage;
import store.exception.ExceptionHandler;

import java.util.LinkedList;
import java.util.List;

public class InputView {

    private final int MAX_ATTEMPT = 2;

    public List<Order> readItem() {
        for (int attempt = 0; attempt < MAX_ATTEMPT; attempt++) {
            try {
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                String input = Console.readLine().trim();
                isNotEmpty(input);
                return parseOrder(input); // If input is valid, return immediately
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] Invalid input. Please try again.");
            }
        }
        throw new IllegalStateException("[ERROR] Maximum input attempts exceeded.");
    }

    public boolean promptForMorePurchase() {
        for (int attempt = 0; attempt < MAX_ATTEMPT; attempt++) {
            try {
                System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
                String input = Console.readLine().trim();
                isNotEmpty(input);
                return isValidLetter(input); // If input is valid, return immediately
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] Invalid input. Please try again.");
            }
        }
        throw new IllegalStateException("[ERROR] Maximum input attempts exceeded.");
    }

    private List<Order> parseOrder(String input) {
        List<Order> orderList = new LinkedList<>();
        String[] products = isValidFormat(input);
        for (String item : products) {
            String[] line = item.substring(1, item.length() - 1).split("-");
            String itemName = line[0];
            int quantity = isValidQuantity(line[1]);
            orderList.add(new Order(itemName, quantity));
        }
        return orderList;
    }

    private int isValidQuantity(String input) {
        try {
            int quantity = Integer.parseInt(input);
            if (quantity <= 0) {
                ExceptionHandler.inputException(ErrorMessage.INVALID_ORDER_FORMAT);
            }
            return quantity;
        } catch (NumberFormatException e) {
            ExceptionHandler.inputException(ErrorMessage.INVALID_ORDER_FORMAT);
        }
        throw new IllegalArgumentException();
    }

    private String[] isValidFormat(String input) {
        try {
            return input.split(",");
        } catch (IllegalArgumentException e) {
            ExceptionHandler.inputException(ErrorMessage.INVALID_ORDER_FORMAT);
            return null;
        }
    }

    private boolean isValidLetter(String input) {
        input = input.toUpperCase();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        ExceptionHandler.inputException(ErrorMessage.INVALID_INPUT);
        throw new IllegalArgumentException();
    }

    private void isNotEmpty(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
