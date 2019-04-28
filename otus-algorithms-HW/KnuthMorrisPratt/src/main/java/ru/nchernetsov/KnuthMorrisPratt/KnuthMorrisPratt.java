package ru.nchernetsov.KnuthMorrisPratt;

public class KnuthMorrisPratt {

    public static StringStateMachine knuthMorrisPrattStateMachine(char[] alphabet, String pattern) {
        return new StringStateMachine(alphabet, pattern);
    }
}
