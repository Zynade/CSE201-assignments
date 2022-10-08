// TODO: when adding products to cart, check if quantity is less than available quantity
// ISSUE: Products from different category but same productID are not differentiated.

import java.util.Scanner;

public class MainCLI {
    static Scanner sc = new Scanner(System.in);
    public static void printMenu() {
        System.out.println("Welcome to FlipZon!");
        System.out.println("\t1. Sign in as admin");
        System.out.println("\t2. Sign in as customer");
        System.out.println("\t3. Explore products");
        System.out.println("\t4. Explore deals");
        System.out.println("\t5. Exit");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.println("Welcome, admin.");
                System.out.print("Enter username: ");
                String username = sc.nextLine();
                System.out.print("Enter password: ");
                String password = sc.nextLine();
                if (FlipZon.signInAsAdmin(username, password)) {
                    System.out.println("Successfully signed in as " + username);

                    AdminCLI.printMenu();
                } else {
                    System.out.println("Invalid credentials!");
                    MainCLI.printMenu();
                }
            }
            case 2 -> {
                CustomerCLI.printLoginOptions();
            }
            case 3 -> {
                MainCLI.printProductCatalog();
                printMenu();
            }
            case 4 -> {
                MainCLI.printDealCatalog();
                printMenu();
            }
            case 5 -> {
                System.out.println("Thank you for shopping with us!");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid choice!");
                printMenu();
            }
        }
    }
    public static void printProductCatalog() {
        System.out.println("Product Catalog");
        System.out.println("---------------");
        FlipZon.exploreProductCatalog();
        System.out.println("---------------");
    }
    public static void printDealCatalog() {
        System.out.println("Deal Catalog");
        System.out.println("---------------");
        FlipZon.exploreDeals();
        System.out.println("---------------");
    }
}
