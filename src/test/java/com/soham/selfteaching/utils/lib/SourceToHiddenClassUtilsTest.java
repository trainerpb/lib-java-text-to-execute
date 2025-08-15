package com.soham.selfteaching.utils.lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SourceToHiddenClassUtilsTest {

    @Test
    /*
     Test case for compileAndLoadClass method.
     This method tests the compilation and loading of a Java class from source code.
     It should return a Class object representing the compiled class.
     If the class is not compiled or loaded correctly, an exception should be thrown.

     */
    void compileAndLoadClass() {
        // Example test case for compileAndLoadClass method
        String code = """
            for(int i = 0; i < 10; i++) {
                System.out.println("Hello, World! " + i);
            }
           """;
        String className = "TestClass";
        String packageName = getClass().getPackageName();
        String containerMethodName = "execute";
        try {
            Class<?> loadedClass = SourceToHiddenClassUtils.compileAndLoadClass(code, className, packageName, containerMethodName);
            assertNotNull(loadedClass, "Loaded class should not be null");
            assertEquals(getClass().getPackageName(), loadedClass.getPackageName(), "Loaded class name should match");
        } catch (IllegalAccessException e) {
            fail("Exception occurred while compiling and loading class: " + e.getMessage());
        }
    }

    @Test
     /*
      Test case for executeCode method.
      This method tests the execution of dynamically compiled code.
      It should compile the provided code and execute it without throwing any exceptions.

     */
    void executeCode() {

        String code = """
            for(int i = 0; i < 10; i++) {
                System.out.println("Hello, World! " + i);
            }
            """;
        String className = "TestClass";
        String packageName = getClass().getPackageName();
        String containerMethodName = "execute";
        try {
            SourceToHiddenClassUtils.executeCode(code, className, packageName, containerMethodName);
            // If no exception is thrown, the test passes
        } catch (Exception e) {
            fail("Exception occurred while executing code: " + e.getMessage());
        }
    }
}