package util;

public interface PaymentReceiver {
    int getAmount();
    void addAmount(int amount);
    void deductAmount(int amount);
    void topUpBalance(int amount);
}