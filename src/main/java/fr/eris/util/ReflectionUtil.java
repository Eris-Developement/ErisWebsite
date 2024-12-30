package fr.eris.util;

public class ReflectionUtil
{
    public static StackTraceElement getCallerTrace() {
        return Thread.currentThread().getStackTrace()[3];
    }
}
