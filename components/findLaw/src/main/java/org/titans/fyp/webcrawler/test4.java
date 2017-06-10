package org.titans.fyp.webcrawler;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.titans.fyp.webcrawler.database.DBConnection;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class test4 {

    private static Logger log = LoggerFactory.getLogger(test4.class);
    private static DBConnection dbconnection = null;
    private static BufferedWriter bw_lt = null;
    private static FileWriter fw_lt = null;

    private static BufferedWriter bw_rt = null;
    private static FileWriter fw_rt = null;

    public static void extractNames() throws IOException {

        String text = "Therefore, if the intent of Congress can be clearly discerned from the statute's language, the judicial inquiry must end.” United States v. McAllister";

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the lemmatized version of the token
                String word = token.value();
                if (String.valueOf(word).chars().allMatch(Character::isLetter)) {
                    if (word != null) {
                        System.out.println(String.valueOf(word.toLowerCase()) + " " + token.tag());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        try {
            (new test4()).extractNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void rawTextWriteToFile(String text) throws IOException {

        // creates a StanfordCoreNLP object, with Tokenizer
        PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<CoreLabel>(new StringReader(text),
                new CoreLabelTokenFactory(), "");
        for (CoreLabel label; ptbt.hasNext(); ) {
            label = ptbt.next();
            if (String.valueOf(label).chars().allMatch(Character::isLetter)) {
                bw_rt.write(String.valueOf(label).toLowerCase() + " ");
            }
        }
    }

}
