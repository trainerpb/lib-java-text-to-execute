package com.soham.selfteaching.utils.lib;

import com.soham.selfteaching.utils.lib.helper.CompiledClassFile;
import com.soham.selfteaching.utils.lib.helper.JavaSourceFromString;

import javax.tools.*;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *  SourceToHiddenClassUtils.java
 *  This class provides utilities to compile and load Java classes from source code strings.
 *  It uses the Java Compiler API to dynamically compile Java code and define hidden classes using MethodHandles.
 *  This is useful for dynamically executing Java code at runtime, such as in educational applications
 *  or self-teaching tools.
 *  This class is part of the Soham Self Teaching Utils library, which provides various utilities
 *  for self-teaching and learning Java.
 *  This class is licensed under the Apache License, Version 2.0.
 */
public class SourceToHiddenClassUtils {



    /**
     * Compiles and loads a Java class from the provided source code.
     *
     * @param code                The Java source code to be compiled.
     * @param className           The name of the class to be created.
     * @param packageName         The package name for the class.
     * @param containerMethodName The name of the method that will contain the code.
     * @return The Class object representing the compiled class.
     * @throws IllegalAccessException If the class or its constructor is not accessible.
     */
    public static Class<?> compileAndLoadClass(String code, String className, String packageName, String containerMethodName) throws IllegalAccessException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        var sourceFile = new JavaSourceFromString(code, className, packageName, containerMethodName);

        var compiledClass = new CompiledClassFile(packageName + "." + className);

        JavaFileManager fileManager = new ForwardingJavaFileManager<>(compiler.getStandardFileManager(null, null, null)) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) {
                return compiledClass;
            }
        };


        compiler.getTask(null, fileManager, null, null, null, List.of(sourceFile)).call();
        byte[] classBytes = compiledClass.getBytes();
        if (null==classBytes || classBytes.length == 0) {
            throw new IllegalStateException("Class " + className + " could not be compiled or is empty.");
        }
        System.out.println("CompilerClass.main " + classBytes.length);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // Define the hidden class
        Class<?> hiddenClass = lookup.defineHiddenClass(classBytes, true).lookupClass();


        System.out.println("Hidden class defined: " + hiddenClass.getName());
        return hiddenClass;
    }

    /**
     * Executes the provided Java code dynamically.
     *
     * @param code                The Java source code to be executed.
     * @param className           The name of the class to be created.
     * @param packageName         The package name for the class.
     * @param containerMethodName The name of the method that will contain the code.
     * @return The result of executing the code, if any.
     * @throws IllegalAccessException     If the class or its constructor is not accessible.
     * @throws InstantiationException     If the class cannot be instantiated.
     * @throws NoSuchMethodException      If the specified method does not exist.
     * @throws InvocationTargetException  If an exception occurs while invoking the method.
     */
    @SuppressWarnings("deprecation")
    public static Object executeCode(String code, String className, String packageName, String containerMethodName) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = compileAndLoadClass(code, className, packageName, containerMethodName);
        var method = clazz.getMethod(containerMethodName);
        return method.invoke(clazz.newInstance());
    }
}