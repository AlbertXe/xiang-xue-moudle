package ch8_tomcat_ngix;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http://localhost:8080/demo/hello
 * 嵌入式启动
 * 86150
 * ServletDemo
 * 2020/3/13 22:59
 */
public class ServletDemo {
    public static void main(String[] args) throws LifecycleException {
        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.getWriter().write("hello world");
            }
        };

        Tomcat tomcat = new Tomcat();
        Context context = tomcat.addContext("/demo", null);
        tomcat.addServlet(context, "/hello", servlet);
        context.addServletMappingDecoded("/hello", "/hello");

        tomcat.init();
        tomcat.start();
        tomcat.getServer().await(); //等待请求
    }

    @Test
    public void test() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        //web项目地址
        tomcat.addWebapp("/hello", "D:\\app\\apache-tomcat-8.5.38\\webapps\\examples");
        tomcat.getConnector().setPort(8080);
        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
