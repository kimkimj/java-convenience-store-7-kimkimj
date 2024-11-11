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

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public void subtractFromStock(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity -= quantity;
    }

    public void subtractFromPromotionQuantity(int quantity) {
        promotionQuantity -= quantity;
    }

    public void printStock() {
        // 프로모션 재품 재고
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
