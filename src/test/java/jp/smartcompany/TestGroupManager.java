package jp.smartcompany;

import jp.smartcompany.base.AbstractMockMvc;
import jp.smartcompany.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class TestGroupManager extends AbstractMockMvc {

  private final static String BASE_URL = "http://localhost:6879";

  public TestGroupManager(WebApplicationContext webApplicationContext) {
      super(webApplicationContext);
  }

  // 获取group详情
  @Test
  void testGetGroupDetail() {
     MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
     params.add("groupId","11");
     params.add("psSite","Admin");
     params.add("psApp","groupmanager");
     printJSONResult(
             perform(
                 getJSON(BASE_URL+"/sys/groupmanager/detail")
                 .queryParams(params)
                 .session(TestUtil.mockLoginInfoSession())
             )
//             .andExpect(matchJson("$"))
             .andReturn()
     );
  }


}
