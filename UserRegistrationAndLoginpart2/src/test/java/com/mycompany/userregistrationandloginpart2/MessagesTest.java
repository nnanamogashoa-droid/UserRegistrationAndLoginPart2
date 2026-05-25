/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.userregistrationandloginpart2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessagesTest {

    private Messages msg1, msg2, msg3, msg4, msg5;

    @BeforeEach
    void setUp() {
        // Test Data Message 1
        msg1 = new Messages();
        msg1.setMessageID("MSG001");
        msg1.setRecipient("+27834557896");
        msg1.setMessage("Did you get the cake?");
        msg1.setMessageHash(msg1.createMessageHash());

        // Test Data Message 2
        msg2 = new Messages();
        msg2.setMessageID("MSG002");
        msg2.setRecipient("+27838884567");
        msg2.setMessage("Where are you? You are late! I have asked you to be on time.");
        msg2.setMessageHash(msg2.createMessageHash());

        // Test Data Message 3
        msg3 = new Messages();
        msg3.setMessageID("MSG003");
        msg3.setRecipient("+27834484567");
        msg3.setMessage("Yohoooo, I am at your gate.");
        msg3.setMessageHash(msg3.createMessageHash());

        // Test Data Message 4
        msg4 = new Messages();
        msg4.setMessageID("MSG004");
        msg4.setRecipient("0838884567");
        msg4.setMessage("It is dinner time !");
        msg4.setMessageHash(msg4.createMessageHash());

        // Test Data Message 5
        msg5 = new Messages();
        msg5.setMessageID("MSG005");
        msg5.setRecipient("+27838884567");
        msg5.setMessage("Ok, I am leaving without you.");
        msg5.setMessageHash(msg5.createMessageHash());
    }

    @Test
    void testAllGettersAndSetters() {
        assertEquals("MSG001", msg1.getMessageID());
        assertEquals("+27834557896", msg1.getRecipient());
        assertEquals("Did you get the cake?", msg1.getMessage());
        assertNotNull(msg1.getMessageHash());

        // Test setters again
        msg1.setMessageID("NEWID");
        msg1.setRecipient("+27123456789");
        msg1.setMessage("New test message");
        assertEquals("NEWID", msg1.getMessageID());
        assertEquals("+27123456789", msg1.getRecipient());
        assertEquals("New test message", msg1.getMessage());
    }

    @Test
    void testCreateMessageHash() {
        assertNotNull(msg1.getMessageHash());
        assertNotEquals("N/A", msg1.getMessageHash());

        // Test null message
        Messages nullMsg = new Messages();
        nullMsg.setMessage(null);
        assertEquals("N/A", nullMsg.createMessageHash());

        // Test empty message
        Messages emptyMsg = new Messages();
        emptyMsg.setMessage("");
        assertNotEquals("N/A", emptyMsg.createMessageHash());
    }

    @Test
    void testStoreMessage() {
        assertDoesNotThrow(() -> msg1.storeMessage());
        assertDoesNotThrow(() -> msg2.storeMessage());
        assertDoesNotThrow(() -> msg5.storeMessage());
    }

    @Test
    void testMessageHashConsistency() {
        String hashBefore = msg3.getMessageHash();
        msg3.setMessage("Yohoooo, I am at your gate."); // same message
        assertEquals(hashBefore, msg3.createMessageHash());

        // Change message and check hash changes
        msg3.setMessage("Different message");
        assertNotEquals(hashBefore, msg3.createMessageHash());
    }

    @Test
    void testAllTestDataMessages() {
        assertAll(
            () -> assertNotNull(msg1),
            () -> assertNotNull(msg2),
            () -> assertNotNull(msg3),
            () -> assertNotNull(msg4),
            () -> assertNotNull(msg5),
            () -> assertEquals(5, new Messages[]{msg1, msg2, msg3, msg4, msg5}.length)
        );
    }

    @Test
    void testDefaultConstructorAndInitialState() {
        Messages defaultMsg = new Messages();
        assertNull(defaultMsg.getMessageID());
        assertNull(defaultMsg.getRecipient());
        assertNull(defaultMsg.getMessage());
        assertNull(defaultMsg.getMessageHash());
    }
}