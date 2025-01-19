package project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.*;

public abstract class BankAcc {

    String accountNumber;
    private String name;
    private String email;
    private double balance;
    private String phone;
    private String dateOpened;
    private String accountType;

    public BankAcc(String accountNumber, String name, String email, double balance, String phone, String dateOpened, String accountType) {
        this.bank = new ArrayList<>();

        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.dateOpened = dateOpened;
        this.accountType = accountType;

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<BankAcc> getBank() {
        return bank;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    protected final ArrayList<BankAcc> bank;
    public int k;
    public int x;

    public int User(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] text = line.split(" ");
                if (text.length == 2 && text[0].equals(username) && text[1].equals(password)) {
                    return 0;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file 'user.txt' was not found.");
            return 3;
        } catch (IOException e) {
            System.out.println("Error: An IO error occurred while reading the file.");
            return 3;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return 3;
        }
        return 1;
    }

    public String generateAccountNumber() {
        try {
            Readfile();

            String accountNumber2;
            boolean isDuplicate;

            do {
                StringBuilder tempAccountNumber = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    int digit = (int) (Math.random() * 10);
                    tempAccountNumber.append(digit);
                }
                accountNumber2 = tempAccountNumber.toString();

                isDuplicate = false;
                for (int i = 0; i < bank.size(); i++) {
                    if (bank.get(i).getAccountNumber().equals(accountNumber2)) {
                        isDuplicate = true;
                        break;
                    }
                }
            } while (isDuplicate);

            return accountNumber2;
        } catch (Exception e) {
            System.err.println("Error generating account number: " + e.getMessage());
            return null;
        }
    }

