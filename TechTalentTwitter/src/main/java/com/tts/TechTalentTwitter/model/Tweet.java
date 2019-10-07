import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tweet_id")
	private Long id;
		
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
		
	private String message;
		
	@CreationTimestamp 
	private Date createdAt;
	
	
	@Repository
	public interface TweetRepository extends CrudRepository<Tweet, Long> {
	    List<Tweet> findAllByOrderByCreatedAtDesc();
	    List<Tweet> findAllByUserOrderByCreatedAtDesc(User user);
	    List<Tweet> findAllByUserInOrderByCreatedAtDesc(List<User> users);
	}
}