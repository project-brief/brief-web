package brief.web.api;

import org.springframework.stereotype.Service;
import wcyoung.spring.mvc.common.base.BaseService;

@Service
public class BriefService extends BaseService {

    public void getBriefURL(){
        BriefDataVO dbVO = BriefDataVO.getInstance();
        dbVO.setUrl(getProperty("brief.api.url"));
    }
}
