package com.unitn.disi.pweb.gruppo25.tum4world;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utility {
    public static void print(String string) {
        System.out.println(string);
    }

    /**
     * Dato l'instance di una classe ottengo i metodi getter, e tramite quelli vi costruisco un oggetto JSON.
     * Se instance == null, restituisce oggetto JSON vuoto
     * @param instance Oggetto della classe che si vuole in JSON
     * @return Stringa formattata come oggetto JSON
     */
    public static String toJSON(Object instance) {
        StringBuilder str = new StringBuilder().append("{");

        if (!(instance == null)) {
            Class<?> clazz = instance.getClass();
            Method[] methods = clazz.getMethods();

            for (Method method : methods) {
                if (isGetterMethod(method)) {
                    try {
                        String fieldName = extractFieldNameFromGetter(method.getName());
                        Object value = method.invoke(instance); //Invoca metodo get
                        str.append("\"" + fieldName + "\":\"" + value + "\",");

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            //Tolgo "," finale e aggiugo "}" di chiusura
            return str.substring(0, str.length() - 1) + "}";
        } else {
            return str.append("}").toString();
        }
    }

    /**
     * Controlla se il metodo passato è un getter o meno.
     * Per essere un getter accettabile:
     *  - il nome del metodo deve cominciare con "get"
     *  - il nome deve essere divero da "getClass"
     *  - numero di parametri == 0
     *  - return type == void
     * @param method metodo da controllare
     * @return true se è un getter, false altrimenti
     */
    private static boolean isGetterMethod(Method method) {
        String methodName = method.getName();
        return methodName.startsWith("get")
                && !methodName.equals("getClass")
                && method.getParameterCount() == 0
                && !void.class.equals(method.getReturnType());
    }

    /**
     * Estrae il nome del metodo getter, togliendo il "get" iniziale, ottenendo così il nome della property
     * @param getterName
     * @return Stringa minuscola con il nome del metodo senza il "get"
     */
    private static String extractFieldNameFromGetter(String getterName) {
        String fieldName = getterName.substring(3);
        fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
        return fieldName;
    }
}
