package de.huddeldaddel.translationchallenge.repositories

import de.huddeldaddel.translationchallenge.model.Translation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ContextConfiguration

/**
 * Tests the integration of ElasticSearch.
 *
 * Takes some times to setup as the SpringBoot context has to be initialized for this test.
 */
@SpringBootTest
@ContextConfiguration
class TranslationElasticRepositoryIntTest {

    @Autowired
    private lateinit var translationElasticRepository: TranslationElasticRepository

    @Test
    fun translationForward() {
        translationElasticRepository.save(Translation(id = "a", source = "Hello World", target = "Hola mundo"))
        translationElasticRepository.save(Translation(id = "b", source = "Hello Everyone", target = "Hola a todos"))
        translationElasticRepository.save(Translation(id = "c", source = "bcd", target = "ghi"))

        val results = translationElasticRepository.findBySourceOrTarget("hello", "hello", PageRequest.of(0, 3))
        Assertions.assertEquals(2, results.totalElements)

        translationElasticRepository.deleteAll()
    }

    @Test
    fun translationReverse() {
        translationElasticRepository.save(Translation(id = "a", source = "A dog was running outside", target = "Un perro corría afuera"))
        translationElasticRepository.save(Translation(id = "c", source = "bcd", target = "ghi"))

        val results = translationElasticRepository.findBySourceOrTarget("Un perro", "Un perro", PageRequest.of(0, 3))
        Assertions.assertEquals(1, results.totalElements)

        translationElasticRepository.deleteAll()
    }

}