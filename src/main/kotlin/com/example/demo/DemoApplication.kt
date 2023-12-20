package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import org.springframework.jdbc.core.query
import org.springframework.web.bind.annotation.PathVariable

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@RestController
class MessageController(val service: MessageService) {
    @GetMapping("/")
//    fun index(@RequestParam("name") name: String) = "Hello, $name!"
    fun index(): List<Message> = service.findMessage()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): List<Message> = service.findMessageById(id)

    @PostMapping("/")
    fun post(@RequestBody message: Message) {
        service.save(message);
    }
}

//data class Message(var id: String?, val text: String)

@Service
class MessageService(val db: MessageRepository) {
//    fun findMessage(): List<Message> = db.query("select * from messages") {
//        response, _ -> Message(response.getString("id"), response.getString("text"))
//    }
//
//    fun findMessageById(id: String): List<Message> = db.query("select * from messages where id= ?", id) {
//        response, _ -> Message(response.getString("id"), response.getString("text"))
//    }
//
//    fun save(message: Message) {
//        val id = message.id ?: UUID.randomUUID().toString();
//        db.update(
//            "insert into messages values (?, ?)",
//            id, message.text,
//        )
//    }
    fun findMessage(): List<Message> = db.findAll().toList()

    fun findMessageById(id: String): List<Message> = db.findById(id).toList()

    fun save(message: Message) {
        db.save(message)
    }

    fun <T: Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()
}

@Table("MESSAGES")
data class Message(@Id var id: String?, val text: String)

interface MessageRepository : CrudRepository<Message, String>
