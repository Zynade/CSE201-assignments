import java.util.Scanner;

public class PrimeCustomer extends Customer implements CustomerStatus {
    public PrimeCustomer(String username, String password) {
        super(username, password);
        this.setStatus("prime");
        this.setDeliveryTime(3, 6);
        this.setDeliveryRate(2);
        this.setDiscountEntitled(5);
        this.setCouponQtyLimit(1, 2);
        this.setCart(new Cart(this));
//        this.generateCoupons();
    }
    public PrimeCustomer(NormalCustomer normalCustomer) {
        super(normalCustomer.getUsername(), normalCustomer.getPassword());
        this.setStatus("prime");
        this.setDeliveryTime(3, 6);
        this.setDeliveryRate(2);
        this.setDiscountEntitled(5);
        this.setCouponQtyLimit(1, 2);
        this.setCart(normalCustomer.getCart());
//        this.generateCoupons();
        this.setWalletBalance(normalCustomer.getWalletBalance());
    }

    @Override
    public void upgradeStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.println("You currently have Rs. " + this.getWalletBalance() + " in your wallet.");
        if (this.getWalletBalance() > 100) {
            System.out.println("You can upgrade to the Elite Membership for Rs. 100.");
            System.out.println("Enter 1 to upgrade, 0 to cancel.");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                this.setWalletBalance(this.getWalletBalance() - 100);
                FlipZon.addCustomer(new EliteCustomer(this));
                FlipZon.removeCustomer(this);
                System.out.println("Congratulations, you have been upgraded to Elite Customer!");
            } else {
                return;
            }
        } else {
            System.out.println("You need to have at least Rs. 100 in your wallet to upgrade to Elite Customer.");
        }
    }
}
