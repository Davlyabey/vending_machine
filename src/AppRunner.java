import enums.ActionLetter;
import model.*;
import util.PaymentReceiver;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {
    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final PaymentReceiver paymentReceiver;

    private static boolean isExit = false;

    private AppRunner(PaymentReceiver paymentReceiver) {
        this.paymentReceiver = paymentReceiver;
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }

    public static void run() {
        PaymentReceiver coinReceiver = new CoinReceiver();
        AppRunner app = new AppRunner(coinReceiver);
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумме: " + paymentReceiver.getAmount());

        while (!isExit) {
            printMenu();
            String action = fromConsole().toLowerCase();
            switch (action) {
                case "1":
                    topUpBalanceAction();
                    break;
                case "2":
                    buyProductAction();
                    break;
                case "3":
                    exitAction();
                    break;
                default:
                    print("Неверная команда. Попробуйте еще раз.");
                    break;
            }
        }
    }

    private void topUpBalanceAction() {
        print("Введите сумму для пополнения:");
        int amount = Integer.parseInt(fromConsole());
        paymentReceiver.topUpBalance(amount);
        print("Счет пополнен на " + amount);
    }

    private void buyProductAction() {
        print("Выберите продукт для покупки:");
        showActions(products);
        String action = fromConsole().toLowerCase();
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().getValue().equals(action)) {
                    if (paymentReceiver.getAmount() >= products.get(i).getPrice()) {
                        paymentReceiver.deductAmount(products.get(i).getPrice());
                        print("Вы купили " + products.get(i).getName());
                        break;
                    } else {
                        print("Недостаточно средств.");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
        }
    }

    private void exitAction() {
        isExit = true;
        print("Выход из программы.");
    }

    private void printMenu() {
        print("Выберите действие:");
        print("1. Пополнить счет");
        print("2. Купить продукт");
        print("3. Выйти");
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
