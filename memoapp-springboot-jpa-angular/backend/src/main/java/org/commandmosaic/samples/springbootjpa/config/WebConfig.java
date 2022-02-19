package org.commandmosaic.samples.springbootjpa.config;

import org.commandmosaic.samples.springbootjpa.MemoApplication;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.commandmosaic.api.configuration.CommandDispatcherConfiguration;
import org.commandmosaic.api.server.CommandDispatcherServer;
import org.commandmosaic.security.interceptor.SecurityCommandInterceptor;
import org.commandmosaic.security.jwt.config.JwtSecurityConfiguration;
import org.commandmosaic.spring.web.CommandDispatcherRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableJpaRepositories(basePackages = "org.commandmosaic.samples.springbootjpa.repositories")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                });
    }

    @Bean
    public CommandDispatcherConfiguration springCommandDispatcherConfiguration() {
        return CommandDispatcherConfiguration.builder()
                .rootPackage(MemoApplication.class.getPackage().getName(), "commands")
                .interceptor(SecurityCommandInterceptor.class)
                .build();
    }


    @Bean
    public JwtSecurityConfiguration jwtSecurityConfiguration() {
        /*
        This causes the app to use a random key: this is fine for our sample.
        NOTE: As a result, a JWT token is only valid till the app is restarted.

        In a real-work production environment, you probably would want to
        use a constant value so that JWT tokens:
          1. ) do not get invalidated on an application restart
          2. ) are consistent across multiple instances of the server (load balancing!)
         */
        byte[] randomKey = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();

        return JwtSecurityConfiguration.builder()
                .setJwtKey(randomKey)
                .build();
    }

    @Bean(name = "/commands")
    public HttpRequestHandler httpRequestHandler(CommandDispatcherServer commandDispatcherServer) {
        return new CommandDispatcherRequestHandler(commandDispatcherServer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("org.commandmosaic.samples.springbootjpa");
        factory.setDataSource(dataSource());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
