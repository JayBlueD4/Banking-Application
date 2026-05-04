public class Transaction {
    
    String type;
    String purpose;
    double amount;
    
    //Constructor
    public Transaction(String type, String purpose, double amount) {
        this.type = type;
        this.purpose = purpose;
        this.amount = amount;
    }
    
    //Getter Methods
    public double getAmount() {
        return amount;
    }
    
    public String toString() {
        return type + ": " + String.format("%-44s", purpose) + "$" + String.format("%,.2f", amount);
    }
}