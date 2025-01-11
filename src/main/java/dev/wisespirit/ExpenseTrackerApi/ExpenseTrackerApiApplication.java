package dev.wisespirit.ExpenseTrackerApi;

import dev.wisespirit.ExpenseTrackerApi.notion.NotionConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author wisespirit
 *
 */

@SpringBootApplication
@EnableConfigurationProperties(NotionConfigProperties.class)
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}

}
