package com.tts.TechTalentTwitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tts.TechTalentTwitter.service.UserService;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.service.TweetService;

@Controller
public class TweetController {
    @Autowired
    private UserService userService;
	
    @Autowired
    private TweetService tweetService;
    
    @GetMapping(value= {"/tweets", "/"})
    public String getFeed(Model model){
        List<Tweet> tweets = tweetService.findAll();
        model.addAttribute("tweetList", tweets);
        return "feed";
    }
    
    @GetMapping(value = "/tweets/new")
    public String getTweetForm(Model model) {
        model.addAttribute("tweet", new Tweet());
        return "newTweet";
        @PostMapping(value = "/tweets")
        public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
            User user = userService.getLoggedInUser();
            if (!bindingResult.hasErrors()) {
                tweet.setUser(user);
                tweetService.save(tweet);
                model.addAttribute("successMessage", "Tweet successfully created!");
                model.addAttribute("tweet", new Tweet());
            }
            return "newTweet";
        
    }
}
}