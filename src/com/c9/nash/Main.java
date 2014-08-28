package com.c9.nash;

import javax.script.*;
import java.io.FileReader;
import java.security.AccessControlException;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws Throwable {
        System.out.println("Starting validation process for basic scenario...");
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        engine.eval(new FileReader("src/com/c9/nash/validator1.js"));
        Invocable invocable = (Invocable) engine;
        Validator validator = invocable.getInterface(Validator.class);
        /*
        Testing the basic scenario
         */
        System.out.println("Validator requires the input is a number greater than 10");
        System.out.println("Validating value of 5: " + validator.validate(5));
        System.out.println("Validating value of 15: " + validator.validate(15));
        System.out.println("Validating value of ahmed: " + validator.validate("ahmed"));
        System.out.println("Validating value of `true`: " + validator.validate(true));

        /*
        Testing injection of attributes and functions in the JS GLOBAL object
         */
        System.out.println("\nStarting validation process for injection scenario...");
        Bindings b = engine.getContext().getBindings(ScriptContext.ENGINE_SCOPE);
        Function<Integer, String> fn = (Integer x) -> "The input value: " + x + " was invalid";
        b.put("alarm", fn);
        b.put("input", 23);
        engine.eval(new FileReader("src/com/c9/nash/validator2.js"));
        b.put("input", 8);
        engine.eval(new FileReader("src/com/c9/nash/validator2.js"));
        //Another way to inject attributes in the global object
        ScriptContext defCtx = engine.getContext();
        ScriptContext myCtx = new SimpleScriptContext();
        myCtx.setBindings(defCtx.getBindings(ScriptContext.ENGINE_SCOPE), ScriptContext.ENGINE_SCOPE);
        Bindings bb = new SimpleBindings();
        bb.put("alarm", fn);
        bb.put("input", 42);
        myCtx.setBindings(bb, ScriptContext.ENGINE_SCOPE);
        String result = (String) engine.eval(new FileReader("src/com/c9/nash/validator2.js"), myCtx);
        System.out.println("\nThe result of running the script was: \n" + result);

        /*
        Testing the SandBoxing
         */
        System.out.println("\nStarting validation process for SandBoxing scenario...");
        FileReader fv3 = new FileReader("src/com/c9/nash/validator3.js");
        SecurityManager appsm = new SecurityManager();
        System.setSecurityManager(appsm);
        try {
            engine.eval(fv3);
        } catch (AccessControlException ace) {
            System.out.println("There was a security violation in this JS script:\n" + ace.getMessage());
        }
    }
}