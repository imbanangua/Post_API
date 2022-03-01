package online.assessment.comparators;

import java.util.Comparator;

import online.assessment.beans.Post;

public class PopularityComparatorDesc implements Comparator<Post> {
	
	/**
	  * return a negative integer, zero, or a positive integer as the first argument is greater than, 
	  * equal to, or less than the second. 
	  */
	@Override
	public int compare(Post post1, Post post2) {
		
		if(post1.getPopularity() > post2.getPopularity()){	//greater
			return -1;
		}else if(post1.getPopularity() == post2.getPopularity()){	//equals
			return 0;
		}else{	//less
			return 1;
		}
	}

}
