package xyz.foulds.aoc.year23.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game
{
    private final int id;
    private final List<Integer> red;
    private final List<Integer> blue;
    private final List<Integer> green;

    public Game(final String input)
    {
        red = new ArrayList<>();
        blue = new ArrayList<>();
        green = new ArrayList<>();

        final String[] parts = input.split(": ");
        id = Integer.parseInt(parts[0].substring(5));
        Arrays.stream(parts[1].split("; "))
              .map(Round::new)
              .forEach(round ->
                  {
                      red.add(round.getRed());
                      blue.add(round.getBlue());
                      green.add(round.getGreen());
                  });
    }

    public boolean isPossible(final int redLimit, final int greenLimit, final int blueLimit)
    {
        return red.stream().mapToInt(Integer::intValue).max().orElse(0) <= redLimit
                && green.stream().mapToInt(Integer::intValue).max().orElse(0) <= greenLimit
                && blue.stream().mapToInt(Integer::intValue).max().orElse(0) <= blueLimit;
    }

    public int getPower()
    {
        final int minRed = red.stream().mapToInt(Integer::intValue).max().orElse(0);
        final int minGreen = green.stream().mapToInt(Integer::intValue).max().orElse(0);
        final int minBlue = blue.stream().mapToInt(Integer::intValue).max().orElse(0);

        return minRed * minGreen * minBlue;
    }

    public int getId()
    {
        return id;
    }
}
