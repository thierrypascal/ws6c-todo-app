package fhnw.ws6c.taskplanner.data

import androidx.compose.ui.graphics.Color

class Module(
    var title: String,
    var color: Color,
) {
    //empty constructor
    constructor() : this(
        title = "",
        color = Color.LightGray,
    )
}