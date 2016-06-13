package ase.ajc.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspect {

	@Before("execution(* ase.ajc.example.MainAppTest.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + ": Olá AspectJ!");
	}

}
