// This program reads a Reverse Polish Notation mathematical Expression
// and evaluates it.  The program will show each step if the user chooses that
// otherwise the program will only print out the end result
//

import java.util.*;

public class ReversePolishNotationCalculator {

    // This gets the remainder of the input out of the Scanner
    // prints that remaining input (and also prints out the current contents of the
    // stack)
    // and then re-loads the remaining input into a new Scanner
    // This means that we can keep Scanning the remainder of the input
    private static Scanner printRemainingExpression(Stack<Double> numbers, Scanner scExpression) {

        String remainderOfExpr = scExpression.nextLine();
        System.out.println("Remaining expression: " + remainderOfExpr + " Stack: " + numbers);
        scExpression.close(); // may as well close out the old one before creating a new replacement
        return new Scanner(remainderOfExpr + "\n");
    }

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        char evalAgain = 'y';

        ShouldWeTryAgain: while (evalAgain == 'y') {
            double nextNumber = 0;

            System.out.println("\nRPN calculator");
            System.out.println("\tSupported operators: + - * /");
            System.out.print("Type your RPN expression in so that it can be evaluated: ");
            String rpnExpr = keyboard.nextLine();

            boolean explain = false;
            System.out.print("Would you like me to explain how to expression is evaluated? (y or Y for yes, anything else means no) ");
            String szExplain = keyboard.nextLine().trim().toLowerCase();
            if (szExplain.length() == 1 && szExplain.charAt(0) == 'y') {
                System.out.println("We WILL explain the evaluation, step by step");
                explain = true;
            }

            Scanner scExpr = new Scanner(rpnExpr + "\n");
            Stack<Double> num = new Stack<>();
            while(scExpr.hasNext()) { // repeat the following while there's another token left in the Scanner:
                if (explain) {
                    scExpr = printRemainingExpression(num, scExpr);
                }
                // if the next thing in the expression is a number:

                //adds number
                if (scExpr.hasNextDouble()) {
                    nextNumber = scExpr.nextDouble();
                    num.push(nextNumber);
                    //only prints if the user said they wanted it printed
                    if(explain)
                        System.out.println("Pushing " + nextNumber + " onto the stack of operands (numbers)");
                //skips over all the operator things if a number was added
                }else{
                    //checks for the first number
                    if (num.isEmpty()) {
                        System.err.println("ERROR! Expected to find 2 operands (numbers) but we don't have any numbers on the stack!");
                        System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain;
                    }
                    double operand2 = num.pop();
                    //checks for the second number
                    if (num.isEmpty()) {
                        System.err.println("ERROR! Expected to find 2 operands (numbers) but we don't have a second number on the stack!");
                        System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain;
                    }
                    double operand1 = num.pop();
                    //here has to be a next because its in the while loop
                    String opterator = scExpr.next();

                    //makes sure the operator isnt a string or words
                    if(opterator.length() > 1){
                        System.err.println("ERROR! Operator (non-numeric input) contains more than 1 character: " + opterator);
                        System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain; // This line will jump back to the outer loop
                    }

                    //this checks the operator and does the math or prints out the error messege
                    switch (opterator) {
                        case("+"):
                            nextNumber = operand2 + operand1;
                            num.push(nextNumber);
                            break;
                        case("-"):
                            nextNumber = operand1 - operand2;
                            num.push(nextNumber);
                            break;
                        case("*"):
                            nextNumber = operand1*operand2;
                            num.push(nextNumber);
                            break;
                        case("/"):
                            nextNumber = operand1/operand2;
                            num.push(nextNumber);
                            break;
                        default:
                            System.err.println("ERROR! Operator not recognized: " + opterator);
                            System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                            continue ShouldWeTryAgain; // This line will jump back to the outer loop
                        }
                        //explans whats going on only of the user asks
                    if (explain)
                        System.out.println("Popping " + operand2 + " and " + operand1 + " then applying "+ opterator + " to them\n then pushing the result back onto the stack");


            // At this point we've finished reading through the expression

            
                }
            }

            // If there's exactly 1 operand then it must be the answer
            if(num.size() == 1)
                System.out.println("END RESULT: " + num.pop());
            else if (num.size() > 1){
                 // If there's more than 1 operand (number) left then we print this error
                 System.err.println("ERROR! Ran out of operators before we used up all the operands (numbers):");
                 while(!num.isEmpty()){
                     System.err.println("\t" + num.pop());
                 }
            }else{
                //if there are no operand left
                System.err.println("ERROR! Ran out of operands (numbers)");
            }
            System.out.print("\nWould you like to evaluate another expression? (y or Y for yes, anything else to exit) ");
            String repeat = keyboard.nextLine().trim().toLowerCase();
            if (repeat.length() == 1 && repeat.charAt(0) == 'y')
                evalAgain = 'y';
            else
                evalAgain = 'n';
        }
        System.out.println("Thank you for using RPN Calculator!");

    }
}
