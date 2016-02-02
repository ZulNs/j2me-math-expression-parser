package MathParser;

import java.util.Vector;

public class History {
    private Vector history;
    private static final int limit = 32;
    
    public History() {
        history = new Vector();
    }
    
    public void append(String statement) {
        int l = history.size();
        for (int i = 0; i < l; i++)
            if (statement.equals((String) history.elementAt(i))) {
                history.removeElementAt(i);
                history.addElement(statement);
                return;
            }
        if (l == limit) history.removeElementAt(0);
        history.addElement(statement);
    }
    
    public String getElement(int index) {
        if (index >= 0 && index < limit)
            return (String) history.elementAt(index);
        else return "";
    }
    
    public String[] getList() {
        int l = history.size();
        String[] sa = new String[l];
        String s;
        for (int i = 0; i < l; i++) {
            s = (String) history.elementAt(i);
            if (s.length() > 32) s = s.substring(0, 29) + "...";
            sa[i] = s;
        }
        return sa;
    }
    
    public int getSize() {
        return history.size();
    }
    
    public void removeElementAt(int index) {
        history.removeElementAt(index);
    }
    
    public void clear() {
        history.removeAllElements();
    }
}
