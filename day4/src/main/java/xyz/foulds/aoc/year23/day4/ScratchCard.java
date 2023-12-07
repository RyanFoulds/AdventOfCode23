//  
//  Copyright (c) 2023 Resonate Group Ltd.  All Rights Reserved.
//  

package xyz.foulds.aoc.year23.day4;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ScratchCard
{
    private final int id;
    private final Set<Integer> winningNumbers;
    private final Set<Integer> playedNumbers;

    private final Set<Integer> prizeIds;
    private final long value;
    private final long matchCount;

    public ScratchCard(final String input)
    {
        final String[] parts = input.split(": ");
        id = Integer.parseInt(parts[0].substring(5).trim());
        final String[] numbers = parts[1].split(" \\| ");

        winningNumbers = Arrays.stream(numbers[0].split(" "))
                               .map(String::trim)
                               .filter(str -> !str.isEmpty())
                               .map(Integer::parseInt)
                               .collect(Collectors.toSet());

        playedNumbers = Arrays.stream(numbers[1].split(" "))
                              .map(String::trim)
                              .filter(str -> !str.isEmpty())
                              .map(Integer::parseInt)
                              .collect(Collectors.toSet());

        matchCount = playedNumbers.stream().filter(winningNumbers::contains).collect(Collectors.toSet()).size();
        value = matchCount == 0L ? 0L : Math.round(Math.pow(2, matchCount - 1));
        prizeIds = new HashSet<>();
        for (int i = 1; i <= matchCount; i++)
        {
            prizeIds.add(id + i);
        }
    }

    public int getId()
    {
        return id;
    }

    public long getValue()
    {
        return value;
    }

    public long getMatchCount()
    {
        return matchCount;
    }

    public Set<Integer> getPrizeCopyIds()
    {
        return Collections.unmodifiableSet(prizeIds);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ScratchCard that = (ScratchCard) o;
        return this.id == that.id
            && Objects.equals(this.winningNumbers, that.winningNumbers)
            && Objects.equals(this.playedNumbers, that.playedNumbers);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, winningNumbers, playedNumbers);
    }
}
