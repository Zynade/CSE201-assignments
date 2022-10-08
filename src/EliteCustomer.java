public class EliteCustomer extends Customer implements CustomerStatus {
    public EliteCustomer(String username, String password) {
        super(username, password);
        this.setStatus("elite");
        this.setDeliveryTime(0, 2);
        this.setDeliveryRate(0);
        this.setDiscountEntitled(10);
        this.setCouponQtyLimit(3, 4);
        this.setCart(new Cart(this));
//        this.generateCoupons();
    }
    public EliteCustomer(NormalCustomer normalCustomer) {
        super(normalCustomer.getUsername(), normalCustomer.getPassword());
        this.setStatus("elite");
        this.setDeliveryTime(0, 2);
        this.setDeliveryRate(0);
        this.setDiscountEntitled(10);
        this.setCouponQtyLimit(3, 4);
        this.setCart(normalCustomer.getCart());
//        this.generateCoupons();
        this.setWalletBalance(normalCustomer.getWalletBalance());
    }
    public EliteCustomer(PrimeCustomer primeCustomer) {
        super(primeCustomer.getUsername(), primeCustomer.getPassword());
        this.setStatus("elite");
        this.setDeliveryTime(0, 1);
        this.setDeliveryRate(0);
        this.setDiscountEntitled(10);
        this.setCouponQtyLimit(3, 4);
        this.setCart(primeCustomer.getCart());
//        this.generateCoupons();
        this.setWalletBalance(primeCustomer.getWalletBalance());
    }

    @Override
    public void upgradeStatus() {
        System.out.println("You are already an Elite Customer!");
    }
}
