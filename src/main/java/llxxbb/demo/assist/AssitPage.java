package llxxbb.demo.assist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
public class AssitPage {
    Logger logger = LoggerFactory.getLogger(AssitPage.class);
    private final String version;

    public AssitPage(@Value("${project.version:undefined}") String version) {
        this.version = version;
        logger.info("**** project version is: {}", version);
    }

    @RequestMapping("/version")
    public String getVersion() {
        // TODO your database query code, such "SELECT 1"
        return version;
    }

    @RequestMapping("/monitorDB/monitor.shtml")
    public String monitor() {
        // TODO your database query code, such "SELECT 1"
        return "Ok";
    }
}
