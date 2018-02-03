/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.task;

import com.statics.carga.ParametrizacionAppJpaController;
import com.statics.vo.ParametrizacionApp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Usuario
 */
@WebListener
public class BackgroundJobManager implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        //                        iterar dias segun parametrizacion
        int segIteracion = 60; // <-- default
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
        ParametrizacionApp app = new ParametrizacionAppJpaController(emf).findParametrizacionApp(1);
        if (app != null && app.getPaapValor() != null && !app.getPaapValor().isEmpty()) {
            try {
                segIteracion = Integer.parseInt(app.getPaapValor());
            } catch (Exception e) {
                System.out.println("ERROR:: --> no se pudo convertir a int el valor del parametro id=1 ");
            }

        }
        scheduler.scheduleAtFixedRate(new ReadFilesCargaJob(), 0, segIteracion, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}
