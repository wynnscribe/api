package com.wynnscribe.server

import com.wynnscribe.server.databases.Filters
import com.wynnscribe.server.databases.Languages
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects
import com.wynnscribe.server.databases.Translations
import com.wynnscribe.server.databases.Votes
import com.wynnscribe.server.initializer.*
import com.wynnscribe.server.libs.FirebaseAdmin
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    // FirebaseAdminを初期化する
    FirebaseAdmin.initialize()
    val database = Database.connect("jdbc:sqlite:data.db")
    transaction(database) {
        SchemaUtils.create(Filters, Languages, Originals, Projects, Translations, Votes)
        initializeDamages()
        initializeStats()
        initializeSkills()
        initializeCharacterInfo()
        initializeGears()
        initializeStatus()
        initializeServerGui()
        initializeSpeeds()
        initializeItemIdentifier()
        initializeBlackSmiths()
        initializeGatheringTools()
        initializeGearBox()
        initializeEmeraldPouch()
        initializeTeleportScroll()
        initializeDungeonKeys()
        initializeHorse()
        initializeMaterials()
        initializeIngredients()
        initializeCrafting()
        initializePotions()
        initializeAbilityShards()
        initializeIngredientsPouch()
        initializeContentBook()
    }
    // ktorを初期化
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureHTTP()
    configureSecurity()
    configureRouting()
}