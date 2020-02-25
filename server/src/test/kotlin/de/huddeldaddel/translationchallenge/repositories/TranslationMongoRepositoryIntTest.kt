package de.huddeldaddel.translationchallenge.repositories

import de.huddeldaddel.translationchallenge.model.Translation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

/**
 * Tests the integration of MongoDB.
 *
 * Takes some times to setup as the SpringBoot context has to be initialized for this test.
 */
@SpringBootTest
@ContextConfiguration
class TranslationMongoRepositoryIntTest {

    @Autowired
    private lateinit var translationMongoRepository: TranslationMongoRepository

    @Test
    fun translationCrud() {
        // Create
        val inserted = translationMongoRepository.save(Translation(id = null, source = "abc", target = "def"))
        Assertions.assertNotNull(inserted.id)

        // Read
        val copy = translationMongoRepository.findById(inserted.id!!)
        Assertions.assertEquals(true, copy.isPresent)

        // Update
        translationMongoRepository.save(copy.get().copy(target = "xyz"))
        val secondCopy = translationMongoRepository.findById(inserted.id!!)
        Assertions.assertEquals(true, secondCopy.isPresent)

        // Delete
        translationMongoRepository.delete(secondCopy.get())
        Assertions.assertEquals(true, translationMongoRepository.findById(inserted.id!!).isEmpty)

        translationMongoRepository.deleteAll()
    }

}