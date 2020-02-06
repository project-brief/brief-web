package brief.web.main;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import brief.web.version.VersionService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import wcyoung.spring.mvc.bind.annotation.JsonRequestMapping;
import wcyoung.spring.mvc.common.base.BaseController;

@Controller
public class PageController extends BaseController {

    @Resource
    private URLService urlService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage() {
        return "/brief-main";
    }

    @RequestMapping(value = "/{shortURL}", method = RequestMethod.GET)
    public String getShortCutUrl( @PathVariable("shortURL") String shortURL) {
        Map<String, Object> body = new HashMap<>();

        try {
            String fullURL = URLEncoder.encode(urlService.getShortURL(shortURL), "UTF-8");
            fullURL = fullURL.replaceAll("%3A", ":");
            fullURL = fullURL.replaceAll("%2F", "/");
            fullURL = "redirect:" + fullURL;
            System.out.println(fullURL);
            if(fullURL.length() < 1 || fullURL.equals("null")){
                return "/home";
            }
            return fullURL;
        } catch (Exception e) {
            log.error("Exception: {}", ExceptionUtils.getStackTrace(e));
            return "/home";
        }

    }
}
