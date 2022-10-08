import java.util.Scanner;
// TODO: not started yet. 100% auto-generated.
public class AdminCLI {
    static Scanner sc = new Scanner(System.in);
    public static void printMenu() {
        System.out.println("\n----------------");
        System.out.println("Welcome, " + FlipZon.getLoggedInAdmin().getUsername() + "!");
        System.out.println("""     
            \t1) add a product
            \t2) delete a product
            \t3) add a category
            \t4) delete a category
            \t5) set discount on a product
            \t6) create a deal
            \t7) back
        """);
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> {
                AdminCLI.addProduct();
                AdminCLI.printMenu();
            }
            case 2 -> {
                AdminCLI.deleteProduct();
                AdminCLI.printMenu();
            }
            case 3 -> {
                AdminCLI.addCategory();
                AdminCLI.printMenu();
            }
            case 4 -> {
                AdminCLI.deleteCategory();
                AdminCLI.printMenu();
            }
            case 5 -> {
                AdminCLI.setDiscount();
                AdminCLI.printMenu();
            }
            case 6 -> {
                AdminCLI.createDeal();
                AdminCLI.printMenu();
            }
            case 7 -> {
                System.out.println("Goodbye, admin!");
                MainCLI.printMenu();
            }
            default -> {
                System.out.println("Invalid choice");
                AdminCLI.printMenu();
            }
        }
    }
    public static void addProduct() {
        System.out.println("Adding a product");
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product price in Rs (must be a number): ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter product category ID (int): ");
        int categoryID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter product description (note: product ID is auto-generated): ");
        StringBuilder description = new StringBuilder();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            description.append("\n").append(line);
        }
        System.out.print("Enter product quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        String productID = categoryID + "." + Product.getNextProductID();
        if (FlipZon.getLoggedInAdmin().addProduct(productID, name, description.toString(), price, quantity, categoryID)) {
            System.out.println("Successfully added product " + name + " with Product ID " + productID);
        } else {
            System.out.println("Failed to add product " + name);
        }
    }

    public static void deleteProduct() {
        System.out.println("Deleting a product");
        System.out.print("Enter product ID: ");
        String productID = sc.nextLine();
        try {
            Product product = FlipZon.getProductByID(Product.parseProductIDToInt(productID));
            FlipZon.getLoggedInAdmin().removeProduct(product.getProductID());
        } catch (IllegalArgumentException e) {
            System.out.println("There is no product with that ID!");
        }
    }
    public static void addCategory() {
        System.out.println("Adding a category");
        System.out.print("Enter category ID: ");
        int categoryID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter category name: ");
        String name = sc.nextLine();
        if (FlipZon.getLoggedInAdmin().addCategory(categoryID, name)) {
            System.out.println("Successfully added category " + name);
            AdminCLI.addProduct();
        } else {
            System.out.println("Category ID already exists. Please try again.");
        }
    }
    public static void deleteCategory() {
        System.out.println("Deleting a category");
        System.out.print("Enter category ID: ");
        int categoryID = sc.nextInt();
        if (!FlipZon.getLoggedInAdmin().removeCategory(categoryID)) {
            System.out.println("Category with ID " + categoryID + " does not exist!");
        }
    }
    public static void setDiscount() {
        System.out.println("Setting discount on a product");
        System.out.print("Enter product ID: ");
        String productID = sc.nextLine();
        System.out.println("Enter discount (%) (must be a number): ");
        System.out.print("Normal: ");
        double discountNormal = sc.nextDouble();
        sc.nextLine();
        System.out.print("Prime: ");
        double discountPrime = sc.nextDouble();
        sc.nextLine();
        System.out.print("Elite: ");
        double discountElite = sc.nextDouble();
        sc.nextLine();
        assert discountNormal >= 0 && discountNormal <= 100;
        assert discountPrime >= 0 && discountPrime <= 100;
        assert discountElite >= 0 && discountElite <= 100;
        try {
            Product product = FlipZon.getProductByID(Product.parseProductIDToInt(productID));
            product.setDiscountRate(discountNormal, discountPrime, discountElite);
            System.out.println("Successfully set discount on product " + product.getName());
        } catch (IllegalArgumentException e) {
            System.out.println("There is no product with that ID!");
        } finally {
            AdminCLI.printMenu();
        }
    }
    public static void createDeal() {
        System.out.println("Creating a deal");
        int dealID = Deal.getNextDealID();
        System.out.print("Enter product 1 ID: ");
        String productID1 = sc.nextLine();
        System.out.print("Enter product 2 ID: ");
        String productID2 = sc.nextLine();
        System.out.println("Enter price (must be a number): ");
        System.out.print("Normal: ");
        double priceNormal = sc.nextDouble();
        sc.nextLine();
        System.out.print("Prime: ");
        double pricePrime = sc.nextDouble();
        sc.nextLine();
        System.out.print("Elite: ");
        double priceElite = sc.nextDouble();
        sc.nextLine();

        Product product1 = FlipZon.getProductByID(Product.parseProductIDToInt(productID1));
        Product product2 = FlipZon.getProductByID(Product.parseProductIDToInt(productID2));
        if (Deal.isValid(product1, product2, priceNormal, pricePrime, priceElite)) {
            FlipZon.getLoggedInAdmin().addDeal(dealID, productID1, productID2, priceNormal, pricePrime, priceElite);
            System.out.println("Successfully created deal " + dealID);
        } else {
            System.out.println("Invalid deal!");
        }
        AdminCLI.printMenu();
    }
}
