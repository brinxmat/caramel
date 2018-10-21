package no.greenall.caramel;

enum Option {

    HELP('h', "help"), INPUT('i', "input"), SHACL('s', "shacl"), FORMAT('r', "report-format");

    private static final String LONG_PREFIX = "--";
    private static final String SHORT_PREFIX = "-";
    private char shortOpt;
    private String longOpt;

    Option(char shortOpt, String longOpt) {
        this.shortOpt = shortOpt;
        this.longOpt = longOpt;
    }


    public char shortOpt() {
        return shortOpt;
    }

    public String longOpt() {
        return longOpt;
    }

    public String prefixedLongOpt() {
        return LONG_PREFIX + this.longOpt;
    }

    public String prefixedShortOpt() {
        return SHORT_PREFIX + String.valueOf(this.shortOpt);
    }

    public String[] getPrefixed() {
        return new String[]{this.prefixedShortOpt(), this.prefixedLongOpt()};
    }
}
