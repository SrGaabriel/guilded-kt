package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import com.deck.core.cache.CacheManager
import com.deck.core.cache.DeckCacheManager
import com.deck.core.cache.observer.CacheObserver
import com.deck.core.cache.observer.DefaultCacheObserver
import com.deck.core.delegator.DeckEntityDecoder
import com.deck.core.delegator.DeckEntityDelegator
import com.deck.core.delegator.EntityDecoder
import com.deck.core.delegator.EntityDelegator
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule
import com.deck.core.service.AuthService
import com.deck.core.service.DefaultAuthService
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.*
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData
import kotlin.properties.Delegates

/**
 * Main class of the core module, used to centralize
 * both gateway and rest modules.
 *
 * @param auth email and password
 * @param rest rest module implementation
 * @param gateway gateway module implementation
 */
public class DeckClient(
    private val auth: Authentication,
    public val rest: RestModule,
    public val gateway: GatewayModule
) : EventSupplier, WrappedEventSupplier {
    public var eventService: EventService = DefaultEventService(this)

    public var authenticationService: AuthService = DefaultAuthService(this)
    public var authenticationResults: AuthenticationResult by Delegates.notNull()

    override val eventSupplierData: EventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    public var cache: CacheManager = DeckCacheManager()

    public var entityDecoder: EntityDecoder = DeckEntityDecoder(this)
    public var entityDelegator: EntityDelegator = DeckEntityDelegator(rest, entityDecoder, cache)

    public var cacheObserver: CacheObserver = DefaultCacheObserver(this, cache, entityDecoder)

    public val selfId: GenericId by rest.restClient::selfId
    public val self: StatelessUser by lazy { BlankStatelessUser(this, selfId) }

    /**
     * Logins into Guilded with provided [auth] (email, password) and
     * opens all team gateways before starting listening to their events.
     */
    public suspend fun login() {
        authenticationResults = authenticationService.login(auth)
        gateway.openTeamGateways(*authenticationResults.self.teams.map { it.id }.toTypedArray())
        gateway.start()
        eventService.startListening()
    }

    public companion object {
        public operator fun invoke(builder: ClientBuilder.() -> Unit): DeckClient = client(builder)
    }
}