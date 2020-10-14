package jp.smartcompany.base;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface MockResponseStatus {

    ResultMatcher STATUS_OK = MockMvcResultMatchers.status().isOk();

}
