

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Item {
    private String name;
    private String vendor;
    private double price;
    private double cost;
    private double weight;
    private double taxRate;

    public Item(String name, String vendor, double price, double cost, double weight, double taxRate) {
        this.name = name;
        this.vendor = vendor;
        this.price = price;
        this.cost = cost;
        this.weight = weight;
        this.taxRate = taxRate;
    }

    public double calculateTax() {
        return (price * taxRate) / 100.0;
    }

    public double calculateTotalCost() {
        return price + calculateTax();
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        return "Name: " + name +
               "\nVendor: " + vendor +
               "\nPrice: $" + df.format(price) +
               "\nTax: $" + df.format(calculateTax()) +
               "\nTotal: $" + df.format(calculateTotalCost());
    }
}

class Publication extends Item {
    private String author;
    private String publicationMonth;
    private int numPages;

    public Publication(String name, String vendor, double price, double cost, double weight, double taxRate,
                      String author, String publicationMonth, int numPages) {
        super(name, vendor, price, cost, weight, taxRate);
        this.author = author;
        this.publicationMonth = publicationMonth;
        this.numPages = numPages;
    }

    @Override
    public String toString() {
        return super.toString() +
               "\nAuthor: " + author +
               "\nPublication Month: " + publicationMonth +
               "\nNumber of Pages: " + numPages;
    }
}

class Food extends Item {
    private String sellByDate;
    private String useByDate;

    public Food(String name, String vendor, double price, double cost, double weight, double taxRate,
                String sellByDate, String useByDate) {
        super(name, vendor, price, cost, weight, taxRate);
        this.sellByDate = sellByDate;
        this.useByDate = useByDate;
    }

    public String toString() {
        return super.toString() +
               "\nSell By Date: " + sellByDate +
               "\nUse By Date: " + useByDate;
    }
}

class ShoppingCart {
    private List<Item> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int getItemCount() {
        return items.size();
    }

    public double calculateTotalCostWithTax() {
        double totalCost = 0;
        for (Item item : items) {
            totalCost += item.calculateTotalCost();
        }
        return totalCost;
    }

    public void printCartItems() {
        DecimalFormat df = new DecimalFormat("#.00");
        for (Item item : items) {
            System.out.println("Item Details:");
            System.out.println(item);
            System.out.println("------------------------------");
        }
    }
}

public class Cart {
	static DecimalFormat df =new DecimalFormat("#.00");
    public static void main(String[] args) {
        Publication book = new Publication("Hunger Games", "Scholastic", 8.89, 8.99, 1.2, 5.0,
                "Suzanne Collins", "September 2008", 384);

        Food fruit = new Food("Apple", "Ralphs", 1.99, 0.99, 0.2, 2.0,
                "10/25/2023", "10/28/2023");

        Item groceryItem = new Item("Deodorant", "Walgreens", 3.49, 1.99, 0.4, 7.5);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(book);
        cart.addItem(fruit);
        cart.addItem(groceryItem);

        System.out.println("Items in the cart: " + cart.getItemCount());
        try {
			System.out.println("Total Cost (including tax): $" + df.format(cart.calculateTotalCostWithTax()));
		} catch (Exception e) {
		
			e.printStackTrace();
		}

        System.out.println("\nItems in the Cart:");
        cart.printCartItems();
    }
}
