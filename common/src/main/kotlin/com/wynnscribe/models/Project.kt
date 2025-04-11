package com.wynnscribe.models

import com.wynnscribe.Placeholder
import com.wynnscribe.models.Language.LanguageId
import com.wynnscribe.models.Project.Filter.FilterId
import com.wynnscribe.utils.best
import kotlinx.serialization.Serializable

interface Project {
    val id: ProjectId

    val name: String

    val description: String

    val filterId: FilterId

    @Serializable
    @JvmInline
    value class ProjectId(val value: Long)

    @Serializable
    @JvmInline
    value class UserId(val value: String)

    @Serializable
    data class Json(
        override val id: ProjectId,
        override val name: String,
        override val description: String,
        val filter: Filter.Json,
        val originals: List<Original.Json>
    ): Project {
        override val filterId: FilterId get() = throw NotImplementedError()

        companion object {
            fun of(project: Project, filter: Filter, filters: List<Filter>, originals: List<Original>, translations: List<Original.Translation>, votes: List<Original.Translation.Vote>): Json {
                return Json(
                    id = project.id,
                    name = project.name,
                    description = project.description,
                    filter = filter.let(Filter.Json::of),
                    originals = originals.filter { it.parentId == null }.filter { it.projectId == project.id }.map { original -> Original.Json.of(original, originals, filters.find { it.id == original.filterId }?: Filter.Json(null, null, null), filters, translations, votes) }
                )
            }
        }
    }

    @Serializable
    data class Impl(
        override val id: ProjectId,
        override val name: String,
        override val description: String,
        override val filterId: FilterId
    ): Project

    @Serializable
    data class Update(
        override val name: String,
        override val description: String,
        val filter: Filter.Update = Filter.Update(null, null, null)
    ): Project {
        override val id: ProjectId get() = throw NotImplementedError()
        override val filterId: FilterId get() = throw NotImplementedError()
    }

    @Serializable
    data class Create(
        override val name: String,
        override val description: String,
        val filter: Filter.Create = Filter.Create(null, null, null)
    ): Project {
        override val id: ProjectId get() = throw NotImplementedError()
        override val filterId: FilterId get() = throw NotImplementedError()
    }

    interface Filter {

        val id: FilterId

        val type: Content?

        val title: Content?

        val content: Content?

        @Serializable
        @JvmInline
        value class FilterId(val value: Long)

        @Serializable
        data class Json(
            override val type: Content.Json?,
            override val title: Content.Json?,
            override val content: Content.Json?
        ): Filter {

            override val id: FilterId get() = throw NotImplementedError()

            companion object {
                fun of(filter: Filter): Json {
                    return Json(
                        type = filter.type?.let(Content.Json::of),
                        title = filter.title?.let(Content.Json::of),
                        content = filter.content?.let(Content.Json::of)
                    )
                }
            }
        }

        @Serializable
        data class Impl(
            override val id: FilterId,
            override val type: Content.Impl?,
            override val title: Content.Impl?,
            override val content: Content.Impl?
        ): Filter

        @Serializable
        data class Create(
            override val type: Content.Create?,
            override val title: Content.Create?,
            override val content: Content.Create?
        ): Filter {
            override val id: FilterId get() = throw NotImplementedError()
        }

        @Serializable
        data class Update(
            override val type: Content.Update?,
            override val title: Content.Update?,
            override val content: Content.Update?
        ): Filter {
            override val id: FilterId get() = throw NotImplementedError()
        }

        interface Content {
            val content: String

            val withColors: Boolean

            val fullMatch: Boolean

            @Serializable
            data class Json(
                override val content: String,
                override val withColors: Boolean,
                override val fullMatch: Boolean
            ): Content {
                companion object {
                    fun of(content: Content): Json {
                        return Json(
                            content = content.content,
                            withColors = content.withColors,
                            fullMatch = content.fullMatch
                        )
                    }
                }
            }

            @Serializable
            data class Impl(
                override val content: String,
                override val withColors: Boolean,
                override val fullMatch: Boolean
            ): Content

            @Serializable
            data class Update(
                override val content: String,
                override val withColors: Boolean,
                override val fullMatch: Boolean
            ): Content

            @Serializable
            data class Create(
                override val content: String,
                override val withColors: Boolean,
                override val fullMatch: Boolean
            ): Content
        }
    }

    interface Original {
        @Serializable
        @JvmInline
        value class OriginalId(val value: Long)

        val id: OriginalId

