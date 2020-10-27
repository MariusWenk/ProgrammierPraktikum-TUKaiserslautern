package de.tukl.programmierpraktikum2020.mp2;

import de.tukl.programmierpraktikum2020.mp2.antlr.FunctionBaseVisitor;
import de.tukl.programmierpraktikum2020.mp2.antlr.FunctionParser;
import de.tukl.programmierpraktikum2020.mp2.functions.*;

public class Parser extends FunctionBaseVisitor<Function> {
    @Override
    public Function visitParExpr(FunctionParser.ParExprContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public Function visitTrigExp(FunctionParser.TrigExpContext ctx) {
        if (ctx.op.getType() == FunctionParser.SIN) {
            return new Sin(this.visit(ctx.expr()));
        } else if(ctx.op.getType() == FunctionParser.TAN){
            return new Tan(this.visit(ctx.expr()));
        }else {
            assert (ctx.op.getType() == FunctionParser.COS);
            return new Cos(this.visit(ctx.expr()));
        }
    }
    @Override
    public Function visitTrigExpInv(FunctionParser.TrigExpInvContext ctx) {
        if (ctx.op.getType() == FunctionParser.ASIN) {
            return new ASin(this.visit(ctx.expr()));
        } else if(ctx.op.getType() == FunctionParser.ATAN){
            return new ATan(this.visit(ctx.expr()));
        }else {
            assert (ctx.op.getType() == FunctionParser.ACOS);
            return new ACos(this.visit(ctx.expr()));
        }
    }

    @Override
    public Function visitAbsExpr(FunctionParser.AbsExprContext ctx) {
        return new Abs(this.visit(ctx.expr()));
    }

    @Override
    public Function visitExpExpr(FunctionParser.ExpExprContext ctx) {
        if (ctx.op.getType() == FunctionParser.EXP) {
            return new Exp(this.visit(ctx.expr()));
        } else {
            assert (ctx.op.getType() == FunctionParser.LOG);
            return new Log(this.visit(ctx.expr()));
        }
    }

    @Override
    public Function visitSgnValExpr(FunctionParser.SgnValExprContext ctx) {
        if (ctx.sgn == null || ctx.sgn.getType() == FunctionParser.PLUS) {
            return this.visit(ctx.var());
        } else {
            return new Mult(new Const(-1), this.visit(ctx.var()));
        }

    }

    @Override
    public Function visitAddExpr(FunctionParser.AddExprContext ctx) {
        if (ctx.op.getType() == FunctionParser.PLUS) {
            return new Plus(this.visit(ctx.lexpr), this.visit(ctx.rexpr));
        } else {
            assert (ctx.op.getType() == FunctionParser.MINUS);
            return new Plus(this.visit(ctx.lexpr), new Mult(new Const(-1), this.visit(ctx.rexpr)));
        }
    }

    @Override
    public Function visitPowExpr(FunctionParser.PowExprContext ctx) {
        return new Pow(this.visit(ctx.lexpr), this.visit(ctx.rexpr));
    }

    @Override
    public Function visitMultExpr(FunctionParser.MultExprContext ctx) {
        if (ctx.op.getType() == FunctionParser.MULT) {
            return new Mult(this.visit(ctx.lexpr), this.visit(ctx.rexpr));
        } else {
            assert (ctx.op.getType() == FunctionParser.DIV);
            return new Div(this.visit(ctx.lexpr), this.visit(ctx.rexpr));
        }
    }

    @Override
    public Function visitConstVar(FunctionParser.ConstVarContext ctx) {
        return new Const(Double.parseDouble(ctx.getText()));
    }

    @Override
    public Function visitIdVar(FunctionParser.IdVarContext ctx) {
        return new X();
    }
}
