package nl.littlerobots.cwexp.router.navigo

@JsModule("navigo")
@JsNonModule
external class Navigo(root: String, options: dynamic = definedExternally) {
    fun on(options: dynamic): Navigo
    fun navigate(to: String, options: dynamic = definedExternally)
    fun resolve()
    fun destroy()
}

external interface Match {
    var url: String
    var data: dynamic
    var params: dynamic
}