package project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Savings extends BankAcc {

    public Savings(String accountNumber, String name, String email, double balance, String phone, String dateOpened, String accountType) {
        super(accountNumber, name, email, balance, phone, dateOpened, accountType);
    }

    @Override

    public int withdraw(double amount, int index) {
        Readfile();
        String accnumber = bank.get(index).getAccountNumber();
        String type = "Withdraw";
        String description = "Withdraw " + amount;
        try {
            if (amount <= 0) {
                return 1;
            }
            if (amount > 10000) {
                return 1;
            }
            if (amount > bank.get(index).getBalance()) {
                return 3;
            }

            double newBalance = bank.get(index).getBalance() - amount;
            bank.get(index).setBalance(newBalance);

            if (!savemodifications()) {
                return 4;
            }
            savetransaction(accnumber, amount, type, description);

            return 0;
        } catch (Exception e) {
            return 4;
        }
    }

    @Override
    public int transfer(int indexSender, int indexReceiver, double amount) {
        Readfile();
        String accnumber1 = bank.get(indexSender).getAccountNumber();
        String accnumber2 = bank.get(indexReceiver).getAccountNumber();
        String type = "Transfer";
        String description1 = "Transfer " + amount + " To Account " + accnumber2;
        String description2 = "Received " + amount + " From Account " + accnumber1;

        try {
            if (indexSender == indexReceiver) {
                return 1;
            }
            if (amount <= 0) {
                return 2;
            }
            if (amount > bank.get(indexSender).getBalance()) {
                return 3;
            }

            bank.get(indexSender).setBalance(bank.get(indexSender).getBalance() - amount);
            bank.get(indexReceiver).setBalance(bank.get(indexReceiver).getBalance() + amount);

            if (!savemodifications()) {
                return 4;
            }
            savetransaction(accnumber1, amount, type, description1);
            savetransaction(accnumber2, amount, type, description2);
            return 0;
        } catch (Exception e) {
            return 4;
        }
    }

    public int applyinterest() {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for (int i = 0; i < bank.size(); i++) {
                if (!checkacctype(i)) {
                    String dateOpened = bank.get(i).getDateOpened();

                    try {
                        LocalDate openedDate = LocalDate.parse(dateOpened, formatter);
                        LocalDate currentDate = LocalDate.now();

                        long monthsActive = java.time.temporal.ChronoUnit.MONTHS.between(openedDate, currentDate);

                        if (monthsActive >= 4) {
                            double currentBalance = bank.get(i).getBalance();
                            double interest = currentBalance * 0.05;
                            bank.get(i).setBalance(currentBalance + interest);
                        }
                    } catch (DateTimeParseException e) {
                    }
                }
            }

            if (savemodifications()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            return 0;
        }
    }

}
