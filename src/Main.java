import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BurgerMasr {
    public static void main(String[] args) {
        UserMain userMain = new UserMain();
        AdminMain adminMain = new AdminMain();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1- User");
            System.out.println("2- Admin");
            int choice = scanner.nextInt();
            if (choice == 1) {
                userMain.showMenu();
            } else if (choice == 2) {
                adminMain.adminMenu();
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}

class UserMain {
    private Menu menu = Menu.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println("Welcome to our restaurant");
        System.out.println("Menu:");
        menu.displayMenu();

        System.out.println("Please select option:");
        int sandwichChoice = scanner.nextInt();

        System.out.println("Do you like to add any additions?");
        System.out.println("1- Yes");
        System.out.println("2- No");
        int addChoice = scanner.nextInt();
        ArrayList<String> additions = new ArrayList<>();

        if (addChoice == 1) {
            menu.displayAdditions();
            System.out.println("Please select options (like this 1, 2):");
            scanner.nextLine(); // consume newline
            String additionChoices = scanner.nextLine();
            String[] additionArray = additionChoices.split(",");
            for (String addition : additionArray) {
                additions.add(menu.getAddition(Integer.parseInt(addition.trim())));
            }
        }

        System.out.println("Your order is:");
        System.out.println(menu.getSandwich(sandwichChoice));
        for (String addition : additions) {
            System.out.println(addition);
        }

        System.out.println("1- Confirm");
        System.out.println("2- Back to Menu");
        int confirmChoice = scanner.nextInt();
        if (confirmChoice == 1) {
            generateReceipt(sandwichChoice, additions);
        } else {
            showMenu();
        }
    }

    private void generateReceipt(int sandwichChoice, ArrayList<String> additions) {
        double total = 0;
        String sandwich = menu.getSandwich(sandwichChoice);
        System.out.println("Receipt:");
        System.out.println("1 " + sandwich + " " + menu.getPrice(sandwichChoice));
        total += menu.getPrice(sandwichChoice);

        for (String addition : additions) {
            System.out.println("1 Extra " + addition + " " + menu.getAdditionPrice(addition));
            total += menu.getAdditionPrice(addition);
        }

        System.out.println("Total: " + total);
        System.out.println("1- Back to Menu");
        System.out.println("2- Exit");
        int choice = scanner.nextInt();
        if (choice == 1) {
            showMenu();
        } else {
            System.exit(0);
        }
    }
}

class AdminMain {
    private Menu menu = Menu.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void adminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1- View Menu");
        System.out.println("2- View Additions");
        System.out.println("3- Add New Item to Menu");
        System.out.println("4- Add New Addition");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                menu.displayMenu();
                break;
            case 2:
                menu.displayAdditions();
                break;
            case 3:
                addNewSandwich();
                break;
            case 4:
                addNewAddition();
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                adminMenu();
                break;
        }
        adminMenu();
    }

    private void addNewSandwich() {
        System.out.println("New Sandwich:");
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.println("Confirm: Y/N");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            menu.addSandwich(name, price);
        }
    }

    private void addNewAddition() {
        System.out.println("New Addition:");
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.println("Confirm: Y/N");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            menu.addAddition(name, price);
        }
    }
}

class Menu {
    private static Menu instance = new Menu();
    private Map<Integer, String> sandwiches = new HashMap<>();
    private Map<String, Double> sandwichPrices = new HashMap<>();
    private Map<Integer, String> additions = new HashMap<>();
    private Map<String, Double> additionPrices = new HashMap<>();

    private Menu() {
        sandwiches.put(1, "Beef Burger");
        sandwiches.put(2, "Chicken Burger");
        sandwiches.put(3, "Cheese Burger");

        sandwichPrices.put("Beef Burger", 80.0);
        sandwichPrices.put("Chicken Burger", 70.0);
        sandwichPrices.put("Cheese Burger", 60.0);

        additions.put(1, "Catchup");
        additions.put(2, "Mayonnaise");
        additions.put(3, "Tomatoes");
        additions.put(4, "Cheese");

        additionPrices.put("Catchup", 5.0);
        additionPrices.put("Mayonnaise", 10.0);
        additionPrices.put("Tomatoes", 5.0);
        additionPrices.put("Cheese", 10.0);
    }

    public static Menu getInstance() {
        return instance;
    }

    public void displayMenu() {
        for (Map.Entry<Integer, String> entry : sandwiches.entrySet()) {
            System.out.println(entry.getKey() + "- " + entry.getValue());
        }
    }

    public void displayAdditions() {
        for (Map.Entry<Integer, String> entry : additions.entrySet()) {
            System.out.println(entry.getKey() + "- " + entry.getValue());
        }
    }

    public String getSandwich(int choice) {
        return sandwiches.get(choice);
    }

    public double getPrice(int choice) {
        return sandwichPrices.get(sandwiches.get(choice));
    }

    public String getAddition(int choice) {
        return additions.get(choice);
    }

    public double getAdditionPrice(String addition) {
        return additionPrices.get(addition);
    }

    public void addSandwich(String name, double price) {
        int newId = sandwiches.size() + 1;
        sandwiches.put(newId, name);
        sandwichPrices.put(name, price);
    }

    public void addAddition(String name, double price) {
        int newId = additions.size() + 1;
        additions.put(newId, name);
        additionPrices.put(name, price);
    }
}
