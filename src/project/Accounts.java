package project;


public class Accounts extends BankAcc {

    public Accounts(String accountNumber, String name, String email, double balance, String phone, String dateOpened, String accountType) {
        super(accountNumber, name, email, balance, phone, dateOpened, accountType);
    }

    @Override
    public int withdraw(double amount,int index) {
return 0;
    }

    @Override
    public int transfer(int indexs, int indexr, double amount) {
return 0;
    }
    


}
