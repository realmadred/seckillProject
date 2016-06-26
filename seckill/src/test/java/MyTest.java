import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Madrid on 2016/6/10.
 */
public class MyTest {
    private static Logger logger = LoggerFactory.getLogger(MyTest.class);
    public static void main(String[] args){
        System.out.println("test");
        logger.info("info");
        logger.debug("debug");
        logger.warn("warn");
        logger.trace("trace");
        logger.error("error");
    }
}
