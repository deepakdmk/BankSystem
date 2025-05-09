package Interest;

import Exception.GeneralException;
import Interest.DTO.InterestDTO;
import Utility.BigDecimalUtils;
import Utility.ConsoleUtils;
import Utility.DateUtils;
import Utility.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static Templates.ConsoleTemplates.*;

public class Interest {

    private static final List<InterestDTO> interestDb = new ArrayList<>();

    public void interestMenu() {
        boolean mainMenu = false;
        while (!mainMenu) {
            try {
                System.out.println(INTEREST_INTRO);
                String input = ConsoleUtils.getInput();
                if (StringUtils.isEmpty(input)) {
                    System.out.println(MENU_OUTRO);
                    break;
                }
                interestHandler(input);
                mainMenu = true;
                System.out.println(MENU_OUTRO);
            } catch (Exception e) { // change to general exception if there is any
                System.out.println(ERROR + " - " + e.getMessage());
            }
        }
    }

    private void interestHandler(String input) throws GeneralException {
        InterestDTO interest = convertInputToDTO(input);
        if (!validateInterestDetails(interest)) {
            throw new GeneralException(ERROR_INTEREST_INPUT);
        }
        //Abit more relaxed than transaction
        //Just compare the rule and remove if it exists
        //Sort by date as well, but the real question is what if the date is earlier
        // and the ruleId is not in order, looks weird for uiux experience. or maybe
        //the rule name is irrelevant

        interestDb.removeIf(rule -> rule.getDate().equals(interest.getDate()));
        interestDb.add(interest);
        interestDb.sort(Comparator.comparing(InterestDTO::getDate));
        printInterestStatement(interestDb);
}

    private boolean validateInterestDetails(InterestDTO interest) {
        return DateUtils.isValidDate(interest.getDate()) && BigDecimalUtils.isBetweenZeroAndHundred(interest.getRate());
    }

    private InterestDTO convertInputToDTO(String input) throws GeneralException {
        String[] interestArr = input.split(" ");
        if (interestArr.length != 3) {
            throw new GeneralException(ERROR_INTEREST_INPUT);
        }
        InterestDTO interest = new InterestDTO();
        interest.setDate(interestArr[0]);
        interest.setRuleId(interestArr[1]);
        interest.setRate(new BigDecimal(interestArr[2]));
        return interest;
    }

    private void printInterestStatement(List<InterestDTO> interest) {
        System.out.println(); //Spacing for nicer UX
        System.out.println(INTEREST_HEADER);
        System.out.printf((INTEREST_TABLE_HEADER_FORMAT) + "%n", "Date", "RuleId", "Rate (%)");
        interest.stream().filter(i -> !StringUtils.isEmpty(i.getDate()) &&
                !StringUtils.isEmpty(i.getRuleId()) &&
                Objects.nonNull(i.getRate())).forEach(i ->
                System.out.printf((INTEREST_TABLE_ROW_FORMAT) + "%n",
                        i.getDate(),
                        i.getRuleId(),
                        i.getRate())
        );
        System.out.println();//Spacing for nicer UX
    }

}
