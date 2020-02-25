package de.huddeldaddel.translationchallenge.services

import de.huddeldaddel.translationchallenge.model.Translation
import de.huddeldaddel.translationchallenge.repositories.TranslationElasticRepository
import de.huddeldaddel.translationchallenge.repositories.TranslationMongoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class TranslationService(
        private val translationMongoRepository: TranslationMongoRepository,
        private val translationElasticRepository: TranslationElasticRepository
) {

    /**
     * Finds translations that are close to a given search term.
     *
     * In our simple case of a shared model between ElasticSearch and MongoDB it should be sufficient to return results
     * from ElasticSearch - no need to query MongoDB here. Actually we don't need MongoDB at all in this example - as we
     * never read the database except in integration tests.
     */
    fun findTranslations(searchTerm: String): List<Translation> {
        val pageable = PageRequest.of(0, NUMBER_OF_SEARCH_RESULTS)
        val page = translationElasticRepository.findBySourceOrTarget(searchTerm, searchTerm, pageable)
        println("Found ${page.totalElements} for search term $searchTerm")
        return page.content
    }

    /**
     * Imports the given translations into the database and indexes them in ElasticSearch and returns the number of
     * imported elements.
     *
     * The task description is not clear about the uniqueness of translation elements - and the languages are not
     * specified at all in the data model. Therefore (and for simplicity) I assume that one source can be translated to
     * multiple target texts and that multiple target texts can be based on multiple source texts (m:n relation). Based
     * on this assumption I do not search / update existing elements at all. This would not be the case in a production
     * environment.
     *
     * This operation has another aspect to take into consideration: it effectively models a distributed transaction
     * that is spanning over multiple systems. To keep thing simple I do a simplified rollback in case of error here.
     */
    fun importTranslations(translationsToImport: Collection<Translation>): Int {
        val insertedMongoRecords = translationMongoRepository.insert(translationsToImport)
        return try {
            translationElasticRepository.saveAll(insertedMongoRecords)
            insertedMongoRecords.size
        } catch(ex: Exception) {
            translationMongoRepository.deleteAll(insertedMongoRecords)
            0
        }
    }

    companion object {

        const val NUMBER_OF_SEARCH_RESULTS = 3

    }

}