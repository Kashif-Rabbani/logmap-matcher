package uk.ac.ox.krr.logmap2.lexicon;


import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.dictionary.Dictionary;
import uk.ac.ox.krr.logmap2.io.LogOutput;

import java.util.HashSet;
import java.util.Set;

/**
 * @author https://github.com/Kashif-Rabbani
 */
public class WordNetEnrichment {

    private Dictionary dictionary;
    private Set<String> hyponymsList = new HashSet<>();

    public WordNetEnrichment() throws JWNLException {
        this.dictionary = Dictionary.getDefaultResourceInstance();
    }

    // hyponym is a word of more specific meaning than a general or superordinate term applicable to it e.g. spoon is a hyponym of cutlery.
    public Set<String> findHyponmys(String word) {
        try {
            if (dictionary.lookupIndexWord(POS.NOUN, word) != null) {
                demonstrateTreeOperation(dictionary.lookupIndexWord(POS.NOUN, word));
            } else if (dictionary.lookupIndexWord(POS.VERB, word) != null) {
                demonstrateTreeOperation(dictionary.lookupIndexWord(POS.VERB, word));
            } else if (dictionary.lookupIndexWord(POS.ADJECTIVE, word) != null) {
                demonstrateTreeOperation(dictionary.lookupIndexWord(POS.ADJECTIVE, word));
            } else if (dictionary.lookupIndexWord(POS.ADVERB, word) != null) {
                demonstrateTreeOperation(dictionary.lookupIndexWord(POS.ADVERB, word));
            }
        } catch (JWNLException e) {
            LogOutput.printError(e.getMessage());
            e.printStackTrace();
        }
        return hyponymsList;
    }

    private void demonstrateTreeOperation(IndexWord word) throws JWNLException {
        // Get all the hyponyms (children) of the first sense of <var>word</var>
        PointerTargetTree hyponyms = PointerUtils.getHyponymTree(word.getSenses().get(0));

        for (Word w : hyponyms.getRootNode().getSynset().getWords()) {
            hyponymsList.add(w.getLemma());
        }
    }
}


