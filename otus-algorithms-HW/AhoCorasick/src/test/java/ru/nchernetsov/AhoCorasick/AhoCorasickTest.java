package ru.nchernetsov.AhoCorasick;

import org.junit.Ignore;
import org.junit.Test;

import static ru.nchernetsov.AhoCorasick.AhoCorasick.ahoCorasickAlg;

public class AhoCorasickTest {

    @Test
    @Ignore
    public void ahoCorasickAlgTest1() {
        String[] words = {"he", "she", "his", "hers"};
        ahoCorasickAlg("uphers", words);
    }
}
