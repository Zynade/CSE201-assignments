import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.max;

public class Cart {
    HashMap<Integer, Integer> products = new HashMap<>();
    ArrayList<Integer> deals = new ArrayList<>();
    Customer cartOwner;
    private double cartValue;

    public Cart(Customer customer) {
        this.cartOwner = customer;
        this.cartValue = 0;
    }

    public void addProduct(String productID, int quantity) {
        this.addProduct(Product.parseProductIDToInt(productID), quantity);
    }
    public boolean addDeal(int dealID) {
        if (!FlipZon.getDeals().containsKey(dealID)) return false;
        this.getDeals().add(dealID);
        return true;
    }
    public void removeDeal(Integer dealID) {
        this.getDeals().remove(dealID);
    }
    public void addProduct(int productID, int quantity) {
        if (products.containsKey(productID)) {
            products.put(productID, products.get(productID) + quantity);
        } else {
            products.put(productID, quantity);
        }
    }

    public void removeProduct(int productID) {
        products.remove(productID);
    }

    public void clearCart() {
        products.clear();
        deals.clear();
    }

    public HashMap<Integer, Integer> getProducts() {
        return products;
    }
    public ArrayList<Integer> getDeals() {
        return deals;
    }
    public double getCartValue() {
        return cartValue;
    }
    private double calculateDiscountedCostForProduct(int productID) {
        Product product = FlipZon.getProductByID(productID);
        double productCost = product.getPrice() * products.get(productID);
        int entitledDiscount = this.cartOwner.getDiscountEntitled();
        double productDiscount = product.getDiscountRate(this.cartOwner.getStatus());
        double actualDiscount = max(entitledDiscount, productDiscount);
        double discount = productCost * actualDiscount / 100.0;
        return productCost - (productCost * discount);
    }
    public void setCartValue() {
        double cartTotal = 0;
        for (int productID : products.keySet()) {
            cartTotal += this.calculateDiscountedCostForProduct(productID);
        }
        for (int dealID : deals) {
            Deal deal = FlipZon.getDealByID(dealID);
            cartTotal += deal.getDiscountedCost(this.cartOwner);
        }
        this.cartValue = cartTotal;
    }
    public double getDeliveryCharge() {
        double totalCost = this.getCartValue();
        return cartOwner.getDeliveryCharge() + (totalCost * cartOwner.getDeliveryRate() / 100.0);
    }
    public double getNetPayable() {
        if (products.size() == 0) return 0;
        this.setCartValue();
        double totalCost = this.getCartValue();
        int coupon = 0;
        if (totalCost > 5000) coupon = cartOwner.getHighestCoupon();
        if (coupon != 0) totalCost = totalCost * (100 - coupon) / 100.0;
        return totalCost + this.getDeliveryCharge();
    }
    public void printCart() {
        if (products.size() == 0) {
            System.out.println("Your cart is empty!");
//            return 0;
        }
        this.setCartValue();
        System.out.println("Your cart contains:");
        for (int productID : products.keySet()) {
            Product product = FlipZon.getProductByID(productID);
            double discountedCost = this.calculateDiscountedCostForProduct(productID);
            int quantity = products.get(productID);
            System.out.println(product.getName() + " x" + quantity + " @ Rs." + discountedCost);
        }
        double totalCost = this.getCartValue();
        if (deals.size() > 0) {
            System.out.println("You also have the following deals:");
            for (Integer dealID : deals) {
                Deal deal = FlipZon.getDealByID(dealID);
                System.out.println(deal.getProduct_1().getName() + " and " + deal.getProduct_2().getName() + " @ Rs." + deal.getDiscountedCost(this.cartOwner));
                totalCost += deal.getDiscountedCost(this.cartOwner);
            }
        }
        System.out.println("------------------------");
        System.out.println("Total cost: Rs." + totalCost);
        double deliveryCharge = this.getDeliveryCharge();
        int coupon = 0;
        if (totalCost > 5000) {
            coupon = cartOwner.getHighestCoupon();
        }
        if (coupon != 0) {
            System.out.println("Coupon applied: " + coupon + "% off");
            totalCost = totalCost * (100 - coupon) / 100.0;
            System.out.println("Cost after coupon: Rs." + totalCost);
        } else {
            System.out.println("No coupon applied.");
        }
        totalCost += deliveryCharge;
        System.out.println("Delivery charge: Rs." + deliveryCharge);
        System.out.println("------------------------");
        System.out.println("Total payable: Rs." + totalCost);
//        return totalCost;
//        System.out.println("Thank you for ordering. Your order will be delivered in " + cartOwner.getDeliveryTime());
    }
    public void checkout() {
        if (!this.cartOwner.makePayment(this.getNetPayable())) return;
        this.cartOwner.applyHighestCoupon();
        for (int productID : products.keySet()) {
            if (products.get(productID) > FlipZon.getProductByID(productID).getQuantity()) {
                System.out.println("Sorry, we don't have enough stock of " + FlipZon.getProductByID(productID).getName() + " to fulfill your order.");
                System.out.println("Please remove some items from your cart and try again.");
                return;
            }
            Product product = FlipZon.getProductByID(productID);
            product.reduceQuantity(products.get(productID));
        }
        this.clearCart();
        System.out.println("Thank you for ordering. Your order will be delivered in " + cartOwner.getDeliveryTime() + " days.");
//        System.out.println("These are the coupons you have earned:");
        if (this.getCartValue() > 5000) {
            this.cartOwner.generateCoupons();
        }
        this.cartOwner.printCoupons();
        System.out.println("------------------------");
    }
}
