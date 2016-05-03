package sima.sync.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import sima.sync.server.hash.MD5Dispatcher;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main entrypoint for the app
 */
public class Main {

    public static void main(String[] args) {
        {
            //First must create log4j configuration.
            ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
            builder.setStatusLevel(Level.ERROR);
            builder.setConfigurationName("SimaSyncServerLogging");
            builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.DENY).addAttribute("level", Level.ALL));
            //Console logging
            AppenderComponentBuilder stdout = builder.newAppender("stdout", "CONSOLE");
            stdout.addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
            stdout.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.DENY).addAttribute("level", Level.DEBUG));
            stdout.add(builder.newLayout("PatternLayout").addAttribute("pattern", "%highlight{%d{HH:mm:ss.SSS} [%level{length=1}:%t] %m%n}"));
            builder.add(stdout);
            //File logging
            AppenderComponentBuilder file = builder.newAppender("file", "File");
            file.addAttribute("append", false);
            //NOTE: WE CANNOT USE THE CONSTANT PATH IN THIS CASE, BECAUSE IF WE DO WE ARE GOING TO TRIGGER LOG4J TOO WHILE WE ARE STILL CONFIGURING IT.
            file.addAttribute("fileName", Paths.get(".").toAbsolutePath().normalize().toString() + "/log/SimaSync-Server_" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()) + ".log");
            file.addAttribute("bufferedIO", true);
            file.addAttribute("immediateFlush", true);
            file.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.DENY).addAttribute("level", Level.ALL));
            file.add(builder.newLayout("PatternLayout").addAttribute("pattern", "%d{HH:mm:ss.SSS} [%level{length=1}:%t] %m%n"));
            builder.add(file);
            //Finalize log4j configuration
            builder.add(builder.newLogger("org.apache.logging.log4j", Level.ALL).add(builder.newAppenderRef("stdout")).add(builder.newAppenderRef("file")).addAttribute("additivity", false));
            builder.add(builder.newRootLogger(Level.ALL).add(builder.newAppenderRef("stdout")).add(builder.newAppenderRef("file")));
            Configurator.initialize(builder.build());
        }
        //Then initialize all required classes
        Constants.log.info("Initializing all required components...");
        MD5Dispatcher.init();
        //And finally create the gui.
        Constants.log.info("Initializing the gui...");
        SwingUtilities.invokeLater(Constants.SCREEN_BUILDER);
    }

    public static void exit(int code) {
        if (code == 0) {
            Constants.log.info("Exiting application...");
        } else {
            Constants.log.fatal("A fatal error has occured and the application must exit.");
        }
        try {
            SwingUtilities.invokeAndWait(() -> {
                //Close gui
                JFrame frame = Constants.SCREEN_BUILDER.mainFrame;
                frame.setVisible(false);
                frame.dispose();});
        } catch (InterruptedException | InvocationTargetException e) {
            System.exit(code);//We can't do anything at that point so just exit the damn JVM.
        }

    }
}