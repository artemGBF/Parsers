package program;

import javafx.util.Pair;
import model.Product;
import model.ProductList;
import org.apache.commons.cli.*;
import parser.Parser;

import java.io.*;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ProductList productList = new ProductList();
        Scanner scanner = new Scanner(System.in);
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = Parser.start(args).getKey();
            Options options = Parser.start(args).getValue();
            while (true) {
                if (commandLine.hasOption("add")) {
                    String str = commandLine.getOptionValue("add");
                    String[] d = str.split(";");
                    for (int i = 0; i < d.length; i++) {
                        productList.add(new Product(d[i].split("-")[0], d[i].split("-")[1],
                                Double.parseDouble(d[i].split("-")[2]), d[i].split("-")[3],
                                Integer.parseInt(d[i].split("-")[4]),
                                Integer.parseInt(d[i].split("-")[5])));
                    }
                }
                if (commandLine.hasOption("saveAll")) {
                    productList.toXMLAll(commandLine.getOptionValue("saveAll"));
                }
                if (commandLine.hasOption("saveApp")) {
                    productList.toXMLApp(commandLine.getOptionValue("saveApp"));
                }
                if (commandLine.hasOption("load")) {
                    try {
                        int i = productList.fromXML(commandLine.getOptionValue("load"));
                    } catch (com.thoughtworks.xstream.io.StreamException e) {
                        System.out.println("file not found");
                    }
                    System.out.println(productList.toString());
                }
                if (commandLine.hasOption("exit"))
                    break;
                commandLine = commandLineParser.parse(options, scanner.nextLine().split(" "));
            }

        } catch (ParseException e) {
            System.out.println("Usage -h");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
