package xyz.foulds.aoc.year23.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{
    public static void main(final String[] args) throws IOException
    {

        if (args.length != 1)
        {
            throw new IllegalArgumentException("Please provide a single file path for the puzzle input.");
        }

        final String[] input = Files.readAllLines(Paths.get(args[0]))
                                    .stream()
                                    .map(String::trim)
                                    .toArray(String[]::new);

        final Schematic schematic = new Schematic(input);

        System.out.println(schematic.getSumOfPartNumbers());
        System.out.println(schematic.getSumOfGearRatios());
    }
}
