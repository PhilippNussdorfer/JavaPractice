package bbrz.at;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AddTest {

    private Add add;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void addPositiveValues() {
        add = new Add(22, 55);
        Assertions.assertEquals(77, add.add());
    }

    @Test
    public void addNegativValues() {
        add = new Add(-22, -55);
        Assertions.assertEquals(-77, add.add());
    }

    @Test
    public void addDecimal() {
        add = new Add(11.11111111, 33.33333333);
        Assertions.assertEquals(44.44444444, add.add());
    }

    @Test
    public void addNegativDecimal() {
        add = new Add(-11.11111, -33.33333);
        Assertions.assertEquals(-44.44444, add.add());
    }

}