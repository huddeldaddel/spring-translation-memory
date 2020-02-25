package de.huddeldaddel.translationchallenge.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.huddeldaddel.translationchallenge.controllers.Mappings.TRANSLATION
import de.huddeldaddel.translationchallenge.controllers.Mappings.TRANSLATION_SEARCH
import de.huddeldaddel.translationchallenge.controllers.api.ImportResponse
import de.huddeldaddel.translationchallenge.controllers.api.SearchResponse
import de.huddeldaddel.translationchallenge.model.Translation
import de.huddeldaddel.translationchallenge.services.TranslationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader


@RestController
@RequestMapping(value = [TRANSLATION], produces = [MediaType.APPLICATION_JSON_VALUE])
class TranslationController(
        private val objectMapper: ObjectMapper,
        private val translationService: TranslationService
) {

    @GetMapping(TRANSLATION_SEARCH)
    fun search(@PathVariable query: String): SearchResponse {
        val results = translationService.findTranslations(query)
        return SearchResponse(query = query, results = results)
    }

    @PostMapping(path = ["/"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun importJsonData(@RequestBody translationsToImport: List<Translation>): ImportResponse {
        val count = translationService.importTranslations(translationsToImport)
        return ImportResponse(importedElements = count)
    }

    @PostMapping(path = ["/"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun importFileData(@RequestParam("file") file: MultipartFile): ImportResponse {
        val json = file.inputStream.bufferedReader().use(BufferedReader::readText)
        val translations: List<Translation> = objectMapper.readValue(json)
        val count = translationService.importTranslations(translations)
        return ImportResponse(importedElements = count)
    }

}