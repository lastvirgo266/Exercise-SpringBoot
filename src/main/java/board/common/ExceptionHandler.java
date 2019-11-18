package board.common;


import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice //예외처리 클래스임을 알려줌
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//이 메서드에서 처리할 예외를 지정함
	/* 여기서는 기능을 확인하기 위해 간단히 Exception.class로 설정해서 모든 예외를 처리했으나 실제 프로젝트에서는 다양한 예외를 처리하기 위해
	 * 각각의 예외처리가 필요하다.(NullPointException, NumerFormatException 등)*/
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception)
	{
		ModelAndView mv = new ModelAndView("/error/error_default"); //예외 발생히 보여줄 화면을 지정
		mv.addObject("exception", exception);
		
		log.error("exception", exception);
		
		return mv;
	}

}
