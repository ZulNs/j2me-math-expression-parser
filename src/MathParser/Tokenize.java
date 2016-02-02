package MathParser;

import java.util.Vector;

public class Tokenize {
    private Vector  tokens;
    private String  expression;
    private String  errorMessage;
    private VarList varList;

    private static final byte[][] DFA_STATE = {
    /*               0   1   2   3   4   5   6   7   8   9  10  11  12  13 */
    /*              0-9  .   e  a-z  (   %  ^/*  +   -   ,  )   =  spc end */
    /* q0 start */{ 13, 13, 12, 12,  3, -1, -1,  8,  9, -1, -1, 15,  0, 16 },
    /* q1   cN  */{ -2, -2, -2, -2, -2,  4,  5, 10, 11,  6,  7, -2,  1, 16 },
    /* q2   cF  */{ -2, -2, -2, -2,  3,  4,  5, 10, 11,  6,  7, 15,  2, 16 },
    /* q3   (   */{ 13, 13, 12, 12,  3, -2, -2,  8,  9,  6,  7, -2,  3, -3 },
    /* q4   %   */{ -2, -2, -2, -2, -2,  4,  5, 10, 11,  6,  7, -2,  4, 16 },
    /* q5   ^/* */{ 13, 13, 12, 12,  3, -2, -2,  8,  9, -2, -2, -2,  5, -3 },
    /* q6   ,   */{ 13, 13, 12, 12,  3, -2, -2,  8,  9,  6,  7, -2,  6, -3 },
    /* q7   )   */{ -2, -2, -2, -2, -2,  4,  5, 10, 11,  6,  7, -2,  7, 16 },
    /* q8   + P */{ 13, 13, 12, 12,  3, -2, -2,  8,  9, -2, -2, -2,  8, -3 },
    /* q9   - N */{ 13, 13, 12, 12,  3, -2, -2,  8,  9, -2, -2, -2,  9, -3 },
    /* q10  +   */{ 13, 13, 12, 12,  3, -2, -2,  8,  9, -2, -2, -2, 10, -3 },
    /* q11  -   */{ 13, 13, 12, 12,  3, -2, -2,  8,  9, -2, -2, -2, 11, -3 },
    /* q12  F   */{ 12, -2, 12, 12,  3,  4,  5, 10, 11,  6,  7, 15,  2, 16 },
    /* q13  N   */{ 13, 13, 14, 13, -2,  4,  5, 10, 11,  6,  7, -2,  1, 16 },
    /* q14  Ne  */{ 13, 13, 14, 13, -2,  4,  5, 14, 14,  6,  7, -2,  1, 16 },
    /* q15  =   */{ 13, 13, 12, 12,  3, -2, -2,  8,  9, -2, -2, -2, 15, 16 }
    };

    public Tokenize(VarList varList) {
        errorMessage = "";
        tokens       = new Vector();
        this.varList = varList;
    }
    
    public Vector getTokens() {
        return tokens;
    }
    
    public String errorMessage() {
        return errorMessage;
    }
    
    public boolean isValid() {
        return errorMessage.equals("");
    }
    
    public String toString() {
        return expression;
    }
    
    private String tokensToString() {
        if (tokens.isEmpty()) return "";
        String s = "";
        for (int i = 0; i < tokens.size(); i++)
            s += ((Token) tokens.elementAt(i)).toString();
        return s;
    }
    
