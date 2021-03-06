package br.com.supercloud.cms;

import br.com.supercloud.cms.model.User;
import br.com.supercloud.cms.util.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class Application  {

	private static final String MAX_REQUEST_SIZE = "15MB";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// Provides internationalization of messages
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}

	@Bean
	public AuditorAware<User> auditorProvider() {
		return new AuditorAwareImpl();
	}

	// This configuration element adds the ability to accept multipart
	// requests to the web container.
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		// Setup the application container to be accept multipart requests
		final MultipartConfigFactory factory = new MultipartConfigFactory();
		// Place upper bounds on the size of the requests to ensure that
		// clients don't abuse the web container by sending huge requests
		factory.setMaxFileSize(MAX_REQUEST_SIZE);
		factory.setMaxRequestSize(MAX_REQUEST_SIZE);

		// Return the configuration to setup multipart in the container
		return factory.createMultipartConfig();
	}

}
