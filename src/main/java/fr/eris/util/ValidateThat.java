package fr.eris.util;

import java.util.Map;

public class ValidateThat
{
    /*------------------*/
    /*     NOT NULL     */
    /*------------------*/

    public static void notNull(Object object, RuntimeException exception) {
        if (object == null) {
            throw exception;
        }
    }

    public static void notNull(Object object, String message) {
        notNull(object, new IllegalArgumentException(message));
    }

    /*------------------*/
    /*      IS TRUE     */
    /*------------------*/

    public static void isTrue(boolean loaded, RuntimeException exception) {
        if (!loaded) {
            throw exception;
        }
    }

    public static void isTrue(boolean value, String message) {
        isTrue(value, new IllegalArgumentException(message));
    }

    /*------------------*/
    /*      IS FALSE    */
    /*------------------*/

    public static void isFalse(boolean loaded, RuntimeException exception) {
        if (loaded) {
            throw exception;
        }
    }

    public static void isFalse(boolean value, String message) {
        isFalse(value, new IllegalArgumentException(message));
    }

    /*------------------*/
    /* DONT CONTAIN KEY */
    /*------------------*/

    public static void mapDontContainKey(Map<?, ?> map, Object key, String message) {
        mapDontContainKey(map, key, new IllegalArgumentException(message));
    }

    public static void mapDontContainKey(Map<?, ?> map, Object key, RuntimeException exception) {
        if (map.containsKey(key)) {
            throw exception;
        }
    }

    /*------------------*/
    /*    CONTAIN KEY   */
    /*------------------*/

    public static void mapContainKey(Map<?, ?> map, Object key, String message) {
        mapContainKey(map, key, new IllegalArgumentException(message));
    }

    public static void mapContainKey(Map<?, ?> map, Object key, RuntimeException exception) {
        if (!map.containsKey(key)) {
            throw exception;
        }
    }
}
