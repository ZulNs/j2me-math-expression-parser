package MathParser;

import java.util.Vector;
import java.util.Stack;

public class Check {
    private String errorMessage;
    private Vector tokens;
    
    public Check(Vector tokens) {
        errorMessage = "";
        this.tokens  = tokens;
    }
    
    public String errorMessage() {
        return errorMessage;
    }
    
    public boolean isValid() {
        return errorMessage.equals("");
    }
    
    public void beginCheck() {
        if (tokens.isEmpty()) return;
        errorMessage = "";
        Stack st = new Stack(), si = new Stack();
        Token t;
        Function f;
        int commaCtr, args, minArgs, maxArgs;
        int l = tokens.size(), idx;
        char c;
        for (int i = 0; i < l; i++) {
            t = (Token)tokens.elementAt(i);
            c = t.mark();
            if (c == 'F') {
                st.push(t);
                si.push(new Integer(i));
                i++; // bypass an open parenthesis after a function
            }
            else if (c == '(') {
                st.push(t);
                si.push(new Integer(i));
            }
            else if (c == ',') {
                if (st.empty()) {
                    errorMessage = "Unknown arguments: " +
                                   getTokensString(i - 1, 3);
                    return;
                }
                st.push(t);
            }
            else if (c == ')') {
                commaCtr = 0;
                while (true) {
                    if (st.empty()) {
                        errorMessage = "Unbalanced brackets: excess of " +
                                       "close parenthesis";
                        return;
                    }
                    t = (Token)st.pop();
                    if (t.mark() == ',') {
                        commaCtr++;
                        continue;
                    }
                    break;
                }
                if (((Token)tokens.elementAt(i - 1)).mark() == '(')
                    args = 0;
                else
                    args = commaCtr + 1;
                idx = ((Integer)si.pop()).intValue();
                if (t.mark() == '(') {
                    if (commaCtr > 0) {
                        errorMessage = "Arguments without function: " +
                              getTokensString(idx, i - idx + 1);
                        return;
                    }
                    if (args == 0) {
                        errorMessage = "Detected an empty parentheses: " +
                                getTokensString(idx - 1, 4);
                        return;
                    }
                    continue;
                }
                // when t.mark() == 'F'
                f = (Function)t.object();
                minArgs = f.getNumberOfArguments();
                maxArgs = minArgs >> 8;
                minArgs &= 0x00ff;
                if (maxArgs == 0) maxArgs = minArgs;
                if (args < minArgs && args == 0) {
                    errorMessage = "No arguments for this function: " +
                            getTokensString(idx, i - idx + 1);
                    return;
                }
                else if (args < minArgs) {
                    errorMessage = "Too few arguments for this function: " +
                            getTokensString(idx, i - idx + 1);
                    return;
                }
                else if (args > maxArgs) {
                    errorMessage = "Too many arguments for this function: " +
                            getTokensString(idx, i - idx + 1);
                    return;
                }
                if (minArgs != maxArgs)
                    ((Function) ((Token) tokens.elementAt(idx)).object()).
                            setNumberOfArguments(args);
            }
        }
        if (!st.empty())
            errorMessage = "Unbalanced brackets: excess of open parenthesis";
    }
    
    /**************************************************************************/
    
    private String getTokensString(int pos, int length) {
        String s = "";
        int begin = pos,
            end   = begin + length,
            size  = tokens.size();
        if (begin < 0) begin = 0;
        if (end > size) end = size;
        for (int i = begin; i < end; i++)
            s += ((Token) tokens.elementAt(i)).toString();
        if (s.length() > 67) {
            s = s.substring(0, 32) + "..." + s.substring(s.length() - 32);
        }
        if (begin > 0)  s = "..." + s;
        if (end < size) s += "...";
        return s;
    }
}
