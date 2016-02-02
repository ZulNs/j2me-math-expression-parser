package MathParser;

import java.util.Vector;
import java.util.Stack;

public class Eval {
    private double  result;
    private String  errorMessage;
    private boolean isAssign;
    private Vector  rpn;
    private Vector  tokens;
    
    public Eval(Vector tokens) {
        result       = 0.0;
        errorMessage = "";
        this.tokens  = tokens;
    }
    
    public boolean isValid() {
        return errorMessage.equals("");
    }
    
    public String errorMessage() {
        return errorMessage;
    }
    
    public String toString() {
        return doubleToString(new Double(result));
    }
    
    public String rpnToString() {
        String s = "";
        Token t;
        for (int i = 0; i < rpn.size(); i++) {
            t = (Token) rpn.elementAt(i);
            s += "[" + t.mark() + ":" + t.toString() + "]";
        }
        return s;
    }
    
    public String toDMSString() {
        double r = Math.abs(result),
               d = Math.floor(r),
               m = Math.floor((r - d) * 60),
               s = ExtMath.round((r - d - m / 60) * 3600, 3);
        String str = doubleToString(new Double(ExtMath.sign(result) * d));
        str += "° " + doubleToString(new Double(m));
        str += "' " + doubleToString(new Double(s)) + "\"";
        return str;
    }
    
    public String toOriginalHexString() {
        long lb = Double.doubleToLongBits(result);
        String r = Long.toString((lb >> 60) & 15L, 16);
        r += (Long.toString((lb & 0x7fffffffffffffffL) |
                   0x4000000000000000L, 16)).substring(1);
        return "0x" + r.toUpperCase();
    }
    
    public String toHexString() {
        if (Double.isNaN(result)) return "NaN";
        if (Double.isInfinite(result)) return Double.toString(result);
        long lb = Double.doubleToLongBits(result);
        String rslt = (((lb >> 63) & 1L) == 1L) ? "-" : "";
        if ((lb & 0x7fffffffffffffffL) == 0) return rslt + "0";
        int exp = (int) ((lb >> 52) & 0x7ffL) - 1023; // extract exponent
        lb &= 0x000fffffffffffffL;
        if (exp > -1023) lb |= 0x0010000000000000L; // case for normal value
        else exp++;                                 // case for subnormal value
        exp -= 52;
        while ((Math.abs(exp) % 4) != 0) {
            lb <<= 1;
            exp--;
        }
        exp /= 4;
        while ((lb & 15L) == 0L) {
            lb >>= 4;
            exp++;
        }
        String hex = Long.toString(lb, 16);
        int dp = hex.length();
        while (exp < 0 && dp > 1) {
            dp--;
            exp++;
        }
        while (exp < 0 && exp > -4) {
            hex = "0" + hex;
            exp++;
        }
        while (exp > 0 && hex.length() + exp < 15) {
            hex += "0";
            exp--;
            dp++;
        }
        while (exp > hex.length() && dp > 1) {
            dp--;
            exp++;
        }
        rslt += hex.substring(0, dp);
        if (hex.length() > dp )
            rslt += "." + hex.substring(dp);
        if (exp != 0) rslt += " * 16 ^ " + Integer.toString(exp);
        return rslt.toUpperCase();
    }
    
    public void beginEval() {
        rpn = new Vector();
        errorMessage = "";
        isAssign = false;
        if (tokens.isEmpty())
            return;
        buildRPN();
        if (isAssign && rpn.isEmpty()) {
            result = ((VarToken)((Token)tokens.elementAt(0)).object()).
                    getVariable().getValue();
            return;
        }
        execute();
        if (isAssign && errorMessage.equals("")) {
            ((VarToken)((Token)tokens.elementAt(0)).object()).
                    getVariable().setValue(result);
        }
    }
    
    /**************************************************************************/
    
