package promotions.utils.validation;

import org.springframework.stereotype.Component;
import promotions.exceptions.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputValidator {

    public void validate(String... strs) throws ValidatorException {
        String msg = "";
        for(String str : strs){
            if(findSpecialCharacters(str)) {
                msg = "The input format is incorrect. Please insert text in the correct format";
            }
        }
        if(!"".equals(msg)){
            throw new ValidatorException(msg);
        }
    }

    public boolean findSpecialCharacters(String str){
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
