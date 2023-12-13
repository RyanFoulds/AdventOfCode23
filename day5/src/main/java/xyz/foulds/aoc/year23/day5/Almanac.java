package xyz.foulds.aoc.year23.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Almanac
{
    private final Map<String, Mapping> mappings;
    private final List<Long> seeds;
    private final Collection<Range> seedRanges;
    private final Collection<Range> locationRanges = new ArrayList<>();
    private final List<Long> locations = new ArrayList<>();

    public Almanac(final String input)
    {
        final String[] chunks = input.split("\n\n");
        final String[] seedsInput = chunks[0].split(" ");
        this.seeds = Arrays.asList(seedsInput)
                           .subList(1, seedsInput.length)
                           .stream()
                           .map(Long::parseLong)
                           .collect(Collectors.toList());

        this.seedRanges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2)
        {
            final long start = seeds.get(i);
            final long seedCount = seeds.get(i + 1);
            seedRanges.add(new Range(start, start + seedCount - 1L));
        }

        this.mappings = Arrays.asList(chunks).subList(1, chunks.length)
                              .stream()
                              .map(Mapping::new)
                              .collect(Collectors.toMap(Mapping::getSource,
                                                        Function.identity()));
        calculateLocations();
        calculateLocationRanges();
    }

    private void calculateLocations()
    {
        for (final long seed : seeds)
        {
            long workingValue = seed;
            String source = "seed";
            while (!source.equals("location"))
            {
                final Mapping mapping = mappings.get(source);
                source = mapping.getTarget();
                workingValue = mapping.map(workingValue);
            }
            locations.add(workingValue);
        }
    }

    private void calculateLocationRanges()
    {
        Collection<Range> workingRanges = seedRanges;
        String source = "seed";
        while (!source.equals("location"))
        {
            final Mapping mapping = mappings.get(source);
            source = mapping.getTarget();
            workingRanges = mapping.map(workingRanges);
        }
        locationRanges.addAll(workingRanges);
    }

    public long getMinLocation()
    {
        return locations.stream().mapToLong(Long::longValue).min().orElse(Long.MIN_VALUE);
    }

    public long getMinLocationRange()
    {
        return locationRanges.stream().mapToLong(Range::getStartInclusive).min().orElse(Long.MIN_VALUE);
    }
}
