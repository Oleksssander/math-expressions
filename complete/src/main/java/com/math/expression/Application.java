package main.java.com.math.expression;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private EquationsRepository equationsRepository;

    private static final Logger LOG = LoggerFactory
            .getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String xRoot;

        while (!input.equalsIgnoreCase("exit")) {
            System.out.print("Enter a mathematical equation (enter 'exit' to quit): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (!checkBrackets(input)) {
                System.out.println("Invalid brackets placement");
                continue;
            }

            if (!checkExpression(input)) {
                System.out.println("Invalid expression");
                continue;
            }
            System.out.print("Enter a value for x: ");
            xRoot = scanner.nextLine();

            Equations equation = new Equations();
            equation.setExpression(input);

            if (isRootCorrect(input, Double.valueOf(xRoot))) {
                equation.setRoot(xRoot);
                System.out.println(xRoot + " is a root of the equation.");
            } else {
                System.out.println(xRoot + " is not a root of the equation.");
            }
            equationsRepository.save(equation);
            LOG.info("Expession saved to DB");
        }

        scanner.close();
        List<Equations> list = equationsRepository.findByRoot("5");
        for (Equations x : list) {
            System.out.println(x);
        }
    }
    public static boolean checkBrackets(String equation) {
        Stack<Character> stack = new Stack<>();
        for (char c : equation.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
    public static boolean checkExpression(String equention) {
        // Remove all whitespace characters from the expression
        equention = equention.replaceAll("\\s", "");
       // Check if the expression ends with an operator
        if (isOperator(equention.charAt(equention.length() - 1))) {
            return false;
        }
        // Check if the expression contains two consecutive operators
        for (int i = 0; i < equention.length() - 1; i++) {
            char c1 = equention.charAt(i);
            char c2 = equention.charAt(i + 1);
            if (isOperator(c1) && isOperator(c2)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static boolean isRootCorrect(String input, Double xRoot) {
        input = input.replaceAll("x", xRoot.toString());
        String[] equation = input.split("=");
        String leftHandSideExpression = equation[0];
        Double rightHandSideValue = Double.valueOf(equation[1]);

        License.iConfirmNonCommercialUse("agree");
        Expression e = new Expression(leftHandSideExpression);
        Double leftHandSideValue = e.calculate();

        return rightHandSideValue.equals(leftHandSideValue);
    }
}






