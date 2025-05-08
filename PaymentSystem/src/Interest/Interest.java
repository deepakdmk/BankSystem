package Interest;

import Utility.ConsoleUtils;
import Exception.GeneralException;
import Utility.StringUtils;

import static Templates.ConsoleTemplates.*;

public class Interest {


    public void interestMenu() {
        boolean mainMenu = false;
        while (!mainMenu) {
            try {
                System.out.println(INTEREST_INTRO);
                String input = ConsoleUtils.getInput();
                if(StringUtils.isEmpty(input)){
                    System.out.println(MENU_OUTRO);
                    break;
                }
                mainMenu = true;
                System.out.println(MENU_OUTRO);
            } catch (Exception e) { // change to general exception if there is any
                System.out.println(ERROR + " - " + e.getMessage());
            }
        }
    }
}
