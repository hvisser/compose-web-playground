package nl.littlerobots.cwexp

import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable("root") {
        Text("It works!")
    }
}
