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

        /* Using Equivalence Partitioning and Boundary Value Analysis */
        assertEquals(200, bankAccount.getBalance(), 0.001); // Normal case

        BankAccount emptyAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, emptyAccount.getBalance(), 0.001);   // Boundary case: zero balance

        BankAccount negativeBalanceAccount = new BankAccount("a@b.com", -100);
        assertThrows(IllegalArgumentException.class, () -> negativeBalanceAccount.getBalance()); // Invalid case: negative starting balance
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        /* Using Equivalence Partitioning and Boundary Value Analysis */
        
        // Equivalence class: valid withdrawal amounts (0 < amount <= balance)
        bankAccount.withdraw(50); // valid amount
        assertEquals(50, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(50); // boundary case: withdrawing remaining balance (amount = balance)
        assertEquals(0, bankAccount.getBalance(), 0.001);

        // Equivalence class: invalid withdrawal amounts
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(10)); // invalid: exceeds balance
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-20)); // invalid: negative amount
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
    void isEmailValidTestEP(){
        // Equivalence Partitions Testing for isEmailValid

        // Equivalence class - username length
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));
        assertTrue(BankAccount.isEmailValid("a@mail.com")); // boundary case
        assertFalse(BankAccount.isEmailValid("@mail.com")); // invalid case

        // Equivalence class - domain length
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc@a.com")); // boundary case
        // assertFalse(BankAccount.isEmailValid("abc@.com")); // invalid case

        // Equivalence class - tld length
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc@mail.cc")); // boundary case
        assertFalse(BankAccount.isEmailValid("abc@mail.c")); // invalid case
        assertFalse(BankAccount.isEmailValid("abc@mail.")); // invalid case

        // Equivalence class - valid characters in username
        assertTrue(BankAccount.isEmailValid("abc.de__f--123@mail.com"));
        assertFalse(BankAccount.isEmailValid("-_@mail.com")); // invalid case
        assertFalse(BankAccount.isEmailValid("-name_@mail.com")); // invalid case
        assertFalse(BankAccount.isEmailValid("((@mail.com")); // invalid case

        // Equivalence class - valid characters in domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // invalid case

        // Equivalence class - valid characters in tld
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.c-o_m"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c#om")); // invalid case

        // Equivalence class - only one '@' symbol
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@@mail.com")); // invalid case

        // Equivalence class - no consecutive dots
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc..def@mail..com")); // invalid case

        // Equivalence class - must have a dot in domain (after '@')
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mailcom")); // invalid case

        // Equivalence class - no spaces
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc def@mail.com")); // invalid case

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    @Test
    void isAmountValidTest(){
        /* EPs are:
        positive numbers 0 to infinity (valid)
        decimal places (0dp, 1dp, 2dp valid; 3dp+ invalid)
        negative numbers (invalid)

        BVA: (BVs are:)
        0 (valid - boundary between negative and positive)
        0.99 (valid - boundary between 2dp and 3dp)
        0.1 (valid - boundary between 0dp and 1dp)
        0.001 (invalid)
        -432.10 (invalid)
        */

        assertTrue(BankAccount.isAmountValid(200)); // valid positive number
        assertTrue(BankAccount.isAmountValid(0)); // BVA boundary between negative and positive

        assertTrue(BankAccount.isAmountValid(200.25)); // valid with 2 decimal places
        assertTrue(BankAccount.isAmountValid(0.99)); // BVA boundary between 2dp and 3dp
        assertFalse(BankAccount.isAmountValid(200.999)); // invalid with 3 decimal places
        
        assertFalse(BankAccount.isAmountValid(-50)); // invalid with negative number
        
    }

}