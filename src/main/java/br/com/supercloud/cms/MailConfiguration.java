package br.com.supercloud.cms;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {

    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.debug}")
    private boolean debug;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.smtp.socketFactory.class}")
    private String socketFactoryClass;
    @Value("${mail.smtp.socketFactory.port}")
    private String socketFactoryPort;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailSender.setDefaultEncoding("UTF-8");
        mailProperties.put("mail.debug", debug);
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailProperties.put("mail.smtp.socketFactory.class", socketFactoryClass);
        mailProperties.put("mail.smtp.socketFactory.port", socketFactoryPort);
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }
}
