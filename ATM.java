import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ATM{
    // hash map that constains {"client number": Client} entries
    
    // hashmap:
    // client_number | entry
    // -----------------------
    // 28399128      |  Client object for 28399128
    // 83992032      |  Client object for 83992032
    private HashMap<Double, Client> ClientTable;
    private Scanner input;
    
    public ATM(){
        this.input = new Scanner(System.in);
        this.ClientTable = new HashMap<Double, Client>();
        
    }

    public void Initialize() throws IOException{
        try{
            FileReader log = new FileReader("./accountslog.txt");

            boolean done = false;

            String SavedLog = "";
            while(!done){
                int character = log.read();
                if(character == -1){
                    done = true;
                    continue;
                }else{
                    SavedLog += (char) character;
                }
            }
            log.close();
            
            // pin~id~savings_bal~checkings_bal | id~pin~savings_bal~checkings_bal ...
            for(String entry: SavedLog.split("|")){
                if(entry.length() == 0){
                    break;
                }
                // fetches and parses each saved entry
                // pin      id      savings_bal     checkings_bal
                String[] EntryFields = entry.split("~");
                int PIN = Integer.parseInt(EntryFields[0]);
                Double ClientId = Double.parseDouble(EntryFields[1]);
                double CheckingBal = Double.parseDouble(EntryFields[2]);
                double SavingsBal = Double.parseDouble(EntryFields[3]);
                Client val = new Client(PIN, (double) ClientId, CheckingBal, SavingsBal);

                this.ClientTable.put(ClientId, val);
            }
        }catch(Exception e){
            return;
        }
    }

    public void RunLoop() throws IOException{
        while(true){
            System.out.println("would you like to 'login' to an account or 'create' an account");
            System.out.println("enter 'leave' to leave");
            String choice = this.input.nextLine();
            //when a user enters leave they will be broken from the loop
            if (choice.equals("leave")){
                System.out.println("goodbye!");
                break;
            }
            else if (choice.equals("login")){
                //if they chose to login they will be asked for their account number
                System.out.println("Please enter your Client Number");
                //checks to see if next input is a double
                if (this.input.hasNextDouble()){
                    double username = this.input.nextDouble();
                    this.input.nextLine();
                    
                    //try to get the account from the table
                    Client ClientAccount = this.ClientTable.get(username);
                        
                    // if the account doesnt exist
                    if(ClientAccount == null){
                        System.out.println("Account does not exist! please create one");
                    }else{
                         System.out.println("Please enter your PIN");
                        //since they entered a username they are asked for a password
                        if(this.input.hasNextInt()){
                            int password = this.input.nextInt();
                            this.input.nextLine();
                            //try to login to account
                            // login method returns a boolean. so if this is true
                            // it means our login attempt was successful
                            if(ClientAccount.Login(username, password)){
                                // while we are logged in the user is given more choices
                                ClientAccount.PerformAction(this.input);
                            }
                        }   
                    }
                }
            }
            else if(choice.equals("create")){
                // if the user choses to create an account
                // we ask for a pin
                System.out.println("Please enter a pin for your account");
                if (this.input.hasNextInt()){
                    //the pin is correct format (int)
                    //so we generate an account number for them
                    int chosenPIN = this.input.nextInt();
                    this.input.nextLine();
                    double accountNumber = Math.floor(Math.random()*89999999)+10000000;
                    //while the generated number already exists
                    //we generate a new one until it is null
                    while(ClientTable.get(accountNumber) != null){
                        accountNumber = Math.floor(Math.random()*89999999)+10000000;
                    }
                    // creates new client
                    Client newClient = new Client(chosenPIN,accountNumber);
                    
                    // we do a string format or else Java prints in scientific notation
                    // this means we do not use e or decimals
                    System.out.printf("your account number is: %.0f\n",accountNumber);
                    // this puts a new account in the table
                    this.ClientTable.put(accountNumber,newClient);
                }
            }
            
            this.Exit();
        }
    }

    public void Exit() throws IOException{
        // write to a new file, we clear the previous file's contents
        FileWriter AccountsLog = new FileWriter("./accountslog.txt", false);

        for(Client ClientData : this.ClientTable.values()){
            double[] ClosingData = ClientData.ClosingData();
            String EntryString = (int) ClosingData[2] + "~" +
                                        String.format("%.0f", ClientData.ClientNumber) + "~" +
                                        String.format("%.0f", ClosingData[0]) + "~" +
                                        String.format("%.0f", ClosingData[1]);
            AccountsLog.write(EntryString + "|");
        }
        AccountsLog.close();
    }
}