package fr.eris;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.PrintStream;

public class ErisOutStream extends PrintStream
{
    private final PrintStream printStream;

    public ErisOutStream(@NotNull OutputStream out) {
        super(out);
        if (!(out instanceof PrintStream)) {
            throw new IllegalArgumentException("The out should be a printStream");
        }
        printStream = (PrintStream) out;
    }

    public void print(boolean b) {
        unsafePrint(b);
    }
    public void print(char c) {
        unsafePrint(c);
    }
    public void print(int i) {
        unsafePrint(i);
    }
    public void print(long l) {
        unsafePrint(l);
    }
    public void print(float f) {
        unsafePrint(f);
    }
    public void print(double d) {
        unsafePrint(d);
    }
    public void print(char @NotNull [] s) {
        unsafePrint(s);
    }
    public void print(String s) {
        unsafePrint(s);
    }
    public void print(Object obj) {
        unsafePrint(obj);
    }

    public PrintStream format(@NotNull String format, Object ... args) {
        return printStream.printf("[UNSAFE PRINT] - %s", String.format(format, args));
    }

    public void unsafePrint(Object obj) {
        printStream.print("[UNSAFE PRINT] - " + obj);
    }
}
