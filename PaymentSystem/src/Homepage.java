import Templates.ConsoleTemplates;
import Transaction.Transaction;

import java.util.List;
import java.util.Objects;

public class Homepage {

    public void printMainMenu() {
        buildMainMenu().stream()
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }

    private List<String> buildMainMenu() {
        return List.of(
                ConsoleTemplates.MAIN_MENU_OPTION_TRANSACTION,
                ConsoleTemplates.MAIN_MENU_OPTION_INTEREST,
                ConsoleTemplates.MAIN_MENU_OPTION_STATEMENT,
                ConsoleTemplates.MAIN_MENU_OPTION_QUIT
        );
    }

    void handleMainMenuInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println(ConsoleTemplates.ERROR_INPUT);
            return;
        }

        switch (input.trim().toLowerCase()) {
            case ConsoleTemplates.MAIN_MENU_SYMBOL_TRANSACTION:
                handleTransactionMenu();
                break;
            case ConsoleTemplates.MAIN_MENU_SYMBOL_INTEREST:
                handleInterestMenu();
                break;
            case ConsoleTemplates.MAIN_MENU_SYMBOL_STATEMENT:
                handleStatementMenu();
                break;
            case ConsoleTemplates.MAIN_MENU_SYMBOL_QUIT:
                quitApplication();
                break;
            default:
                System.out.println(ConsoleTemplates.ERROR_INPUT);
                break;
        }
    }

    private void quitApplication() {
        System.out.println(ConsoleTemplates.APPLICATION_OUTRO);
        System.exit(0);
    }

    private void handleTransactionMenu() {
        Transaction transaction = new Transaction();
        transaction.transactionMenu();
    }

    private void handleInterestMenu() {
        System.out.println("interest");
    }

    private void handleStatementMenu() {
        System.out.println("statement");
    }
}
