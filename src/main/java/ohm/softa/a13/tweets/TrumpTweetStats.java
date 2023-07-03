package ohm.softa.a13.tweets;

import ohm.softa.a13.model.Tweet;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrumpTweetStats {

    public static Map<String, Long> calculateSourceAppStats(Stream<Tweet> tweetStream) {
        /* TODO group the tweets by the `sourceApp` they were created with and count how many it were per `sourceApp` */
		return tweetStream
			.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.counting()));
    }

    public static Map<String, Set<Tweet>> calculateTweetsBySourceApp(Stream<Tweet> tweetStream) {
        /* TODO group the tweets by the `sourceApp`
         * collect the tweets in `Set`s for each source app */
        return tweetStream
			.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.toSet()));
    }

    public static Map<String, Integer> calculateWordCount(Stream<Tweet> tweetStream, List<String> stopWords) {
        /* Remark: implement this method at last */
        /* TODO split the tweets, lower them, trim them, remove all words that are in the `stopWords`,
         * reduce the result to a Map<String, Integer> to count how often each word were in the tweets
         * optionally you could filter for all words that were used more than 10 times */
		return tweetStream
			.map(Tweet::getText)
			.map(tweet -> tweet.split("( )+"))
			.flatMap(Arrays::stream)
			.filter(word -> !stopWords.contains(word))
			.reduce(new HashMap<>(), (HashMap<String, Integer> map, String word) -> {
				map.putIfAbsent(word, 0);
				map.put(word, map.get(word)+1);
				return map;
			}, (m1, m2) -> {
				m1.forEach((k, v) -> {
					m2.putIfAbsent(k, 0);
					m2.put(k, m2.get(k) + v);
				});
				return m2;
			});
    }
}
