package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Documentation
import com.deck.core.entity.impl.DeckDocumentation
import com.deck.core.stateless.StatelessEntity
import com.deck.rest.builder.CreateDocumentationRequestBuilder
import java.util.*

public interface StatelessDocumentationChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public suspend fun createDocumentation(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.strategize(client, client.rest.channel.createDocumentation(id, builder))

    public suspend fun getDocumentation(documentationId: IntGenericId): Documentation =
        DeckDocumentation.strategize(client, client.rest.channel.getDocumentation(id, documentationId))

    public suspend fun getDocumentations(): List<Documentation> =
        client.rest.channel.getDocumentations(id).map { DeckDocumentation.strategize(client, it) }
}