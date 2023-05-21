import java.util.concurrent.locks.ReentrantLock;

public class BankAccount{
    private double balance;
    // two actions on the same account cannot be performed at same time
    // so we create a lock
    private ReentrantLock accountLock;
    
    public BankAccount(){
        this.balance=0;
        this.accountLock = new ReentrantLock();
    }
    public BankAccount(double initialBalance){
        this.balance=initialBalance;
        this.accountLock = new ReentrantLock();
    }
    public void deposit(double amount){
        this.accountLock.lock();
        this.balance+=amount;
        this.accountLock.unlock();
    }
    public void withdraw(double amount) throws AccountException{
        // if the user inputs an amount that they do not own we throw an exception
        if(amount>balance){
            throw new AccountException();
        }
        this.accountLock.lock();
        this.balance-=amount;
        this.accountLock.unlock();
    }
    public double getBalance(){
        return balance; //instance variable
    }
    
    
}
