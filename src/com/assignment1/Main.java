package com.assignment1;

import javax.swing.JOptionPane;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(20);//initialise stack
        String outputString = "";//initialise output string

        String userInput = JOptionPane.showInputDialog(null, "Enter an infix notation");
        if (isValidInput(userInput)) {//check if input is valid
            outputString = infix2Postfix(userInput, stack);
        }
        JOptionPane.showMessageDialog(null, "Answer is: " + outputString);
    }

    static String infix2Postfix(String userInput, ArrayStack stack) {
        String outputString = "";
        for (int i = 0; i < userInput.length(); i++) //loop through input
        {
            char c = userInput.charAt(i);
            if (stack.isEmpty()) //before we calculate precedences, check if stack is empty
            {
                //if so, push the input char if it's a digit or append to string if not
                if (!Character.isDigit(c)) {
                    stack.push(c);
                } else {
                    outputString += c;
                }
            } else {
                //calculate precedences
                int precedence = operatorPrecedence(c);
                int stackPrecedence = operatorPrecedence((char) stack.top());
                if (Character.isDigit(c)) //append digits to string
                {
                    outputString += c;
                } else { //if the char is an operator
                    if (precedence > stackPrecedence || c == '(') //if precedence is greater then that on the top of the stack, or char is '('
                    {
                        stack.push(c);
                    } else if (c == ')') { //else if closing bracket
                        for (int j = 0; j <= stack.size(); j++) { //loop through stack
                            if (stack.isEmpty())
                                break;

                            char top = (char) stack.top();
                            if (top == '(') {//if top of stack is '(' discard it
                                stack.pop();
                                break;
                            }
                            outputString += (char) stack.pop();//append top of stack to output
                        }
                    } else {//if our input has a lesser precedence then that on the stack, or is not a closing bracket
                        for (int j = 0; j <= stack.size(); j++) {//loop through stack
                            if (stack.isEmpty())
                                break;
                            //TODO see if this section can be removed
                            char top = (char) stack.top();
                            if (top == ')' || top == '(') {
                                stack.push(c);
                                if (top == '(')
                                    stack.pop();
                                break;
                            }
                            if (stackPrecedence >= precedence) {//while our stack precedence is larger then our current input char, pop the stack
                                outputString += (char) stack.pop();
                            }
                        }
                        stack.push(c);//after popping elements from the stack push the current char
                    }

                }
            }

        }
        outputString += (char) stack.pop();// pop the remaining char from the stack and add to output
        return outputString;
    }

    static boolean isValidInput(String input) {
        boolean consecDigits = false; //bool to check if consecutive digits are in the input string
        String accepted = "-+*/()^";
        if (input.length() > 20 || input.length() < 3) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {//if char is digit
                if (!consecDigits) {//ensures if we encounter another digit after this we know not to allow it
                    consecDigits = true;
                } else {
                    return false;
                }
                consecDigits = true;
            } else {
                consecDigits = false;//reset consecDigits if we encounter an operator
                if (accepted.indexOf(c) < 0) return false;//check if chars are valid
            }
        }
        return true;
    }

    static int operatorPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '^':
                return 2;
            case '(':
            case ')':
                return -1; //only want to push parentheses to stack when we want to, this way parentheses will not be pushed
                            // by precedence since every other operator has a greater precedence
        }
        return 0;
    }
}
