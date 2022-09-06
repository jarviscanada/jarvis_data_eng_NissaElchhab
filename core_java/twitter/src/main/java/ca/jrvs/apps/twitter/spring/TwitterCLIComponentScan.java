package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCLIApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "ca.jrvs.apps.twitter")
public class TwitterCLIComponentScan {

  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(TwitterCLIComponentScan.class);
    TwitterCLIApp app = ctx.getBean(TwitterCLIApp.class);
    app.run(args);
  }

}