    // Builds Reverse Polish Notation (RPN) tokens.
    private void buildRPN() {
        Stack s = new Stack();
        Token t, t0;
        char  c;
        int l = tokens.size();
        for (int i = 0; i < l; i++) {
            t  = (Token) tokens.elementAt(i);
            c  = t.mark();
            if (c == '=') {
                if (i == 1) {
                    isAssign = true;
                    rpn.removeAllElements();
                }
                continue;
            }
            if (c == 'N' || c == 'V') {
                rpn.addElement(t);
                continue;
            }
            if (c == '(' || c == 'F') {
                s.push(t);
                continue;
            }
            if (c == ',') {
                while (peekMark(s) != '(') {
                    rpn.addElement(s.pop());
                }
                continue;
            }
            if (c == ')') {
                while (peekMark(s) != '(') {
                    rpn.addElement(s.pop());
                }
                s.pop(); // remove '(' from stack
                if (peekMark(s) == 'F')
                    rpn.addElement(s.pop());
                continue;
            }
            while (!s.empty()) {
                t0 = (Token) s.peek();
                if (t0.mark() == 'S' && t.mark() == 'S')
                    break;
                if (t0.mark() != '(' && t0.priority() <= t.priority()) {
                    rpn.addElement(s.pop());
                    continue;
                }
                break;
            }
            s.push(t);
        }
        while (!s.empty()) {
            rpn.addElement(s.pop());
        }
    }
    
    private void execute() {
        Stack s = new Stack();
        Vector args = new Vector();
        Token t;
        Function f;
        int numArgs;
        Double oprn1, oprn2;
        char c;
        int l = rpn.size();
        for (int i = 0; i < l; i++) {
            t = (Token)rpn.elementAt(i);
            c = t.mark();
            args.removeAllElements();
            if (c == 'V')
                result = ((VarToken)t.object()).getVariable().getValue();
            else if (c == 'N')
                result = ((Number)t.object()).doubleValue();
            else if (c == 'F') {
                f = (Function)t.object();
                numArgs = f.getNumberOfArguments();
                if (numArgs > 0) {
                    for (int j = numArgs; j > 0; j--)
                        args.addElement((Double)s.pop());
                }
                result = performMathFunctions(f, args);
                if (isResultHasError()) {
                    errorMessage += f.toString() + "(";
                    for (int j = numArgs; j > 0; j--)
                        errorMessage += doubleToString(
                                        (Double)args.elementAt(j - 1)) + ",";
                    errorMessage = errorMessage.substring(0,
                                   errorMessage.length() - 1) + ")";
                    return;
                }
            }
            else if (c == 'S') {
                result = ((Double)s.pop()).doubleValue();
                if (t.toString().charAt(0) == '-')
                    result = -result;
            }
            else if (c == '%')
                result = ((Double)s.pop()).doubleValue() / 100.0;
            else { // c == 'O'
                oprn2 = (Double)s.pop();
                oprn1 = (Double)s.pop();
                result = performBasicOperations((Term)t.object(),
                         oprn1.doubleValue(), oprn2.doubleValue());
                if (isResultHasError()) {
                    errorMessage += doubleToString(oprn1) +
                                    t.object().toString() +
                                    doubleToString(oprn2);
                    return;
                }
            }
            if (i < l - 1) {
                s.push(new Double(result));
            }
        }
    }
    
    /**************************************************************************/
    
    private char peekMark(Stack s) {
        if (s.empty()) return '_';
        return ((Token)s.peek()).mark();
    }
    
    private boolean isResultHasError() {
        if (!errorMessage.equals("")) return true;
        if (Double.isNaN(result))
            errorMessage = "Undefined result: ";
        else if (Double.isInfinite(result))
            errorMessage = "Infinite result: ";
        return !errorMessage.equals("");
    }
    
    private String doubleToString(Double dbl) {
        if (dbl.doubleValue() == -0.0) dbl = new Double(0.0);
        String s = dbl.toString();
        int l = s.length();
        if (s.substring(l - 2).equals(".0"))
            s = s.substring(0, l - 2);
        return s;
    }
    
    private double performBasicOperations(Term oprt,
                                          double oprn1, double oprn2) {
        char c = oprt.toString().charAt(0);
        if (c == '*') return oprn1 * oprn2;
        if (c == '/') return oprn1 / oprn2;
        if (c == '+') return oprn1 + oprn2;
        if (c == '-') return oprn1 - oprn2;
        return ExtMath.power(oprn1, oprn2);
    }
    
