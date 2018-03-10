package osk.akka.sample

import akka.actor.AbstractActor
import akka.actor.Props
import akka.event.Logging



class Printer : AbstractActor() {

    data class Greeting(val message: String)

    private val log = Logging.getLogger(context.system, this)

    companion object {
        @JvmStatic
        fun props() = Props.create(Printer::class.java, { Printer() })
    }

    fun onGreeting(greeting: Greeting) {
        log.info(greeting.message)
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(Greeting::class.java, this::onGreeting)
                .build()
    }
}