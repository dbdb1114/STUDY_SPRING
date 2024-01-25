## Hello 서블릿

### 메인 서블릿 환경 구성 - 어노테이션 추가 -

```java
package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
/*** 서블릿 자동등록***/
@SpringBootApplication
public class ServletApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServletApplication.class, args);
  }

}
```

### 서블릿 생성

**WebServlet**

- name : 서블릿 이름
- urlPattern : URL매핑

```java
package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    System.out.println("HelloServlet.service");
    System.out.println("request = " + request);
    System.out.println("response = " + response);

    String username = request.getParameter("username");
    System.out.println("username = " + username);

    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    response.getWriter().write("hello " + username);
  }
}

```

**로그 설정** (application.properties 파일 추가)

```dtd
logging.level.org.apache.coyote.http11=debug
```

아래와 같은 로그 출력 가능

```dtd
Host: localhost:8080
        Connection: keep-alive
        Cache-Control: max-age=0
        sec-ch-ua: "Whale";v="3", "Not-A.Brand";v="8", "Chromium";v="120"
        sec-ch-ua-mobile: ?0
        sec-ch-ua-platform: "macOS"
        Upgrade-Insecure-Requests: 1
        User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Whale/3.24.223.12 Safari/537.36
        Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
        Sec-Fetch-Site: none
        Sec-Fetch-Mode: navigate
        Sec-Fetch-User: ?1
        Sec-Fetch-Dest: document
        Accept-Encoding: gzip, deflate, br
        Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6,zh;q=0.5
        Cookie: _ga=GA1.1.1422266854.1691122521; _ga_0P9YPW1GQ3=GS1.1.1696652833.74.1.1696652833.0.0.0; JSESSIONID=91C8A969E6D65633C3A33D8CA167337D
```

### HttpServletRequest - 개요

**HttpServletRequest 역할**<br>
HTTP 요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편할 것이다.
서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에
HTTP 요청 메시지를 파싱한다. 그리고 그 결과를 HttpServletRequest 객체에 담아서 제공한다.

**HTTP 요청 메시지**

- START LINE
  - HTTP 메소드
  - URL
  - 쿼리 스트링
  - 스키마, 프로토콜
- 헤더
  - 헤더 조회
- 바디
  - form 파라미터 형식 조회
  - message body 데이터 직접 조회

```
POST /save HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
username=kim&age=20
```

**임시 저장소 기능**

- 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
  - 저장 : request.setAttribute(name, value)
  - 조회 : request.getAttribute(name)
    **세션 관리 기능**
- request.getSession
-
- (create : true)

**중요** <br>
**HttpServletRequest, HttpServletResponse를 사용할 때 가장 중요한 점은 이 객체들이 HTTP
요청 메시지, HTTP응답 메시지를 편리하게 사용하도록 도와주는 객체라는 점이다. 따라서 이 기능에 대해서
깊이있는 이해를 하려면 HTTP 스펙이 제공하는 요청, 응답 메시지 자체를 이해해야 한다.**

## HttpServletRequest - 기본 사용법

### Get요청 데이터 - Get 쿼리 파라미터

```java

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    System.out.println("[전체 파라미터 조회] - START");
    Enumeration<String> parameterNames = request.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      String parameter = parameterNames.nextElement();
      System.out.println(parameter + " = " + request.getParameter(parameter));
    }

    request.getParameterNames().asIterator().forEachRemaining(parameterName -> {
      System.out.println(parameterName + " = " + request.getParameter(parameterName));
    });

    System.out.println("[단일 파라미터 조회]");
    String username = request.getParameter("username");
    System.out.println("request.getParameter(username) = " + username);

    String age = request.getParameter("age");
    System.out.println("request.getParameter(age) = " + age);
    System.out.println();

    System.out.println("[이름이 같은 복수 파라미터 조회]");
    System.out.println("request.getParameterValues(username)");
    String[] usernames = request.getParameterValues("username");
    for (String name : usernames) {
      System.out.println("username= " + name);
    }

    System.out.println("[전체 파라미터 조회] - END");
  }
}

```

### Post HTML Form

- content-type : application/x-www-form-urlencoded
- 메시지 바디에 쿼리 파라미터 형식으로 데이터를 전달한다. username=hello&age=20
- 이 방식은 Get의 쿼리 파라미터 형식과 같다. 따라서 쿼리 파라미터 조회 메서드를 그대로 사용하면 된다.

### API 메시지 바디 - 단순 텍스트

- HTTP message body에 데이터를 직접 담아서 요청한다.
  - HTTP API에서 주로 사용하며, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH
- HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.

