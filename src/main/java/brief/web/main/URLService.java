package brief.web.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import brief.web.api.ApiCallResponseExtractor;
import brief.web.api.ApiRestTemplate;
import brief.web.api.BriefDataVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import wcyoung.spring.mvc.common.base.BaseService;

@Service
public class URLService extends BaseService {

    @Resource
    private ResourceLoader resourceLoader;

    private ApiRestTemplate apiRestTemplate = new ApiRestTemplate();

    //POST방식의 rest api 호출, 첨에 짧은 URL 만들려고 원래 URL 보내기
    public String sendFullURL(Map param) throws Exception {
        final String url = BriefDataVO.getInstance().getUrl() + "/api/v1/url";
        RestTemplate restTemplate = apiRestTemplate.getApiRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map map= new HashMap();
        map.put("original_url", (String)param.get("original_url"));

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                map,
                String.class);
        String result = response.getBody();

        return result;
    }

    //GET방식의 rest api 호출, 단축된 URL의 원본 가져오기
    public String getShortURL(String shortURL) throws Exception {
        final String url = BriefDataVO.getInstance().getUrl() + "/api/v1/url/"+ shortURL;
        RestTemplate restTemplate = apiRestTemplate.getApiRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        //patch patch란 업데이트할때 쓰는 걸로 method 방식 추가된거임
        //get, post같이 쓸 수 있다
        RestTemplate restTemplate_patch = apiRestTemplate.getApiRestTemplatePatch();
        HttpEntity<String> response_patch = restTemplate_patch.exchange(
                url,
                HttpMethod.PATCH,
                entity,
                String.class);
        //restTemplate.put(url, "");
        //String을 Map으로 변경
        ObjectMapper mapper = new ObjectMapper();
        Map resultMap = mapper.readValue(response.getBody(), Map.class);
        String result = (String)resultMap.get("original_url");
        return result;
    }

   /* //GET방식의 rest api 호출, 단축된 URL의 원본 가져오기
    public String getShortURL(String shortURL) throws Exception {
        final String url = BriefDataVO.getInstance().getUrl() + "/api/v1/url/"+ shortURL;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);
        //String을 Map으로 변경
        ObjectMapper mapper = new ObjectMapper();
        Map resultMap = mapper.readValue(response.getBody(), Map.class);
        String result = (String)resultMap.get("original_url");
        return result;
    }*/
}
