package io.renren.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "renren.elasticsearch.open", havingValue = "true")
public class MyElasticSearchConfig {
    //设置公共的请求头 https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/java-rest-low-usage-requests.html#java-rest-low-usage-request-options
    public  static final RequestOptions COMMON_OPTIONS;

    @Value("${renren.elasticsearch.host}")
    public String HOST;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(HOST, 9200, "http")
//                        new HttpHost("localhost", 9201, "http")
                )
        );
    }

}
