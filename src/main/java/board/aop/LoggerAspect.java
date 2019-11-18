package board.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggerAspect {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/* @Around 어노테이션을 사용해 해당기능이 실행될 지점, 즉 어드바이스를 정의한다 
	 * (여기서는 대상 메서드의 실행 전후 or 예외 발생 시점에 사용할 수 있는 Around 어드바이스를 적용했다.
	 * excution은 포인트컷 표현식으로 적용할 메서드를 명시할 때 사용된다.*/
	@Around("execution(* board..controller.*Controller.*(..)) or "
		  + "execution(* board..service.*Impl.*(..)) or "
		  + "execution(* board.dto.*Mapper.*(..))")	
	public Object logPrint(ProceedingJoinPoint jointPoint) throws Throwable
	{
		/* 실행되는 메서드의 이릉믈 이용해서 컨트롤러, 서비스, 매퍼를 구분한 후 실행되는 메서드의 이름을 출력한다 */	
		String type = "";
		String name = jointPoint.getSignature().getDeclaringTypeName();
		
		if(name.indexOf("Controller") > -1)
		{
			type = "Controller \t:   ";
		}
		
		else if(name.indexOf("Service") > -1)
		{
			type = "ServiceImpl   \t:  ";
		}
		
		else if(name.indexOf("Mapper") > -1)
		{
			type = "Mapper   \t\t:  ";
		}
		
		log.debug(type + name + "." + jointPoint.getSignature().getName() + "()");
		return jointPoint.proceed();
		
	}

}
