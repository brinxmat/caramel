package no.greenall.caramel;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

@Command(name = "App", subcommands = {Validate.class})
public class App implements Runnable {

    private static final String DISPLAY_HELP_MESSAGE = "Display help message";

    @Option(names = {"-h", "--help"}, usageHelp = true, description = DISPLAY_HELP_MESSAGE)
    boolean getHelp = false;

    public static void main(String[] args) {
        CommandLine.run(new App(), args);
    }


    @Override
    public void run() {

    }
}
