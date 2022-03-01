package online.assessment;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class PostsRouteTest {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void tagsHealthShouldReturn200WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void tagsHealthHistoryTechShouldReturn200WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health,history,tech", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void tagsHealthHistoryTechSortByLikesShouldReturn200WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health,history,tech&sortBy=likes", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void tagsHealthHistoryTechDirectionDescShouldReturn200WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health,history,tech&direction=desc", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void tagsHealthHistoryTechSortByLikesDirectionDescShouldReturn200WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health,history,tech&sortBy=likes&direction=desc", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void missingTagsShouldReturn400WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void invalidSortByTagsHealthHistoryTechShouldReturn400WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health,history,tech&sortBy=like", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void invalidDirectionTagsHealthHistoryTechShouldReturn400WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.port + "/api/posts?tags=health,history,tech&direction=like", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
	    @SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
	        "http://localhost:" + this.mgt + "/actuator", Map.class);
	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
}
