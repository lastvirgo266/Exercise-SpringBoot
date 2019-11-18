package board.common;


import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice //����ó�� Ŭ�������� �˷���
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//�� �޼��忡�� ó���� ���ܸ� ������
	/* ���⼭�� ����� Ȯ���ϱ� ���� ������ Exception.class�� �����ؼ� ��� ���ܸ� ó�������� ���� ������Ʈ������ �پ��� ���ܸ� ó���ϱ� ����
	 * ������ ����ó���� �ʿ��ϴ�.(NullPointException, NumerFormatException ��)*/
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception)
	{
		ModelAndView mv = new ModelAndView("/error/error_default"); //���� �߻��� ������ ȭ���� ����
		mv.addObject("exception", exception);
		
		log.error("exception", exception);
		
		return mv;
	}

}
