package com.bgokce.eventmanagement.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TCNoValidator implements ConstraintValidator<TCKimlikNo, String> {
   public void initialize(TCKimlikNo constraint) {
   }

   @Override
   public boolean isValid(String tcKimlikNo, ConstraintValidatorContext constraintValidatorContext) {
      if(tcKimlikNo.length() != 11) {
         return false;
      }

      int sumAllNumbers = 0;
      int sumOdd = 0;
      int sumEven = 0;

      Integer[] numbers = new Integer[11];
      for (int i = 0; i < 11; i++) {
         numbers[i] = Integer.parseInt(tcKimlikNo.substring(i, i + 1));
      }

      for (int i = 0; i < 9; i++) {
         sumAllNumbers = sumAllNumbers + numbers[i];
         if (i % 2 != 0) {
            sumEven = sumEven + numbers[i];
         } else {
            sumOdd = sumOdd + numbers[i];
         }
      }
      if ((sumAllNumbers + numbers[9]) % 10 != numbers[10]) {
         return false;
      }
      if ((sumOdd * 7 + sumEven * 9) % 10 != numbers[9]) {
         return false;
      }
      if (((sumOdd) * 8) % 10 != numbers[10]) {
         return false;
      }
      return true;
   }
}