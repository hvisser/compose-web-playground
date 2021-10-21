package nl.littlerobots.cwexp

import nl.littlerobots.cwexp.router.Router
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable("root") {
        Router {
            composable("/") {
                Text("The start of it all")
                Br()
                A("test") {
                    Text("Try navigating with a link")
                }
            }
            composable("test") {
                Text("Test!")
            }
            composable("test/:something") {
                Text("Test with ${it.data["something"]}, the url is ${it.url}")
            }
        }
    }
}
