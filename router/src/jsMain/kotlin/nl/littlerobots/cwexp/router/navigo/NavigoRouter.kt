package nl.littlerobots.cwexp.router.navigo

import androidx.compose.runtime.NoLiveLiterals
import nl.littlerobots.cwexp.router.RouteParameters

class NavigoRouter(
    private val navigo: Navigo
) {
    fun navigate(to: String) {
        navigo.navigate(to)
    }

    @NoLiveLiterals
    fun addRoute(path: String, name: String? = null, onRouteChanged: (RouteParameters) -> Unit) {
        val params: dynamic = object {}
        val routeParams: dynamic = object {}
        name?.let { routeParams["as"] = it }
        routeParams["uses"] = { match: Match ->
            onRouteChanged(match.toRouteParameters())
        }

        params[path] = routeParams
        navigo.on(params)
    }

    fun resolve() {
        navigo.resolve()
    }

    fun reset() {
        navigo.destroy()
    }
}

@Suppress("UNCHECKED_CAST")
private fun Match.toRouteParameters(): RouteParameters {
    val dataMap = mapOf(data).filterNot { it.value == null } as Map<String, String>
    return RouteParameters(url, dataMap, mapOf(params))
}

private fun entriesOf(jsObject: dynamic): List<Pair<String, String?>> {
    return when (jsObject) {
        undefined,
        null -> emptyList()
        else -> (js("Object.entries") as (dynamic) -> Array<Array<Any?>>)
            .invoke(jsObject)
            .map { entry -> entry[0] as String to entry[1].toString() }
    }
}

private fun mapOf(jsObject: dynamic): Map<String, String?> =
    entriesOf(jsObject).toMap()