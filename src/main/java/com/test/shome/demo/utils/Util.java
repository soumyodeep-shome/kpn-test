package com.test.shome.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static List<String> extractTimes(String text) {
        List<String> times = new ArrayList<>();

        // Define regular expressions for starttijd and eindtijd
        /*String starttijdRegex = "Starttijd:\\s*(\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2})";
        String eindtijdRegex = "Eindtijd:\\s*(\\w+)";*/

        String starttijdRegex = "Starttijd:\\s*([\\d\\w\\s:-]+)";
        String eindtijdRegex = "Eindtijd:\\s*([\\d\\w\\s:-]+)";



        // Create pattern objects
        Pattern startPattern = Pattern.compile(starttijdRegex);
        Pattern endPattern = Pattern.compile(eindtijdRegex);

        // Match starttijd
        Matcher startMatcher = startPattern.matcher(text);
        if (startMatcher.find()) {
            String starttijd = startMatcher.group(1);
            times.add(starttijd);
        } else {
            times.add("Starttijd not found.");
        }

        // Match eindtijd
        Matcher endMatcher = endPattern.matcher(text);
        if (endMatcher.find()) {
            String eindtijd = endMatcher.group(1);
            times.add(eindtijd);
        } else {
            times.add("Eindtijd not found.");
        }

        return times;
    }

    public static void main(String[] args) {
        String text = "Als gevolg van een storing kunt u mogelijk in enkele gebieden in de regio Amersfoort niet bij ons inloggen via het ADSL-netwerk. Indien u telefonie of televisie via internet van ons afneemt dan kunnen deze diensten door de storing verstoord zijn. Onze excuses voor dit ongemak. Wij stellen alles in het werk om de storing zo spoedig mogelijk op te lossen.<br><br><br/>Starttijd: 2011-06-22 11:00&nbsp;Eindtijd: onbekend&nbsp;";

        List<String> times = Util.extractTimes(text);
        System.out.println("Starttijd: " + times.get(0));
        System.out.println("Eindtijd: " + times.get(1));
    }
}

