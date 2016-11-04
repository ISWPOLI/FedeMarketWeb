package com.qantica.fedemarket.conf;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class InitVariables implements ServletContextListener {

	public void contextDestroyed(final ServletContextEvent event) {
	}

	public void contextInitialized(final ServletContextEvent event) {
		String props = "../server/default/conf/fedemarket.properties";
		
		final Properties propsFromFile = new Properties();
		try {
			
			FileInputStream in = new FileInputStream(props);
			
//			propsFromFile.load(getClass().getResourceAsStream(props));
			propsFromFile.load(in);			
			for (String prop : propsFromFile.stringPropertyNames()) {
				if (System.getProperty(prop) == null) {
					System.setProperty(prop, propsFromFile.getProperty(prop));

				}
			}

//			System.out.println(System.getProperty("eth.dmha.tmp"));

			Conf.RUTA_CONTENIDO = System.getProperty("eth.dmha.content");
			Conf.RUTA_ICO_CONTENIDO = System.getProperty("eth.dmha.ico");
			Conf.RUTA_SCREEN = System.getProperty("eth.dmha.screen");
			
		} catch (final IOException e) {
			System.out.println("[Properties] - Error cargando el archivo de propiedades  "+ props);
		}

	}
}