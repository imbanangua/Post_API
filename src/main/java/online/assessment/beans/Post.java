package online.assessment.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Post{
	
	private String author;
	private int authorId;
	private int id;
	private int likes;
	private float popularity;
	private int reads;
	private String tags[];
	
	

}
