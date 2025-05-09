package Statement;

import Transaction.DTO.TransactionDTO;
import Utility.ConsoleUtils;
import Utility.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static Templates.ConsoleTemplates.*;

public class Statement {


    public void statementMenu() {
        boolean mainMenu = false;
        while (!mainMenu) {
            try {
                System.out.println(STATEMENT_INTRO);
                String input = ConsoleUtils.getInput();
                if (StringUtils.isEmpty(input)) {
                    System.out.println(MENU_OUTRO);
                    break;
                }

                System.out.println(MENU_OUTRO);
                mainMenu = true;
            } catch (Exception e) {
                System.out.println(ERROR + " - " + e.getMessage());
            }
        }
    }

    private void printStatement(String accountId, List<TransactionDTO> transactions) {
        System.out.println(); //Spacing for nicer UX
        System.out.printf((STATEMENT_HEADER) + "%n", accountId);
        System.out.printf((STATEMENT_TABLE_HEADER_FORMAT) + "%n", "Date", "Txn Id", "Type", "Amount", "Balance");
        transactions.stream().filter(txn ->
                        !StringUtils.isEmpty(txn.getDate()) &&
                                !StringUtils.isEmpty(txn.getTransactionId()) &&
                                !StringUtils.isEmpty(txn.getType()) &&
                                Objects.nonNull(txn.getAmount()) &&
                                Objects.nonNull(txn.getBalanceAfter()))
                .forEach(txn -> System.out.printf((STATEMENT_TABLE_ROW_FORMAT) + "%n",
                        txn.getDate(),
                        txn.getTransactionId(),
                        txn.getType(),
                        txn.getAmount(),
                        txn.getBalanceAfter()));
        System.out.println();//Spacing for nicer UX
    }


}
