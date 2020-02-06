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

    public String getManifestVersion() throws IOException {
        Properties properties = new Properties();
        properties.load(resourceLoader.getResource("/META-INF/MANIFEST.MF").getInputStream());
        return properties.getProperty("Implementation-Version");
    }

    //GET방식의 rest api 호출
    public String getTest() throws IOException {
        final String url = "http://localhost:5050/api/v1/version/app";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders(); // 헤더 설정해줘야 함
        headers.setContentType(MediaType.APPLICATION_JSON); //media type 은 json

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        HttpEntity<String> resultEx = restTemplate.exchange(url, HttpMethod.GET,entity, String.class); //GET방식
        String result = resultEx.toString();
        System.out.println(result);
        return result;
    }

    //POST방식의 rest api 호출, 첨에 짧은 URL 만들려고 원래 URL 보내기
    public String sendFullURL(Map param) throws Exception {
        final String url = "http://128.199.112.91:28080/api/v1/url";
        RestTemplate restTemplate = new RestTemplate();

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
        final String url = "http://128.199.112.91:28080/api/v1/url/"+ shortURL;
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
    }

}
