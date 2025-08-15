package com.soham.selfteaching.utils.lib.helper;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
/**
 * CompiledClassFile.java
 * This class is used to create a compiled class file from a string of code.
 * It extends SimpleJavaFileObject and provides an output stream to write the compiled bytes.
 * This is useful for dynamically compiling and executing Java code at runtime.
 * This class is part of the Soham Self Teaching Utils library, which provides various utilities for self-teaching and learning Java.
 * This class is licensed under the Apache License, Version 2.0.
 */
public class CompiledClassFile extends SimpleJavaFileObject {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * Constructs a new CompiledClassFile object.
     * @param fullyQualifiedClassName The name of the class file to be created.
     * This should be the fully qualified class name, e.g., "com.example.MyClass".
    */
    public CompiledClassFile(String fullyQualifiedClassName) {
        super(URI.create("bytes:///" + fullyQualifiedClassName + Kind.CLASS.extension), Kind.CLASS);
    }

    /**
     * Returns the output stream to write the compiled bytes.
     * This method overrides the openOutputStream method from SimpleJavaFileObject.
     *
     * @return An OutputStream to write the compiled class bytes.
     */
    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    /**
     * Returns the compiled class bytes as a byte array.
     * This method retrieves the bytes written to the output stream.
     *
     * @return A byte array containing the compiled class bytes.
     */
    public byte[] getBytes() {
        return outputStream.toByteArray();
    }
}
