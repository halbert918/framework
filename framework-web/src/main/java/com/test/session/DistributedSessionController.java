package com.test.session;


//import com.rest.client.RestHttpClient;
import com.rest.client.RestHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/11/26
 * @Description
 */
@Controller
@RequestMapping("/distributed/session")
public class DistributedSessionController {

    @RequestMapping("test")
    public String test(HttpSession session, ModelMap modelMap) {
        RestHttpClient client = new RestHttpClient();
        Map<String, String> params = new HashMap<>();
        params.put("id", "12312");
        params.put("name", "test");

        client.postForEntity("http://www.baidu.com/forum/topic/{id}", params, HashMap.class, params);
//        session.setAttribute("test", "testDistributed");
        modelMap.put("hyb", "hyb1");
        return "/index.jsp";
    }


    @RequestMapping("/test1")
    public String test1(HttpSession session, HttpServletRequest request, ModelMap modelMap) {
        Map<String, String[]>  map = request.getParameterMap();
        System.out.print("======================>" + session.getAttribute("test"));
        return "/index.jsp";
    }
}
