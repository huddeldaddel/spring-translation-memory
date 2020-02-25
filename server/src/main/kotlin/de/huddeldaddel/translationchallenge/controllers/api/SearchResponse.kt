package de.huddeldaddel.translationchallenge.controllers.api

import de.huddeldaddel.translationchallenge.model.Translation

data class SearchResponse(val query: String, val results: List<Translation>)