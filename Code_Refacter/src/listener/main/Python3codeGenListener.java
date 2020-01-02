package listener.main;

import gen.Python3BaseListener;
import gen.Python3Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.util.HashMap;

import static listener.main.Python3codeGenListenerHelper.*;

public class Python3codeGenListener extends Python3BaseListener implements ParseTreeListener {

    boolean import_flag = false;

    HashMap<Integer, String> newtext = new HashMap<>();

    int lineNumber = 0;

    @Override
    public void enterFile_input(Python3Parser.File_inputContext ctx) {
        for (int i = 0; i < ctx.stmt().size(); i++) {
            if (import_flag && (ctx.stmt(i).start.getText().equals("from") || ctx.stmt(i).start.getText().equals("import"))) {
                System.out.println(i+": Always place an import statement at the top of the file ");
            }
            if (!(ctx.stmt(i).start.getText().equals("from") || ctx.stmt(i).start.getText().equals("import"))) {
                import_flag = true;
            }
        }
    }

    @Override
    public void enterWhile_stmt(Python3Parser.While_stmtContext ctx) {
        if (ishasElseStmt(ctx)) {
            System.out.print("Clear else_stmt after loop!");
        }
    }

    @Override
    public void enterFor_stmt(Python3Parser.For_stmtContext ctx) {
        if (ishasElseStmt(ctx)) {
            System.out.print("Clear else_stmt after loop!");
        }
    }

    //모든 basic block의 시작노드
    @Override
    public void enterSmall_stmt(Python3Parser.Small_stmtContext ctx) {
//        System.out.println(ctx);
    }

    @Override
    public void enterStmt(Python3Parser.StmtContext ctx) {
        if (ctx.depth() == 4) System.out.print(lineNumber++ + ": ");
    }

    @Override
    public void exitStmt(Python3Parser.StmtContext ctx) {
        if (ctx.depth() == 4) System.out.println();
    }

    @Override
    public void exitSmall_stmt(Python3Parser.Small_stmtContext ctx) {
//        System.out.println(ctx);
    }
}
