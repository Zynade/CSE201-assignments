import java.util.Scanner;

public class CustomerCLI {
    static Scanner sc = new Scanner(System.in);
    public static void printLoginOptions() {
        System.out.println("\t1. Sign up");
        System.out.println("\t2. Log in");
        System.out.println("\t3. Go back");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> {
                CustomerCLI.signUp();
                CustomerCLI.printLoginOptions();
            }
            case 2 -> {
                CustomerCLI.logIn();
            }
            case 3 -> {
                MainCLI.printMenu();
            }
            default -> {
                System.out.println("Invalid choice!");
                CustomerCLI.printLoginOptions();
            }
        }
    }
    public static void printMenu() {
        System.out.println("Welcome, " + FlipZon.getLoggedInUser().getUsername() + "!");
        System.out.println("""
            \t1) browse products
            \t2) browse deals
            \t3) add a product to cart
            \t4) add products in deal to cart
            \t5) view coupons
            \t6) check account balance
            \t7) view cart
            \t8) empty cart
            \t9) checkout cart
            \t10) upgrade customer status
            \t11) Add amount to wallet
            \t12) back
        """);
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> {
                MainCLI.printProductCatalog();
                CustomerCLI.printMenu();
            }
            case 2 -> {
                MainCLI.printDealCatalog();
                CustomerCLI.printMenu();
            }
            case 3 -> {
                CustomerCLI.addProductToCart();
                CustomerCLI.printMenu();
            }
            case 4 -> {
                CustomerCLI.addDealToCart();
                CustomerCLI.printMenu();
            }
            case 5 -> {
                CustomerCLI.printCoupons();
                CustomerCLI.printMenu();
            }
            case 6 -> {
                CustomerCLI.printBalance();
                CustomerCLI.printMenu();
            }
            case 7 -> {
                CustomerCLI.printCart();
                CustomerCLI.printMenu();
            }
            case 8 -> {
                CustomerCLI.emptyCart();
                CustomerCLI.printMenu();
            }
            case 9 -> {
                CustomerCLI.checkoutCart();
                CustomerCLI.printMenu();
            }
            case 10 -> {
                CustomerCLI.upgradeCustomerStatus();
                CustomerCLI.printMenu();
            }
            case 11 -> {
                CustomerCLI.topUpWallet();
                CustomerCLI.printMenu();
            }
            case 12 -> {
                MainCLI.printMenu();
            }
            default -> {
                System.out.println("Invalid choice!");
                CustomerCLI.printMenu();
            }
        }
    }

    public static void signUp() {
        System.out.println("Welcome, new customer!");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (FlipZon.signUpCustomer(username, password)) {
            System.out.println("Successfully signed up as " + username);
        } else {
            System.out.println("Username already taken!");
        }
    }
    public static void logIn() {
        System.out.println("Welcome, customer.");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (FlipZon.signInAsCustomer(username, password)) {
            System.out.println("Successfully logged in as " + username);
            CustomerCLI.printMenu();
        } else {
            System.out.println("Invalid credentials!");
            CustomerCLI.printLoginOptions();
        }
    }
    public static void addProductToCart() {
        System.out.print("Enter product id: ");
        String productId = sc.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        if (FlipZon.getLoggedInUser().addProductToCart(productId, quantity)) {
            System.out.println("Product added to cart!");
        } else {
            System.out.println("Product not found!");
        }
    }
    public static void addDealToCart() {
        System.out.println("Enter deal id: ");
        int dealId = sc.nextInt();
        sc.nextLine();
        if (FlipZon.getLoggedInUser().addDealToCart(dealId)) {
            System.out.println("Deal added to cart!");
        } else {
            System.out.println("Deal not found!");
        }
    }
    public static void printCoupons() {
        System.out.println("Coupons");
        System.out.println("-------");
        FlipZon.getLoggedInUser().getCoupons().forEach(coupon -> System.out.print(coupon + "%\t"));
        System.out.println("-------");
    }
    public static void printBalance() {
        System.out.println("Account balance: " + FlipZon.getLoggedInUser().getWalletBalance());
    }
    public static void printCart() {
        System.out.println("Cart");
        System.out.println("-------");
        FlipZon.getLoggedInUser().getCart().printCart();
        System.out.println("-------");
    }
    public static void emptyCart() {
        FlipZon.getLoggedInUser().getCart().clearCart();
        System.out.println("Cart emptied!");
    }
    public static void checkoutCart() {
        FlipZon.getLoggedInUser().getCart().checkout();
    }
    public static void upgradeCustomerStatus() {
        String status = FlipZon.getLoggedInUser().getClass().getName();
        switch (status) {
            case "NormalCustomer" -> ((NormalCustomer) FlipZon.getLoggedInUser()).upgradeStatus();
            case "PrimeCustomer" -> ((PrimeCustomer) FlipZon.getLoggedInUser()).upgradeStatus();
            case "EliteCustomer" -> ((EliteCustomer) FlipZon.getLoggedInUser()).upgradeStatus();
            default -> System.out.println("Unexpected customer status!");
        }
    }
    public static void topUpWallet() {
        System.out.print("How much do you want to add?: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        FlipZon.getLoggedInUser().setWalletBalance(FlipZon.getLoggedInUser().getWalletBalance() + amount);
    }
}
