package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        // IsEmailValidTest added by Ruth

        // IsEmailValidTest comments added by Rhys
        // Equivalence class: invalid email with no '@' symbol
        // Not a boundary case, just a clearly invalid format
        assertFalse(BankAccount.isEmailValid("invalid.email"));   // missing domain

        // Equivalence class: invalid email with no '@' symbol
        // Not a boundary case, just a clearly invalid format
        assertFalse(BankAccount.isEmailValid("a@b"));             // missing top-level domain
       
        // Equivalence class: invalid email missing username
        // Boundary case since the username length is zero
        assertFalse(BankAccount.isEmailValid("@b.com"));          // missing username
        
        // Equivalence class: valid email with dash in username
        // Not a boundary case, dash is an allowed character
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com"));
        
        // Equivalence class: valid email with dot in username
        // Not a boundary case, common valid format
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        // Equivalence class: simple valid email
        // Boundary case because it uses the minimum username length
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));

        // Equivalence class: simple valid email
        // Boundary case because it uses the minimum username length
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com"));

        // Equivalence class: invalid username ending with special character
        // Boundary case because the special character is right before '@'
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));

        // Equivalence class: invalid username with consecutive dots
        // Not a boundary case, but violates formatting rules
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));

        // Equivalence class: invalid username starting with a dot
        // Boundary case testing invalid starting character
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));

        // Equivalence class: invalid username with illegal character
        // Not a boundary case, tests character restrictions
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));

        // Equivalence class: invalid username with illegal character
        // Not a boundary case, tests character restrictions
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc"));

        // Equivalence class: invalid username with illegal character
        // Not a boundary case, tests character restrictions
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));

        // Equivalence class: invalid username with illegal character
        // Not a boundary case, tests character restrictions
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org"));

        // Equivalence class: standard valid email format
        // Not a boundary case, baseline valid example
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        // Equivalence class: invalid domain suffix too short
        // Boundary case because suffix length is below minimum
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));

        // Equivalence class: invalid domain with illegal character
        // Not a boundary case, tests invalid character handling
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com"));

        // Equivalence class: invalid domain with consecutive dots
        // Not a boundary case, but violates formatting rules
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com"));

        // Equivalence class: invalid email missing domain suffix
        // Boundary case because the domain is incomplete
        assertFalse(BankAccount.isEmailValid("abc.def@mail"));

        // Equivalence class: invalid email with multiple '@' symbols
        // Not a boundary case, but tests overall structure
        assertFalse(BankAccount.isEmailValid("abc.def@@mail"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}