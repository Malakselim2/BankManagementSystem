package project;

public class Current extends BankAcc {

    public Current(String accountNumber, String name, String email, double balance, String phone, String dateOpened, String accountType) {
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

        double fee = amount < 1000 ? 10.0 : 0.0;
        if (amount + fee > bank.get(index).getBalance()) {
            return 3; 
        }

        double newBalance = bank.get(index).getBalance() - (amount + fee);
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
    String description1 = "Transfer " + amount +" To Account "  + accnumber2;
    String description2 = "Received " + amount +" From Account "  + accnumber1;


        try {
            if (indexSender == indexReceiver) {
                return 1;
            }
            if (amount <= 0) {
                return 2;
            }

            double fee = 10.0;
            if (amount + fee > bank.get(indexSender).getBalance()) {
                return 3;
            }

            bank.get(indexSender).setBalance(bank.get(indexSender).getBalance() - (amount + fee));
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
}
