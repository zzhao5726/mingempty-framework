package top.mingempty.boot;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.Duration;

/**
 * springboot启动监听器
 *
 * @author zzhao
 */
@Slf4j
public class MeCloudListener implements SpringApplicationRunListener {

    private final SpringApplication SPRING_APPLICATION;

    public MeCloudListener(SpringApplication application, String[] args) {
        this.SPRING_APPLICATION = application;
    }


    /**
     * Called immediately when the run method has first started. Can be used for very
     * early initialization.
     *
     * @param bootstrapContext the bootstrap context
     */
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        SpringApplicationRunListener.super.starting(bootstrapContext);
    }

    /**
     * Called once the environment has been prepared, but before the
     * {@link ApplicationContext} has been created.
     *
     * @param bootstrapContext the bootstrap context
     * @param environment      the environment
     */
    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        if (!isAppNameExist(environment)) {
            return;
        }
        String startMsg = "************************* Server [" + appName(environment) + "] Starting *************************************";
        Class<?> mainApplicationClass = SPRING_APPLICATION.getMainApplicationClass();
        if (mainApplicationClass != null) {
            LogFactory.getLog(mainApplicationClass).info(startMsg);
        } else {
            log.info(startMsg);
        }
    }

    /**
     * Called once the application context has been loaded but before it has been
     * refreshed.
     *
     * @param context the application context
     */
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        SpringApplicationRunListener.super.contextLoaded(context);
    }

    /**
     * Called immediately before the run method finishes, when the application context has
     * been refreshed and all {@link CommandLineRunner CommandLineRunners} and
     * {@link ApplicationRunner ApplicationRunners} have been called.
     *
     * @param context   the application context.
     * @param timeTaken the time taken for the application to be ready or {@code null} if
     *                  unknown
     * @since 2.6.0
     */
    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        SpringApplicationRunListener.super.ready(context, timeTaken);
    }

    /**
     * Called once the {@link ApplicationContext} has been created and prepared, but
     * before sources have been loaded.
     *
     * @param context the application context
     */
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        if (isAppNameExist(context.getEnvironment()) && StrUtil.isNotEmpty(System.getProperty("AppInfo"))) {
            log.info("Show only application information and exit...");
            System.exit(1);
        }
    }

    /**
     * Called when a failure occurs when running the application.
     *
     * @param context   the application context or {@code null} if a failure occurred before
     *                  the context was created
     * @param exception the failure
     * @since 2.0.0
     */
    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        SpringApplicationRunListener.super.failed(context, exception);
    }

    /**
     * The context has been refreshed and the application has started but
     * {@link CommandLineRunner CommandLineRunners} and {@link ApplicationRunner
     * ApplicationRunners} have not been called.
     *
     * @param context   the application context.
     * @param timeTaken the time taken to start the application or {@code null} if unknown
     * @since 2.6.0
     */
    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        if (!isAppNameExist(context.getEnvironment())) {
            return;
        }
        String appName = appName(context.getEnvironment());
        File pidFile = new File(appName + ".pid");
        if (context.getEnvironment().getProperty("me.write-pid", Boolean.class, Boolean.FALSE)) {
            try {
                FileUtil.del(pidFile);
                try (FileWriter writer = new FileWriter(pidFile)) {
                    String name = ManagementFactory.getRuntimeMXBean().getName();
                    writer.write(name.substring(0, name.indexOf(64)));
                }
            } catch (IOException e) {
                log.info("Writer pid error!", e);
                throw new RuntimeException(e);
            }
        }

        String startedMsg = "************************* Server [" + appName + "] Started *************************************";
        String stoppedMsg = "************************* Server [" + appName + "] Stopping *************************************";
        Class<?> mainApplicationClass = this.SPRING_APPLICATION.getMainApplicationClass();
        if (mainApplicationClass != null) {
            Log mainClassLog = LogFactory.getLog(mainApplicationClass);
            mainClassLog.info(startedMsg);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                mainClassLog.info(stoppedMsg);
                FileUtil.del(pidFile);
            }, "MeCloudListener-Shutdown"));
        } else {
            log.info(startedMsg);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info(stoppedMsg);
                FileUtil.del(pidFile);
            }, "MeCloudListener-Shutdown"));

        }
    }

    private boolean isAppNameExist(ConfigurableEnvironment environment) {
        return StrUtil.isNotEmpty(appName(environment));
    }

    private String appName(ConfigurableEnvironment environment) {
        return environment.getProperty("me.name");
    }
}