package de.huddeldaddel.translationchallenge.model

import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "idx_translation")
data class Translation(val id: String?, val source: String, val target: String)