package io.github.deck.core.event.message

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Message
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayChatMessageUpdatedEvent
import java.util.*

/**
 * Called when a [Message]'s content is edited, be it on a private channel (DM)
 * or in a server channel
 */
public data class MessageUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val message: Message
) : DeckEvent {
    val authorId: GenericId get() = message.authorId
    val channelId: UUID get() = message.channelId
    val serverId: GenericId? get() = message.serverId

    val channel: StatelessMessageChannel get() = message.channel
    val author: StatelessUser get() = message.author
    val server: StatelessServer? get() = message.server
}

internal val EventService.messageUpdateEvent: EventMapper<GatewayChatMessageUpdatedEvent, MessageUpdateEvent> get() = mapper { client, event ->
    val message = DeckMessage.from(client, event.message)
    MessageUpdateEvent(
        client = client,
        barebones = event,
        message = message
    )
}