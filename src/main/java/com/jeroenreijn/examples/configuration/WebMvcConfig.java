package com.jeroenreijn.examples.configuration;

import com.github.enpassant.ickenham.springmvc.IckenhamViewResolver;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import com.jeroenreijn.examples.repository.InMemoryPresentationsRepository;
import com.jeroenreijn.examples.repository.PresentationsRepository;
import com.jeroenreijn.examples.view.HtmlFlowViewResolver;
import com.jeroenreijn.examples.view.KotlinxHtmlViewResolver;
import com.jeroenreijn.examples.view.LiqpView;
import com.jeroenreijn.examples.view.LiqpViewResolver;
import com.jeroenreijn.examples.view.RockerViewResolver;
import com.jeroenreijn.examples.view.TrimouViewResolver;
import com.jeroenreijn.examples.view.VelocityViewResolver;
import com.x5.template.spring.ChunkTemplateView;
import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.springframework.boot.autoconfigure.JteProperties;
import gg.jte.springframework.boot.autoconfigure.JteViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.jeroenreijn.examples.controller", "com.jeroenreijn.examples.model" })
public class WebMvcConfig implements ApplicationContextAware, WebMvcConfigurer {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("default");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/robots.txt").addResourceLocations("/robots.txt");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(true)
                .addResolver(new WebJarsResourceResolver());
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public LocaleResolver myLocaleResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);

        return slr;
    }

    @Bean
    public PresentationsRepository presentationsRepository() {
        return new InMemoryPresentationsRepository();
    }

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/thymeleaf/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());

        return templateEngine;
    }

    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setViewNames(new String[] { "*-thymeleaf" });
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setCache(false);

        return viewResolver;
    }

    @Bean
    public ViewResolver velocityViewResolver() {
        VelocityViewResolver viewResolver = new VelocityViewResolver();
        viewResolver.setPrefix("/WEB-INF/velocity/");
        viewResolver.setViewNames(new String[] { "*-velocity" });
        viewResolver.setLayoutUrl("/WEB-INF/velocity/layout.vm");
        viewResolver.setSuffix(".vm");
        viewResolver.setCache(false);
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setMessageSource(applicationContext.getBean(MessageSource.class));
        return viewResolver;
    }

    @Bean
    public ViewResolver handlebarsViewResolver() {
        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        viewResolver.setViewNames("*-handlebars");
        viewResolver.setPrefix("/WEB-INF/handlebars/");
        viewResolver.setSuffix(".hbs");
        viewResolver.setCache(false);

        return viewResolver;
    }

    @Bean(name = "chunkTemplatesConfig")
    @Scope("prototype")
    public Map<String, String> chunkTemplatesConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("default_extension", "chtml");
        config.put("cache_minutes", "0");
        config.put("layers", "");
        config.put("theme_path", "");
        config.put("hide_errors", "FALSE");
        config.put("error_log", "");
        config.put("encoding", "UTF-8");
        config.put("locale", "");
        config.put("filters", "");

        return config;
    }

    @Bean
    public ViewResolver chunkViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(ChunkTemplateView.class);
        viewResolver.setPrefix("/WEB-INF/chunk/");
        viewResolver.setViewNames("*-chunk");
        viewResolver.setSuffix(".chtml");
        viewResolver.setCache(false);
        viewResolver.setRequestContextAttribute("rc");
        viewResolver.setContentType("text/html;charset=UTF-8");

        return viewResolver;
    }

    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setViewNames("*-jsp");
        viewResolver.setSuffix(".jsp");
        viewResolver.setCache(false);
        viewResolver.setRequestContextAttribute("rc");

        return viewResolver;
    }

   /* @Bean
    public ViewResolver httlViewResolver() {
        HttlViewResolver viewResolver = new HttlViewResolver();
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setViewNames("*-httl");
        viewResolver.setCache(false);

        return viewResolver;
    }*/

    /*@Bean
    public SpringTemplateLoader jade4jTemplateLoader() {
        SpringTemplateLoader templateLoader = new SpringTemplateLoader();
        templateLoader.setEncoding("UTF-8");

        return templateLoader;
    }

    @Bean
    public JadeConfiguration jadeConfiguration() {
        JadeConfiguration config = new JadeConfiguration();
        config.setPrettyPrint(true);
        config.setCaching(false);
        config.setTemplateLoader(applicationContext.getBean(SpringTemplateLoader.class));

        return config;
    }

    @Bean
    public ViewResolver jadeViewResolver() {
        JadeViewResolver viewResolver = new JadeViewResolver();
        viewResolver.setPrefix("/WEB-INF/jade/");
        viewResolver.setSuffix(".jade");
        viewResolver.setViewNames("*-jade");
        viewResolver.setRenderExceptions(true);
        viewResolver.setCache(false);
        viewResolver.setConfiguration(applicationContext.getBean(JadeConfiguration.class));

        return viewResolver;
    }*/

    /*@Bean
    public ViewResolver scalateViewResolver() {
        ScalateViewResolver viewResolver = new ScalateViewResolver();
        viewResolver.setPrefix("/WEB-INF/scalate/");
        viewResolver.setSuffix(".scaml");
        viewResolver.setViewNames("*-scalate");
        viewResolver.setRequestContextAttribute("rc");
        viewResolver.setCache(false);
        viewResolver.setContentType("text/html;charset=UTF-8");

        return viewResolver;
    }*/

    @Bean
    public ViewResolver htmlFlowViewResolver() {
        HtmlFlowViewResolver viewResolver = new HtmlFlowViewResolver();
        viewResolver.setViewNames("*-htmlFlow");
        viewResolver.setCache(false);

        return viewResolver;
    }

    @Bean
    public ViewResolver trimouViewResolver() {
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        TrimouViewResolver viewResolver = new TrimouViewResolver(messageSource);
        viewResolver.setPrefix("classpath:/templates/trimou/");
        viewResolver.setSuffix(".trimou");
        viewResolver.setViewNames("*-trimou");
        viewResolver.setCache(false);

        return viewResolver;
    }

    @Bean
    public ViewResolver rockerViewResolver() {
        RockerViewResolver viewResolver = new RockerViewResolver();
        viewResolver.setViewNames("*-rocker");
        viewResolver.setCache(false);

        return viewResolver;
    }

    @Bean
    public ViewResolver ickenhamViewResolver() {
        IckenhamViewResolver viewResolver = new IckenhamViewResolver();
        viewResolver.setPrefix("/WEB-INF/ickenham/");
        viewResolver.setSuffix(".hbs");
        viewResolver.setViewNames("*-ickenham");
        viewResolver.setRequestContextAttribute("rc");
        viewResolver.setCache(false);
        viewResolver.setContentType("text/html;charset=UTF-8");

        return viewResolver;
    }

    /*@Bean
    public RythmConfigurer rythmConfigurer() {
        RythmConfigurer conf = new RythmConfigurer();
        conf.setDevMode(true);
        conf.setResourceLoaderPath("/WEB-INF/rythm/");
        conf.setAutoImports("com.jeroenreijn.examples.model.*");

        return conf;
    }

    @Bean
    public ViewResolver rythmViewResolver() {
        RythmViewResolver viewResolver = new RythmViewResolver();
        viewResolver.setPrefix("/WEB-INF/rythm/");
        viewResolver.setSuffix(".html");
        viewResolver.setViewNames("*-rythm");
        viewResolver.setCache(false);
        viewResolver.setContentType("text/html;charset=UTF-8");

        return viewResolver;
    }*/

    @Bean
    public LiqpViewResolver liqpViewResolver() {
        LiqpViewResolver viewResolver = new LiqpViewResolver(applicationContext.getBean(MessageSource.class));
        viewResolver.setViewClass(LiqpView.class);
        viewResolver.setPrefix("classpath:./templates/liqp/");
        viewResolver.setSuffix(".liqp");
        viewResolver.setViewNames("*-liqp");
        viewResolver.setCache(false);
        viewResolver.setContentType("text/html;charset=UTF-8");

        return viewResolver;
    }

    @Bean
    public ViewResolver jteViewResolve(TemplateEngine templateEngine, JteProperties jteProperties) {
        var viewResolver = new JteViewResolver(templateEngine, jteProperties);
        viewResolver.setPrefix("/templates/jte/");
        viewResolver.setSuffix(".jte");
        viewResolver.setViewNames("*-jte");
        viewResolver.setCache(false);
        return viewResolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        String profile = System.getenv("SPRING_ENV");
        if ("prod".equals(profile)) {
            // Templates will be compiled by the maven build task
            return TemplateEngine.createPrecompiled(ContentType.Html);
        } else {
            // Here, a JTE file watcher will recompile the JTE templates upon file save (the web browser will auto-refresh)
            // If using IntelliJ, use Ctrl-F9 to trigger an auto-refresh when editing non-JTE files.
            CodeResolver codeResolver = new DirectoryCodeResolver(Path.of(
                    "src", "main", "resources", "templates", "jte"));
            TemplateEngine templateEngine = TemplateEngine.create(codeResolver, Paths.get("jte"), ContentType.Html,
                    getClass().getClassLoader());
            templateEngine.setBinaryStaticContent(true);
            return templateEngine;
        }
    }

    @Bean
    public ViewResolver kotlinxHtmlViewResolver() {
        KotlinxHtmlViewResolver viewResolver = new KotlinxHtmlViewResolver();
        viewResolver.setViewNames("*-kotlinx");
        viewResolver.setCache(false);

        return viewResolver;
    }

    @Controller
    static class FaviconController {
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/resources/images/favicon.ico";
        }
    }
}
