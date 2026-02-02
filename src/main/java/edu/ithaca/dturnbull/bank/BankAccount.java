package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws IllegalArgumentException if amount is negative
     * @throws InsufficientFundsException if amount is larger than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    public static boolean isEmailValid(String email) {
        // null or empty email is invalid
        if (email == null || email.isEmpty()) {
            return false;
        }

        // email cannot start with a dot
        if (email.startsWith(".")) {
            return false;
        }

        // email cannot contain spaces
        if (email.contains(" ")) {
            return false;
        }

        // email cannot contain illegal characters
        if (email.contains("#") || email.contains("$") || email.contains("%") || email.contains("^") ||
            email.contains("&") || email.contains("*") || email.contains("(") || email.contains(")") ||
            email.contains("=") || email.contains("+") || email.contains("!") || email.contains("~") ) {
            return false;
        }

        // must contain exactly one '@'
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }

        // split into prefix and domain
        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // prefix cannot end with special characters
        if (prefix.endsWith("-") || prefix.endsWith("_") || prefix.endsWith(".")) {
        return false;
        }

        // prefix and domain cannot be empty
        if (prefix.isEmpty() || domain.isEmpty()) {
            return false;
        }

        // no consecutive dots anywhere
        if (email.contains("..")) {
            return false;
        }

        // domain must contain a dot
        if (!domain.contains(".")) {
            return false;
        }

        // top-level domain must be at least 2 characters
        int lastDotIndex = domain.lastIndexOf('.');
        if (domain.length() - lastDotIndex <= 2) {
            return false;
        }

        // passed all checks
        return true;
    }

    /**
     * @param amount
     * @return true if amount is positive and has 2 decimal places or less
     * @return false otherwise
     */
    public static boolean isAmountValid(double amount) {
        return false;
    }

}