    private double performMathFunctions(Function f, Vector args) {
        if (f.getCode() == 46)
            return ExtMath.pi();
        if (f.getCode() == 50)
            return ExtMath.rand();
        int numArgs = args.size();
        double a[] = new double[numArgs];
        for (int i = 0; i < numArgs; i++)
            a[i] = ((Double)args.elementAt(numArgs - 1 - i)).doubleValue();
        switch (f.getCode()) {
            case  0: return ExtMath.abs(a[0]);
            case  1: return ExtMath.acos(a[0]);
            case  2: return ExtMath.acosh(a[0]);
            case  3: return ExtMath.asin(a[0]);
            case  4: return ExtMath.asinh(a[0]);
            case  5: return ExtMath.atan(a[0]);
            case  6: return ExtMath.atan2(a[0], a[1]);
            case  7: return ExtMath.atanh(a[0]);
            case  8: return ExtMath.aveDev(a);
            case  9: return ExtMath.average(a);
            case 10: if (numArgs == 1) return ExtMath.ceiling(a[0]);
                     return ExtMath.ceiling(a[0], a[1]);
            case 11: return ExtMath.combin(a[0], a[1]);
            case 12: return ExtMath.cos(a[0]);
            case 13: return ExtMath.cosh(a[0]);
            case 14: return ExtMath.degrees(a[0]);
            case 15: return ExtMath.devSq(a);
            case 16: return ExtMath.distance(a[0], a[1], a[2], a[3]);
            case 17: return ExtMath.dms2dec(a);
            case 18: return ExtMath.even(a[0]);
            case 19: return ExtMath.exp(a[0]);
            case 20: return ExtMath.fact(a[0]);
            case 21: return ExtMath.factDouble(a[0]);
            case 22: return ExtMath.fisher(a[0]);
            case 23: return ExtMath.fisherInv(a[0]);
            case 24: if (numArgs == 1) return ExtMath.floor(a[0]);
                     return ExtMath.floor(a[0], a[1]);
            case 25: return ExtMath.frac(a[0]);
            case 26: return ExtMath.gcd(a);
            case 27: return ExtMath.geoMean(a);
            case 28: return ExtMath.heading(a[0], a[1], a[2], a[3]);
            case 29: return ExtMath.integer(a[0]);
            case 30: return ExtMath.latitude(a[0], a[1], a[2], a[3]);
            case 31: return ExtMath.lcm(a);
            case 32: return ExtMath.ln(a[0]);
            case 33: if (numArgs == 1) return ExtMath.log10(a[0]);
                     return ExtMath.log(a[0], a[1]);
            case 34: return ExtMath.log10(a[0]);
            case 35: return ExtMath.longitude(a[0], a[1], a[2], a[3]);
            case 36: return ExtMath.max(a);
            case 37: return ExtMath.mean(a);
            case 38: return ExtMath.median(a);
            case 39: return ExtMath.min(a);
            case 40: return ExtMath.mod(a[0], a[1]);
            case 41: return ExtMath.mode(a);
            case 42: return ExtMath.mRound(a[0], a[1]);
            case 43: return ExtMath.multiNomial(a);
            case 44: return ExtMath.odd(a[0]);
            case 45: return ExtMath.permut(a[0], a[1]);
            case 47: return ExtMath.power(a[0], a[1]);
            case 48: return ExtMath.product(a);
            case 49: return ExtMath.radians(a[0]);
            case 51: return ExtMath.randBetween(a[0], a[1]);
            case 52: if (numArgs == 1) return ExtMath.round(a[0]);
                     return ExtMath.round(a[0], a[1]);
            case 53: return ExtMath.roundDown(a[0], a[1]);
            case 54: if (numArgs == 1) return ExtMath.roundUp(a[0]);
                     return ExtMath.roundUp(a[0], a[1]);
            case 55: return ExtMath.sign(a[0]);
            case 56: return ExtMath.sin(a[0]);
            case 57: return ExtMath.sinh(a[0]);
            case 58: return ExtMath.sqrt(a[0]);
            case 59: return ExtMath.sqrtPi(a[0]);
            case 60: return ExtMath.stDev(a);
            case 61: return ExtMath.stDevP(a);
            case 62: return ExtMath.sum(a);
            case 63: return ExtMath.sumSq(a);
            case 64: return ExtMath.tan(a[0]);
            case 65: return ExtMath.tanh(a[0]);
            case 66: if (numArgs == 1) return ExtMath.trunc(a[0]);
                     return ExtMath.trunc(a[0], a[1]);
            case 67: return ExtMath.var(a);
            case 68: return ExtMath.varP(a);
            default: return 0.0;
        }
    }
}