    public boolean saveToFile(String name, String email, String phone, String accountType) {
        String accno = generateAccountNumber();
        double balancee = 0.0;
        String namef = accno + ".txt";
        Path path = Paths.get(namef);
        String DateO = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        File file = new File("accounts.txt");
        if (!file.exists()) {
            System.err.println("Error can't find the file: ");

            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s,%s,%s,%.2f,%s,%s,%s%n", accno, name, email, balancee, phone, DateO, accountType));
            Files.createFile(path);
            return true;

        } catch (IOException e) {
            System.err.println("Error saving account to file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }

    public boolean isValidPhoneNumber(String phone) {
        try {
            return phone != null && phone.matches("^(011|010|012)\\d{8}$");
        } catch (Exception e) {
            System.err.println("Error validating phone number: " + e.getMessage());
            return false;
        }
    }

    public boolean isValidName(String name) {
        try {
            return name != null && name.matches("[A-Za-z\\s]+") && name.length() >= 3;
        } catch (Exception e) {
            System.err.println("Error validating name: " + e.getMessage());
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        try {
            String emailRegex = "^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+\\.com$";
            Pattern pattern = Pattern.compile(emailRegex);
            return email != null && pattern.matcher(email).matches();
        } catch (Exception e) {
            System.err.println("Error validating email: " + e.getMessage());
            return false;
        }
    }

    public void Readfile() {
        File file = new File("accounts.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                BankAcc bankAccount;
                bankAccount = new Accounts(parts[0].trim(), parts[1].trim(), parts[2].trim(), Double.parseDouble(parts[3].trim()), parts[4].trim(), parts[5].trim(), parts[6].trim());
                bank.add(bankAccount);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: The file 'accounts.txt' was not found. Please ensure the file exists.");
            x = 2;
        } catch (Exception e) {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
            x = 2;
        }
    }

    public int Accnumber(String accNumber) {
        Readfile();
        if (x == 2) {
            return 2;
        }
        try {
            for (int i = 0; i < bank.size(); i++) {
                if (bank.get(i) != null && bank.get(i).getAccountNumber().equals(accNumber)) {
                    k = i;
                    return 1;
                }
            }

            return 0;
        } catch (Exception e) {
            System.err.println("An unexpected error  occurred: " + e.getMessage());
            return 2;
        }
    }

    public void Modifyname(String name, int index) {
        bank.get(index).setName(name);
    }

    public void Modifyphone(String phone, int index) {
        bank.get(index).setPhone(phone);

    }

    public void Modifyemail(String email, int index) {
        bank.get(index).setEmail(email);
    }

    public boolean savemodifications() {

        File file = new File("accounts.txt");

        if (!file.exists()) {
            System.err.println("File not found.");
            return false;
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            for (BankAcc account : bank) {
                if (account != null) {
                    writer.println(account.getAccountNumber() + ","
                            + account.getName() + ","
                            + account.getEmail() + ","
                            + account.getBalance() + ","
                            + account.getPhone() + ","
                            + account.getDateOpened() + ","
                            + account.getAccountType());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred while saving changes to the file: " + e.getMessage());
            return false;
        }
    }

    public int checkmod(String field, String type, int index) {
        try {
            switch (type) {
                case "Phone number " -> {
                    if (isValidPhoneNumber(field)) {
                        Modifyphone(field, index);
                        if (!savemodifications()) {
                            return 3;
                        }
                        return 0;
                    } else {
                        return 1;
                    }
                }
                case "Name" -> {
                    if (isValidName(field)) {
                        Modifyname(field, index);
                        if (!savemodifications()) {
                            return 3;
                        }
                        return 0;
                    } else {
                        return 1;
                    }
                }
                case "Email" -> {
                    if (isValidEmail(field)) {
                        Modifyemail(field, index);
                        if (!savemodifications()) {
                            return 3;
                        }
                        return 0;
                    } else {
                        return 1;
                    }
                }
                default -> {
                }
            }

            return 3;

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return 3;
        }
    }

    public int close() {
        try {

            if (bank.get(k).getBalance() > 0.0) {
                return 2;
            } else if (bank.get(k).getBalance() == 0) {
                bank.remove(k);
                if (!savemodifications()) {
                    return 0;
                }
            }

            return 1;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while closing the account: " + e.getMessage());
            return 0;
        }
    }

    public boolean checkacctype(int index) {
        return "Current".equals(bank.get(index).getAccountType());
    }

    public int deposit(double amount) {
        String accnumber = bank.get(k).getAccountNumber();
        String type = "Deposit";
        String description = "Deposit " + amount;
        try {
            if (amount <= 0 || amount > 10000) {
                return 1;
            }
            double currentBalance = bank.get(k).getBalance();

            bank.get(k).setBalance(currentBalance + amount);

            if (!savemodifications()) {
                return 4;
            }
            savetransaction(accnumber, amount, type, description);

            return 0;
        } catch (Exception e) {
            System.err.println("An error occurred during withdrawal: " + e.getMessage());
            return 2;
        }

    }

    public Object searchAccounts(String name) {
        ArrayList<BankAcc> foundAccounts = new ArrayList<>();

        try {
            Readfile();

            for (BankAcc account : bank) {
                if (account.getName().toLowerCase().contains(name.toLowerCase())) {
                    foundAccounts.add(account);
                }
            }

            Collections.sort(foundAccounts, (a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));

            if (foundAccounts.isEmpty()) {
                return 0;
            } else {
                return foundAccounts;
            }

        } catch (NullPointerException e) {
            System.err.println("Error: Encountered a null object. Check for null BankAcc objects.");
            return 1;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during the search process: " + e.getMessage());
            return 1;
        }
    }

    public boolean savetransaction(String accnumber, double amount, String type, String description) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        File file = new File(accnumber + ".txt");

        if (!file.exists()) {
            System.err.println("Error: can't find the file: " + file.getName());
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s,%s,%.2f,%s,%s%n", accnumber, type, amount, currentDate, description));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving account to file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<BankAcc> getBankAccounts() {
        Readfile();

        return bank;
    }

    public ArrayList<String> readTransactionData(String accnumber) {

        ArrayList<String> transactionData = new ArrayList<>();
        String namefile = accnumber + ".txt";
        File file = new File(namefile);

        if (!file.exists()) {
            return transactionData;
        }

        if (file.length() == 0) {
            return transactionData;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactionData.add(line);
            }
        } catch (IOException e) {
            return transactionData;
        }

        return transactionData;
    }

    public abstract int withdraw(double amount, int index);

    public abstract int transfer(int indexs, int indexr, double amount);

    public ArrayList<BankAcc> sort(String sorttype) {
        Readfile();

        switch (sorttype.toLowerCase()) {
            case "name" ->
                Collections.sort(bank, (BankAcc a, BankAcc b) -> a.getName().toLowerCase().compareTo(b.getName().toLowerCase()));
            case "balance" ->
                Collections.sort(bank, (BankAcc a, BankAcc b) -> Double.compare(b.getBalance(), a.getBalance()));
            case "date" ->
                Collections.sort(bank, (BankAcc a, BankAcc b) -> b.getDateOpened().compareTo(a.getDateOpened()));
            default ->
                System.out.println("Invalid sort type. Returning unsorted list.");
        }

        return bank;

    }
}
