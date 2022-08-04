package io.renren;

import com.alibaba.fastjson.JSON;
import io.renren.config.MyElasticSearchConfig;
import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.service.JobService;
import io.renren.modules.front.vo.JobDetailVo;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

//@SpringBootTest
public class ElasticSearchIndex {
    @Autowired
    private RestHighLevelClient esClient;
    @Autowired
    private JobService jobService;

    public static final String settings = "{\n" +
            "    \"number_of_shards\": 1,\n" +
            "    \"number_of_replicas\": 1,\n" +
            "    \"analysis\": {\n" +
            "      \"filter\": {\n" +
            "        \"katakana_readingform\": {\n" +
            "          \"type\": \"kuromoji_readingform\",\n" +
            "          \"use_romaji\": \"false\"\n" +
            "        },\n" +
            "        \"romaji_readingform\": {\n" +
            "          \"type\": \"kuromoji_readingform\",\n" +
            "          \"use_romaji\": \"true\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"analyzer\": {\n" +
            "        \"ja_reading_analyzer\": {\n" +
            "          \"type\": \"custom\",\n" +
            "          \"filter\": [\n" +
            "            \"cjk_width\",\n" +
            "            \"lowercase\",\n" +
            "            \"kuromoji_stemmer\",\n" +
            "            \"ja_stop\",\n" +
            "            \"kuromoji_part_of_speech\",\n" +
            "            \"kuromoji_baseform\",\n" +
            "            \"katakana_readingform\"\n" +
            "          ],\n" +
            "          \"tokenizer\": \"kuromoji_tokenizer\"\n" +
            "        },\n" +
            "        \"ja_romaji_analyzer\": {\n" +
            "          \"type\": \"custom\",\n" +
            "          \"filter\": [\n" +
            "            \"cjk_width\",\n" +
            "            \"lowercase\",\n" +
            "            \"kuromoji_stemmer\",\n" +
            "            \"ja_stop\",\n" +
            "            \"kuromoji_part_of_speech\",\n" +
            "            \"kuromoji_baseform\",\n" +
            "            \"romaji_readingform\"\n" +
            "          ],\n" +
            "          \"tokenizer\": \"kuromoji_tokenizer\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }";
    public static final String mapping = "{\n" +
            "    \"properties\": {\n" +
            "      \"jobName\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"id\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"workDetail\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\"\n" +
            "      },\n" +
            "      \"chinese\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"jobType\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"workType\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"city\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"area\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"address\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"phone\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"line\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"station\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"distance\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\"\n" +
            "      },\n" +
            "      \"japanese\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"companyTrain\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"experience\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"paidType\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"salary\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"hiredNumber\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"feature\": {\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"workTime\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"createdTime\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"updatedTime\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"businessUserId\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"companyName\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"kuromoji\",\n" +
            "        \"fields\": {\n" +
            "          \"reading\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_reading_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"romaji\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"ja_romaji_analyzer\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          },\n" +
            "          \"suggestion\": {\n" +
            "            \"type\": \"completion\",\n" +
            "            \"analyzer\": \"kuromoji\",\n" +
            "            \"preserve_separators\": false,\n" +
            "            \"preserve_position_increments\": false,\n" +
            "            \"max_input_length\": 20\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }";


    //@Test
    void createIndex() throws IOException {
        GetIndexRequest getRequest = new GetIndexRequest("jobs");
        boolean exists = esClient.indices().exists(getRequest, RequestOptions.DEFAULT);
        if (exists) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("jobs");
            esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        }

        //File file = ResourceUtils.getFile("classpath:elastic-init.json");

        CreateIndexRequest request = new CreateIndexRequest("jobs");
        request.settings(settings, XContentType.JSON);
        request.mapping(mapping, XContentType.JSON);
        esClient.indices().create(request, MyElasticSearchConfig.COMMON_OPTIONS);
    }

    //@Test
    void addJobs() throws IOException {
        IndexRequest request = new IndexRequest("jobs");

        List<Long> list = jobService.list().stream().map(JobEntity::getId).collect(Collectors.toList());
        for (Long id : list) {
            JobDetailVo jobDetailVo = jobService.getDetailById(id);
            request.id(String.valueOf(id));
            request.source(JSON.toJSONString(jobDetailVo), XContentType.JSON);
            esClient.index(request, MyElasticSearchConfig.COMMON_OPTIONS);
        }
    }
}
