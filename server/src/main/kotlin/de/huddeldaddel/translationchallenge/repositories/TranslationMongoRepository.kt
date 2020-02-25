package de.huddeldaddel.translationchallenge.repositories

import de.huddeldaddel.translationchallenge.model.Translation
import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationMongoRepository : MongoRepository<Translation, String>