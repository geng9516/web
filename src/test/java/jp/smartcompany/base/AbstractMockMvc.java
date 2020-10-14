package jp.smartcompany.base;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * 测试通用方法
 */
public class AbstractMockMvc {

    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;

    protected AbstractMockMvc(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 发送请求
     */
    protected ResultActions perform(MockHttpServletRequestBuilder builder) {
        try {
            return mockMvc.perform(builder);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 匹配返回的json结果
     */
    protected JsonPathResultMatchers matchJson(String jsonPath) {
        return MockMvcResultMatchers.jsonPath(jsonPath);
    }

    /**
     * 打印请求结果，只打印response
     */
    protected void printJSONResult(MvcResult rs) {
        try {
            MockHttpServletResponse resp = rs.getResponse();
            resp.setCharacterEncoding("UTF-8");
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            System.out.println("==========================================");
            System.out.println("执行方法:"+stackTraceElement.getMethodName());
            System.out.println(StrUtil.format("状态码:{}",resp.getStatus()));
            System.out.println(StrUtil.format("请求Response:{}",rs.getResponse().getContentAsString()));
            System.out.println("==========================================");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印详细的请求信息
     */
    private ResultHandler print() {
        return MockMvcResultHandlers.print();
    }

    /**
     * 发送GET请求
     * 构建perform使用的ReqiestBuilder对象
     * @param url 请求url
     */
    protected MockHttpServletRequestBuilder getJSON(String url) {
        return MockMvcRequestBuilders.get(url)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
    }

    /**
     * 发送POST请求
     * 构建perform使用的ReqiestBuilder对象
     * @param url 请求url
     */
    protected MockHttpServletRequestBuilder postJSON(String url) {
        return MockMvcRequestBuilders.post(url)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
    }

}
