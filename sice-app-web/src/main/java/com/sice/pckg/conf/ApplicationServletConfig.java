package com.sice.pckg.conf;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.bussiness.impl.ColaboradorBL;
import com.sice.pckg.bussiness.impl.EventoBL;
import com.sice.pckg.bussiness.impl.IntentosLoginBL;
import com.sice.pckg.bussiness.impl.UsuarioBL;
import com.sice.pckg.dao.IDAO;
import com.sice.pckg.dao.impl.ColaboradorDAO;
import com.sice.pckg.dao.impl.EventoDAO;
import com.sice.pckg.dao.impl.IntentosLoginDAO;
import com.sice.pckg.dao.impl.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@ComponentScan("com.sice.pckg")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableWebMvc
public class ApplicationServletConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("Login");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        return viewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigures() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/tiles.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(300);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(300);
        registry.addResourceHandler("/images/**").addResourceLocations("/img/").setCachePeriod(300);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(300);
        registry.addResourceHandler("/extjs/**").addResourceLocations("/extjs/");
        registry.addResourceHandler("/html/**").addResourceLocations("/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        return converter;
    }

    @Bean(name = "dataSource")
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(this.hibernateProperties());
        //sessionBuilder.addPackage("com.sice.pckg.entidades");

        sessionBuilder.addAnnotatedClass(com.sice.pckg.entidades.Colaborador.class);
        sessionBuilder.addAnnotatedClass(com.sice.pckg.entidades.Evento.class);
        sessionBuilder.addAnnotatedClass(com.sice.pckg.entidades.Usuario.class);
        sessionBuilder.addAnnotatedClass(com.sice.pckg.entidades.IntentosLogin.class);

        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.connection.autocommit", "true");
        return props;
    }

    @Bean(autowire = Autowire.BY_NAME, name = "colaboradorDAO")
    public IDAO getColaboradoDAO() {
        return new ColaboradorDAO();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "usuarioDAO")
    public IDAO getUsuarioDAO() {
        return new UsuarioDAO();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "eventoDAO")
    public IDAO getEventoDAO() {
        return new EventoDAO();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "intentosDAO")
    public IntentosLoginDAO getIntentosDAO() {
        return new IntentosLoginDAO();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "usuarioBL")
    public IBussiness getUsuarioBL() {
        return new UsuarioBL();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "colaboradorBL")
    public IBussiness getColaboradorBL() {
        return new ColaboradorBL();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "eventoBL")
    public IBussiness getEventoBL() {
        return new EventoBL();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "intentosBL")
    public IntentosLoginBL getIntentosBL() {
        return new IntentosLoginBL();
    }
}
