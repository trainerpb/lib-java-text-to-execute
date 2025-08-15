package com.soham.selfteaching.utils.lib.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaSourceFromStringTest {

    // add test cases here to validate the functionality of JavaSourceFromString class
    // This class is used to create a Java source file from a string of code.
    // It extends SimpleJavaFileObject and provides a method to get the Java source code as
    // a CharSequence, which can be used for dynamic compilation and execution of Java code at
    // runtime. This is useful for educational applications or self-teaching tools that require
    // dynamic code execution.\
    @Test
    public void testGetCharContent() {
        // Example test case
        String code = """
            for(int i = 0; i < 10; i++) {
                System.out.println("Hello, World! " + i);
            }
        """;

        String className = "TestClass";
        String packageName = "com.example";
        String containerMethodName = "execute";

        JavaSourceFromString javaSource = new JavaSourceFromString(code, className, packageName, containerMethodName);
        try {
            CharSequence content = javaSource.getCharContent(true);
            assertTrue(content.toString().contains("System.out.println"));
            assertTrue(content.toString().contains("TestClass"));
            assertTrue(content.toString().contains("execute"));
        } catch (Exception e) {
            fail("Exception occurred while getting char content: " + e.getMessage());
        }
    }
}