/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mountainbuffalo.udpchat;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

/**
 * The main application class.
 *
 * @author Ryan
 */
public class Application {

    /**
     * The application entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(Application::run);
    }

    /**
     * Method to actually run the application.
     */
    public static void run() {
        Main view = new Main();
        view.setVisible(true);
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.disconnect();
            }
        });
    }

}
