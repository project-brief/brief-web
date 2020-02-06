package brief.web.main;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import brief.web.version.VersionService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.context.support.HttpRequestHandlerServlet;
import wcyoung.spring.mvc.bind.annotation.JsonRequestMapping;
import wcyoung.spring.mvc.common.base.BaseController;

@RequestMapping(value = "/url")
@RestController
public class URLController extends BaseController {

    @Resource
    private URLService urlService;

    @GetMapping(value = "/getShortcut")
    public ResponseEntity<Map<String, Object>> getShortCutUrl(@RequestParam Map<String, Object> param) {
        Map<String, Object> body = new HashMap<>();
        try {
            String org = (String)param.get("original_url");
            if(org.matches("^(http|https)?:\\/\\/.*")){
                body.put("shortURL", urlService.sendFullURL(param));
            }else{
                return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Exception: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);

    }

}
