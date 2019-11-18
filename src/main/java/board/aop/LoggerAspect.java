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
	
	/* @Around ������̼��� ����� �ش����� ����� ����, �� �����̽��� �����Ѵ� 
	 * (���⼭�� ��� �޼����� ���� ���� or ���� �߻� ������ ����� �� �ִ� Around �����̽��� �����ߴ�.
	 * excution�� ����Ʈ�� ǥ�������� ������ �޼��带 ����� �� ���ȴ�.*/
	@Around("execution(* board..controller.*Controller.*(..)) or "
		  + "execution(* board..service.*Impl.*(..)) or "
		  + "execution(* board.dto.*Mapper.*(..))")	
	public Object logPrint(ProceedingJoinPoint jointPoint) throws Throwable
	{
		/* ����Ǵ� �޼����� �̸��� �̿��ؼ� ��Ʈ�ѷ�, ����, ���۸� ������ �� ����Ǵ� �޼����� �̸��� ����Ѵ� */	
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
