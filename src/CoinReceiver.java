import util.PaymentReceiver;

public class CoinReceiver implements PaymentReceiver {
    private int amount;

    public CoinReceiver() {
        this.amount = 0;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void addAmount(int amount) {
        this.amount += amount;
    }
    @Override
    public void topUpBalance(int amount) {
        addAmount(amount);
    }

    @Override
    public void deductAmount(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
        } else {
            System.out.println("Недостаточно средств");
        }
    }
}