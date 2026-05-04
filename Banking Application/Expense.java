public class Expense extends Transaction {
    
    //Constructor
    public Expense(String purpose, double amount) {
        super("Expense", purpose, amount);
    }
}