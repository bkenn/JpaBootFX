package com.github.bkenn.jpafx.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.ResourcePatternUtils
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import java.util.*
import javax.sql.DataSource

@Configuration
class HibernateConfiguration(val rl: ResourceLoader) {

    init {
        println("Init")
    }

    @Bean
    fun sessionFactory(dataSource: DataSource): LocalSessionFactoryBean = LocalSessionFactoryBean().apply {
        setDataSource(dataSource)
        setPackagesToScan("com.github.bkenn.jpafx")
        //setMappingLocations(*ResourcePatternUtils.getResourcePatternResolver(rl).getResources("classpath:/hibernate/*.hbm.xml"))
        setConfigLocation(resourceLoader.getResource("classpath:hibernate.cfg.xml"))
       // setMappingDirectoryLocations(rl.getResource("classpath:/hibernate/"))
        hibernateProperties = hibernateProperties()
    }

    private fun hibernateProperties(): Properties = Properties().apply {
        put("hibernate.mappingLocations", "classpath:/hibernate/*.hbm.xml")
        put("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
    }

}