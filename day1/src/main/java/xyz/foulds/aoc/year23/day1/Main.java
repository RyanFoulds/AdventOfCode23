package xyz.foulds.aoc.year23.day1;

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

        final List<Line> lines = Files.readAllLines(Paths.get(args[0]))
                                      .stream()
                                      .map(String::trim)
                                      .map(Line::new)
                                      .collect(Collectors.toList());

        try
        {
            System.out.println("Part 1: " +lines.stream().mapToInt(Line::getDigitOnlyValue).sum());
        }
        catch (IllegalStateException illegalStateException)
        {
            System.out.println(illegalStateException.getMessage());
        }

        System.out.println("Part 2: " + lines.stream().mapToInt(Line::getSpelledValue).sum());
    }
}
