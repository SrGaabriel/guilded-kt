package io.github.deck.rest

import io.github.deck.common.log.DeckLogger
import io.github.deck.common.util.MicroutilsLogger
import io.github.deck.rest.route.ChannelRoute
import io.github.deck.rest.route.GroupRoute
import io.github.deck.rest.route.ServerRoute
import io.github.deck.rest.route.WebhookRoute
import io.github.deck.rest.util.DEFAULT_HTTP_CLIENT
import io.github.deck.rest.util.RequestService
import io.ktor.client.*

public class RestClient(public val token: String) {
    public var httpClient: HttpClient = DEFAULT_HTTP_CLIENT
    public val requestService: RequestService = RequestService(httpClient, this)

    public var logger: DeckLogger = MicroutilsLogger("Rest Client Logger")

    public var logRequests: Boolean = false
    public var logResponses: Boolean = true

    public val channel: ChannelRoute = ChannelRoute(this)
    public val group: GroupRoute = GroupRoute(this)
    public val server: ServerRoute = ServerRoute(this)
    public val webhook: WebhookRoute = WebhookRoute(this)
}