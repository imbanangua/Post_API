package online.assessment.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import online.assessment.beans.Ping;
import online.assessment.beans.Post;
import online.assessment.beans.ResponseError;
import online.assessment.comparators.*;

@RestController
@RequestMapping("/api")
public class PostsController {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
		
	@RequestMapping("/ping")
	public ResponseEntity<Ping> getPing() {
		return ResponseEntity.ok(new Ping(true));
	}
	
	@GetMapping(value="/posts")
    public ResponseEntity<String> getPostInfo(@RequestParam(value = "tags", required = false) String tags, 
    		@RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy, 
    		@RequestParam(value = "direction", required = false, defaultValue = "asc") String direction) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		if(tags == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(mapper.writeValueAsString(new ResponseError("Tags parameter is required")));
		}
		
		RestTemplate restTemplate = new RestTemplate();
		String[] tagsArray = tags.split(",");
		Map<Integer, Post> postIdMap = new HashMap<>();
		Post post;
		
		for(String tag: tagsArray) {
			
			Map<String, String> params = new HashMap<>();
			params.put("tag", tag);
			ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.hatchways.io/assessment/blog/posts?tag={tag}", String.class, params);
			
			Map<String, List<Post>> postMap = mapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, List<Post>>>(){});
			List<Post> postListMap = postMap.get("posts");	
			
			for(int i = 0; i < postListMap.size(); i++) {	
				post = mapper.convertValue(postListMap.get(i), Post.class);
				
				if(postIdMap.get(post.getId()) == null) {
					postIdMap.put(post.getId(), post);
				}				
			}			
		}
				
		List<Post> postList = new ArrayList<Post>(postIdMap.values());				
		
		//sort the query
		switch(sortBy) {
			case "reads":
				if(direction.equals("desc")) {
					Collections.sort(postList, new ReadsComparatorDesc());
				}else if(direction.equals("asc")){
					Collections.sort(postList, new ReadsComparatorAsc());
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(mapper.writeValueAsString(new ResponseError("direction parameter is invalid")));
				}
				break;
			case "likes":
				if(direction.equals("desc")) {
					Collections.sort(postList, new LikesComparatorDesc());
				}else if(direction.equals("asc")){
					Collections.sort(postList, new LikesComparatorAsc());
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(mapper.writeValueAsString(new ResponseError("direction parameter is invalid")));
				}
				break;
			case "popularity":
				if(direction.equals("desc")) {
					Collections.sort(postList, new PopularityComparatorDesc());
				}else if(direction.equals("asc")){
					Collections.sort(postList, new PopularityComparatorAsc());
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(mapper.writeValueAsString(new ResponseError("direction parameter is invalid")));
				}
				break;
			case "id":
				if(direction.equals("desc")) {
					Collections.sort(postList, new IdComparatorDesc());
				}else if(direction.equals("asc")){
					Collections.sort(postList, new IdComparatorAsc());
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(mapper.writeValueAsString(new ResponseError("direction parameter is invalid")));
				}
				break;
			default:
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(mapper.writeValueAsString(new ResponseError("sortBy parameter is invalid")));
		}
		
		//convert post list to JSON
		Map<String, List<Post>> postListToJsonMap = new HashMap<String, List<Post>>();
		postListToJsonMap.put("posts", postList);
				
		return ResponseEntity.ok().body(mapper.writeValueAsString(postListToJsonMap));
    }

	
}


