package com.tts.TechTalentTwitter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tts.TechTalentTwitter.repository.TweetRepository;
import com.tts.TechTalentTwitter.model.Tag;
import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    
    public List<Tweet> findAll() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
        return formatTweets(tweets);
    }
    
    private List<Tweet> formatTweets(List<Tweet> tweets) {
        addTagLinks(tweets);
        return tweets;
    }
	
    public List<Tweet> findAllByUser(User user) {
        List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
        return tweets;
    }
	
    public List<Tweet> findAllByUsers(List<User> users){
        List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
        return tweets;
    }
	
    public List<Tweet> findAllWithTag(String tag){
        List<Tweet> tweets = tweetRepository.findByTags_PhraseOrderByCreatedAtDesc(tag);
        return formatTweets(tweets);
    }
    
    private void handleTags(Tweet tweet) {
        List<Tag> tags = new ArrayList<Tag>(); 
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(tweet.getMessage());
        while (matcher.find())
        {
        	String phrase = matcher.group().substring(1).toLowerCase();
            Tag tag = tagRepository.findByPhrase(phrase);
            if(tag == null) {
                tag = new Tag();
                tag.setPhrase(phrase);
                tagRepository.save(tag);
            }
            tags.add(tag);
        }    
        tweet.setTags(tags);
    }
    
    public void save(Tweet tweet) {
        handleTags(tweet);
        tweetRepository.save(tweet);
    }
    
    private void addTagLinks(List<Tweet> tweets) {
        Pattern pattern = Pattern.compile("#\\w+");
        for(Tweet tweet: tweets) {
            String message = tweet.getMessage();
            Matcher matcher = pattern.matcher(message);
            Set<String> tags = new HashSet<String>();
            while(matcher.find()) {
                tags.add(matcher.group());
            }
            for(String tag : tags) {
                message = message.replaceAll(tag, 
                "<a class=\"tag\" href=\"/tweets/" + tag.substring(1).toLowerCase() + "\">" + tag + "</a>");
            }
            tweet.setMessage(message);
        }
    }
    
    
}
