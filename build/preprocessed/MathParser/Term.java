package MathParser;

public class Term {
    private char symbol;
    
    public Term(char symbol) {
        this.symbol = symbol;
    }
    
    public char symbol() {
        return symbol;
    }
    
    public String toString() {
        return new Character(symbol).toString();
    }
}