    public void buildTokens(String expression, String vendor) {
        this.expression = expression.trim().toUpperCase();
        if (!expression.endsWith("" + Midlet.LIMITER))
            this.expression += Midlet.LIMITER;
        tokens.removeAllElements();
        errorMessage = "";
        String buffer = "";
        Token t = null;
        Number number;
        Function function;
        VarToken vt;
        char c, mark;
        int q = 0, q0;
        for (int i = 0; i < this.expression.length(); i++) {
            c = this.expression.charAt(i);
            q0 = q;
            q = deltaQ(q, c);
            if (q >= 12 && q <= 14) {    // q  =  F ||  N ||  Ne
                if (q0 < 12 || q0 > 14)  // q0 = !F && !N && !Ne
                    buffer = "";
                buffer += c;
                continue;
            }
            if (q0 == 12) {              // q0 = F
                function = new Function(buffer);
                if (function.isValidFunction()) {
                    t = new Token(function, 'F');
                    tokens.addElement(t);
                }
                else {
                    if (buffer.equalsIgnoreCase(vendor)) {
                        errorMessage = buffer + " is my author";
                        tokens.removeAllElements();
                        return;
                    }
                    if (buffer.length() > 24) buffer = buffer.substring(0, 24);
                    vt = new VarToken(buffer, varList);
                    if (vt.getIndex() == -1) {
                        errorMessage = "Insufficient memory to hold " +
                                "variable: " + buffer;
                        tokens.removeAllElements();
                        return;
                    }
                    t = new Token(vt, 'V');
                    tokens.addElement(t);
                }
            }
            if (q0 == 13 || q0 == 14) {  // q0 = N || Ne
                number = new Number(buffer);
                if (number.isNaN()) {
                    errorMessage = "Invalid number specification: " + buffer;
                    tokens.removeAllElements();
                    return;
                }
                if (number.isInfinite()) {
                    // Double.MAX_VALUED = 1.7976931348623157E+308
                    // Double.MIN_VALUED = 4.9E-324
                    errorMessage = "Overflow value: " + buffer;
                    tokens.removeAllElements();
                    return;
                }
                t = new Token(number, 'N');
                tokens.addElement(t);
            }
            if (q == 16) {
                if (t !=null && t.mark() == 'F') {
                    errorMessage = "Illegal ending: " + buffer;
                    tokens.removeAllElements();
                    return;
                }
                break;
            }
            if (c == ' ' || c == '\n') continue;
            if (q < 0) {
                if (t != null) {
                    buffer = t.toString();
                }
                switch (q) {
                    case -1:
                        errorMessage = "Illegal beginning: " + c;
                        break;
                    case -2:
                        errorMessage = "Illegal sequencing: " +
                                buffer + " " + c;
                        break;
                    case -3:
                        errorMessage = "Illegal ending: " + buffer;
                        break;
                    case -4:
                        errorMessage = "Unrecognized symbol: " + c;
                }
                tokens.removeAllElements();
                return;
            }
            if (t != null && t.mark() == 'F') {
                if (q >= 4 && q <= 7 || q == 10 || q == 11) {
                    errorMessage = "Illegal use of variable name: " + buffer;
                    tokens.removeAllElements();
                    return;
                }
            }
            mark = c;
            switch (q) {
                case 3:  // '('
                    if (t != null && t.mark() == 'V') {
                        varList.removeLastElement(buffer);
                        errorMessage = "Unrecognized function: " + buffer;
                        tokens.removeAllElements();
                        return;
                    }
                    break;
                case 5:  //  '^' || '*' || '/'  (Operator)
                    mark = 'O';
                    break;
                case 6:  //  ','                (Comma or Argument Separator)
                    if (q0 == 3 || q0 == 6)
                        // "(,"   ->   "(0,"   ||   ",,"   ->   ",0,"
                        tokens.addElement(new Token(new Number("0"), 'N'));
                    break;
                case 7:  //  ')'                (Right-Parenthesis)
                    if (q0 == 6)  // ",)"   ->   ",0)"
                        tokens.addElement(new Token(new Number("0"), 'N'));
                    break;
                case 8:   //  '+'                (Plus Sign)
                case 9:   //  '-'                (Minus Sign)
                    mark = 'S';
                    break;
                case 10:  //  '+'                (Addition Operator)
                case 11:  //  '-'                (Subtraction Operator)
                    mark = 'O';
                    break;
                case 15:  //  '='
                    if (tokens.size() > 1) {
                        errorMessage = "Invalid assignment: " + buffer + " =";
                        tokens.removeAllElements();
                        return;
                    }
                    if (t != null && t.mark() == 'F') {
                        errorMessage = "Illegal assignment: " + 
                                buffer + " " + c;
                        tokens.removeAllElements();
                        return;
                    }
                    break;
            }
            t = new Token(new Term(c), mark);
            tokens.addElement(t) ;
        }
        this.expression = tokensToString();
    }
    
    /**************************************************************************/
    
    private int deltaQ(int q, char c) {
        int col;
        if (Character.isDigit(c)) col = 0;
        else if (Character.isUpperCase(c) || c == '_') {
            if (c == 'E' )        col = 2;
            else                  col = 3;
        }
        else {
            switch (c) {
                case  '.': col =  1; break;
                case  '(': col =  4; break;
                case  '%': col =  5; break;
                case  '^':
                case  '*':
                case  '/': col =  6; break;
                case  '+': col =  7; break;
                case  '-': col =  8; break;
                case  ',': col =  9; break;
                case  ')': col = 10; break;
                case  '=': col = 11; break;
                case  ' ':
                case '\n': col = 12; break;
                case Midlet.LIMITER:   // the code for the end of expression
                           col = 13; break;
                default:   return -4;  // Unknown symbol error
            }
        }
        return DFA_STATE[q][col];
    }
}
