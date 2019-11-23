스프링 부트 연습


# 기존 스프링과 부트의 차이점
 - 우선 web.xml을 통한 Dispatcher 서블리과 context 서블릿이 없다 --> 그외에 자바설정 기반으로 빈 설정을 해줄수있다.
 - my batis를 쓸때 스프링에서는 resouce하위 폴더로 자동으로 xml을 매핑시켜주지만 부트에서는 경로를 지정해줘야한다.


 # 구현된것들
  - Oracle DB 연결
  - Mybatis
  - sl4j
  - Interceptor
  - AOP(Logger, Transaction)
  - Exception(@ControllAdvice)
  - Filter
  - File Uploading


## 2019-11-15
  부트를 작성하는데 Model and View의 요소에서 프론트에 파라미터를 전달해줄때 반드시 이름을 지정해줬는지 확인해야한다.


## 2019-11-18
  로거에 대한 파일을 작성중 gradle에서 제대로 의존성을 설정 안해줬는데도 불구하고 Error가 뜨지않고 다만 실행이 되지 않았다. 에러가 뜨지 않아 디버깅을 하는데 시간이 걸렸다.
  꼭 코딩을 다 하고나서 유닛 테스팅을 한번씩 해보자