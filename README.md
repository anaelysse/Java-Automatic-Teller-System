# Java-Automatic-Teller-System
The objective of this project is to design a simulation of an automatic teller machine, also known as an ATM.

A simulation is an imitation of systems that models behavior and operates with essential properties of an actual system. 

In our case we are simulating an electronic banking outlet which enables customers to complete financial transactions through a bank system without a teller.

Bank customers utilize and access banks in different ways.

Every customer is able to have both a savings and checking account under the same ATM Client object. Both these accounts will be able to store money. 

Customers are able to make deposits and withdraw money however many times they need, until they leave.

//

//

//

//

CLIENT CLASS:


Customers are represented by a Client object.
A Client has four field:
PIN: this is a user chosen password to log into the Client object
ClientNumber: this is chosen by the ATM, and is assigned to the user and client object on instantiation. 
Checking Account: This is an instance of the Accounts class.
Savings Account: this is an instance of the Accounts class.

The Client ID and PIN will allow the customer to log into their account through the ATM initially.

The PIN is taken from user integer input through a scanner. This is what we would consider a "passcode."

We make sure that the user enters integers, not other input like strings for the PIN.  This is done by using the hasNextInt and nextInt functions.

The Client ID in our case will be a double. It is a big, and randomized number. 
To obtain the Client ID. The program uses the Math.random() method. This method reduces collision in generated IDs. This is so that customers will get IDs that are different from each other, which brings us to our next point.

Two Client IDs may not be the same. This is simply because unique customers should have unique accounts.

To store our Clients created by the user, we use a table.
In our case, we use a Hashmap specifically.

Hashmaps have the form of:    Hashmap<Key, Value>

For our case specifically, our Hashmap looks like:
Hashmap<Double, Client>

Where Double is the java class Double, and Client is our class Client.

This is because Java hashmaps MUST have objects as both keys AND values.
Therefore, we must use the Double class, not the primitive “double”.

//

//

//

//

BANK ACCOUNT CLASS:


Bank accounts allow for customers to deposit and withdraw money from either "checking" or "savings."

An account should only be able to perform one action at a time.

The client class mentioned above proves that we need to have a code to get into the account, this being said more than one person may have a code.

Example: A married couple may share an account, meaning both partners are able to deposit and withdraw money.

The account will not let two users interact with money at the same time.

Here we will import the ReentrantLock class. This class uses a lock interface and provides simultaneity to methods while accessing shared resources.

Exception

Exceptions are unwanted events in the execution of a java program

In our ATM, we have an exception. 

A user may want to withdraw, or unknowingly withdraw more money than they have stored in their account. When this happens, there is an error

//

//

//

//

ATM Class:


The ATM Class only deals with the Client Classes.
The ATM is the interface between the user and client accounts.
The ATM has 1 field.
Clients: We have an array of Client objects, built from from a Client class

The ATM has 1 method which runs on a loop for user input. It has two responsibilities:
Allow users to CREATE a new account (Client Object)
Allow users to CHOOSE a current account (Log in to existing Client Object)


if the user inputs an amount that they do not own we throw an exception. 

To do this we make it so that if the amount they chose to withdraw is greater than their current balance we tell them they cannot withdraw that, and to input a different amount.

The ATM object should also store the state of different accounts between program executions. To achieve this, before and after the main running loop of the ATM, we have an:
Initialize() function
Exit() function

Initialize should read from a previous saved log using FileReader to restore the state of the ATM.


public void Initialize(){
    Filereader reader = new FileReader(“./AccountsLog.txt”);
    this.ClientTable.put( entry1key, entry1val);
    // restore state from reader
    reader.close()
}

Exit should write to a save log for future executions to be able to restore the state of the ATM.

In the main loop of the ATM, we take in user inputs via scanner.nextLine().

Afterwards, we match it against the options of:
“create”
“login”
“leave”

If the user wants to “create” an account, we ask for their PIN.
We generate them a CustomerNumber, and create a new Client() object for them.

Furthermore, we give them a Checking account and Savings account, with balances set to zero.

If the user wants to “login” to a previous account, we ask them to enter their CustomerNumber and PIN and make sure their inputs are actually a double and an int.

Then we pass their inputs to the actual client object.
The reasoning is that users should not be able to see the PIN and data tied to an object. To prevent that, instead of having the Client return the PIN, we forward the pin to the Client.

Then, the client will process the inputs and set a field “hasAccess” to true or false.

If it is true, it means the pin was correct, and the user may start performing withdraw or deposit actions and check their balances

If it is false, it means the pin was incorrect, and the user must re enter their pin or choose another action, such as creating an account.

Doing it this way prevents the user or ATM interface from ever seeing the actual PIN and data tied to an account. However, it will still give users the ability to log into the account.

After all users are done with the ATM, in the real world, this would be like if the ATM is closed for the night, it will store all new data into an output file through calling its Exit() function.

Just like how Initialize() is tied to the constructor of the ATM class,
Exit() is tied to the destructor of the ATM class.

And through different program runs, it will properly maintain state through its output log


