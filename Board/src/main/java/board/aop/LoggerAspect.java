package board.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
@Aspect 어노테이션을 이용해서 자바 코드에서 AOP를 설정 
*/
@Component
@Aspect
public class LoggerAspect {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	먼저 @Around 어노테이션으로 해당 기능이 실행될 시점, 즉 어드바이스를 정의
	어드바이스는 다섯 종류가 있지만 여기서는 대상 메서드의 실행 전후 또는 예외 발생 시점에 사용할 수 있는 Around 어드바이스를 적용
	execution은 포인트컷 표현식으로 적용할 메서드를 명시할 때 사용
	*/	
	@Around("execution(* board..controller.*Controller.*(..)) or execution(* board..service.*Impl.*(..)) or execution(* board..mapper.*Mapper.*(..))")
	public Object LogPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		/*
		실행되는 메서드의 이름을 이용해서 컨트롤러, 서비스, 매퍼를 구분한 후 실행되는 메서드의 이름을 출력 
		*/		
		if (name.indexOf("Controller") > -1) {
			type = "Controller \t: "; 
		}
		else if(name.indexOf("Service") > -1) {
			type = "ServiceImpl \t: ";
		}
		else if(name.indexOf("Mapper") > -1) {
			type = "Mapper \t: ";
		}
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
