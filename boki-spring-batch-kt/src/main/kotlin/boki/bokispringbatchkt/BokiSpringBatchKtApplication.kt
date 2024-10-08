package boki.bokispringbatchkt

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// @EnableBatchProcessing
@SpringBootApplication
class BokiSpringBatchKtApplication

fun main(args: Array<String>) {
    runApplication<BokiSpringBatchKtApplication>(*args)
}
