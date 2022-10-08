import java.util.Scanner;

public class NormalCustomer extends Customer implements CustomerStatus {
    public NormalCustomer(String username, String password) {
        super(username, password);
        this.setStatus("normal");
        this.setDeliveryTime(7, 10);
        this.setDeliveryRate(5);
        this.setDiscountEntitled(0);
        this.setCouponQtyLimit(0, 0);
        this.setCart(new Cart(this));
    }

    @Override
    public void upgradeStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.println("You currently have Rs. " + this.getWalletBalance() + " in your wallet.");
        if (this.getWalletBalance() > 300) {
            System.out.println("You can upgrade to the Elite Membership for Rs. 300.");
            System.out.println("You can also upgrade to the Prime Membership for Rs. 200.");
            System.out.println("Enter 2 to upgrade to Elite, 1 to upgrade to Prime, 0 to cancel.");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 2) {
                this.setWalletBalance(this.getWalletBalance() - 300);
                FlipZon.addCustomer(new EliteCustomer(this));
                FlipZon.removeCustomer(this);
                System.out.println("Congratulations, you have been upgraded to Elite Customer!");
            } else if (choice == 1) {
                this.setWalletBalance(this.getWalletBalance() - 200);
                FlipZon.addCustomer(new PrimeCustomer(this));
                FlipZon.removeCustomer(this);
                System.out.println("Congratulations, you have been upgraded to Prime Customer!");
            } else {
                return;
            }
        } else if (this.getWalletBalance() > 200) {
            System.out.println("You can upgrade to the Prime Membership for Rs. 200.");
            System.out.println("Enter 1 to upgrade to Prime, 0 to cancel.");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                this.setWalletBalance(this.getWalletBalance() - 200);
                FlipZon.addCustomer(new PrimeCustomer(this));
                FlipZon.removeCustomer(this);
                System.out.println("Congratulations, you have been upgraded to Prime Customer!");
            } else {
                return;
            }
        } else {
            System.out.println("You need at least Rs. 200 in your wallet to upgrade your membership.");
        }
    }
}
