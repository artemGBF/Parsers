package parser;

import javafx.util.Pair;
import org.apache.commons.cli.*;


public class Parser {
    public static Pair<CommandLine, Options> start(String[] args) throws ParseException {
        Option option1 = new Option("saveApp", true,"save appropriate goods to new file");
        option1.setArgs(1);
        option1.setRequired(false);

        Option option2 = new Option("saveAll", true,"save all goods to new file");
        option2.setArgs(1);
        option2.setRequired(false);

        Option option3 = new Option("load",true,"load data from file");
        option3.setArgs(1);
        option3.setRequired(false);

        Option option5 = new Option("add", true, "add to existing ProductList");
        option5.setRequired(false);
        option5.setArgs(1);

        Option option6 = new Option("exit",false,"exit program");
        option6.setRequired(false);

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(option1);
        optionGroup.addOption(option2);
        optionGroup.addOption(option3);
        optionGroup.addOption(option6);

        Options options = new Options();
        options.addOptionGroup(optionGroup);
        options.addOption(option5);

        CommandLineParser commandLineParser = new PosixParser();
        CommandLine commandLine = commandLineParser.parse(options,args);

        return new Pair<>(commandLine,options);
    }
}
