import java.util.ArrayList;

public class Customer extends User {
    private String status;
    private int deliveryTimeLowerBound, deliveryTimeUpperBound;
    private final int deliveryCharge = 100;
    private int deliveryRate;
    private int discountEntitled;
    private int couponLowerBound;
    private int couponUpperBound;
    private double walletBalance = 1000;
//    private int couponCount = generateCoupons();

    ArrayList<Integer> coupons = new ArrayList<>();
    Cart cart;

    public Customer(String username, String password) {
        super(username, password);
//        this.cart = new Cart(this);
//        this.generateCoupons();
    }
//    public abstract void upgradeStatus();
    public boolean makePayment(double amount) {
        if (amount > this.getWalletBalance()) {
            System.out.println("Insufficient balance in your wallet.");
            return false;
        }
        this.setWalletBalance(this.getWalletBalance() - amount);
        return true;
    }
    public boolean addDealToCart(int dealID) {
        return this.getCart().addDeal(dealID);
    }
    public boolean addProductToCart(String productID, int quantity) {
        if (FlipZon.getProducts().containsKey(Product.parseProductIDToInt(productID))) {
            this.getCart().addProduct(productID, quantity);
            return true;
        } else {
            return false;
        }
    }

    public void generateCoupons() {
        int couponCount = (int) (Math.random() * (couponUpperBound - couponLowerBound + 1)) + couponLowerBound;
        for (int i = 0; i < couponCount; i++) {
//            random number between 5 and 15, both inclusive
            this.getCoupons().add((int) (Math.random() * (15 - 5 + 1)) + 5);
        }
    }
    public void printCoupons() {
        System.out.println("Your coupons are:");
        for (int i = 0; i < this.getCoupons().size(); i++) {
            System.out.print(this.getCoupons().get(i) + "% off,\t");
        }
    }
    public int getCouponCount() {
        return this.getCoupons().size();
    }
    public ArrayList<Integer> getCoupons() {
        return this.coupons;
    }
    public void applyHighestCoupon() {
        int highestCoupon = this.getHighestCoupon();
        this.removeCoupon(highestCoupon);

    }
    public int getHighestCoupon() {
        int highestCoupon = 0;
        for (int coupon : this.getCoupons()) {
            if (coupon > highestCoupon) {
                highestCoupon = coupon;
            }
        }
        return highestCoupon;
    }
    private void removeCoupon(int coupon) {
        this.coupons.remove(Integer.valueOf(coupon));
    }
    public void checkout() {
        this.getCart().checkout();
    }
    // Getters
    public String getStatus() {
        return this.status;
    }
    public int getDeliveryCharge() {
        return this.deliveryCharge;
    }
    public int getDeliveryRate() {
        return this.deliveryRate;
    }
    public int getDiscountEntitled() {
        return this.discountEntitled;
    }
    public int getDeliveryTime() {
        // return random integer between deliveryTimeLowerBound and deliveryTimeUpperBound inclusive
        return (int) (Math.random() * (deliveryTimeUpperBound - deliveryTimeLowerBound + 1)) + deliveryTimeLowerBound;
    }
    public double getWalletBalance() {
        return this.walletBalance;
    }
    public Cart getCart() {
        return this.cart;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDeliveryTime(int lowerBound, int upperBound) {
        this.deliveryTimeLowerBound = lowerBound;
        this.deliveryTimeUpperBound = upperBound;
    }
    public void setDeliveryRate(int deliveryRate) {
        this.deliveryRate = deliveryRate;
    }
    public void setDiscountEntitled(int discountEntitled) {
        this.discountEntitled = discountEntitled;
    }
    public void setCouponQtyLimit(int lowerBound, int upperBound) {
        this.couponLowerBound = lowerBound;
        this.couponUpperBound = upperBound;
    }
    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
