import com.ConsumerApp;
import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;

/**
 * 86150
 * RibbonTest
 * 2020/10/28 21:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsumerApp.class)
@WebAppConfiguration
public class RibbonTest {
    @Test
    public void test() throws Exception {
        ConfigurationManager.getConfigInstance().setProperty("myclient.ribbon.lisfOfServers", "localhost:8084");
        RestClient client = (RestClient) ClientFactory.getNamedClient("myclient");
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/order")).build();

        for (int i = 0; i < 10; i++) {
            HttpResponse httpResponse = client.executeWithLoadBalancer(request);
            String result = httpResponse.getEntity(String.class);
            System.out.println(result);
        }
    }

}
