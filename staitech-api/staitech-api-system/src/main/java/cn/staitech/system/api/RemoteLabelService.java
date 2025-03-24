package cn.staitech.system.api;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * @Author wudi
 * @Date 2023/9/14 13:47
 * @desc 调用远程接口
 */
@FeignClient(name = "phyon",url = "${forward.phyonUrl}")
public interface RemoteLabelService {

    @PostMapping(value = "/get_label",consumes= MediaType.APPLICATION_JSON_VALUE)
    JSONObject getLabel(JSONObject labelEntity);

    @PostMapping(value = "/Standard",consumes= MediaType.APPLICATION_JSON_VALUE)
    JSONObject Standard(JSONObject labelEntity);

    @PostMapping(value = "/marking",consumes= MediaType.APPLICATION_JSON_VALUE)
    JSONObject marking(List<JSONObject> labelEntity);

    @PostMapping(value = "/algo_examine",consumes= MediaType.APPLICATION_JSON_VALUE)
    JSONObject algoExamine(JSONObject labelEntity);

}