        @Serializable
        @JvmInline
        value class OriginalText(val value: String)

        val text: OriginalText

        val stopOnMatch: Boolean

        @Serializable
        @JvmInline
        value class Template(val value: String)

        val template: Template?

        val filterId: FilterId

        val parentId: OriginalId?

        val projectId: ProjectId

        val type: Type

        enum class Type {
            UserInterface,
            GameItem,
            Chat,
            Conversation,
            Stat,
            Damage,
            Status
        }

        @Serializable
        data class Json(
            override val text: OriginalText,
            override val stopOnMatch: Boolean,
            override val type: Type,
            val filter: Filter.Json,
            val translations: List<Translation.Json>,
            val children: List<Json>,
            override val template: Template?
        ): Original {
            override val projectId: ProjectId get() = throw NotImplementedError()
            override val filterId: FilterId get() = throw NotImplementedError()
            override val id: OriginalId get() = throw NotImplementedError()
            override val parentId: OriginalId? get() = throw NotImplementedError()

            val regexes: Regexes by lazy { Regexes(this.text.value) }

            class Regexes(val text: String) {
                private var placeholders = mutableMapOf<Placeholder<*>, CompiledPlaceholder<*>>()

                fun <T> placeholder(placeholder: Placeholder<T>, holders: List<CompiledPlaceholder.Holder<*>>, original: Original.Json): CompiledPlaceholder<T> {
                    return this.placeholders.getOrPut(placeholder) { placeholder.compile(holders, original) } as CompiledPlaceholder<T>
                }

                fun <T> placeholder(placeholder: Placeholder<T>, block: () -> CompiledPlaceholder<T>): CompiledPlaceholder<T> {
                    return this.placeholders.getOrPut(placeholder, block) as CompiledPlaceholder<T>
                }

                class CompiledPlaceholder<T>(val regex: Regex?, val holders: List<Holder<T>>) {
                    class Holder<T>(val pattern: String, val type: Placeholder<T>, val data: T? = null)
                }
            }

            companion object {
                fun of(original: Original, originals: List<Original>, filter: Filter, filters: List<Filter>, translations: List<Translation>, votes: List<Translation.Vote>): Json {
                    return Json(
                        text = original.text,
                        stopOnMatch = original.stopOnMatch,
                        filter = filter.let(Filter.Json::of),
                        translations = translations.filter { it.originalId == original.id }.map { Translation.Json.of(it, votes) },
                        type = original.type,
                        template = original.template,
                        children = originals.filter { it.parentId == original.id }.map { Json.of(it, originals, filters.find { f -> f.id == it.filterId }?: Filter.Impl(it.filterId, null, null, null), filters, translations, votes) }
                    )
                }
            }
        }

        @Serializable
        data class Impl(
            override val id: OriginalId,
            override val text: OriginalText,
            override val stopOnMatch: Boolean,
            override val template: Template?,
            override val filterId: FilterId,
            override val parentId: OriginalId?,
            override val projectId: ProjectId,
            override val type: Type
        ): Original

        @Serializable
        data class Update(
            override val text: OriginalText,
            override val stopOnMatch: Boolean,
            override val template: Template? = null,
            override val type: Type,
            val filter: Filter.Update = Filter.Update(null, null, null)
        ): Original {
            override val projectId: ProjectId get() = throw NotImplementedError()
            override val filterId: FilterId get() = throw NotImplementedError()
            override val parentId: OriginalId? get() = throw NotImplementedError()
            override val id: OriginalId get() = throw NotImplementedError()
        }

        @Serializable
        data class Create(
            override val text: OriginalText,
            override val stopOnMatch: Boolean,
            override val template: Template? = null,
            val children: List<Create>? = null,
            val filter: Filter.Create = Filter.Create(null, null, null),
            override val parentId: OriginalId? = null,
            override val type: Type
        ): Original {
            override val projectId: ProjectId get() = throw NotImplementedError()
            override val filterId: FilterId get() = throw NotImplementedError()
            override val id: OriginalId get() = throw NotImplementedError()
        }

