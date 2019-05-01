package ru.nchernetsov.KnuthMorrisPratt;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.nchernetsov.KnuthMorrisPratt.KnuthMorrisPratt.KMP;
import static ru.nchernetsov.KnuthMorrisPratt.KnuthMorrisPratt.computePrefix;

public class KnuthMorrisPrattTest {

    @Test
    public void computePrefixTest1() {
        String pattern = "abababac";
        int[] table = computePrefix(pattern);
        assertThat(table).hasSize(8);
        assertThat(table).containsExactly(0, 0, 1, 2, 3, 4, 5, 0);
    }

    @Test
    public void KMPTest1() {
        String text = "AABAACAADAABAABA";
        String pattern = "AABA";
        List<Integer> matches = KMP(text, pattern);
        assertThat(matches).hasSize(3);
        assertThat(matches).containsExactly(0, 9, 12);
    }
}
