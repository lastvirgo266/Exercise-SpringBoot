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
  - File Uploading(MySLQ, Oracle구문 포함(다중 업로딩))
  - File Downloading(Oracle)


## 2019-11-15
  부트를 작성하는데 Model and View의 요소에서 프론트에 파라미터를 전달해줄때 반드시 이름을 지정해줬는지 확인해야한다.


## 2019-11-18
  로거에 대한 파일을 작성중 gradle에서 제대로 의존성을 설정 안해줬는데도 불구하고 Error가 뜨지않고 다만 실행이 되지 않았다. 에러가 뜨지 않아 디버깅을 하는데 시간이 걸렸다.
  꼭 코딩을 다 하고나서 유닛 테스팅을 한번씩 해보자


## 2019-11-23
  어떤 논리가 돌아갈때 걸쳐야 할 단계들을 생각해보면서 디버깅을 해야한다.


## 2020-02-05
  - REST API로 작성중(현재 node로 프로젝트 병행하고있음)
  - <https://dydals5678.tistory.com/2> 상태코드 작성하는 방법

##  org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'boardController': Unsatisfied dependency expressed through field 'boardService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'boardServiceImpl': Unsatisfied dependency expressed through field 'boardMapper'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'boardMapper' defined in file [C:\Users\LaVi\Documents\Spring_Boot_Project\board\bin\main\board\board\mapper\BoardMapper.class]: Unsatisfied dependency expressed through bean property 'sqlSessionFactory'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'sqlSessionFactory' defined in class path resource [board/configuration/DatabaseConfiguration.class]: Unsatisfied dependency expressed through method 'sqlSessionFactory' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [board/configuration/DatabaseConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.sql.DataSource]: Factory method 'dataSource' threw exception; nested exception is com.zaxxer.hikari.pool.HikariPool$PoolInitializationException: Failed to initialize pool: IO 오류: The Network Adapter could not establish the connection
  - 결론부터 말하자면 DB 오류이다. 오라클이 안켜져 있어서 그랬다.
  - <https://m.blog.naver.com/PostView.nhn?blogId=blogpyh&logNo=40209232034&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F>
  - 이거 오라클 서비스 키면되는건데 안켜짐 --> 이유는 저번에 PL/SQL때문에 다른 오라클파일 가져오고 ORACLE_HOME 경로 지정해줬는데 이것때문에 오류난것으로 확인됨