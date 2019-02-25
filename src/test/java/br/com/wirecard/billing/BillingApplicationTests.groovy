package br.com.wirecard.billing

import org.junit.Test
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles(value = ['test'])
@ContextConfiguration(loader = SpringBootContextLoader, classes = [BillingApplication])
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BillingApplicationTests {

	@Test
	void contextLoads() {
	}

}
