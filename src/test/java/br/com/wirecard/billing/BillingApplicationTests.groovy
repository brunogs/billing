package br.com.wirecard.billing

import br.com.wirecard.billing.config.IntegrationTestMockingConfig
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Import([IntegrationTestMockingConfig])
@ActiveProfiles(value = ['test'])
@ContextConfiguration(loader = SpringBootContextLoader, classes = [BillingApplication])
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BillingApplicationTests extends Specification {

	void contextLoads() {
	}

}
