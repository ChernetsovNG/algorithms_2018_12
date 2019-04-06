package ru.nchernetsov.BoyerMoore;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.BoyerMoore.BoyerMoore.readTsvFileFromResources;

public class BoyerMooreTest {

    @Test
    public void readTsvFileTest() {
        List<TestCase> testCases = readTsvFileFromResources("string_matching_test_cases.tsv");
        assertThat(testCases).hasSize(4776);
    }
}
