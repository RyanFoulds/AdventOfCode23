package xyz.foulds.aoc.year23.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(final String[] args) throws IOException
    {

        if (args.length != 1)
        {
            throw new IllegalArgumentException("Please provide a single file path for the puzzle input.");
        }

        final List<String> input = Files.readAllLines(Paths.get(args[0]));

        // Part 1
        final String[] times = input.get(0).split(" +");
        final String[] records = input.get(1).split(" +");
        final List<Race> races = new ArrayList<>(times.length - 1);

        for (int i = 1; i < times.length; i++)
        {
            races.add(new Race(Long.parseLong(times[i]), Long.parseLong(records[i])));
        }

        System.out.println(races.stream()
                                .mapToLong(Race::getNumberOfWaysToWin)
                                .reduce(1, (x, y) -> x * y));

        // Part 2
        final long time = Long.parseLong(input.get(0).split(":")[1].replaceAll(" ", ""));
        final long record = Long.parseLong(input.get(1).split(":")[1].replaceAll(" ", ""));
        final Race race = new Race(time, record);
        System.out.println(race.getNumberOfWaysToWin());

    }

}
