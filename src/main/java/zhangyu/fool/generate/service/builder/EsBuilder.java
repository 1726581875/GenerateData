package zhangyu.fool.generate.service.builder;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author xiaomingzhang
 * @date 2021/9/1
 */
public class EsBuilder {


    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("course_record");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hitArr = hits.getHits();
        for (SearchHit hit : hitArr){
            System.out.println(hit.getSourceAsString());
        }
    }

}
