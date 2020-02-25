package de.huddeldaddel.translationchallenge.repositories

import de.huddeldaddel.translationchallenge.model.Translation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface TranslationElasticRepository : ElasticsearchRepository<Translation, String> {

    fun findBySourceOrTarget(source: String, target: String, pageable: Pageable): Page<Translation>

}