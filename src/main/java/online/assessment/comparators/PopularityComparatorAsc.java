package online.assessment.comparators;

import java.util.Comparator;

import online.assessment.beans.Post;

public class PopularityComparatorAsc implements Comparator<Post> {
	
	/**
	  * return a negative integer, zero, or a positive integer as the first argument is less than, 
	  * equal to, or greater than the second. 
	  */
	@Override
	public int compare(Post post1, Post post2) {
		
		if(post1.getPopularity() > post2.getPopularity()){	//greater
			return 1;
		}else if(post1.getPopularity() == post2.getPopularity()){	//equals
			return 0;
		}else{	//less
			return -1;
		}
	}

}
