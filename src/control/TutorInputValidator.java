/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public class TutorInputValidator {
    
    Scanner sc = new Scanner(System.in);
    
    public boolean checkTutorName(String name){
        boolean valid = true;
        
        for(int i  = 0 ; i < name.length(); i++){
            if(!Character.isLetter(name.charAt(i))){
                valid = false;
                System.out.println("Invalid. Please enter again... ");
                break;
            }
    }
        return valid;
    }
    
    public boolean checkTutorGender(char gender){
        boolean valid = true;
        
        if (gender != 'M' && gender != 'F') {
            valid = false;
            System.out.println("Invalid. Please enter (F or M)...");
        }
        
        return valid;
    }
    
    public boolean checkTutorIC(String ic, char gender){
        boolean valid = true;
        
        if (ic.length() == 12) {
        int month = Character.getNumericValue(ic.charAt(2)) * 10 + Character.getNumericValue(ic.charAt(3));
        int day = Character.getNumericValue(ic.charAt(4)) * 10 + Character.getNumericValue(ic.charAt(5));

        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                valid = (day >= 1 && day <= 31);
                break;
            case 4: case 6: case 9: case 11:
                valid = (day >= 1 && day <= 30);
                break;
            case 2:
                valid = (day >= 1 && day <= 28);
                break;
        }

        if (valid) {
            boolean isEvenGenderDigit = Character.getNumericValue(ic.charAt(11)) % 2 == 0;
            valid = (gender == 'M' && isEvenGenderDigit) ||
                    (gender == 'F' && !isEvenGenderDigit);
        }
        
        if(!valid){
            System.out.println("Invalid. Please enter valid ic format and follow your gender...");
        }
    }
        return valid;
    }

    public boolean checkTutorPhoneNum(String tutorPhoneNum) {
        boolean valid = true;
        
        if (tutorPhoneNum.isEmpty() || tutorPhoneNum == null) {
            valid = false;
        }

        String phoneRegex = "^(\\+?6?01)[02-46-9][-][0-9]{7}$|^(\\+?6?01)[1][-][0-9]{8}$";
        Pattern phonepat = Pattern.compile(phoneRegex);
        Matcher matcher = phonepat.matcher(tutorPhoneNum);
        valid = matcher.find();

        if(!valid){
            System.out.println("Invalid. Please enter valid phone format (01x-xxxxxxx)...");
        }
        return valid;
    }

    public boolean checkTutorEmail(String tutorEmail) {
        boolean valid = true;
        
        if (tutorEmail.isEmpty() || tutorEmail == null) {
            valid = false;
        }
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(tutorEmail);
        valid = matcher.find();
        
        if(!valid){
            System.out.println("Invalid. Please enter valid email format...");
        }

        return valid;
    }
    
    
    
    
}
