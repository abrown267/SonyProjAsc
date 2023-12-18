import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;

class Customer {
    String firstName;
    String lastName;
    String dob;
    String ssn;
    

    public Customer(String firstName, String lastName,  String dob, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.ssn = ssn;
        
    }
}

class Transaction {
    static int transactionIdCounter = 1;

    int transactionId;
    String transactionType;
    double amount;

    public Transaction(String transactionType, double amount) {
        this.transactionId = transactionIdCounter++;
        this.transactionType = transactionType;
        this.amount = amount;
    }
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return transactionId + " : " + transactionType + " : $" + df.format(amount);
    }
}

class Account {
    static int accountNumberCounter = 1000;
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return accountNumber + "(" + accountType + ") : " +
                customer.firstName + " : " + customer.lastName + " : " +
                customer.ssn + " : $" + df.format(balance) + " : " +
                (isOpen ? "Account Open" : "Account Closed");
    }

    int accountNumber;
    Customer customer;
    String accountType;
    double balance;
    List<Transaction> transactions;
    boolean isOpen;

	public int overdraftLimit;

    public Account(Customer customer, String accountType, double overdraftLimit2) {
        this.accountNumber = accountNumberCounter++;
        this.customer = customer;
        this.accountType = accountType;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.isOpen = true;

    }

    public void deposit(double amount) {
        if (amount > 0) {
            transactions.add(new Transaction("Credit", amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            transactions.add(new Transaction("Debit", amount));
            balance -= amount;
        }
    }
}

class Bank {
    List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void openCheckingAccount(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter social security number (xxx-xx-xxxx): ");
        String ssn = scanner.nextLine();
        if (!isValidSSNForm(ssn)) {
            System.out.println("Error: Invalid SSN format. Please enter in the format xxx-xxx-xxxx.");
            return;
        }

        System.out.print("Enter date of birth (MM-DD-YYYY): ");
        String dob = scanner.nextLine();
        if (!isValidDateFormat(dob)) {
            System.out.println("Error: Invalid date of birth format. Please enter in the format MM-DD-YYYY.");
            return;
        }

        Customer customer = new Customer(firstName, lastName, ssn, dob);

        if (isEligibleForChecking(customer)) {
        	 double overdraftLimit = 0;

            int age = calculateAge(dob);
            if (age < 16) {
                System.out.println("Sorry, only customers aged 16 and above are allowed to open a checking account.");
                return;
            }

                if (age >= 18) {
                    System.out.println("Enter overdraft limit: ");
                    overdraftLimit = scanner.nextDouble();
                    scanner.nextLine();
                }
            }

            double overdraftLimit = 0;
			Account checkingAccount = new Account(customer, "checking", overdraftLimit);
            accounts.add(checkingAccount);

            System.out.println("Thank you, the account number is " + checkingAccount.accountNumber);
        }
    
    private int calculateAge(String dob) {
 
        int birthMonth = Integer.parseInt(dob.substring(0, 2));
        int birthDay = Integer.parseInt(dob.substring(3, 5));
        int birthYear = Integer.parseInt(dob.substring(6));

        int currentMonth = 12;
        int currentDay = 16;
        int currentYear = 2023;

        int age = currentYear - birthYear;

        if (birthMonth > currentMonth || (birthMonth == currentMonth && birthDay > currentDay)) {
            age--;
        }

        return age;
    }

	private boolean isValidDateFormat(String dob) {
       
        String dateFormatRegex = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-\\d{4}$";
        return dob.matches(dateFormatRegex);
    }

	private boolean isEligibleForChecking(Customer customer) {
        if (customerIsAdult(customer) && !customerIsUnder16(customer)) {
            return true;
        } else {
            System.out.println("Sorry, only customers aged 16 and above are allowed to open a checking account.");
            return false;
        }
    }

    private boolean customerIsAdult(Customer customer) {
        
        int yearOfBirth = Integer.parseInt(customer.dob.substring(6));
        return (2023 - yearOfBirth) >= 18;
    }

    private boolean customerIsUnder16(Customer customer) {
       
        int yearOfBirth = Integer.parseInt(customer.dob.substring(6));
        return (2023 - yearOfBirth) < 16;
    }

    public void openSavingAccount(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        String ssn;
        do {
            System.out.print("Enter social security number (xxx-xx-xxxx): ");
            ssn = scanner.nextLine();
        } while (!isValidSSNForm(ssn));

        System.out.print("Enter date of birth (MM-DD-YYYY): ");
        String dob = scanner.nextLine();
        if (!isValidDateFormat(dob)) {
            System.out.println("Error: Invalid date of birth format. Please enter in the format MM-DD-YYYY.");
            return;
        }

        Customer customer = new Customer(firstName, lastName, ssn, dob);

        if (customerIsUnder5(customer)) {
        	int age =calculateAge1(dob);
        	if(age < 5) {
        		  System.out.println("Sorry, only customers aged 5 and above are allowed to open a savings account.");
                  return;
        	}
        }

        Account savingsAccount = new Account(customer, "savings", 0);
        accounts.add(savingsAccount);

        System.out.println("Thank you, the account number is " + savingsAccount.accountNumber);
    }

    private boolean isValidSSNForm(String ssn) {
        // Validate SSN format using a regular expression
        return ssn.matches("\\d{3}-\\d{2}-\\d{4}");
    }

    private boolean customerIsUnder5(Customer customer) {
        return true;
    }

    private int calculateAge1(String dob) {
        
        int birthMonth = Integer.parseInt(dob.substring(0, 2));
        int birthDay = Integer.parseInt(dob.substring(3, 5));
        int birthYear = Integer.parseInt(dob.substring(6));

       
        int currentMonth = 12;
        int currentDay = 16;
        int currentYear = 2023;

        int age = currentYear - birthYear;

       
        if (birthMonth > currentMonth || (birthMonth == currentMonth && birthDay > currentDay)) {
            age--;
        }

        return age;
    }

    public void listAccounts() { 
    	 System.out.println("Account Number (Account Type) : First Name : Last Name : SSN : Balance : Account Status");

         for (Account account : accounts) {
             System.out.println(account.accountNumber + " (" + account.accountType + ") : " +
                     account.customer.firstName + " : " + account.customer.lastName + " : " +
                     account.customer.ssn + " : $" + account.balance + " : " +
                     (account.isOpen ? "Account Open" : "Account Closed"));
         }
     }
  
	public void accountStatement(Scanner scanner) { 
    	System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); 

        Account account = findAccountByNumber1(accountNumber);

        if (account != null && account.isOpen) {
        	DecimalFormat df = new DecimalFormat("0.00");
            System.out.println("Transaction ID : Type : Amount");
            for (Transaction transaction : account.transactions) {
				System.out.println(transaction.transactionId + " : " +
                        transaction.transactionType + " : $" + df.format(transaction.amount));
            }
            System.out.println("Balance: $" + df.format(account.balance));
        } else {
            System.out.println("Account not found or closed.");
        }
    }
    private Account findAccountByNumber1(int accountNumber) {
        for (Account account : accounts) {
            if (account.accountNumber == accountNumber) {
                return account;
            }
        }
        return null;
    }
    

    public void depositFunds(Scanner scanner) { 
    	 System.out.print("Enter account number: ");
         int accountNumber = scanner.nextInt();
         scanner.nextLine(); 

         Account account = findAccountByNumber1(accountNumber);

         if (account != null) {
             double amount;

             System.out.print("Enter the amount to deposit: $");
             amount = scanner.nextDouble();
             scanner.nextLine(); 
             
             DecimalFormat df = new DecimalFormat("0.00");

             if (account.isOpen || (account.balance + amount >= 0)) {
                 account.deposit(amount);
				System.out.println("Deposit successful, the new balance is: $" + df.format(account.balance));
             } else {
                 System.out.println("Deposit failed, the balance is: $" + df.format(account.balance));
             }
         } else {
             System.out.println("Account not found.");
         }
     }

    public void withdrawFunds(Scanner scanner) { 
    	System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); 
        Account account = findAccountByNumber1(accountNumber);

        if (account != null) {
            double amount;

            System.out.print("Enter the withdrawal amount: $");
            amount = scanner.nextDouble();
            scanner.nextLine();
            
            DecimalFormat df = new DecimalFormat("0.00");

            if ((account.isOpen && (account.balance - amount >= -account.overdraftLimit)) ||
                    (!account.isOpen && (account.balance - amount >= 0))) {
                account.withdraw(amount);
                System.out.println("Withdrawal successful, the new balance is: $" + df.format(account.balance));
            } else {
                System.out.println("Withdrawal failed, the balance is: $" + df.format(account.balance));
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void closeAccount(Scanner scanner) { 
        System.out.print("Enter account number to close: ");
        int accountNumberToClose = scanner.nextInt();
        scanner.nextLine(); 

        Account accountToClose = findAccountByNumber1(accountNumberToClose);

        DecimalFormat df = new DecimalFormat("0.00");

        if (accountToClose != null && accountToClose.isOpen) {
            double closingBalance = accountToClose.balance;
            accountToClose.isOpen = false;

            System.out.println("Account closed, current balance is $" + df.format(closingBalance));
        } else {
            System.out.println("Account not found or already closed.");
        }
    }
}

public class Banking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("1 – Open a Checking account");
            System.out.println("2 – Open Saving Account");
            System.out.println("3 – List Accounts");
            System.out.println("4 – Account Statement");
            System.out.println("5 – Deposit funds");
            System.out.println("6 – Withdraw funds");
            System.out.println("7 – Close an account");
            System.out.println("8 – Exit");

            System.out.print("Please enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    bank.openCheckingAccount(scanner);
                    break;
                case 2:
                    bank.openSavingAccount(scanner);
                    break;
                case 3:
                    bank.listAccounts();
                    break;
                case 4:
                    bank.accountStatement(scanner);
                    break;
                case 5:
                    bank.depositFunds(scanner);
                    break;
                case 6:
                    bank.withdrawFunds(scanner);
                    break;
                case 7:
                    bank.closeAccount(scanner);
                    break;
                case 8:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }
}

