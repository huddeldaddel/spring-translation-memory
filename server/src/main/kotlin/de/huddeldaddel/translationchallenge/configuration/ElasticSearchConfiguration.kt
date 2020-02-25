package de.huddeldaddel.translationchallenge.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.client.Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.DefaultResultMapper
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.EntityMapper
import org.springframework.stereotype.Component
import java.util.*

@Configuration
open class ElasticsearchConfig(val elasticsearchEntityMapper: ElasticsearchEntityMapper) {

    @Bean
    open fun elasticsearchTemplate(client: Client) = ElasticsearchTemplate(client, DefaultResultMapper(elasticsearchEntityMapper))

}

@Component
class ElasticsearchEntityMapper(val objectMapper: ObjectMapper) : EntityMapper {

    override fun mapToString(`object`: Any?) = objectMapper.writeValueAsString(`object`)

    override fun <T : Any?> readObject(p0: MutableMap<String, Any>?, p1: Class<T>?): T? = mapToObject(mapToString(p0), p1);

    override fun <T : Any?> mapToObject(source: String?, clazz: Class<T>?) = objectMapper.readValue(source, clazz)

    override fun mapObject(p0: Any?): MutableMap<String, Any> = (objectMapper.readValue(mapToString(p0), HashMap::class.java) as Map<String, Any>).toMutableMap()

}
