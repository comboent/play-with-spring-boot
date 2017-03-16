package org.combo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> index() {
        Map<String, Object> rst = new HashMap<>();
        rst.put("a", 123);
        rst.put("b", 456);
        rst.put("c", "ninini");
        return rst;
    }

}
