package online.assessment.comparators;

import java.util.Comparator;

import online.assessment.beans.Post;

public class IdComparatorAsc implements Comparator<Post> {
	
	/**
	  * return a negative integer, zero, or a positive integer as the first argument is less than, 
	  * equal to, or greater than the second. 
	  */
	@Override
	public int compare(Post post1, Post post2) {
		
		if(post1.getId() > post2.getId()){	//greater
			return 1;
		}else if(post1.getId() == post2.getId()){	//equals
			return 0;
		}else{	//less
			return -1;
		}
	}

}
