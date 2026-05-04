/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args) {
	    Scanner input = new Scanner(System.in);
	    ArrayList<Client> clientList = new ArrayList<Client>();
	    //Initial Login
	    Client currentClient = logIn(clientList);
	    while (true) {
	        System.out.println();
	        switch (promptForAction()) {
	            case 1: System.out.println();
	                    System.out.println(currentClient);
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 2: System.out.println();
	                    currentClient.displayBalances();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 3: System.out.println();
	                    currentClient.internalTransfer();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 4: System.out.println();
	                    currentClient.deposit();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 5: System.out.println();
	                    currentClient.withdraw();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 6: System.out.println();
	                    currentClient.purchaseItem();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 7: System.out.println();
	                    currentClient.eTransfer();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 8: System.out.println();
	                    currentClient.receiveMoney();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 9: System.out.println();
	                    currentClient.displayExpenses();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 10: System.out.println();
	                    currentClient.displayIncome();
	                    System.out.println();
	                    exitAction();
	                    break;
	            case 11: System.out.println();
	                     currentClient.displayTransactionSummary();
	                     System.out.println();
	                     exitAction();
	                     break;
	            case 12: System.out.println();
	                     currentClient.addContact(clientList);
	                     System.out.println();
	                     exitAction();
	                     break;
	            case 13: System.out.println();
	                     currentClient.displayContacts();
	                     System.out.println();
	                     exitAction();
	                     break;
	            case 14: System.out.println();
	                     currentClient.changeAccountInfo();
	                     System.out.println();
	                     exitAction();
	                     break;
	            case 15: System.out.println();
	                     logOut();
	                     currentClient = logIn(clientList);
	        }
	    }
	}
	
	public static int promptForAction() {
	    Scanner input = new Scanner(System.in);
	    displayActions();
	    int chosenAction = input.nextInt();
	    while (chosenAction < 1 || chosenAction > 15) {
	        System.out.print("Sorry, that is not an option! Please try again: ");
	        chosenAction = input.nextInt();
	    }
	    return chosenAction;
	}
	
	public static Client logIn(ArrayList<Client> clientList) {
	    Scanner input = new Scanner(System.in);
        System.out.print("\u001B[44m\u001B[1m\u001B[33mWelcome to CodersBank! Already have an account? Sign in (1). New to CodersBank? Create an account (2):\u001B[0m ");
        int clientIndex = -1;
        while (clientIndex == -1) {
            int signInOption = input.nextInt();
            while (signInOption != 1 && signInOption != 2) {
                System.out.println("\u001B[1m\u001B[31mError: invalid sign-in option. Please try again.\u001B[0m");
                System.out.print("Sign In (1) or Create An Account (2): ");
                signInOption = input.nextInt();
            }
            switch (signInOption) {
                case 1: System.out.print("\nEmail/Phone # (e.g. 7788241259): ");
                        String loginCredential = input.next();
                        while (!((loginCredential.matches("[0-9]+") && loginCredential.length() == 10) || loginCredential.contains("@"))) {
                            System.out.println("\u001B[1m\u001B[31mError: Invalid input. Please try again.\u001B[0m");
                            System.out.print("Please enter a valid email or phone # (e.g. 7781294713): ");
                            loginCredential = input.next();
                        }
                        System.out.print("Password: ");
                        String inputtedPassword = input.next();
                        for (int i = 0; i < clientList.size(); i++) {
                            if ((clientList.get(i).getEmail().equals(loginCredential) || clientList.get(i).getPhoneNumber().equals(loginCredential)) && clientList.get(i).getPassword().equals(inputtedPassword))
                                clientIndex = i;
                        }
                        if (clientIndex == -1) {
                            System.out.println("\u001B[1m\u001B[31mInvalid login credentials. Please try again.\u001B[0m");
                            System.out.print("Sign In (1) or Create An Account (2): ");
                        }
                        break;
                case 2: System.out.print("\n\u001B[1m\u001B[4mName:\u001B[0m ");
                        input.nextLine();
                        String name = input.nextLine();
                        System.out.print("\u001B[1m\u001B[4mEmail:\u001B[0m ");
                        String email = input.next();
                        while (!email.contains("@")) {
                            System.out.println("\u001B[1m\u001B[31mError: Invalid email format. Please try again.\u001B[0m");
                            System.out.print("\u001B[1m\u001B[4mEmail:\u001B[0m ");
                            email = input.next();
                        }
                        System.out.print("\u001B[1m\u001B[4mPhone Number (e.g. 7788231457):\u001B[0m ");
                        String phoneNumber = input.next();
                        while (!(phoneNumber.matches("[0-9]+") && phoneNumber.length() == 10)) {
                            System.out.println("\u001B[1m\u001B[31mError: Invalid phone number format. Please try again.\u001B[0m");
                            System.out.print("\u001B[1m\u001B[4mPhone # (e.g.7788124729):\u001B[0m ");
                            phoneNumber = input.next();
                        }
                        System.out.print("\u001B[1m\u001B[4mPassword:\u001B[0m ");
                        String password = input.next(); //make a method for creating a valid password later
                        clientList.add(new Client(name, password, email, phoneNumber));
                        clientIndex = clientList.size() - 1;
            }
        }
        clearScreen();
        System.out.println("\nWelcome \u001B[1m\u001B[4m" + clientList.get(clientIndex).getName() + "\u001B[0m!");
        return clientList.get(clientIndex);
    }
    
    public static void logOut() {
        clearScreen();
    }
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void exitAction() {
        Scanner input = new Scanner(System.in);
        System.out.print("{Press ENTER to return to actions page}");
        String response = input.nextLine();
        while (!response.equals("")) {
            System.out.println("\u001B[1m\u001B[31mError: invalid input. Please try again.\u001B[0m");
            System.out.print("{Press ENTER to return to actions page}");
            response = input.nextLine();
        }
        clearScreen();
    }
    
    public static void displayActions() {
        System.out.println("Choose one of the following actions:");
        System.out.println("- 1: View Account Info");
	    System.out.println("- 2: Check Account Balances");
	    System.out.println("- 3: Transfer Between Accounts");
	    System.out.println("- 4: Deposit");
	    System.out.println("- 5: Withdraw");
	    System.out.println("- 6: Make a Purchase With Bank Card");
	    System.out.println("- 7: Send Money (E-Transfer)");
	    System.out.println("- 8: Receive Money (E-Transfer)");
	    System.out.println("- 9: View Expenses");
	    System.out.println("- 10: View Income");
	    System.out.println("- 11: View Transaction Summary");
	    System.out.println("- 12: Add Contact");
	    System.out.println("- 13: View Contacts");
	    System.out.println("- 14: Change Account Info");
	    System.out.println("- 15: Sign Out");
	    System.out.print("\nUser Chooses Action: ");
    }
}
