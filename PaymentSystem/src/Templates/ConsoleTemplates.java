package Templates;

public class ConsoleTemplates {

    private ConsoleTemplates() {}

    public static final String APPLICATION_INTRO= "Welcome to AwesomeGIC Bank! What would you like to do?";


    public static final String MAIN_MENU_OPTION_TRANSACTION= "[T] Input transactions";
    public static final String MAIN_MENU_OPTION_INTEREST= "[I] Define interest rules";
    public static final String MAIN_MENU_OPTION_STATEMENT= "[P] Print statement";
    public static final String MAIN_MENU_OPTION_QUIT= "[Q] Quit";
    public static final String MAIN_MENU_SYMBOL_TRANSACTION= "t";
    public static final String MAIN_MENU_SYMBOL_INTEREST= "i";
    public static final String MAIN_MENU_SYMBOL_STATEMENT= "p";
    public static final String MAIN_MENU_SYMBOL_QUIT= "q";
    public static final String APPLICATION_OUTRO="Thank you for banking with AwesomeGIC Bank.\nHave a nice day!";


    public static final String TRANSACTION_INTRO="Please enter transaction details in <Date> <Account> <Type> <Amount> format (or enter blank to go back to main menu):";
    public static final String TRANSACTION_HEADER = "Account: %s";
    public static final String TRANSACTION_TABLE_HEADER = "| %-9s | %-12s | %-4s | %7s | %8s |";
    public static final String TRANSACTION_TABLE_ROW = "| %-9s | %-12s | %-4s | %7.2f | %8.2f |";
    public static final String TRANSACTION_OUTRO = "Is there anything else you'd like to do?";

    public static final String ERROR_INPUT="Invalid input";


}
