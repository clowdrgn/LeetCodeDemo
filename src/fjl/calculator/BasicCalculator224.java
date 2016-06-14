package fjl.calculator;

import java.util.Stack;
//Implement a basic calculator to evaluate a simple expression string.
//
//The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
//
//You may assume that the given expression is always valid.
//
//Some examples:
//"1 + 1" = 2
//" 2-1 + 2 " = 3
//"(1+(4+5+2)-3)+(6+8)" = 23
//Note: Do not use the eval built-in library function.
public class BasicCalculator224 {
	 public static int calculate(String s) {
		    int len = s.length(), sign = 1, result = 0;
		    Stack<Integer> stack = new Stack<Integer>();
		    for (int i = 0; i < len; i++) {
		        if (Character.isDigit(s.charAt(i))) {
		            int sum = s.charAt(i) - '0';
		            while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
		                sum = sum * 10 + s.charAt(i + 1) - '0';
		                i++;
		            }
		            result += sum * sign;
		        } else if (s.charAt(i) == '+')
		            sign = 1;
		        else if (s.charAt(i) == '-')
		            sign = -1;
		        else if (s.charAt(i) == '(') {
		            stack.push(result);
		            stack.push(sign);
		            result = 0;
		            sign = 1;
		        } else if (s.charAt(i) == ')') {
		            result = result * stack.pop() + stack.pop();
		        }

		    }
		    return result;
		}
	 public static int calculate1(String s) {
		    if (s.length() == 0) return 0;
		    s = "(" + s + ")";
		    int[] p = {0};
		    return eval(s, p);
		}
		// calculate value between parentheses
		private static int eval(String s, int[] p){
		    int val = 0;
		    int i = p[0]; 
		    int oper = 1; //1:+ -1:-
		    int num = 0;
		    while(i < s.length()){
		        char c = s.charAt(i);
		        switch(c){
		            case '+': val = val + oper * num; num = 0; oper = 1; i++; break;// end of number and set operator
		            case '-': val = val + oper * num; num = 0; oper = -1; i++; break;// end of number and set operator
		            case '(': p[0] = i + 1; val = val + oper * eval(s, p); i = p[0]; break; // start a new eval
		            case ')': p[0] = i + 1; return val + oper * num; // end current eval and return. Note that we need to deal with the last num
		            case ' ': i++; continue;
		            default : num = num * 10 + c - '0'; i++;
		        }
		    }
		    return val;
		}
	 public static void main(String[] args) {
		System.out.println( calculate1("(1+0)- 2+4-5+(2-7)"));
	}
}
