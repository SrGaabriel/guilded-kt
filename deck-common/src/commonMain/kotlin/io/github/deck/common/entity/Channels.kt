package io.github.deck.common.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
public data class RawServerChannel(
    public val id: UniqueId,
    public val type: RawServerChannelType,
    public val name: String,
    public val topic: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val serverId: GenericId,
    public val parentId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    public val categoryId: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent,
    public val groupId: GenericId,
    public val isPublic: Boolean = false,
    public val archivedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val archivedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
)

@JvmInline
@Serializable
public value class RawChannelId(public val id: UniqueId)

@Serializable
public enum class RawServerChannelType {
    @SerialName("announcements")
    ANNOUNCEMENTS,
    @SerialName("chat")
    CHAT,
    @SerialName("calendar")
    CALENDAR,
    @SerialName("forums")
    FORUMS,
    @SerialName("media")
    MEDIA,
    @SerialName("docs")
    DOCS,
    @SerialName("voice")
    VOICE,
    @SerialName("list")
    LIST,
    @SerialName("scheduling")
    SCHEDULING,
    @SerialName("stream")
    STREAM
}

@Serializable
public data class RawDocumentation(
    public val id: IntGenericId,
    public val serverId: GenericId,
    public val channelId: UniqueId,
    public val title: String,
    public val content: String,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class RawListItem(
    public val id: UniqueId,
    public val serverId: GenericId,
    public val channelId: UniqueId,
    public val message: String,
    public val note: OptionalProperty<RawListItemNote> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public data class RawListItemNote(
    public val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawListItemNoteSummary(
    public val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawForumTopic(
    val id: IntGenericId,
    val serverId: GenericId,
    val channelId: UniqueId,
    val title: OptionalProperty<String> = OptionalProperty.NotPresent,
    val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdAt: Instant,
    val createdBy: GenericId,
    val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public data class RawCalendarEvent(
    val id: IntGenericId,
    val serverId: GenericId,
    val channelId: UniqueId,
    val name: String,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val location: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val color: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val startsAt: Instant,
    val duration: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val isPrivate: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent,
    val createdAt: Instant,
    val createdBy: GenericId,
    val cancellation: OptionalProperty<RawCalendarEventCancellation> = OptionalProperty.NotPresent
)

@Serializable
public data class RawCalendarEventCancellation(
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdBy: OptionalProperty<String> = OptionalProperty.NotPresent,
)