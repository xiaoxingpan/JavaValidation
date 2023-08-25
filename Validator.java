
public class Validator {

    public static void main(String[] args) {
        
    }

    public static boolean isAlphaNum(char input) {

        //using built in methods to check a char input
        if (Character.isLetter(input) || Character.isDigit(input)) {
            return true;
        } else {
            return false;
        }
    }

	public static boolean isSpecialChar(char input, boolean isUnderscoreOK) {

		if (input == '-' || input == '.') {
			return true;
		} else if (input == '_' && isUnderscoreOK) {
			return true;
		} else return false;
	}

    // method checks if a string has a number
    public static boolean hasNumber(String input) {

        char[] chars = input.toCharArray(); //create an array of chars from input
        for (char c : chars) {
            if (Character.isDigit(c)) { //check each one to see if it is a digit
                return true; //if so return true
            }
        }
        return false; //otherwise false
    }

    public static boolean singleAtSign(String emailAddress) {

        int countAt = 0;
        for (int i = 0; i < emailAddress.length(); i++) {
            if (emailAddress.charAt(i) == '@') {
				countAt += 1;
            }
        }
        if (countAt == 1) {
            return true;
        } else return false;
    }

    public static boolean isPrefixChar(char input) {

        boolean isAlphaNum = isAlphaNum(input);
        boolean isSpecialChar = isSpecialChar(input, true);
        return isAlphaNum || isSpecialChar;
    }

    public static boolean isDomainChar(char input) {

        boolean isAlphaNum = isAlphaNum(input);
        boolean isSpecialChar = isSpecialChar(input, false);
        return isAlphaNum || isSpecialChar;
    }

    // method checks if a underscore/periods/dashes is followed by another underscore/periods/dashes
    public static boolean reSpecial(String input) {

        if (input.contains("..") || input.contains(".-") || input.contains("-.") || input.contains("._") || input.contains("_.")
                || input.contains("--") || input.contains("-_") || input.contains("_-") || input.contains("__")) {
            return true;  // 9 possible cases of repeat symbols
        } else {
            return false;
        }
    }

    public static String fetchBeforeAt(String email) {

        String[] fetcher = null;
        fetcher = email.split("@"); //splitting email in half with an at

        return fetcher[0]; //returning first part... ** fetch after the same only its [1]
    }

    public static String fetchAfterAt(String email) {

        if ( email.charAt( email.length()-1 ) == '@') {
        	return null;
        } else {
        	String[] fetcher = null;
        	fetcher = email.split("@");
        	 return fetcher[1];
        }       
    }

    public static boolean isPrefix(String input) {

        if (input == null || input.length() < 1 || !isAlphaNum(input.charAt(0)) || !isAlphaNum(input.charAt(input.length() - 1))) {
            return false; // ^^ starting point of minimums before running the code
        }

        if (reSpecial(input)) {    // checks for repeat .. -- etc
            return false;
        }

        for (int i = 1; i < input.length() - 1; i++) { //checking if each character is valid in the prefix
            char check = input.charAt(i);
            if (!isPrefixChar(check)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDomain(String input) {
        // split the string based on the last occurrence of a period
        int lastIndex = input.lastIndexOf('.');
        if (lastIndex == -1) {
            return false;
        } else {
            String firstPortion = input.substring(0, lastIndex);
            String secondPortion = input.substring(lastIndex + 1);
            int firstPortionLength = firstPortion.length();
            int secondPortionLength = secondPortion.length();

            // no character in firstPortion / last character of firstPortion is not a alphanumeric character / secondPortion has less than 2 characters
            if (firstPortion == null || secondPortion == null || firstPortionLength < 1 || secondPortionLength < 2 || !isAlphaNum(firstPortion.charAt(firstPortionLength - 1))) {
                return false;
            }

            if (reSpecial(firstPortion)) {    // checks for repeat .. -- etc
                return false;
            } else {
                for (int f = 0; f < firstPortionLength - 1; f++) {
                    for (int s = 0; s < secondPortionLength; s++) {
                        // characters of firstPortion are not alphanumeric characters nor special characters(underscore is not allowed)
                        if (!isDomainChar(firstPortion.charAt(f))) {
                            return false;
                        }
                        // characters of secondPortion are not letters
                        if (!Character.isLetter(secondPortion.charAt(s))) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
    }

    public static boolean isEmail(String input) {
        
        if ( fetchAfterAt(input) == null || !singleAtSign(input) || reSpecial(input) ) {
            return false;
        }

        String prefix = fetchBeforeAt(input);
        String domain = fetchAfterAt(input);

        if (!isPrefix(prefix) || !isDomain(domain)) {
            return false;
        }
        return true;
    }

    public static String isUsername(String input) {
        if (input == null || input.length() < 2 || input.length() > 7 || reSpecial(input) || !isSpecialChar(input.charAt(0), false) ) {
            return "";
        } else {
            for (int i = 1; i < input.length(); i++) {
                char check = input.charAt(i);
                if (!input.matches(".*\\w.*") || (!isDomainChar(check) && check != '!')) {
                    return "";
                }
            }
        }
        return input.toLowerCase();

    }

    public static boolean safePassword(String input) {
        if (input == null || input.length() < 7 || input.length() > 15 || reChar(input) || !hasCaps(input) || !hasLow(input)
                || !hasNumber(input)) {
            return false; // checking the basic parameters of the password using custom methods

        }
        for (int i = 0; i < input.length(); i++) { //checking if there is a special character like a cherry on top
            char check = input.charAt(i);
            if (isSpecialChar(check, true)) {
                return true; //if so it's a good password
            }

        }
        return false; //otherwise it's too basic

    }

    // 2 very similar methods to check if there are caps and low characters
    public static boolean hasCaps(String input) {

        String comper = input.toLowerCase(); // make clone of input string in lowercase
        if (comper.equals(input)) {
            return false; // if it is the exact same as the input it has no caps
        } else {
            return true; // if it is different it has caps
        }                 // hasLow is exactly the same but reversed

    }

    public static boolean hasLow(String input) {

        String comper = input.toUpperCase();
        if (comper.equals(input)) {
            return false;
        } else {
            return true;
        }

    }


    // checks for repeat characters
    public static boolean reChar(String input) {


        for (int i = 1; i < input.length(); i++) { //iterates through each char
            char check = input.charAt(i);
            char previous = input.charAt(i - 1); // assign a value to previous char
            if (check == previous) { // check if it matches and return true if it does
            return true;
            }
        } return false;
    }

}
