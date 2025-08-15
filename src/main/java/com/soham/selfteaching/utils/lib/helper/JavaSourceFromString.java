package com.soham.selfteaching.utils.lib.helper;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/***
 * JavaSourceFromString.java
 * This class is used to create a Java source file from a string of code.
 *  This is useful for dynamically compiling and executing Java code at runtime.
 *  This class is part of the Soham Self Teaching Utils library, which provides various utilities for self-teaching and learning Java.
 *  This class is licensed under the Apache License, Version 2.0.
 *
 */

public class JavaSourceFromString extends SimpleJavaFileObject {

    private String code;
    private final String className;
    private final String packageName;
    private String containerMethodName = "execute";

    /**
     * Constructs a new JavaSourceFromString object.
     *
     * @param code                The Java source code as a string.
     * @param className           The name of the class to be created.
     * @param packageName         The package name for the class.
     * @param containerMethodName The name of the method that will contain the code.
     */
    public JavaSourceFromString(String code, String className, String packageName, String containerMethodName) {
        super(URI.create("string:///" + className + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
        this.className = className;
        this.packageName = packageName;
        if (containerMethodName != null && !containerMethodName.isEmpty()) {
            this.containerMethodName = containerMethodName;
        }


    }

    /**
     * Returns the Java source code as a CharSequence.
     * This method formats the code to include the package name, class name, and method name.
     *
     * @param ignoreEncodingErrors Ignored in this implementation.
*    * This parameter is included to match the method signature of SimpleJavaFileObject.
     * @return The formatted Java source code as a CharSequence.
     * @throws IOException If an I/O error occurs while retrieving the content.
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        this.code = """
                package %s;
                public class %s{
                public void %s(){
                     %s
                }
                }
                
                """.formatted(packageName, className, containerMethodName, code);
        return code;
    }
}
