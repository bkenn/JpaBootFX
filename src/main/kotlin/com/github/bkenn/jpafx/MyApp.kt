package com.github.bkenn.jpafx

import com.github.bkenn.jpafx.view.JavaView
import com.github.bkenn.jpafx.view.MainView
import javafx.application.Application
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import tornadofx.*
import kotlin.reflect.KClass

@SpringBootApplication
class MyApp: App(MainView::class, Styles::class) {

    private lateinit var springContext: ConfigurableApplicationContext

    // Need to hook in spring boot context to the TornadoFX DI container
    override fun init() {
        this.springContext = SpringApplication.run(this.javaClass)
        springContext.autowireCapableBeanFactory.autowireBean(this)

        FX.dicontainer = object: DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>) = springContext.getBean(type.java)
            override fun <T : Any> getInstance(type: KClass<T>, name: String) = springContext.getBean(type.java, name)
        }
    }

    // Need to shutdown spring on shutdown
    override fun stop() {
        super.stop()
        springContext.close()
    }

    // Need for startup
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(MyApp::class.java, *args)
        }
    }
}