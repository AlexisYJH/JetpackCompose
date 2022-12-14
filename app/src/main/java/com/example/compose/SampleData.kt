package com.example.compose

/**
 * @author AlexisYin
 */

data class Message(val author: String, val body: String)

object SampleData {
    val conversationSample = listOf(
        Message("Colleague", "Test..."),
        Message(
            "Colleague",
            "List of Android versions:\n" +
                    "Android Kitkat(API 19)\n" +
                    "Android Lollipop(API 21)\n" +
                    "Android Marshmallow(API 23)\n" +
                    "Android Nougat(API 24)\n" +
                    "Android Oreo(API 26)\n" +
                    "Android Pie(API 28)\n" +
                    "Android 10(API 29)\n" +
                    "Android 11(API 30)\n" +
                    "Android 12(API 31)\n"
        ),
        Message(
            "Colleague",
            "Writing Kotlin for UI seems so natural"
        ),
        Message(
            "Colleague",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Colleague",
            "Android Studio Arctic Fox tooling for Compose"
        )
    )
}