```java

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body")
public class RequestBodyStringServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    InputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    System.out.println("messageBody = " + messageBody);
    response.getWriter().write("ok");
  }
}

```

### API 메시지 바디 - JSON

JSON 형식 전송

- POST http://localhost:8080/request-body-json
- content-type:application/json
- message body : {"username":"hello", "age":20}

```java
package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.util.StreamUtils;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    System.out.println("messageBody = " + messageBody);

    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
    System.out.println("helloData.username = " + helloData.getUsername());
    System.out.println("helloData.age = " + helloData.getAge());

    response.getWriter().write("ok");
  }
}
```

**참고**<br>
JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson,Gson 같은 JSON라이브러리
를 추가해서 사용해야 한다. 스프링 부트로 SpringMVC를 선택하면 기본으로 Jackson 라이브러리( ObjectMapper )를
제공한다.
또한 HTML form 데이터도 메시지 바디를 통해 전송되므로 직접 읽을 수 있다. 하지만 편리한 파라미터 조회
기능 ( request.getParamete())을 이미 제공하기 때문에 파라미터 조회 기능을 사용하면 된다.

## HttpServletResponse - 기본 사용

### HttpServletResponse 역할

- HTTP 응답 메시지 생성
  - HTTP 응답코드 지정
  - 헤더 생성
  - 바디 생성
- 편의 기능 제공
  - Content-Type, 쿠키, Redirect

```java

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    // status-line
    response.setStatus(HttpServletResponse.SC_OK);

    // [response-headers]
    response.setHeader("Content-Type", "text/plain;charset=utf-8");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("my-header", "hello");

    //[Header 편의 메서드]
    content(response);
    cookie(response);
    redirect(response);

    //[message body]
    PrintWriter writer = response.getWriter();
    writer.println("ok");

  }

  /** Content 편의 메서드 */
  private void content(HttpServletResponse response) {
    //Content-Type: text/plain;charset=utf-8
    //Content-Length: 2
    //response.setHeader("Content-Type", "text/plain;charset=utf-8");
    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    //response.setContentLength(2); //(생략시 자동 생성)
  }

  /** 쿠키편의 메서드 */
  private void cookie(HttpServletResponse response) {
    //Set-Cookie: myCookie=good; Max-Age=600;
    // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
    Cookie cookie = new Cookie("myCookie", "good");
    cookie.setMaxAge(600); //600초
    response.addCookie(cookie);
  }

  /** redirect 편의 메서드 */
  private void redirect(HttpServletResponse response) throws IOException {
    //Status Code 302
    //Location: /basic/hello-form.html
    //response.setStatus(HttpServletResponse.SC_FOUND); //302
    //response.setHeader("Location", "/basic/hello-form.html");
    response.sendRedirect("/basic/hello-form.html");
  }
}
```

### HTTP 응답 데이터 - 단순 텍스트, HTML

- HTTP 응답 메시지는 주로 다음 내용을 담아서 전달한다.
  - 단순 텍스트 응답
    - writer.println("ok")
  - HTML응답
  - HTTP API - MessageBody JSON 응답

```java

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse
          response)
          throws ServletException, IOException {
    //Content-Type: text/html;charset=utf-8
    response.setContentType("text/html");
    response.setCharacterEncoding("utf-8");
    PrintWriter writer = response.getWriter();
    writer.println("<html>");
    writer.println("<body>");
    writer.println(" <div>안녕?</div>");
    writer.println("</body>");
    writer.println("</html>");
  }
}
```

### HTTP 응답 데이터 - API JSON

```java

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    //Content-Type: application/json
    response.setHeader("content-type", "application/json");
    response.setCharacterEncoding("utf-8");
    HelloData data = new HelloData();
    data.setUsername("kim");
    data.setAge(20);
    //{"username":"kim","age":20}
    String result = objectMapper.writeValueAsString(data);
    response.getWriter().write(result);
  }
}
```

HTTP 응답으로 JSON을 반환할 때는 content-type을 `application/json`로 지정해야한다.
Jackson라이브러리가 제공하는 objectMapper.wrieValueAsString()를 사용하면 객체를 JSON 문자로
변경할 수 있다.

**참고**<br>
`application/json` 은 스펙상 utf-8 형식을 사용하도록 정의되어 있다. 그래서 스펙에서 charset=utf-8 과
같은 추가 파라미터를 지원하지 않는다. 따라서 `application/json` 이라고만 사용해야지 `application/json;charset=utf-8` 이라고 전달하는 것은 의미 없는 파라미터를 추가한 것이
된다.
response.getWriter()를 사용하면 추가 파라미터를 자동으로 추가해버린다. 이때는 response.getOutputStream()으로 출력하면 그런 문제가 없다.
