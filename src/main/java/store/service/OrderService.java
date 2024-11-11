package store.service;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Order;
import store.domain.Receipt;
import store.domain.Stock;
import store.exception.ErrorMessage;
import store.exception.ExceptionHandler;
import java.util.List;
import java.util.Map;

public class OrderService {

    private Map<String, Stock> stockMap;
    private MembershipService membershipService;

    public OrderService(Map<String, Stock> stockMap) {
        this.stockMap = stockMap;
        this.membershipService = new MembershipService();
    }

    public int order(List<Order> orderList) {
        Receipt receipt = new Receipt();
        int totalPrice = 0, totalDiscount = 0;

        for (Order order : orderList) {
            int[] result = processOrder(order, receipt);
            totalPrice += result[0];
            totalDiscount += result[1];
        }

        int membershipDiscount = applyMembershipDiscount(totalPrice - totalDiscount);
        receipt.setMembershipDiscount(membershipDiscount);
        receipt.printReceipt(totalPrice, totalDiscount, membershipDiscount);
        return totalPrice - totalDiscount - membershipDiscount;
    }

    private int[] processOrder(Order order, Receipt receipt) {
        Stock stock = validateItem(order.getProductName());
        int orderQuantity = order.getQuantity();
        int[] result = handleOrder(stock, orderQuantity);
        int promotionalQuantity = calculatePromotionalQuantity(stock, orderQuantity);
        receipt.addItem(order.getProductName(), orderQuantity, result[0], promotionalQuantity);
        return result;
    }

    private int applyMembershipDiscount(int amountAfterPromotion) {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return "Y".equals(Console.readLine().trim().toUpperCase())
                ? membershipService.calculateMembershipDiscount(amountAfterPromotion)
                : 0;
    }

    private int[] handleOrder(Stock stock, int orderQuantity) {
        if (stock.getPromotion() != null && stock.getPromotion().isValid()) {
            return handlePromotionOrder(stock, orderQuantity);
        }
        return new int[]{handleRegularOrder(stock, orderQuantity), 0};
    }

    private int calculatePromotionalQuantity(Stock stock, int orderQuantity) {
        if (stock.getPromotion() == null || !stock.getPromotion().isValid()) return 0;
        int minQuantity = stock.getPromotion().getMinimumQuantity();
        int givenForFree = stock.getPromotion().getGivenForFree();
        return (orderQuantity / minQuantity) * givenForFree;
    }

    private Stock validateItem(String itemName) {
        if (!stockMap.containsKey(itemName)) {
            ExceptionHandler.inputException(ErrorMessage.INVALID_ITEM);
        }
        return stockMap.get(itemName);
    }

    private int[] handlePromotionOrder(Stock stock, int orderQuantity) {
        int minQuantity = stock.getPromotion().getMinimumQuantity();
        if (orderQuantity >= minQuantity) {
            return handlePromotionAddition(stock, orderQuantity, minQuantity);
        }
        return new int[]{handleInsufficientPromotionOrder(stock, orderQuantity, minQuantity), 0};
    }

    private int[] handlePromotionAddition(Stock stock, int orderQuantity, int minQuantity) {
        int remainder = minQuantity - (orderQuantity % minQuantity);
        if (remainder != 0 || minQuantity == 1) {
            System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n",
                    stock.getProductName(), remainder);
            String input = Console.readLine().trim().toUpperCase();

            if ("Y".equals(input)) {
                orderQuantity += remainder;
            } else if ("N".equals(input)) {
                if (orderQuantity > stock.getQuantity()) {
                    System.out.println("[ERROR] 재고가 부족합니다. 다른 수량을 입력해 주세요: ");
                    for (int attempt = 0; attempt < 2; attempt++) {
                        String newInput = Console.readLine().trim();
                        try {
                            int newQuantity = Integer.parseInt(newInput);
                            if (newQuantity <= 0) {
                                throw new IllegalArgumentException("[ERROR] 유효한 수량을 입력해 주세요.");
                            }
                            if (newQuantity > stock.getQuantity()) {
                                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                            }
                            orderQuantity = newQuantity;
                            break; // Exit the loop on valid input
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("[ERROR] 숫자를 입력해 주세요.");
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. Y 또는 N만 입력해 주세요.");
            }
        }
        return processPromotionOrder(stock, orderQuantity);
    }




    private int handleRegularOrder(Stock stock, int quantity) {
        if (quantity > stock.getQuantity()) {
            ExceptionHandler.inputException(ErrorMessage.INSUFFICIENT_STOCK);
        }
        stock.subtractFromStock(quantity);
        return stock.getPrice() * quantity;
    }

    private int[] processPromotionOrder(Stock stock, int quantity) {
        int minQuantity = stock.getPromotion().getMinimumQuantity();
        int givenForFree = stock.getPromotion().getGivenForFree();
        int totalPromotions = quantity / minQuantity;
        int itemsForFree = totalPromotions * givenForFree;

        return finalizePromotionOrder(stock, quantity, itemsForFree);
    }

    private int[] finalizePromotionOrder(Stock stock, int quantity, int itemsForFree) {
        int promotionalStockUsed = Math.min(stock.getPromotionQuantity(), quantity + itemsForFree);
        stock.subtractFromPromotionQuantity(promotionalStockUsed);
        int remainingToSubtract = quantity + itemsForFree - promotionalStockUsed;
        deductRemainingStock(stock, remainingToSubtract);
        int discountAmount = itemsForFree * stock.getPrice();
        int totalPrice = (quantity - itemsForFree) * stock.getPrice();
        return new int[]{totalPrice, discountAmount};
    }

    private void deductRemainingStock(Stock stock, int remainingToSubtract) {
        if (remainingToSubtract > stock.getQuantity()) {
            ExceptionHandler.inputException(ErrorMessage.INSUFFICIENT_STOCK);
        }
        stock.subtractFromStock(remainingToSubtract);
    }

    private int handleInsufficientPromotionOrder(Stock stock, int orderQuantity, int minQuantity) {
        if (orderQuantity > stock.getQuantity()) {
            System.out.println("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            String input = Console.readLine().trim().toUpperCase();
            if ("N".equals(input)) {
                String newInput = Console.readLine().trim();
                int newQuantity = Integer.parseInt(newInput);
                if (newQuantity <= 0 || newQuantity > stock.getQuantity()) {
                    System.out.println("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                    throw new IllegalArgumentException();
                }
                orderQuantity = newQuantity;

            } else {
                System.out.println("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                throw new IllegalArgumentException();
            }
        }
        stock.subtractFromStock(orderQuantity);
        System.out.printf("주문 수량이 %d개보다 적어 %s에 대한 프로모션이 적용되지 않습니다.%n", minQuantity, stock.getProductName());
        return stock.getPrice() * orderQuantity;
    }
}
