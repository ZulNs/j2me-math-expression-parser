package MathParser;

import java.util.Vector;

public class VarList {
    private Vector varList;
    private static final int limit = 256;
    
    public VarList() {
        varList = new Vector();
    }
    
    public int append(String name) {
        int l = varList.size(), idx = getIndex(name);
        if (idx == -1) {
            if (l < limit) {
                varList.addElement(new Variable(name, 0.0));
                return l;
            }
            return -1;
        }
        return idx;
    }
    
    public Variable getVariable(int index) {
        return (Variable) varList.elementAt(index);
    }
    
    public void removeElementAt(int index) {
        varList.removeElementAt(index);
    }
    
    public void removeLastElement(String confirmVarName) {
        if (getIndex(confirmVarName) == varList.size() - 1)
            varList.removeElementAt(varList.size() - 1);
    }
    
    public String[] getList() {
        String[] s = new String[varList.size()];
        for (int i = 0; i < varList.size(); i++)
            s[i] = getVariable(i).toString();
        return s;
    }
    
    public int getSize() {
        return varList.size();
    }
    
    public void clear() {
        varList.removeAllElements();
    }
    
    private int getIndex(String name) {
        for (int i = 0; i < varList.size(); i++) {
            if (getVariable(i).getName().equals(name))
                return i;
        }
        return -1;
    }
}
