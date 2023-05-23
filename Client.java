import java.util.Scanner;

public class Client{
    private BankAccount Checking;
    private BankAccount Savings;
    public double ClientNumber;
    private int PIN;
    
    private boolean accessible;
    
    public Client(){
        this.Checking = new BankAccount();
        this.Savings = new BankAccount();
        this.ClientNumber = 0;
        this.PIN = 0;
        this.accessible = false;
    }
    
    public Client(int PIN, double numberCreated){
        this.Checking = new BankAccount();
        this.Savings = new BankAccount();
        this.ClientNumber = numberCreated;
        this.PIN = PIN;
        this.accessible = false;
    }

    public Client(int PIN, double numberCreated, double CheckingsBalance, double SavingsBalance){
        this.Checking = new BankAccount(CheckingsBalance);
        this.Savings = new BankAccount(SavingsBalance);
        this.ClientNumber = numberCreated;
        this.PIN = PIN;
        this.accessible = false;
    }
    
    public boolean Login(double enteredClientNumber, int enteredPin){
        if (this.PIN == enteredPin && this.ClientNumber == enteredClientNumber){
            this.accessible = true;
            return true;
        }
        else{
            this.accessible = false;
            return false;
        }
    }
    
    public void PerformAction(Scanner input){
        if(!this.accessible){
            System.out.println("sorry! you have to log in first!");
            return;
        }
        
        BankAccount chosenAccount = new BankAccount();
        boolean done = false;
        
        // force the user to choose an account
        while((chosenAccount != this.Checking) && (chosenAccount != this.Savings)){
            System.out.println("Choose 'checking' or 'savings':");
            String choice = input.nextLine();
            
            if(choice.equals("checking")){
                chosenAccount = this.Checking;
            }else if(choice.equals("savings")){
                chosenAccount = this.Savings;
            }   
        }
        
        // at this point, we know the user has chosen an account
        // we ask them to perform actions
        while(!done){
            System.out.println("you have " + chosenAccount.getBalance() + " dollars");
            System.out.println("would you like to 'withdraw','deposit' or 'switch' accounts?");
            System.out.println("(enter a 'done' line to finish):");
            String choice = input.nextLine();
    
            if(choice.equals("done")){
                done = true;
                continue;
            }
            
            if(choice.equals("withdraw")){
                System.out.println("enter how much you want to withdraw");
                
                try{
                    // ask the user to input how much they want to withdraw
                    // and check if its a double type
                    if(input.hasNextDouble()){
                        double withdrawAmount = input.nextDouble();
                        input.nextLine();
                        chosenAccount.withdraw(withdrawAmount);
                    }else{
                        System.out.println("enter a NUMBER");
                    }
                }
                catch(AccountException e){
                    System.out.println("the amount you are withdrawing exceeds your current balance");
                }
                
            }else if(choice.equals("deposit")){
                
                System.out.println("enter how much you want to deposit");
                // ask the user to input how much they want to deposit
                // check if next input is a double
                if(input.hasNextDouble()){
                    
                    // the user gave a number(double) amount, lets deposit it!
                    chosenAccount.deposit(input.nextDouble());
                    input.nextLine();
                }else{
                    
                    // if it isnt a double, yell at the user
                    System.out.println("enter a NUMBER >:(");
                }
                
            }else if(choice.equals("switch")){
                // the user wants to switch accounts
                if(chosenAccount == this.Checking){
                    // if its currently checking, we set it to savings
                    chosenAccount = this.Savings;
                }else{
                    // if its not checking (savings), we set it to checking
                    chosenAccount = this.Checking;
                }
            }
        }
        
        this.accessible = false;
    }

    public double[] ClosingData(){
        return new double[]{this.Checking.getBalance(), this.Savings.getBalance(), (double) this.PIN};
    }
}
