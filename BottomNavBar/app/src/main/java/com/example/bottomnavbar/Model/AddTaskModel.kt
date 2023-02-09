package com.example.bottomnavbar.Model

data class AddTaskModel(
    var task: String? = null,
    var taskOwner: String? = null,
    var description: String? = null,
    val creationDate: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var taskId: String? = null,
    var taskStatus: String? = "ongoing",
    var group: String? = null
)

