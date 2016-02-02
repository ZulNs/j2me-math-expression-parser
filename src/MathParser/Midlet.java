package MathParser;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Midlet extends MIDlet implements CommandListener {
    private final String TITLE        = getAppProperty("MIDlet-Name");
    private final String VENDOR       = getAppProperty("MIDlet-Vendor");
    private final String VERSION      = getAppProperty("MIDlet-Version");
    private final String AUTHOR       = getAppProperty("MIDlet-Author");
    private final String DESCRIPTION  = getAppProperty("MIDlet-Description");
    private final String ORGANIZATION = getAppProperty("MIDlet-Organization");
    private final String YEAR         = getAppProperty("MIDlet-Year");
    private final String ABOUT_ICON   = getAppProperty("MIDlet-About-Icon");
    public static final char LIMITER = ';';
    private static final byte NORMAL_VIEW = 0,
                              DMS_VIEW    = 1,
                              HEX_VIEW    = 2;
    private byte resultDisplayMode;
    private boolean    midletPaused = false;
    private TextBox    expTxt;
    private Form       resultForm,
                       helpForm,
                       functHelpForm,
                       aboutForm;
    private List       functList,
                       varList,
                       historyList;
    private StringItem functNameStr,
                       functDescStr,
                       expStr;
    private TextField  resultTxt,
                       hexTxt,
                       longBitsTxt;
    private Alert      alert,
                       confirmAlert;
    private Command    exitCmd,
                       evalCmd,
                       functCmd,
                       varCmd,
                       lastRsltCmd,
                       clearExpCmd,
                       historyCmd,
                       helpCmd,
                       aboutCmd,
                       okCmd,
                       cancelCmd,
                       editCmd,
                       newCmd,
                       normalViewCmd,
                       dmsViewCmd,
                       hexViewCmd,
                       delCmd,
                       delAllCmd,
                       yesCmd,
                       noCmd;
    private boolean varCmdEnabled,
                    historyCmdEnabled,
                    varListDisp,
                    delAll;
    private Tokenize tknz;
    private Check    check;
    private Eval     eval;
    private History  history;
    private VarList  vlist;
    
    public Midlet() {
    }
    
    private void initialize() {
        evalCmd       = new Command("Evaluate",           Command.OK,     1);
        functCmd      = new Command("Insert function",    Command.SCREEN, 2);
        varCmd        = new Command("Insert variable",    Command.SCREEN, 3);
        lastRsltCmd   = new Command("Insert last result", Command.SCREEN, 4);
        clearExpCmd   = new Command("Clear expression",   Command.SCREEN, 5);
        historyCmd    = new Command("History",            Command.SCREEN, 6);
        helpCmd       = new Command("Help",               Command.SCREEN, 7);
        aboutCmd      = new Command("About",              Command.SCREEN, 8);
        exitCmd       = new Command("Exit",               Command.EXIT,   9);
        okCmd         = new Command("OK",                 Command.OK,     1);
        delCmd        = new Command("Delete",             Command.SCREEN, 2);
        delAllCmd     = new Command("Delete all",         Command.SCREEN, 3);
        cancelCmd     = new Command("Cancel",             Command.CANCEL, 4);
        editCmd       = new Command("Edit",               Command.OK,     1);
        newCmd        = new Command("New",                Command.SCREEN, 2);
        normalViewCmd = new Command("Normal view",        Command.SCREEN, 3);
        dmsViewCmd    = new Command("DMS view",           Command.SCREEN, 4);
        hexViewCmd    = new Command("Hex view",           Command.SCREEN, 5);
        yesCmd        = new Command("Yes",                Command.OK,     1);
        noCmd         = new Command("No",                 Command.CANCEL, 2);
        int l = Help.FUNCTIONS.length;
        String[] f = new String[l];
        for (int i = 0; i < l; i++) {
            f[i] = Help.FUNCTIONS[i] + Help.getArgsText(i);
        }
        functList     = new List("Select Function", List.EXCLUSIVE, f, null);
        varList       = new List("Select Variable", List.EXCLUSIVE);
        historyList   = new List("Select History",  List.EXCLUSIVE);
        expTxt        = new TextBox(TITLE, "", 65536, TextField.ANY);
        helpForm      = new Form(TITLE + " Help");
        functHelpForm = new Form("Function Help");
        aboutForm     = new Form("About " + TITLE);
        resultForm    = new Form("Result");
        functNameStr = new StringItem(null, null);
        functDescStr = new StringItem("Description:", null);
        expStr       = new StringItem("Expression:", null);
        resultTxt   = new TextField("Result:", "", 32, TextField.UNEDITABLE);
        hexTxt      = new TextField("Hex:", "", 32, TextField.UNEDITABLE);
        longBitsTxt = new TextField("LongBits:", "", 32, TextField.UNEDITABLE);
        history = new History();
        vlist   = new VarList();
        tknz    = new Tokenize(vlist);
        check   = new Check(tknz.getTokens());
        eval    = new Eval(tknz.getTokens());
        expTxt.setTicker(new Ticker(
            DESCRIPTION + " (c) " + VENDOR + " - " + ORGANIZATION + " - " + YEAR
            ));
        expTxt.addCommand(evalCmd);
        expTxt.addCommand(functCmd);
        expTxt.addCommand(lastRsltCmd);
        expTxt.addCommand(clearExpCmd);
        expTxt.addCommand(helpCmd);
        expTxt.addCommand(aboutCmd);
        expTxt.addCommand(exitCmd);
        expTxt.setCommandListener(this);
        functList.addCommand(okCmd);
        functList.addCommand(helpCmd);
        functList.addCommand(cancelCmd);
        functList.setCommandListener(this);
        varList.addCommand(okCmd);
        varList.addCommand(delCmd);
        varList.addCommand(delAllCmd);
        varList.addCommand(cancelCmd);
        varList.setCommandListener(this);
        historyList.addCommand(okCmd);
        historyList.addCommand(delCmd);
        historyList.addCommand(delAllCmd);
        historyList.addCommand(cancelCmd);
        historyList.setCommandListener(this);
        helpForm.append(
            "Arithmetic Operators:\n" +
            "To perform basic mathematical operations such as addition, " +
            "subtraction, or multiplication; combine numbers; and produce " +
            "numeric results, use the following arithmetic operators.\n" +
            " [+] Addition\n" + 
            " [-] Substraction\n" +
            " [*] Multiplication\n" +
            " [/] Division\n" +
            " [%] Percent\n" +
            " [^] Exponentiation\n" +
            "Operator Precedence:\n" +
            "If you combine several operators, " + TITLE + " performs the " +
            "operations in the order shown in the following precedence. If " +
            "the expression contains some operators with similar precedence, " +
            TITLE + " evaluates the operators from left to right.\n" +
            " [-] & [%] Negation & Percent\n" + 
            " [^] Exponentiation\n" +
            " [*] & [/] Mul & Div\n" +
            " [+] & [-] Add & Sub\n" +
            "Use of Parentheses:\n" +
            "To change the order of evaluation, enclose in parentheses the " +
            "part of the expression to be calculated first. For example, " +
            "the following expression produces 11 because " + TITLE +
            " calculates multiplication before addition. The expression " +
            "multiplies 2 by 3 and then adds 5 to the result.\n" +
            " 5+2*3\n" +
            "In contrast, if you use parentheses to change the syntax, " +
            TITLE + " adds 5 and 2 together and then multiplies the " +
            "result by 3 to produce 21.\n" +
            " (5+2)*3\n" +
            "Use of Functions and Comma Separator:\n" +
            "You can use built-in functions (select Menu and then select " +
            "Insert function). The argument(s) of a function must be " +
            "enclose in parentheses and if more than one it must be " +
            "separated by comma \",\". The use of parentheses is required " +
            "even for a function that doesn't take any arguments.\n" +
            "For example:" +
            " SQRT(25) produces 5\n" +
            " POWER(2,3) pruduces 8\n" +
            " PI() produces 3.14"
            );
        helpForm.addCommand(okCmd);
        helpForm.setCommandListener(this);
        functHelpForm.append(functNameStr);
        functHelpForm.append(functDescStr);
        functHelpForm.addCommand(okCmd);
        functHelpForm.setCommandListener(this);
        Image img = null;
        try { img = Image.createImage(ABOUT_ICON); }
        catch (java.io.IOException err) {}
        aboutForm.append(img);
        aboutForm.append(TITLE + " v" + VERSION + "\n");
        aboutForm.append(DESCRIPTION + "\n");
        aboutForm.append("(c) " + AUTHOR + "\n");
        aboutForm.append(ORGANIZATION + "\n");
        aboutForm.append(YEAR);
        for (int i = 0; i < aboutForm.size(); i++) {
            aboutForm.get(i).setLayout(Item.LAYOUT_CENTER);
        }
        aboutForm.addCommand(okCmd);
        aboutForm.setCommandListener(this);
        resultForm.append(expStr);
        resultForm.append(resultTxt);
        resultForm.addCommand(exitCmd);
        resultForm.addCommand(editCmd);
        resultForm.addCommand(newCmd);
        resultForm.addCommand(dmsViewCmd);
        resultForm.addCommand(hexViewCmd);
        resultForm.setCommandListener(this);
        alert        = new Alert("", "", null, null);
        alert.setTimeout(Alert.FOREVER);
        confirmAlert = new Alert("", "", null, AlertType.CONFIRMATION);
        confirmAlert.setTimeout(Alert.FOREVER);
        confirmAlert.addCommand(yesCmd);
        confirmAlert.addCommand(noCmd);
        confirmAlert.setCommandListener(this);
        resultDisplayMode = NORMAL_VIEW;
        varCmdEnabled     = false;
        historyCmdEnabled = false;
        varListDisp       = false;
        delAll            = false;
    }
    
    private boolean evaluate(String expression) {
        tknz.buildTokens(expression, VENDOR);
        if (vlist.getSize() > 0 && !varCmdEnabled) {
            expTxt.addCommand(varCmd);
            varCmdEnabled = true;
        }
        if (!tknz.isValid()) {
            setAlert("Error on tokenizing", tknz.errorMessage(),
                     AlertType.ERROR, expTxt);
            return false;
        }
        if (tknz.toString().equals("")) return true;
        check.beginCheck();
        if (!check.isValid()) {
            setAlert("Error on checking", check.errorMessage(),
                     AlertType.ERROR, expTxt);
            return false;
        }
        history.append(tknz.toString());
        if (!historyCmdEnabled) {
            expTxt.addCommand(historyCmd);
            historyCmdEnabled = true;
        }
        eval.beginEval();
        if (!eval.isValid()) {
            setAlert("Error on evaluating", eval.errorMessage(),
                     AlertType.ERROR, expTxt);
            return false;
        }
        return true;
    }
    
    private void setAlert(String title, String message, AlertType type,
                          Displayable nextDisplayable) {
        alert.setTitle(title);
        alert.setString(message);
        alert.setType(type);
        switchDisplayable(alert, nextDisplayable);
    }
    
    private void changeDisplayMode(byte displayMode) {
        switch (resultDisplayMode) {
            case NORMAL_VIEW:
                resultForm.addCommand(normalViewCmd);
                break;
            case DMS_VIEW:
                resultForm.addCommand(dmsViewCmd);
                break;
            case HEX_VIEW:
                resultForm.addCommand(hexViewCmd);
                for (int i = 0; i < resultForm.size(); i++)
                    if (resultForm.get(i) == hexTxt) resultForm.delete(i);
                for (int i = 0; i < resultForm.size(); i++)
                    if (resultForm.get(i) == longBitsTxt) resultForm.delete(i);
        }        
        switch (displayMode) {
            case NORMAL_VIEW:
                resultForm.removeCommand(normalViewCmd);
                break;
            case DMS_VIEW:
                resultForm.removeCommand(dmsViewCmd);
                break;
            case HEX_VIEW:
                resultForm.removeCommand(hexViewCmd);
                resultForm.append(hexTxt);
                resultForm.append(longBitsTxt);
        }
        resultDisplayMode = displayMode;
        displayResult(displayMode);
    }
    
    private void displayResult(byte displayMode) {
        expStr.setText(tknz.toString());
        switch (displayMode) {
            case NORMAL_VIEW:
                resultTxt.setString(eval.toString());
                break;
            case DMS_VIEW:
                resultTxt.setString(eval.toDMSString());
                break;
            case HEX_VIEW:
                resultTxt.setString(eval.toString());
                hexTxt.setString(eval.toHexString());
                longBitsTxt.setString(eval.toOriginalHexString());
        }
    }
    
    public void startMIDlet() {
        switchDisplayable(null, expTxt);
    }
    
    public void resumeMIDlet() {
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current instanceof javax.microedition.lcdui.Alert) {
            current = expTxt;
        }
        setAlert(TITLE, "Welcome back... :-)", AlertType.INFO, current);
    }
    
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }
    
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        Display display = Display.getDisplay(this);
        if (alert == null) display.setCurrent(nextDisplayable);
        else display.setCurrent(alert, nextDisplayable);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCmd) exitMIDlet();
        else if (c == cancelCmd)
            switchDisplayable(null, expTxt);
        else if (c == editCmd) {
            expTxt.setString(tknz.toString());
            switchDisplayable(null, expTxt);
        }
        else if (c == functCmd) switchDisplayable(null, functList);
        else if (c == varCmd) {
            if (varList.size() !=0) varList.deleteAll();
            String[] s = vlist.getList();
            int l = s.length;
            if (l > 0) {
                for (int i = 0; i < l; i++) varList.append(s[i], null);
                switchDisplayable(null, varList);
            }
        }
        else if (c == historyCmd) {
            if (historyList.size() != 0) historyList.deleteAll();
            String[] s = history.getList();
            int l = s.length;
            if (l > 0) {
                for (int i = 0; i < l; i++) historyList.append(s[i], null);
                switchDisplayable(null, historyList);
            }
        }
        else if (c == delCmd) {
            if (d == varList) {
                confirmAlert.setTitle("Delete Variable");
                confirmAlert.setString(
                        "Are you sure want to delete this variable?\n" +
                        varList.getString(varList.getSelectedIndex()));
                varListDisp = true;
            }
            else {  // if (d == historyList)
                confirmAlert.setTitle("Delete History");
                confirmAlert.setString(
                        "Are you sure want to delete this history?\n" +
                        historyList.getString(historyList.getSelectedIndex()));
                varListDisp = false;
            }
            delAll = false;
            switchDisplayable(null, confirmAlert);
        }
        else if (c == delAllCmd) {
            if (d == varList) {
                confirmAlert.setTitle("Delete Variable");
                confirmAlert.setString(
                        "Are you sure want to delete all variables?");
                varListDisp = true;
            }
            else {  // if (d == historyList)
                confirmAlert.setTitle("Delete History");
                confirmAlert.setString(
                        "Are you sure want to delete all histories?");
                varListDisp = false;
            }
            delAll = true;
            switchDisplayable(null, confirmAlert);
        }
        else if (c == yesCmd) {
            if (varListDisp) {  // varList
                if (delAll) {  // delete all
                    vlist.clear();
                    varList.deleteAll();
                    expTxt.removeCommand(varCmd);
                    varCmdEnabled = false;
                    switchDisplayable(null, expTxt);
                }
                else {  // delete one line
                    vlist.removeElementAt(varList.getSelectedIndex());
                    varList.delete(varList.getSelectedIndex());
                    if (varList.size() == 0) {
                        expTxt.removeCommand(varCmd);
                        varCmdEnabled = false;
                        switchDisplayable(null, expTxt);
                    }
                    switchDisplayable(null, varList);
                }
            }
            else {  // historyList
                if (delAll) {  // delete all
                    history.clear();
                    historyList.deleteAll();
                    expTxt.removeCommand(historyCmd);
                    historyCmdEnabled = false;
                    switchDisplayable(null, expTxt);
                }
                else {  // delete one line
                    history.removeElementAt(historyList.getSelectedIndex());
                    historyList.delete(historyList.getSelectedIndex());
                    if (historyList.size() == 0) {
                        expTxt.removeCommand(this.historyCmd);
                        historyCmdEnabled = false;
                        switchDisplayable(null, expTxt);
                    }
                    switchDisplayable(null, historyList);
                }
            }
        }
        else if (c == noCmd) {
            if (varListDisp) switchDisplayable(null, varList);
            else             switchDisplayable(null, historyList);
        }
        else if (c == lastRsltCmd)
            expTxt.insert(eval.toString(), expTxt.getCaretPosition());
        else if (c == clearExpCmd) expTxt.setString("");
        else if (c == helpCmd) {
            if (d == expTxt) switchDisplayable(null, helpForm);
            else { // if (d == functList)
                int idx = functList.getSelectedIndex();
                functNameStr.setText(functList.getString(idx));
                functDescStr.setText(Help.FUNCTION_DESCS[idx]);
                switchDisplayable(null, functHelpForm);
            }
        }
        else if (c == aboutCmd) switchDisplayable(null, aboutForm);
        else if (c == okCmd) {
            if (d == functList) {
                String ts = "";
                int idx = functList.getSelectedIndex();
                ts += Help.FUNCTIONS[idx] + "(";
                if (Help.FUNCTION_ARGS[idx] == 0) ts += ")";
                expTxt.insert(ts, expTxt.getCaretPosition());
                switchDisplayable(null, expTxt);
            }
            else if (d == varList) {
                String s =
                        vlist.getVariable(varList.getSelectedIndex()).getName();
                expTxt.insert(s, expTxt.getCaretPosition());
                switchDisplayable(null, expTxt);
            }
            else if (d == historyList) {
                expTxt.insert(history.getElement(
                        historyList.getSelectedIndex()),
                        expTxt.getCaretPosition());
                switchDisplayable(null, expTxt);
            }
            else if (d == helpForm || d == aboutForm)
                switchDisplayable(null, expTxt);
            else // if (d == functHelpForm)
                switchDisplayable(null, functList);
        }
        else if (c == newCmd) {
            expTxt.setString("");
            switchDisplayable(null, expTxt);
        }
        else if (c == normalViewCmd)
            changeDisplayMode(NORMAL_VIEW);
        else if (c == dmsViewCmd)
            changeDisplayMode(DMS_VIEW);
        else if (c == hexViewCmd)
            changeDisplayMode(HEX_VIEW);
        else if (c == evalCmd) {
            String all = expTxt.getString().trim(), exp;
            int from = 0, mark = 0;
            boolean disp = false;
            while (mark != -1) {
                mark = all.indexOf(LIMITER, from);
                if (mark == -1) exp = all.substring(from);
                else exp = all.substring(from, mark + 1);
                exp = exp.trim();
                if (!exp.equals("") && !exp.equals("" + LIMITER)) {
                    disp = evaluate(exp);
                    if (!disp) break;
                }
                from = mark + 1;
                if (from >= all.length()) break;
            }
            if (disp) {
                displayResult(resultDisplayMode);
                switchDisplayable(null, resultForm);
            }
        }
    }
    
    public void startApp() {
        if (midletPaused) resumeMIDlet();
        else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }
    
    public void pauseApp() {
        midletPaused = true;
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
