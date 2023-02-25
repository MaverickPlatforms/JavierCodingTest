package com.global.commtech.test.anagramfinder;

import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.groupingBy;


@Component
@RequiredArgsConstructor
public class AnagramCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(final String... args) throws Exception {

    log.debug("Starting the process for anagram");

    Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

    Path path = Paths.get(args[0]);

    try (Stream<String> lines = Files.lines(path)) {
          lines
            .collect(groupingBy(String::length))
            .values()
              .forEach(listWordSameSize -> printAnagramList(listWordSameSize));
      }
    }

    private static void printAnagramList(List<String> listWordBySize) {
        for(int i=0; i<listWordBySize.size(); i++) {

            var firstWord = listWordBySize.get(i);
            var anagramFound = false;

            for(int j=0; j<listWordBySize.size(); j++) {
            var secondWord = listWordBySize.get(j);

            if (isAnagram(firstWord, secondWord)) {
                if (anagramFound == true) {
                    System.out.print(",");
                }
                    System.out.print(secondWord);
                    anagramFound = true;
                }
            }
            System.out.println();
        }
    }

    private static boolean isAnagram(String word1, String word2) {
       word1 = word1.replaceAll("\\s", "").toLowerCase();
       word2 = word2.replaceAll("\\s", "").toLowerCase();
        
       // Length of two strings must match!
       if(word1.length() != word2.length()) {
           return false;
       }
        
       for(int i=0;i<word1.length();i++) {
           // Is there a letter in word2 corresponding to the word1 letter?
           int pos = word2.indexOf( word1.substring(i, i+1));
           if(pos>=0) {
               // Remove the letter!
               word2 = word2.substring(0, pos) + word2.substring(pos+1);
           }else {
               // Letter not found! Hence not an anagram
               return false;
           }
       }
        
       // Finally if word2 is empty, it is an anagram of word1
       return word2.length()==0 ? true : false;
    }

}
