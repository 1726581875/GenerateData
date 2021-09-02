package zhangyu.fool.generate.service.executor;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import zhangyu.fool.generate.exception.RunSqlException;

import java.io.IOException;

/**
 * @author xiaomingzhang
 * @date 2021/9/1
 */
public class ElasticsearchExecutor {

    private static RestHighLevelClient restHighLevelClient;

    static {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public void execute(String index, String jsonArrStr) {
        BulkRequest bulkRequest = new BulkRequest();
        JsonArray jsonArray =JsonParser.parseString(jsonArrStr).getAsJsonArray();
        jsonArray.forEach(obj -> {
            IndexRequest indexRequest = getIndexRequest(index, obj.getAsString(), null);
            bulkRequest.add(indexRequest);
        });
        try {
            BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if(response == null && response.hasFailures()){
                throw new RunSqlException("bulk has failures");
            }
        } catch (IOException e) {
            throw new RunSqlException("elasticsearch bulk exception", e);
        }
    }

    /**
     * 根据index、type、content 得到IndexRequest
     *
     * @param index   ES的Index
     * @param content 需要插入ES的内容
     * @return IndexRequest 实例化
     */
    private IndexRequest getIndexRequest(String index, String content, String id) {
        IndexRequest indexRequest = new IndexRequest(index);
        if (StringUtils.isNotBlank(id)) {
            indexRequest.id(id);
        }
        indexRequest.source(content, XContentType.JSON);
        return indexRequest;
    }

}
