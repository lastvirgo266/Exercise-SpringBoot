package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Slf4j //������ ���� --> �̰� ������̼� �ٿ��� log ��밡��
public class LoggerInterceptor extends HandlerInterceptorAdapter{
	
	//preHandle�� ��Ʈ�ѷ��� ����Ǳ� ���� �����
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception
	{
		log.debug("===============START================");
		log.debug("Request URI \t :" + request.getRequestURI());
		
		return super.preHandle(request, response, handler);
	}
	
	
	//postHandle�� ��Ʈ�ѷ��� ���������� ����� �� ����
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		log.debug("===============END================");

	}
}
