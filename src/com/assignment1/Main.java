package com.assignment1;
import javax.swing.JOptionPane;
import javax.swing.*;
public class Main {

    public static void main(String[] args) {
        Stack stack = new ArrayStack(20);
         String userInput = JOptionPane.showInputDialog(null,"Enter an infix notation");
         System.out.println(isValidInput(userInput));
    }

    static boolean isValidInput(String input)
    {
        boolean consecDigits = false; //bool to check if consecutive digits are in the input string
        String accepted = "-+*/()^";
        if(input.length()>20 || input.length()<3)
        {
            return false;
        }
        for (int i=0;i<input.length();i++)
        {
            char c = input.charAt(i);
            if(Character.isDigit(c))
            {
                if(!consecDigits)
                {
                    consecDigits = true;
                }
                else{
                    return  false;
                }
                consecDigits = true;
            }
            else
            {
                consecDigits = false;
                if (accepted.indexOf(c) < 0) return false;
            }
        }
        return true;
    }
}
