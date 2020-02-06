package brief.web.version;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import wcyoung.spring.mvc.common.base.BaseService;

@Service
public class VersionService extends BaseService {

    @Resource
    private ResourceLoader resourceLoader;

    public String getManifestVersion() throws IOException {
        Properties properties = new Properties();
        properties.load(resourceLoader.getResource("/META-INF/MANIFEST.MF").getInputStream());
        return properties.getProperty("Implementation-Version");
    }

}
