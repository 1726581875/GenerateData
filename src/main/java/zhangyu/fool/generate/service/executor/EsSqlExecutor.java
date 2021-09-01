package zhangyu.fool.generate.service.executor;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author xiaomingzhang
 * @date 2021/9/1
 */
public class EsSqlExecutor implements SqlExecutor{

    private static RestHighLevelClient restHighLevelClient;

    static {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    @Override
    public void execute(String sql) {

    }

    @Override
    public Object execute(String sql, Class<?> type) {
        return null;
    }
}
