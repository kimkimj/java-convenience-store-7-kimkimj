package store.domain;

public class Stock {
    private final String productName;
    private int quantity;
    private int price;
    private Promotion promotion;
    private int promotionQuantity;

    public Stock(String productName, int quantity, int price, Promotion promotion, int promotionQuantity) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int subtractQuantity(int number) {
        quantity -= number;
        return quantity;
    }

    public void printStock() {
        // 프로모션 재고
        if (promotion != null) {
            System.out.printf("- %s %,d원 %d개 %s%n", productName, price, promotionQuantity, promotion.getName());
        }

        // 정가 재품 재고
        if (quantity == 0) {
            System.out.printf("- %s %,d원 재고 없음%n", productName, price);
        } else {
            System.out.printf("- %s %,d원 %d개%n", productName, price, quantity);
        }
    }
}
