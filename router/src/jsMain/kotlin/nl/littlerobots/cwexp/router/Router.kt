package nl.littlerobots.cwexp.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import nl.littlerobots.cwexp.router.navigo.Navigo
import nl.littlerobots.cwexp.router.navigo.NavigoRouter

typealias ComposableRouteContent = @Composable (RouteParameters) -> Unit

private data class RouterState(val parameters: RouteParameters, val content: ComposableRouteContent)

interface RouteBuilder {
    fun composable(path: String, name: String? = null, destination: ComposableRouteContent)
}

@Composable
@NoLiveLiterals
fun rememberRouter(root: String = "/") = remember {
    val options: dynamic = object {}
    options["linksSelector"] = "a"
    NavigoRouter(Navigo(root, options))
}

@Composable
fun Router(router: NavigoRouter = rememberRouter(), routes: RouteBuilder.() -> Unit) {
    val routeState = remember(routes) {
        mutableStateOf<RouterState?>(null)
    }

    remember(router, routes) {
        router.reset()
        routes(NavigoRouteBuilder(router, routeState))
        router.resolve()
    }

    routeState.value?.let {
        it.content(it.parameters)
    }
}

private class NavigoRouteBuilder(
    private val router: NavigoRouter,
    private val state: MutableState<RouterState?>
) : RouteBuilder {
    override fun composable(path: String, name: String?, destination: ComposableRouteContent) {
        router.addRoute(path, name) {
            state.value = RouterState(it, destination)
        }
    }
}