package xyz.foulds.aoc.year23.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
    public static void main(final String[] args) throws IOException
    {

        if (args.length != 1)
        {
            throw new IllegalArgumentException("Please provide a single file path for the puzzle input.");
        }

        final String input = String.join("\n", Files.readAllLines(Paths.get(args[0])));
        final Almanac almanac = new Almanac(input);

        // Part 1
        System.out.println(almanac.getMinLocation());

        // Part 2
        System.out.println(almanac.getMinLocationRange());
    }

}
