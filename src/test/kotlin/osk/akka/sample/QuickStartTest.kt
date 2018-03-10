package osk.akka.sample

import akka.actor.ActorRef
import akka.actor.ActorSystem
import org.junit.AfterClass
import org.junit.BeforeClass
import akka.testkit.javadsl.TestKit
import junit.framework.Assert.assertEquals
import org.junit.Test


class QuickStartTest {

    companion object {
        private lateinit var system: ActorSystem

        @JvmStatic
        @BeforeClass
        fun setup() {
            system = ActorSystem.create()
        }

        @JvmStatic
        @AfterClass
        fun teardown() {
            TestKit.shutdownActorSystem(system)
        }
    }


    @Test
    fun testGreeterActorSendingOfGreeting() {
        val testProbe = TestKit(system)
        val helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.ref))
        helloGreeter.tell(Greeter.WhoToGreet("Akka"), ActorRef.noSender())
        helloGreeter.tell(Greeter.Greet, ActorRef.noSender())
        val greeting = testProbe.expectMsgClass(Printer.Greeting::class.java)
        assertEquals("Hello, Akka", greeting.message)
    }
}