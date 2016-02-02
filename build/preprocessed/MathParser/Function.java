package MathParser;

public class Function {
    private byte code;
    private int numberOfArguments;
    
    public Function(String function) {
        code = -1;
        int l = Help.FUNCTION_ARGS.length;
        for (int i = 0; i < l; i++) {
            if (function.equals(Help.FUNCTIONS[i])) {
                code = (byte) i;
                numberOfArguments = Help.FUNCTION_ARGS[i];
                break;
            }
        }
    }
    
    public int getNumberOfArguments() {
        return numberOfArguments;
    }
    
    public void setNumberOfArguments(int value) {
        numberOfArguments = value;
    }
    
    public boolean isValidFunction() {
        return (code == -1) ? (false) : (true);
    }
    
    public byte getCode() {
        return code;
    }
    
    public String toString() {
        return Help.FUNCTIONS[code];
    }
}
