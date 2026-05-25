/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.userregistrationandloginpart2;

/**
 *
 * @author Chelsea
 */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.UUID;

public class UserRegistrationAndLoginpart2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ====================== REGISTRATION ======================
        System.out.println("Enter your first name: ");
        String fname = input.nextLine();

        System.out.println("Enter your last name: ");
        String lname = input.nextLine();

        // Username validation
        String username = "";
        boolean validUsername = false;
        while (!validUsername) {
            System.out.println("Enter a username (must contain an underscore _ and be 5 characters long): ");
            username = input.nextLine();
            if (username.length() == 5 && username.contains("_")) {
                validUsername = true;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is exactly 5 characters long.");
            }
        }

        // Password validation
        String password = "";
        boolean validPassword = false;
        while (!validPassword) {
            System.out.println("Enter a password (8 characters in length, one special character, one number, one uppercase): ");
            password = input.nextLine();
            if (isValidPassword(password)) {
                validPassword = true;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least 8 characters, one uppercase letter, one number and one special character.");
            }
        }

        // Phone number validation
        String phonenumber = "";
        boolean validPhonenumber = false;
        while (!validPhonenumber) {
            System.out.println("Enter your cellphone number (must start with '+27'): ");
            phonenumber = input.nextLine();
            if (phonenumber.startsWith("+27") && phonenumber.length() == 12) {
                boolean onlyDigitsAfter = true;
                for (int i = 3; i < phonenumber.length(); i++) {
                    if (!Character.isDigit(phonenumber.charAt(i))) {
                        onlyDigitsAfter = false;
                        break;
                    }
                }
                if (onlyDigitsAfter) {
                    validPhonenumber = true;
                }
            }
            if (!validPhonenumber) {
                System.out.println("Cellphone number incorrectly formatted or does not contain SA national code.");
            }
        }

        System.out.println("\n----- Registration Successful -----");
        System.out.println("First name: " + fname);
        System.out.println("Last name: " + lname);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Cellphone number: " + phonenumber);

        // ====================== LOGIN ======================
        System.out.println("\n----- Login -----");
        while (true) {
            System.out.print("Enter username: ");
            String loginUser = input.nextLine();
            System.out.print("Enter password: ");
            String loginPass = input.nextLine();

            if (loginUser.equals(username) && loginPass.equals(password)) {
                System.out.println("Welcome " + fname + " " + lname + "! It is great to see you again.");
                break;
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }
        }

        // ====================== QUICKCHAT ======================
        System.out.println("\nWelcome to QuickChat!");

        ArrayList<Messages> messages = new ArrayList<>();
        int totalMessagesSent = 0;

        while (true) {
            System.out.println("\n----- Menu -----");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recent sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter choice: ");
            int choice = input.nextInt();
            input.nextLine();  // Consume newline

            if (choice == 1) {
                // Send Message
                System.out.print("How many messages do you want to send? ");
                int numberOfMessages = input.nextInt();
                input.nextLine();

                for (int i = 0; i < numberOfMessages; i++) {
                    Messages message = new Messages();

                    message.setMessageID(UUID.randomUUID().toString().substring(0, 10));

                    System.out.print("Enter recipient cell number with international code: ");
                    String recipient = input.nextLine();
                    message.setRecipient(recipient);

                    System.out.print("Enter message (max 250 characters): ");
                    String messageText = input.nextLine();

                    while (messageText.length() > 250) {
                        System.out.println("Please enter a message of less than 250 characters.");
                        messageText = input.nextLine();
                    }

                    message.setMessage(messageText);
                    message.setMessageHash(message.createMessageHash());

                    System.out.println("\n1) Send Message");
                    System.out.println("2) Store Message to send later");
                    System.out.println("3) Disregard Message");
                    System.out.print("Choose: ");
                    int action = input.nextInt();
                    input.nextLine();

                    if (action == 1) {
                        System.out.println("Message successfully sent.");
                        messages.add(message);
                        totalMessagesSent++;

                        System.out.println("Message ID: " + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("Recipient: " + message.getRecipient());
                        System.out.println("Message: " + message.getMessage());

                    } else if (action == 2) {
                        message.storeMessage();
                        System.out.println("Message successfully stored.");
                    } else if (action == 3) {
                        System.out.println("Message disregarded.");
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }

            } else if (choice == 2) {
                System.out.println("\nRecent Sent Messages:");
                if (messages.isEmpty()) {
                    System.out.println("No messages sent yet.");
                } else {
                    for (Messages msg : messages) {
                        System.out.println("ID: " + msg.getMessageID() +
                                " | To: " + msg.getRecipient() +
                                " | Message: " + msg.getMessage());
                    }
                }

            } else if (choice == 3) {
                System.out.println("GoodBye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        input.close();
    }

    // Password validation method
    private static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) hasUpperCase = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpperCase && hasNumber && hasSpecial;
    }
}

// ====================== MESSAGES CLASS ======================
class Messages {
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }

    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessage(String message) { this.message = message; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }

    public String createMessageHash() {
        if (message == null) return "N/A";
        return String.valueOf(message.hashCode());
    }

    public void storeMessage() {
        System.out.println("[Stored for later] ID: " + messageID + " -> " + recipient);
    }
}