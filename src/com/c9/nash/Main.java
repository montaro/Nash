package com.c9.nash;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Throwable {
        System.out.println("Starting validation process...");
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        engine.eval(new FileReader("src/com/c9/nash/validator.js"));
        Invocable invocable = (Invocable) engine;
        Validator validator = invocable.getInterface(Validator.class);
        System.out.println("Validator requires the input is a number greater than 10");
        System.out.println("Validating value of 5: " + validator.validate(5));
        System.out.println("Validating value of 15: " + validator .validate(15));
        System.out.println("Validating value of ahmed: " + validator.validate("ahmed"));
        System.out.println("Validating value of `true`: " + validator.validate(true));
    }
}
