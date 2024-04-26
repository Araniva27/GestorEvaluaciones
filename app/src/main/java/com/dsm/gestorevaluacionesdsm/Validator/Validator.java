package com.dsm.gestorevaluacionesdsm.Validator;

import java.util.regex.Pattern;

public class Validator {

    public static boolean validarTelefono(String telefono)
    {
        String regex = "\\d{4}-\\d{4}";
        return Pattern.matches(regex, telefono);
    }

    public static boolean validarCorreo(String correo)
    {
        String regex = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";
        return Pattern.matches(regex, correo);
    }
}
