import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tts.TechTalentTwitter.service.UserService;
import com.tts.TechTalentTwitter.service.TweetService;

@Controller
public class TweetController {
    @Autowired
    private UserService userService;
	
    @Autowired
    private TweetService tweetService;
}