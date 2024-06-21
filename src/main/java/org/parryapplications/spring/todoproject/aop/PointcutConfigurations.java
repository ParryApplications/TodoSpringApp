package org.parryapplications.spring.todoproject.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * This Class is used for Pointcut configuration for maintenance and reuse purpose as an AOP Best Practice
 */
public class PointcutConfigurations {

    @Pointcut("execution(* org.parryapplications.spring.todoproject.service.TodoServiceImpl.*(..))")
    public void TodoServiceImplMethodsConfig() {}


    @Pointcut("execution(org.parryapplications.spring.todoproject.dto.TodoDto org.parryapplications.spring.todoproject.Controller.TodoWebserviceImpl.*(..))")
    public void TodoWebserviceImplMethod_RtTodoDtoConfig() {}


    @Pointcut("execution(java.util.List<org.parryapplications.spring.todoproject.dto.TodoDto> org.parryapplications.spring.todoproject.Controller.TodoWebserviceImpl.*(..))")
    public void TodoWebserviceImplMethod_RtListOfTodoDtosConfig() {}

    @Pointcut("execution(* org.parryapplications.spring.todoproject.repository.TodoJpaRepository.*(..))")
    public void TodoJpaRepositoryMethodConfig() {}


    @Pointcut("@annotation(org.parryapplications.spring.todoproject.customAnnotations.TimeComplexityCalculator)")
    public void TimeComplexityCalculatorConfig() {}

    //Not in Use
    @Pointcut("bean(*Service*)")
    public void aspectAllBeansContainsServiceInName() {}
}
