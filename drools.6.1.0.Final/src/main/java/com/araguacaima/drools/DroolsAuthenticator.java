package com.araguacaima.drools;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Autenticador para invocación de servicios REST vía HTTP hacia servidores Drools <p>
 * Clase: DroolsAuthenticator.java <br>
 *
 * @author Alejandro Manuel Méndez Araguacaima (AMMA)
 *         Changes:<br>
 *         <ul>
 *         <li> 2014-11-26 (AMMA)  Creacion de la clase. </li>
 *         </ul>
 */


public class DroolsAuthenticator extends Authenticator {
    private String username, password;

    @SuppressWarnings("UnusedDeclaration")
    private DroolsAuthenticator() {

    }

    public DroolsAuthenticator(String user, String pass) {
        username = user;
        password = pass;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
