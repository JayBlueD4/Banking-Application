import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    
    Scanner input = new Scanner(System.in);
    
    String name;
    String password;
    String email;
    String phoneNumber;
    String cardNumber; 
    ChequingAccount clientChequingAccount;
    SavingsAccount clientSavingsAccount;
    ArrayList<Client> contacts;
    ArrayList<Income> unreceivedMoney;
    ArrayList<Income> incomeList;
    ArrayList<Expense> expensesList;
    ArrayList<Transaction> transactionsList;
    
    //Constructor
    public Client(String name, String password, String email, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        cardNumber = "";
        for (int i = 0; i < 4; i++) {
            if (i != 3)
                cardNumber += (int)(Math.random() * 9000 + 1000) + " ";
            else
                cardNumber += (int)(Math.random() * 9000 + 1000);
        }
        clientChequingAccount = new ChequingAccount();
        clientSavingsAccount = new SavingsAccount();
        contacts = new ArrayList<Client>();
        unreceivedMoney = new ArrayList<Income>();
        incomeList = new ArrayList<Income>();
        expensesList = new ArrayList<Expense>();
        transactionsList = new ArrayList<Transaction>();
    }
    
    //Getter Methods
    public ChequingAccount getChequingAccount() {
        return clientChequingAccount;
    }
    
    public SavingsAccount getSavingsAccount() {
        return clientSavingsAccount;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String toString() {
        return "Card Number: " + cardNumber 
        + "\nName: " + name
        + "\nEmail: " + email
        + "\nPhone Number: " + phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10)
        + "\nPassword: " + password;
    }
    
    public String confidentialToString() {
        return "\nName: " + name
        + "\nEmail: " + email
        + "\nPhone Number: " + phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10);
    }
    
    public ArrayList<Income> getUnreceivedMoney() {
        return unreceivedMoney;
    }
    
    //Actions
    public void deposit() {
        System.out.print("Would you like to deposit into your Chequing Account (1) or Savings Account (2)? ");
        int depositOption = input.nextInt();
        while (depositOption != 1 && depositOption != 2) {
            System.out.print("\u001B[1m\u001B[31mError: Invalid deposit option. Please try again.\n\u001B[0m");
            System.out.print("Would you like to deposit into your Chequing Account (1) or Savings Account (2)? ");
            depositOption = input.nextInt();
        }
        double amount = 0;
        switch (depositOption) {
            case 1: System.out.print("Enter deposit amount: $");
                    amount = input.nextDouble();
                    clientChequingAccount.setBalance(clientChequingAccount.getBalance() + amount);
                    System.out.println("\u001B[1m\u001B[32mDeposit successful! Chequing Account balance now at $" + String.format("%.2f", clientChequingAccount.getBalance()) + "\u001B[0m");
                    break;
            case 2: System.out.print("Enter deposit amount: $");
                    amount = input.nextDouble();
                    clientSavingsAccount.setBalance(clientSavingsAccount.getBalance() + amount);
                    System.out.println("\u001B[1m\u001B[32mDeposit successful! Savings Account balance now at $" + String.format("%.2f", clientSavingsAccount.getBalance()) + "\u001B[0m");
        }
        incomeList.add(new Income("Deposit", amount));
        transactionsList.add(new Income("Deposit", amount));
    }
    
    public void withdraw() {
        System.out.print("Enter withdrawal amount: $");
        double amount = input.nextDouble();
        if (amount > clientChequingAccount.getBalance()) {
            System.out.println("\u001B[1m\u001B[31mError: Insufficient funds. Please deposit/transfer additional money into Chequing Account and try again.\u001B[0m");
        }
        else {
            clientChequingAccount.setBalance(clientChequingAccount.getBalance() - amount);
            System.out.println("\u001B[1m\u001B[32mWithdrawal successful! Chequing Account balance now at $" + String.format("%.2f", clientChequingAccount.getBalance()) + "\u001B[0m");
            if (clientChequingAccount.getBalance() < 100)
                System.out.println("\u001B[1m\u001B[31mWarning: Chequing Account balance below $100.00\u001B[0m");
            expensesList.add(new Expense("Withdrawal", amount));
            transactionsList.add(new Expense("Withdrawal", amount));
        }
    }
    
    public void purchaseItem() {
        System.out.print("What item are you purchasing? ");
	    input.nextLine();
	    String item = input.nextLine();
	    System.out.print("How much does it cost: $");
	    double cost = input.nextDouble();
        if (cost > clientChequingAccount.getBalance()) {
            System.out.println("\u001B[1m\u001B[31mError: insufficient funds. Please deposit/transfer additional money into Chequing Account and try again.\u001B[0m");
        }
        else {
            clientChequingAccount.setBalance(clientChequingAccount.getBalance() - cost);
            System.out.println("\u001B[1m\u001B[32mPurchase successful! Chequing Account balance now at $" + String.format("%.2f", clientChequingAccount.getBalance()) + "\u001B[0m");
            if (clientChequingAccount.getBalance() < 100)
                System.out.println("\u001B[1m\u001B[31mWarning: Chequing Account balance below $100.00\u001B[0m");
            expensesList.add(new Expense(item, cost));
            transactionsList.add(new Expense(item, cost));
        }
    }
    
    public void internalTransfer() {
        System.out.print("Do you want to transfer from Chequing Account to Savings Account (1) or from Savings Account to Chequing Account (2)? ");
        int transferOption = input.nextInt();
        while (transferOption != 1 && transferOption != 2) {
            System.out.print("\u001B[1m\u001B[31mError: Invalid transfer option. Please try again.\n\u001B[0m");
            System.out.print("Do you want to transfer from Chequing Account to Savings Account (1) or from Savings Account to Chequing Account (2)? ");
            transferOption = input.nextInt();
        }
        
        switch (transferOption) {
            case 1: if (transfer(clientChequingAccount, clientSavingsAccount)) {
                        System.out.println("\u001B[1m\u001B[32mTransfer Successful!\n\u001B[0m");
                        this.displayBalances();
                        System.out.println();
                        if (clientChequingAccount.getBalance() < 100)
                            System.out.println("\u001B[1m\u001B[31mWarning: Chequing Account balance below $100.00\u001B[0m");
                    }
                    break;
            case 2: if (transfer(clientSavingsAccount, clientChequingAccount)) {
                        System.out.println("\u001B[1m\u001B[32mTransfer successful!\n\u001B[0m");
                        this.displayBalances();
                    }
        }
    }
    
    public boolean transfer(BankAccount givingAccount, BankAccount receivingAccount) {
        boolean transferSuccessful = false;
        System.out.print("Enter transfer amount: $");
        double amount = input.nextDouble();
        if (amount > givingAccount.getBalance()) {
            System.out.println("\u001B[1m\u001B[31mError: Insufficient funds. Please deposit additional money into " + givingAccount.getType() + " and try again.\u001B[0m");
        }
        else {
            givingAccount.setBalance(givingAccount.getBalance() - amount);
            receivingAccount.setBalance(receivingAccount.getBalance() + amount);
            transferSuccessful = true;
        }
        return transferSuccessful;
    }
    
    public void addContact(ArrayList<Client> clientList) {
        System.out.print("Please enter contact's email or phone # (e.g. 7781294713): ");
        String contactInfo = input.next();
        
        // If user provides invalid input
        while (!((contactInfo.matches("[0-9]+") && contactInfo.length() == 10) || contactInfo.contains("@"))) {
            System.out.println("\u001B[1m\u001B[31mError: Invalid input. Please try again.\u001B[0m");
            System.out.print("Please enter a valid email or phone # (e.g. 7781294713): ");
            contactInfo = input.next();
        }
        
        // If user attempts to add themselves
        if (contactInfo.equals(this.phoneNumber) || contactInfo.equals(this.email)) {
            System.out.println("\u001B[1m\u001B[31mError: Cannot add self.\u001B[0m");
            return;
        }
        
        int contactIndex = findContact(contactInfo, clientList);
        if (contactIndex == -1)
            System.out.println("\u001B[1m\u001B[31mError: Account not found.\u001B[0m");
        else {
            if (findContact(contactInfo, contacts) > -1) {
                System.out.println("\u001B[1m\u001B[31mError: Contact \u001B[37m\u001B[4m" + clientList.get(contactIndex).getName() + "\u001B[0m\u001B[1m\u001B[31m already exists.\u001B[0m");
            }
            else {
                contacts.add(clientList.get(contactIndex));
                System.out.println("\u001B[1m\u001B[32mContact \u001B[37m\u001B[4m" + contacts.get(contacts.size() - 1).getName() + "\u001B[0m\u001B[1m\u001B[32m successfully added!\u001B[0m");
            }
        }
    }
    
    public void eTransfer() {
        System.out.print("Please enter recipient's email or phone # (e.g. 7781294713): ");
        String contactInfo = input.next();
        while (!((contactInfo.matches("[0-9]+") && contactInfo.length() == 10) || contactInfo.contains("@"))) {
            System.out.println("\u001B[1m\u001B[31mError: Invalid input. Please try again.\u001B[0m");
            System.out.print("Please enter a valid email or phone # (e.g. 7781294713): ");
            contactInfo = input.next();
        }
        int recipientIndex = findContact(contactInfo, contacts);
        if (recipientIndex == -1)
            System.out.println("\u001B[1m\u001B[31mError: Contact not found. Please review your contacts and try again.\u001B[0m");
        else {
            System.out.print("Enter transfer amount: $");
            double amount = input.nextDouble();
            System.out.print("You wish to send $" + amount + " to " + contacts.get(recipientIndex).getName() + ". Confirm (1) / Cancel (2): ");
            int confirmationOption = input.nextInt();
            while (confirmationOption != 1 && confirmationOption != 2) {
                System.out.println("\u001B[1m\u001B[31mError: invalid confirmation response. Please try again.\u001B[0m");
                System.out.print("You wish to send $" + String.format("%.2f", amount) + " to " + contacts.get(recipientIndex).getName() + ". Confirm (1) / Cancel (2): ");
                confirmationOption = input.nextInt();
            }
            switch (confirmationOption) {
                case 1: if (amount > clientChequingAccount.getBalance()) {
                            System.out.println("\u001B[1m\u001B[31mError: Insufficient funds. Please deposit additional money into Chequing Account and try again.\u001B[0m");
                        }
                        else {
                            clientChequingAccount.setBalance(clientChequingAccount.getBalance() - amount);
                            contacts.get(recipientIndex).getUnreceivedMoney().add(new Income("E-Transfer from " + name, amount));
                            expensesList.add(new Expense("E-Transfer to " + contacts.get(recipientIndex).getName(), amount));
                            transactionsList.add(new Expense("E-transfer to " + contacts.get(recipientIndex).getName(), amount));
                            System.out.println("\u001B[1m\u001B[32mTransfer successful. $" + String.format("%.2f", amount) + " have been transferred to " + contacts.get(recipientIndex).getName() + "\u001B[0m");
                            if (clientChequingAccount.getBalance() < 100)
                                System.out.println("\u001B[1m\u001B[31mWarning: Chequing Account balance below $100.00\u001B[0m");
                        }
                        break;
                case 2: System.out.println("\u001B[1m\u001B[31mE-Transfer Cancelled.\u001B[0m");
            }
        }
    }
    
    public int findContact(String contactInfo, ArrayList<Client> clientList) {
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getEmail().equals(contactInfo) || clientList.get(i).getPhoneNumber().equals(contactInfo))
                return i;
        }
        return -1;
    }
    
    public void receiveMoney() {
        if (unreceivedMoney.size() == 0) {
            System.out.println("No unreceived money.");
        }
        else {
            for (Income eTransfer : unreceivedMoney) {
                System.out.println("- " + eTransfer.toString());
            }
            System.out.print("You have " + unreceivedMoney.size() + " unclaimed E-Transfers. Would you like to claim them? Yes (1) or No (2): ");
            int claimOption = input.nextInt();
            while (claimOption != 1 && claimOption != 2) {
                System.out.println("\u001B[1m\u001B[31mError: invalid option. Please try again.\u001B[0m");
                System.out.print("Claim E-Transfers (1), Don't claim (2): ");
                claimOption = input.nextInt();
            }
            if (claimOption == 1) {
                while (unreceivedMoney.size() > 0) {
                    clientChequingAccount.setBalance(clientChequingAccount.getBalance() + unreceivedMoney.get(0).getAmount());
                    incomeList.add(unreceivedMoney.get(0));
                    transactionsList.add(unreceivedMoney.get(0));
                    unreceivedMoney.remove(0);
                }
                System.out.println("\u001B[1m\u001B[32mAll E-Transfers successfully claimed! Chequing Account balance is now $" + String.format("%.2f", clientChequingAccount.getBalance()) + "\u001B[0m");
            }
        }
    }
    
    public void changeAccountInfo() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the number corresponding to the credential you wish to change (1 = name, 2 = email, 3 = phone number, 4 = password): ");
        int changeOption = input.nextInt();
        while (changeOption < 1 || changeOption > 4) {
            System.out.println("\u001B[1m\u001B[31mError: invalid choice. Please try again.\u001B[0m");
            System.out.print("Please enter the number corresponding to the credential you wish to change (1 = name, 2 = email, 3 = phone number, 4 = password): ");
            changeOption = input.nextInt();
        }
        System.out.println();
        switch (changeOption) {
            case 1: System.out.print("Please enter a new name: ");
                    input.nextLine(); 
                    name = input.nextLine();
                    System.out.println("\u001B[1m\u001B[32mName successfully changed.\u001B[0m");
                    break;
            case 2: System.out.print("Please enter a new email: ");
                    String newEmail = input.next();
                    while (!newEmail.contains("@")) {
                        System.out.println("\u001B[1m\u001B[31mError: invalid email format. Please try again.\u001B[0m");
                        System.out.print("Please enter a new email: ");
                        newEmail = input.next();
                    }
                    email = newEmail;
                    System.out.println("\u001B[1m\u001B[32mEmail successfully changed.\u001B[0m");
                    break;
            case 3: System.out.print("Please enter a new phone number (e.g. 7781427879): ");
                    String newPhoneNumber = input.next();
                    while (!(newPhoneNumber.matches("[0-9]+") && newPhoneNumber.length() == 10)) {
                        System.out.println("\u001B[1m\u001B[31mError: invalid phone number format. Please try again.\u001B[0m");
                        System.out.print("Please enter a new phone number (e.g. 7781572559): ");
                        newPhoneNumber = input.next();
                    }
                    phoneNumber = newPhoneNumber;
                    System.out.println("\u001B[1m\u001B[32mPhone number successfully changed.\u001B[0m");
                    break;
            case 4: System.out.print("Please enter a new password: ");
                    String newPassword = input.next();
                    password = newPassword;
                    System.out.println("\u001B[1m\u001B[32mPassword successfully changed.\u001B[0m");
        }
    }
    
    public void displayContacts() {
        for (Client contact : contacts) {
            System.out.println(contact.confidentialToString() + "\n");
        }
        System.out.println("You have \u001B[1m\u001B[4m" + contacts.size() + "\u001B[0m contact(s)");
    }
    
    public void displayBalances() {
        System.out.println("Account Balances:");
        System.out.println(clientChequingAccount);
        System.out.println(clientSavingsAccount);
    }
    
    public void displayIncome() {
        System.out.println("Income: ");
        for (Income incomeItem : incomeList) {
            System.out.println("- " + incomeItem.toString());
        } 
    }
    
    public void displayExpenses() {
        System.out.println("Expenses: ");
        for (Expense expenseItem : expensesList) {
            System.out.println("- " + expenseItem);
        }
    }
    
    public void displayTransactionSummary() {
        System.out.println("You have made \u001B[1m\u001B[4m" + transactionsList.size() + "\u001B[0m transaction(s)");
        System.out.println();
        displayIncome();
        System.out.println();
        displayExpenses();
        System.out.println();
        
        double netIncome = 0;
        for (int i = 0; i < incomeList.size(); i++) {
            netIncome += incomeList.get(i).getAmount();
        }
        double netExpenses = 0;
        for (int i = 0; i < expensesList.size(); i++) {
            netExpenses += expensesList.get(i).getAmount();
        }
        double netTotal = netIncome - netExpenses;
        System.out.println("Net Total: " + ((netTotal >= 0) ? "$" + String.format("%.2f", netTotal) : "-$" + String.format("%.2f", netTotal * -1)));
    }
}