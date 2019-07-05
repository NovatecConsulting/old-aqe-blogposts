package util

import org.junit.jupiter.api.Tag

/**
 * Custom annotation for filtering integration tests on the class level.
 */

@Target(AnnotationTarget.CLASS)
@Retention
@Tag("integration-test")
annotation class IntegrationTest