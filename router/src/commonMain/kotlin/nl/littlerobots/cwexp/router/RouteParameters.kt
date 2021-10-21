package nl.littlerobots.cwexp.router

data class RouteParameters(
    val url: String,
    val data: Map<String, String>,
    val params: Map<String, String?>
)