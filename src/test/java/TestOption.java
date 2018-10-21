package no.greenall.caramel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOption {
    @Test
    public void testOptionExists() {
        Option.HELP.longOpt();
    }

    @Test
    public void testHelp() {
        assertEquals("help", Option.HELP.longOpt());
        assertEquals('h', Option.HELP.shortOpt());
        assertEquals("--help", Option.HELP.prefixedLongOpt());
        assertEquals("-h", Option.HELP.prefixedShortOpt());
    }
}
