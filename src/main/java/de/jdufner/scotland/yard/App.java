package de.jdufner.scotland.yard;

import de.jdufner.scotland.yard.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class App {

  public static void main(final String[] args) {

    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

  }

}
