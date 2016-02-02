package MathParser;

public class Variable {
    private String name;
    private double value;
    
    public Variable(String name, double value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public double getValue() {
        return value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public String toString() {
        String s = new Double(value).toString();
        int l = s.length();
        if (s.substring(l - 2).equals(".0"))
            s = s.substring(0, l - 2);
        return name + " = " + s;
    }
}
