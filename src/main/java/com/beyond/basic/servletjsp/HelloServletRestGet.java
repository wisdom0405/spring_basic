package com.beyond.basic.servletjsp;

import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Controller가 아닌 WebServlet을 통해 라우팅
// 메서드 단위가 아닌 클래스 단위
@WebServlet("/hello/servlet/rest/get")
public class HelloServletRestGet extends HttpServlet {
    @Override
    //HttpServletRequest에는 사용자의 요청정보가 담겨있음.
    //HttpServletResponse에는 사용자에게 응답정보를 담아줘야 함.
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // HttpServletResponse res : 사용자에게 주는 데이터 형태
        Hello hello = new Hello();
        hello.setName("hongildong");
        hello.setEmail("hong@naver.com");
        hello.setPassword("12341234");
        // response에 ContentType과 Encoding 방식 세팅
        resp.setContentType("applcation/json");
        resp.setCharacterEncoding("UTF-8");

        //object mapper로 직접 직렬화(안해주면 그냥 String 형태)
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(hello);

        // 응답바디 생성
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        // 기본적으로 버퍼를 통해 데이터가 조립되므로, 버퍼를 비우는 과정.
        printWriter.flush();

    }
}
