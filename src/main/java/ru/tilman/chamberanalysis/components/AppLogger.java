package ru.tilman.chamberanalysis.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.tilman.chamberanalysis.ChamberAnalysisApplication;

import java.util.Arrays;

@Aspect
@Component
public class AppLogger {

    private static final Logger logger = LogManager.getLogger(ChamberAnalysisApplication.class);

    @Pointcut("execution (* ru.tilman.chamberanalysis.services..*.*(String))")
    private void loggableServicesMethodsWithOneStringParameter() {
    }

    @Before("loggableServicesMethodsWithOneStringParameter()")
    public void logLoginExist(JoinPoint joinPoint) {

        logger.warn("Hey, This is a warning!");
        System.out.println(
                "Method name:" + joinPoint.toShortString() +
                        "\nArgs:\n" + Arrays.asList(joinPoint.getArgs())
        );
    }


}
