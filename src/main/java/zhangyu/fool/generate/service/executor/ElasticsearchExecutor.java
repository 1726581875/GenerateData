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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhangyu.fool.generate.exception.RunSqlException;
import zhangyu.fool.generate.object.test.es.Course;
import zhangyu.fool.generate.service.builder.ElasticsearchDataBuilder;

import java.io.IOException;

/**
 * @author xiaomingzhang
 * @date 2021/9/1
 */
public class ElasticsearchExecutor {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchExecutor.class);

    private static RestHighLevelClient restHighLevelClient;

    static {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public void execute(String index, String jsonArrStr) {
        BulkRequest bulkRequest = new BulkRequest();
        JsonArray jsonArray = JsonParser.parseString(jsonArrStr).getAsJsonArray();

        jsonArray.forEach(obj -> {
            IndexRequest indexRequest = getIndexRequest(index, obj.toString(), null);
            bulkRequest.add(indexRequest);
        });
        try {
            BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if(response == null || response.hasFailures()){
                log.error("insert es fail,case: {}", response.buildFailureMessage());
                throw new RunSqlException("bulk has failures");
            }else {
                System.out.println("bulk has success");
            }
        } catch (IOException e) {
            throw new RunSqlException("elasticsearch bulk exception", e);
        } finally {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private IndexRequest getIndexRequest(String index, String content, String id) {
        IndexRequest indexRequest = new IndexRequest(index);
        if (StringUtils.isNotBlank(id)) {
            indexRequest.id(id);
        }
        indexRequest.source(content, XContentType.JSON);
        return indexRequest;
    }


    public static void main(String[] args) {
        ElasticsearchDataBuilder dataBuilder = new ElasticsearchDataBuilder();
        String objectArray = dataBuilder.buildJsonObjectArray(Course.class, 10);
        System.out.println(objectArray);
        ElasticsearchExecutor elasticsearchExecutor = new ElasticsearchExecutor();
        elasticsearchExecutor.execute("mooc_course", objectArray);
    }


}
