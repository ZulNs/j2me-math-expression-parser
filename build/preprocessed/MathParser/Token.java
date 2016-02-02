package MathParser;

public class Token {
    private Object object;
    private char   mark;
    private byte   priority;
    
    public Token(Object token, char mark) {
        object    = token;
        this.mark = mark;
        priority  = 0;
        setPriority();
    }
    
    public Object object() {
        return object;
    }
    
    public byte priority() {
        return priority;
    }
    
    public char mark() {
        return mark;
    }
    
    public String toString() {
        return object.toString();
    }
    
    private void setPriority() {
        if      (mark == '(')                priority = 1;
        else if (mark == 'F')                priority = 2;
        else if (mark == 'S' || mark == '%') priority = 3;
        else if (mark == 'O') {
            char c = ((Term) object).symbol();
            if      (c == '^')               priority = 4;
            else if (c == '*' || c == '/')   priority = 5;
            else if (c == '+' || c == '-')   priority = 6;
        }
    }
}
