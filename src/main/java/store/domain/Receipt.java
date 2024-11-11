package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private static class Item {
        String productName;
        int quantity;
        int price;
        int promotionalQuantity;

        Item(String productName, int quantity, int price, int promotionalQuantity) {
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.promotionalQuantity = promotionalQuantity;
        }
    }

    private List<Item> items;
    private int membershipDiscount;

    public Receipt() {
        this.items = new ArrayList<>();
        this.membershipDiscount = 0;
    }

    public void addItem(String productName, int quantity, int price, int promotionalQuantity) {
        items.add(new Item(productName, quantity, price, promotionalQuantity));
    }

    public void setMembershipDiscount(int discount) {
        this.membershipDiscount = discount;
    }

    public void printReceipt(int totalPrice, int totalPromotionalDiscount, int membershipDiscount) {
        System.out.println("=============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        for (Item item : items) {
            System.out.printf("%s\t\t%d\t%,d%n", item.productName, item.quantity, item.price);
        }
        System.out.println("=============증\t정===============");
        for (Item item : items) {
            if (item.promotionalQuantity > 0) {
                System.out.printf("%s\t\t%d%n", item.productName, item.promotionalQuantity);
            }
        }
        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\t%,d%n", totalPrice / 1000, totalPrice);
        System.out.printf("행사할인\t\t\t-%,d%n", totalPromotionalDiscount);
        System.out.printf("멤버십할인\t\t\t-%,d%n", membershipDiscount);
        System.out.printf("내실돈\t\t\t%,d%n", totalPrice - totalPromotionalDiscount - membershipDiscount);
    }
}
