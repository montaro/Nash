package com.c9.nash;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Throwable {
        System.out.println("Ahmed");
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        engine.eval("function sum(a, b) { return a + b; }");
        System.out.println(engine.eval("sum(1, 2);"));
        Invocable invocable = (Invocable) engine;
        System.out.println(invocable.invokeFunction("sum", 4, 5));
        Adder adder = invocable.getInterface(Adder.class);
        System.out.println(adder.sum(9, 9));
        engine.eval(new FileReader("src/com/c9/nash/dir.js"));
        System.out.println(invocable.invokeFunction("sum", 5, 7));
    }
}
