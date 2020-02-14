package brief.web.api;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ApiRestTemplate {
    private RestTemplate restTemplate = new RestTemplate();
    private RestTemplate restTemplate_patch = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    //기본생성
    public RestTemplate getApiRestTemplate(){
        return restTemplate;
    }

    //패치용
    public RestTemplate getApiRestTemplatePatch(){
        return restTemplate_patch;
    }
}
