package com.deck.core.entity.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.Entity
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface ForumThread: Entity {
    public val id: Int

    public val authorId: GenericId
    public val serverId: GenericId
    public val channelId: UUID

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, serverId)

    public val title: String
    public val content: String

    public val createdAt: Instant
    public val updatedAt: Instant?
}