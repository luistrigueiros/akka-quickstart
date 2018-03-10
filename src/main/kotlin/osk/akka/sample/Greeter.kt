package osk.akka.sample

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props

class Greeter(private var message: String = "",
              private val printerActor: ActorRef) : AbstractActor() {
    companion object {
        @JvmStatic
        fun props(message: String, printerActor: ActorRef): Props {
            return Props.create(Greeter::class.java, { Greeter(message, printerActor) })
        }
    }

    data class WhoToGreet(val who: String)
    object Greet

    fun onGreet(greet: Greet) {
        Printer.Greeting(message).let {
            this.printerActor.tell(it, self)
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(WhoToGreet::class.java, { this.message = "$message, ${it.who}" })
                .match(Greet::class.java, this::onGreet)
                .build()
    }
}