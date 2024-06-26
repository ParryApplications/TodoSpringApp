package org.parryapplications.spring.todoproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.parryapplications.spring.todoproject.dto.ResultSet;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Configuration
@Aspect
public class AopConfigurations {

    private final Logger logger = LoggerFactory.getLogger(AopConfigurations.class);

    @Before("org.parryapplications.spring.todoproject.aop.PointcutConfigurations.TodoServiceImplMethodsConfig()")
    public void loggingBeforeBizLogicExecuted(JoinPoint joinPoint) {
        logger.info("{} method start executing...", joinPoint);
//        logger.info("{} method starting executing...",joinPoint.getSignature());
//        logger.info("{} method starting executing...",joinPoint.getSourceLocation());
//        logger.info("{} method starting executing...",joinPoint.getTarget());
//        logger.info("{} method starting executing...",joinPoint.toShortString());
//        logger.info("{} method starting executing...",joinPoint.getKind());
    }

    //TODO: Note: Below method calling but not impacting the response (Need to check why response not modified):
    @AfterReturning(pointcut = "org.parryapplications.spring.todoproject.aop.PointcutConfigurations.TodoWebserviceImplMethod_RtTodoDtoConfig()", returning = "successResult")
    public ResultSet<TodoDto> addingResultSetToSuccessResult(TodoDto successResult) {
        logger.info("addingResultSetToSuccessResult() executed");
        if (!ObjectUtils.isEmpty(successResult)) {
            return new ResultSet<>(successResult, 1, "Operation completed successfully");
        }

        return new ResultSet<>(successResult,0, "Operation completed successfully");
    }

    //TODO: Note: Below method calling but not impacting the response (Need to check why response not modified):
    @AfterReturning(pointcut = "org.parryapplications.spring.todoproject.aop.PointcutConfigurations.TodoWebserviceImplMethod_RtListOfTodoDtosConfig()", returning = "successResults")
    public ResultSet<List<TodoDto>> addingResultSetsToSuccessResults(List<TodoDto> successResults) {
        logger.info("addingResultSetsToSuccessResults() executed");
        if (!CollectionUtils.isEmpty(successResults))
            return new ResultSet<>(successResults, successResults.size(), "Operations completed successfully");

        return new ResultSet<>(successResults,0, "Operations completed successfully");
    }

    @AfterThrowing(pointcut = "org.parryapplications.spring.todoproject.aop.PointcutConfigurations.TodoJpaRepositoryMethodConfig()", throwing = "exceptionHandler")
    public void loggingExceptionOnDaoLayer(JoinPoint joinPoint, Exception exceptionHandler) {
        logger.error("Weaver Captured an Exception:");
        logger.error("Exception captured under : {}\n --> Exception Info: {}", joinPoint, exceptionHandler);
    }

    @Around("org.parryapplications.spring.todoproject.aop.PointcutConfigurations.TimeComplexityCalculatorConfig()")
    public Object timeComplexityCalculatorLogic(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Instant startTime = Instant.now();

        Object result = proceedingJoinPoint.proceed();

        Instant endTime = Instant.now();

        long finalCalculatedTime = Duration.between(startTime, endTime).toMillis();

        logger.info("{} API's Method took {}ms time to complete the request",proceedingJoinPoint,finalCalculatedTime);

        return result;
    }
}
