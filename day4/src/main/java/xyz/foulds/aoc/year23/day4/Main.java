package xyz.foulds.aoc.year23.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main
{
    public static void main(final String[] args) throws IOException
    {

        if (args.length != 1)
        {
            throw new IllegalArgumentException("Please provide a single file path for the puzzle input.");
        }

        final List<ScratchCard> input = Files.readAllLines(Paths.get(args[0]))
                                             .stream()
                                             .map(String::trim)
                                             .map(ScratchCard::new)
                                             .collect(Collectors.toList());

        // Part 1
        System.out.println(input.stream().mapToLong(ScratchCard::getValue).sum());

        // Part 2
        System.out.println(totalCardCount(input));
        
    }

    private static long totalCardCount(final Collection<ScratchCard> originalCards)
    {
        final Map<Integer, ScratchCard> cardCache = originalCards.stream()
                                                                 .collect(Collectors.toMap(ScratchCard::getId,
                                                                                           Function.identity()));
        final Map<ScratchCard, Long> prizeCountCache = new HashMap<>(originalCards.size());
        long runningTotal = 0L;

        // Loop backwards over the input, so we can build up the cache from leaves to trunk.
        for (int i = originalCards.size(); i > 0; i--)
        {
            final ScratchCard originalCard = cardCache.get(i);
            runningTotal += cardCount(originalCard, cardCache, prizeCountCache);
        }
        return runningTotal;
    }

    private static long cardCount(final ScratchCard start, final Map<Integer, ScratchCard> cardCache, final Map<ScratchCard, Long> prizeCountCache)
    {
        final Queue<ScratchCard> workingCards = new ArrayDeque<>();
        workingCards.add(start);
        long totalCards = 1L;

        while (!workingCards.isEmpty())
        {
            final ScratchCard workingCard = workingCards.poll();
            if (prizeCountCache.containsKey(workingCard))
            {
                totalCards += prizeCountCache.get(workingCard) - 1;
            }
            else
            {
                final Set<ScratchCard> wonCopies = workingCard.getPrizeCopyIds().stream()
                                                              .map(cardCache::get)
                                                              .filter(Objects::nonNull)
                                                              .collect(Collectors.toSet());
                workingCards.addAll(wonCopies);
                totalCards += workingCard.getMatchCount();
            }

        }

        prizeCountCache.put(start, totalCards);
        return totalCards;
    }
}
