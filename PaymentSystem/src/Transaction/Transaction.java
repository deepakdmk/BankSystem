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

import static Templates.ConsoleTemplates.*;

public class Transaction {
    private static final Map<String, List<TransactionDTO>> accountTransactionMap = new HashMap<>();

    public void transactionMenu() {
        boolean success = false;
        while (!success) {
            try {
                System.out.println(TRANSACTION_INTRO);
                String input = ConsoleUtils.getInput();
                transactionHandler(input);
                System.out.println(TRANSACTION_OUTRO);
                success = true;
            } catch (GeneralException e) {
                System.out.println(ERROR + " - " + e.getMessage());
            }
        }
    }

    private void printTransactionStatement(String accountId, List<TransactionDTO> transactions) {
        System.out.println(); //Spacing for nicer UX
        System.out.printf((TRANSACTION_HEADER) + "%n", accountId);
        System.out.printf((TRANSACTION_TABLE_HEADER) + "%n", "Date", "Txn Id", "Type", "Amount", "Balance");
        transactions.forEach
                (txn -> System.out.printf((TRANSACTION_TABLE_ROW) + "%n",
                        txn.getDate(),
                        txn.getTransactionId().isEmpty() ? "" : txn.getTransactionId(),
                        txn.getType(),
                        txn.getAmount(),
                        txn.getBalanceAfter())
                );
        System.out.println();//Spacing for nicer UX
    }

    public void transactionHandler(String input) throws GeneralException {
        TransactionDetailsDTO request = null;
        request = convertInputToDTO(input);

        if (validateTransactionDetails(request)) {

            //Check if account exists
            if (accountExists(request.getAccountNumber())) {
                //Existing Account
                //I need to do further checks on balance etc
                //create the transaction number
                createTransactionNumber(request);
                TransactionDTO txn = createTransaction(request);
                //Insert into Map
                addTransaction(request.getAccountNumber(),txn);
                printTransactionStatement(request.getAccountNumber(),getTransactions(request.getAccountNumber()));
            } else {
                //New account

                //Validate that it is a deposit Type
                if (!validateTransactionTypeDeposit(request.getTransactionType())) {
                    throw new GeneralException(ERROR_INVALID_TRANSACTION_TYPE_NEW_ACCOUNT);
                }

                //create the transaction number
                createTransactionNumber(request);
                TransactionDTO txn = createTransaction(request);
                //Insert into Map
                addTransaction(request.getAccountNumber(),txn);
                printTransactionStatement(request.getAccountNumber(),getTransactions(request.getAccountNumber()));
            }
        } else {
            throw new GeneralException(ERROR_INPUT);
        }
    }

    public TransactionDTO createTransaction(TransactionDetailsDTO request){
       TransactionDTO txn = new TransactionDTO();
       txn.setTransactionId(request.getTransactionId());
       txn.setAmount(new BigDecimal(request.getAmount()));
       txn.setType(request.getTransactionType());
       txn.setDate(request.getDate());
//     txn.setBalanceAfter();  //Need to look into this logic.
       return txn;
    }

    public void createTransactionNumber(TransactionDetailsDTO request) {
        String requestDate = request.getDate();
        List<TransactionDTO> transactionList = getTransactions(request.getAccountNumber());

        long count = transactionList.stream()
                .filter(txn -> txn.getDate().equals(requestDate))
                .count();

        Integer transactionNumber = (int) count+1;
        String transactionId = request.getDate() + "-" + String.format("%02d", transactionNumber);
        request.setTransactionId(transactionId);
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

    private boolean validateTransactionTypeDeposit(String transactionType) {
        return StringUtils.equals(transactionType.trim().toLowerCase(), "d");
    }

}