        @Serializable
        data class Details(
            override val id: Original.OriginalId,
            override val text: Original.OriginalText,
            override val stopOnMatch: Boolean,
            override val template: Original.Template?,
            override val projectId: ProjectId,
            val children: List<Details>,
            val bestTranslation: Translation.Details?,
            override val parentId: Original.OriginalId?,
            override val type: Type
        ): Original {
            override val filterId: FilterId get() = throw NotImplementedError()

            companion object {
                fun of(original: Original, translations: List<Translation>, votes: List<Translation.Vote>, originals: List<Original>): Details {
                    return Details(
                        id = original.id,
                        text = original.text,
                        stopOnMatch = original.stopOnMatch,
                        template = original.template,
                        projectId = original.projectId,
                        type = original.type,
                        children = originals.filter { it.parentId == original.id }.map { of(it, translations, votes, originals) },
                        bestTranslation = translations.filter { it.originalId == original.id }.map { Translation.Details.of(it, votes) }.best(),
                        parentId = original.parentId
                    )
                }
            }
        }

        interface Translation {
            @Serializable
            @JvmInline
            value class TranslationId(val value: Long)

            val id: TranslationId

            val originalId: OriginalId

            @Serializable
            @JvmInline
            value class TranslatedText(val value: String)

            val text: TranslatedText

            val languageId: LanguageId

            val userId: UserId

            val status: Status

            @Serializable
            data class UpdateStatus(
                val status: Status
            )

            @Serializable
            data class Json(
                override val originalId: OriginalId,
                override val text: TranslatedText,
                override val languageId: LanguageId,
                override val status: Status,
                val score: Long
            ): Translation {
                override val id: TranslationId get() = throw NotImplementedError()
                override val userId: UserId get() = throw NotImplementedError()

                companion object {
                    fun of(translation: Translation, votes: List<Vote>): Json {
                        return Json(
                            originalId = translation.originalId,
                            text = translation.text,
                            languageId = translation.languageId,
                            status = translation.status,
                            score = votes.filter { it.translationId == translation.id }.sumOf { if(it.type == Vote.Type.Up) 1L else -1L }
                        )
                    }
                }
            }

            @Serializable
            data class Details(
                override val id: TranslationId,
                override val originalId: OriginalId,
                override val text: TranslatedText,
                override val languageId: LanguageId,
                override val userId: UserId,
                override val status: Status,
                val votes: List<Vote.Details>
            ): Translation {

                companion object {
                    fun of(translation: Translation, votes: List<Vote>): Details {
                        return Details(
                            id = translation.id,
                            originalId = translation.originalId,
                            text = translation.text,
                            languageId = translation.languageId,
                            userId = translation.userId,
                            status = translation.status,
                            votes = votes.filter { it.translationId == translation.id }.map(Vote.Details::of)
                        )
                    }
                }
            }

            @Serializable
            data class Impl(
                override val id: Project.Original.Translation.TranslationId,
                override val originalId: OriginalId,
                override val text: Project.Original.Translation.TranslatedText,
                override val languageId: Language.LanguageId,
                override val userId: Project.UserId,
                override val status: Status
            ): Translation

            @Serializable
            data class Create(
                override val text: TranslatedText,
            ): Translation {
                override val userId: UserId get() = throw NotImplementedError()
                override val id: TranslationId get() = throw NotImplementedError()
                override val originalId: OriginalId get() = throw NotImplementedError()
                override var status: Status = Status.Pending
                override val languageId: LanguageId get() = throw NotImplementedError()
            }

            @Serializable
            data class CreateMany(
                override val originalId: OriginalId,
                override val text: TranslatedText
            ): Translation {
                override val userId: UserId get() = throw NotImplementedError()
                override val id: TranslationId get() = throw NotImplementedError()
                override var status: Status = Status.Pending
                override val languageId: LanguageId get() = throw NotImplementedError()
            }

            interface Vote {
                val userId: UserId

                val type: Type

                val translationId: TranslationId

                @Serializable
                data class Details(
                    override val userId: UserId,
                    override val type: Type,
                    override val translationId: TranslationId
                ): Vote {
                    companion object {
                        fun of(vote: Vote): Details {
                            return Details(
                                userId = vote.userId,
                                type = vote.type,
                                translationId = vote.translationId
                            )
                        }
                    }
                }

                @Serializable
                data class Impl(
                    override val userId: Project.UserId,
                    override val translationId: TranslationId,
                    override val type: Vote.Type
                ): Vote

                @Serializable
                data class Create(
                    override val type: Type
                ): Vote {
                    override val userId: UserId get() = throw NotImplementedError()
                    override val translationId: TranslationId get() = throw NotImplementedError()
                }

                enum class Type {
                    Up, Down
                }
            }

            enum class Status {
                Accepted, Rejected, Pending, NeedsReview
            }
        }
    }
}