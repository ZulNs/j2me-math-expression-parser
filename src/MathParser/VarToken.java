package MathParser;

public class VarToken {
    private int index;
    private VarList varList;
    
    public VarToken(String name, VarList varList) {
        this.varList = varList;
        index = this.varList.append(name);
    }
    
    public Variable getVariable() {
        return varList.getVariable(index);
    }
    
    public int getIndex() {
        return index;
    }
    
    public String toString() {
        return getVariable().getName();
    }
}
