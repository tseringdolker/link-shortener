import java.io.*;
import java.util.*;

// Expense class to represent an expense entry
class Expense {
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount;
    }
}

// ExpenseTracker class to manage expenses
class ExpenseTracker {
    private List<Expense> expenses;
    private static final String FILE_NAME = "expenses.txt";

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        loadExpenses(); // Load expenses from file when the tracker starts
    }

    // Add a new expense
    public void addExpense(String date, String category, double amount) {
        expenses.add(new Expense(date, category, amount));
        saveExpenses(); // Save expenses to file after adding
    }

    // List all expenses
    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    // Show total expenses by category
    public void showCategorySummation() {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.put(expense.getCategory(), categoryTotals.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        if (categoryTotals.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                System.out.println("Category: " + entry.getKey() + ", Total: $" + entry.getValue());
            }
        }
    }

    // Save expenses to a file
    private void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.write(expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    // Load expenses from a file
    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    expenses.add(new Expense(date, category, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses from file: " + e.getMessage());
        }
    }
}

// Main class to run the Expense Tracker
public class Project3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Show Category-wise Summation");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    tracker.addExpense(date, category, amount);
                    break;
                case 2:
                    tracker.listExpenses();
                    break;
                case 3:
                    tracker.showCategorySummation();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}