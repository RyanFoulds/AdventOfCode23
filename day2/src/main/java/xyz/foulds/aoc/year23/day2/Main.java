package xyz.foulds.aoc.year23.day2;

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

        final List<Game> games = Files.readAllLines(Paths.get(args[0]))
                                      .stream()
                                      .map(String::trim)
                                      .map(Game::new)
                                      .collect(Collectors.toList());

        System.out.println("Part 1: "
                           + games.stream()
                                  .filter(game -> game.isPossible(12, 13, 14))
                                  .mapToInt(Game::getId)
                                  .sum());

        System.out.println("Part 2: " + games.stream().mapToInt(Game::getPower).sum());
    }
}
