import java.util.Scanner;

public class BankAccount {
    
    Scanner input = new Scanner(System.in);
    
    double balance;
    String type;
    
    //Constructor
    public BankAccount(String type) {
        this.type = type;
    }
    
    //Getter Methods
    public double getBalance() {
        return balance;
    }
    
    public String getType() {
        return type;
    }
    
    //Setter Methods
    public void setBalance(double amount) {
        balance = amount;
    }
    
    public String toString() {
        return type + ": $" + String.format("%.2f", balance);
    }
}