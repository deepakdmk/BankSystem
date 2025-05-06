package Transaction;

import Exception.GeneralException;
import Templates.ConsoleTemplates;
import Transaction.DTO.TransactionDTO;
import Transaction.DTO.TransactionDetailsDTO;
import Utility.BigDecimalUtils;
import Utility.ConsoleUtils;
import Utility.DateUtils;
import Utility.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction {
    private static final Map<String, List<TransactionDTO>> accountTransactionMap = new HashMap<>();

    public void transactionMenu() {
        System.out.println(ConsoleTemplates.TRANSACTION_INTRO);
        while (true) {
            try {
                transactionHandler(ConsoleUtils.getInput());
                printTransactionStatement("test", new ArrayList<TransactionDTO>());
                System.out.println(ConsoleTemplates.TRANSACTION_OUTRO);
                break;
            } catch (Exception e) {
                System.out.println(ConsoleTemplates.ERROR_INPUT);
            }
        }
    }


    private void printTransactionStatement(String accountId, List<TransactionDTO> transactions) {
        System.out.println(); //Spacing for nicer UX
        System.out.printf((ConsoleTemplates.TRANSACTION_HEADER) + "%n", accountId);
        System.out.printf((ConsoleTemplates.TRANSACTION_TABLE_HEADER) + "%n", "Date", "Txn Id", "Type", "Amount", "Balance");
        transactions.forEach
                (txn -> System.out.printf((ConsoleTemplates.TRANSACTION_TABLE_ROW) + "%n",
                        txn.getDate(),
                        txn.getTransactionId().isEmpty() ? "" : txn.getTransactionId(),
                        txn.getType(),
                        txn.getAmount(),
                        txn.getBalanceAfter())
                );
        System.out.println();//Spacing for nicer UX
    }


    private Map<String, List<TransactionDTO>> createAccount(String accountId) {
        return null;
    }

    public void transactionHandler(String input) throws GeneralException {
        TransactionDetailsDTO request = null;

        request = convertInputToDTO(input);
//        System.out.println(request.toString());

        if (validateTransactionDetails(request)) {

            //Check if account exists
            if (accountExists(request.getAccountNumber())) {
                //I need to do further checks on balance etc
            }else{

            }
        }
    }


    public static void addTransaction(String accountNumber, TransactionDTO txn) {
        accountTransactionMap
                .computeIfAbsent(accountNumber, k -> new ArrayList<>())
                .add(txn);
    }

    public static List<TransactionDTO> getTransactions(String accountNumber) {
        return accountTransactionMap.getOrDefault(accountNumber, new ArrayList<>());
    }

    public static boolean accountExists(String accountNumber) {
        return accountTransactionMap.containsKey(accountNumber);
    }


    public TransactionDetailsDTO convertInputToDTO(String input) throws GeneralException {
        String[] information = input.split(" ");
        if (information.length != 4) {
            throw new GeneralException(ConsoleTemplates.ERROR_INPUT);
        }
        TransactionDetailsDTO resp = new TransactionDetailsDTO();
        resp.setDate(information[0]);
        resp.setAccountNumber(information[1]);
        resp.setTransactionType(information[2]);
        resp.setAmount(information[3]);
        return resp;
    }

    private boolean validateTransactionDetails(TransactionDetailsDTO request) {
        return DateUtils.isValidDate(request.getDate()) &&
                validateTransactionTypeInput(request.getTransactionType()) &&
                BigDecimalUtils.ifBgThanZero(new BigDecimal(request.getAmount()));
    }

    private boolean validateTransactionTypeInput(String transactionType) {
        return StringUtils.equals(transactionType.trim().toLowerCase(), "w") ||
                StringUtils.equals(transactionType.trim().toLowerCase(), "d");
    }

}
