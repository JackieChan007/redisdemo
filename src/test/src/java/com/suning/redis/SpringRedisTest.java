package src.java.com.suning.redis;

import com.cnsuning.redis.util.SpringRedsUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import src.java.BaseTest;


public class SpringRedisTest extends BaseTest {
    @Autowired
    private SpringRedsUtil springRedsUtil;

    @Test
    public void getRedis(){
        String key ="key1111";
//        springRedsUtil.putCache(key,"1000");
        springRedsUtil.addLink(key,"wwww.baidu.com");
    }
}
