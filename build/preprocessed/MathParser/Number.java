package MathParser;

public class Number {
    private double doubleValue;
    
    public Number(String number) {
        try {
            doubleValue = Double.parseDouble(number);
            if (doubleValue == -0.0) doubleValue = 0.0;
        }
        catch (NumberFormatException nfe) {
            doubleValue = Double.NaN;
        }
    }
    
    public double doubleValue() {
        return doubleValue;
    }
    
    public boolean isInfinite() {
        return new Double(doubleValue).isInfinite();
    }
    
    public boolean isNaN() {
        return new Double(doubleValue).isNaN();
    }
    
    public String toString() {
        String s = Double.toString(doubleValue);
        int l = s.length();
        if (s.substring(l - 2).equals(".0"))
            s = s.substring(0, l - 2);
        return s;
    }
}
