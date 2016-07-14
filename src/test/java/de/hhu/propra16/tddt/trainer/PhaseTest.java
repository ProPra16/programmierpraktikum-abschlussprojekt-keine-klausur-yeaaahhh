package de.hhu.propra16.tddt.trainer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhaseTest {
    private Phase[] phases;
    private Phase[] expectedNext;
    private Phase[] expectedPrev;

    @Before
    public void setUp() {
        phases = new Phase[]{Phase.RED, Phase.GREEN, Phase.BLACK};
        expectedNext = new Phase[]{Phase.GREEN, Phase.BLACK, Phase.RED};
        expectedPrev = new Phase[]{Phase.BLACK, Phase.RED, Phase.GREEN};
    }

    @Test
    public void getNext() throws Exception {
        for (int i = 0; i < phases.length; i++) {
            assertEquals(expectedNext[i], phases[i].next());
        }
    }

    @Test
    public void getPrev() throws Exception {
        for (int i = 0; i < phases.length; i++) {
            assertEquals(expectedPrev[i], phases[i].previous());
        }
    }
}
