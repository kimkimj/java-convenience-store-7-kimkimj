package store.service;

public class MembershipService {
    private static final double DISCOUNT_RATE = 0.30;
    private static final int MAX_DISCOUNT = 8000;

    public int calculateMembershipDiscount(int amountAfterPromotion) {
        int discount = (int) (amountAfterPromotion * DISCOUNT_RATE);
        return Math.min(MAX_DISCOUNT, discount);
    }

}
