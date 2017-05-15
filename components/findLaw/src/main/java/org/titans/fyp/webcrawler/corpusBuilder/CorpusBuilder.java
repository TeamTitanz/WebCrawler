package org.titans.fyp.webcrawler.corpusBuilder;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.titans.fyp.webcrawler.database.DBConnection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class CorpusBuilder {

    private static Logger log = LoggerFactory.getLogger(CorpusBuilder.class);
    private static DBConnection dbconnection = null;
    static BufferedWriter bw = null;
    static FileWriter fw = null;
    static String text = null;

    public void run(String txtFileName) {

        try {
            fw = new FileWriter(System.getProperty("user.dir") + File.separator + txtFileName + ".txt");
            bw = new BufferedWriter(fw);
            dbconnection = DBConnection.getDBConnection();

            getCasesContent();
            getCasesSummary();
            getFootNoteContent();

            if (bw != null)
                bw.close();

            if (fw != null)
                fw.close();

        } catch (ClassNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getCasesContent() throws IOException {
        ResultSet rs = null;
        String str = "SELECT content FROM cases";
        try {
            rs = DBAccess.getData(dbconnection.getConnectionToDB(), str);
            while (rs.next()) {
                try {
                    String content = rs.getString("content");
                    LemmatizeAndWriteToFile(content);
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public static String getCasesSummary() throws IOException {
        ResultSet rs = null;
        String str = "SELECT summary FROM cases";
        try {
            rs = DBAccess.getData(dbconnection.getConnectionToDB(), str);
            while (rs.next()) {

                try {
                    String content = rs.getString("summary");
                    LemmatizeAndWriteToFile(content);
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public static String getFootNoteContent() throws IOException {
        ResultSet rs = null;
        String str = "SELECT content FROM foot_notes";
        try {
            rs = DBAccess.getData(dbconnection.getConnectionToDB(), str);
            while (rs.next()) {
                rs.next();
                try {
                    String content = rs.getString("content");
                    LemmatizeAndWriteToFile(content);
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public static void LemmatizeAndWriteToFile(String text) throws IOException {

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
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
                String word = token.get(CoreAnnotations.LemmaAnnotation.class);
                if (String.valueOf(word).chars().allMatch(Character::isLetter)) {
                    bw.write(String.valueOf(word) + " ");
                }
            }
        }
    }

}
