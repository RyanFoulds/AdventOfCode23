package xyz.foulds.aoc.year23.day5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Translation
{
    private final Range sourceRange;
    private final long diff;

    public Translation(final String input)
    {
        final String[] numbers = input.split(" ");
        final long destinationStart = Long.parseLong(numbers[0]);
        final long sourceStart = Long.parseLong(numbers[1]);
        final long sourceEnd = sourceStart + Long.parseLong(numbers[2]) - 1L;
        this.sourceRange = new Range(sourceStart, sourceEnd);
        this.diff = destinationStart - sourceRange.getStartInclusive();
    }

    public boolean appliesTo(final long input)
    {
        return input >= sourceRange.getStartInclusive() && input <= sourceRange.getEndInclusive();
    }

    public Map<Boolean, Collection<Range>> getChildRanges(final Collection<Range> sourceRanges)
    {
        // This is disgusting, but it does "getChildRanges(final Range input)" on each supplied range
        // and flatly maps the result, combining the contents of the Collections in the map values based on the key.
        return sourceRanges.stream()
                           .map(this::getChildRanges)
                           .map(Map::entrySet)
                           .flatMap(Collection::stream)
                           .collect(Collectors.groupingBy(Map.Entry::getKey,
                                   Collectors.mapping(Map.Entry::getValue,
                                           Collector.of(ArrayList::new,
                                                   Collection::addAll,
                                                   (list1, list2) -> {
                                                                list1.addAll(list2);
                                                                return list1;
                                                              }))));
    }

    /**
     * Get the ranges that result from applying this translation to a range. Contained in a map keyed on whether a translation occurred for that range.
     *
     */
    private Map<Boolean, Collection<Range>> getChildRanges(final Range input)
    {
        final Map<Boolean, Collection<Range>> changedMap = new HashMap<>();
        changedMap.put(Boolean.TRUE, new ArrayList<>());
        changedMap.put(Boolean.FALSE, new ArrayList<>());

        if (input.getEndInclusive() < sourceRange.getStartInclusive() || input.getStartInclusive() > sourceRange.getEndInclusive())
        {
            // Wholly outside
            changedMap.get(Boolean.FALSE).add(input);
        }
        else if (input.getStartInclusive() >= sourceRange.getStartInclusive() && input.getEndInclusive() <= sourceRange.getEndInclusive())
        {
            // Wholly within.
            changedMap.get(Boolean.TRUE).add(new Range(getTranslationOf(input.getStartInclusive()), getTranslationOf(input.getEndInclusive())));
        }
        else if (input.getStartInclusive() < sourceRange.getStartInclusive() && input.getEndInclusive() <= sourceRange.getEndInclusive())
        {
            // Split over source start
            changedMap.get(Boolean.FALSE).add(new Range(input.getStartInclusive(), sourceRange.getStartInclusive() - 1L));
            changedMap.get(Boolean.TRUE).add(new Range(getTranslationOf(sourceRange.getStartInclusive()), getTranslationOf(input.getEndInclusive())));
        }
        else if (input.getStartInclusive() >= sourceRange.getStartInclusive() && input.getEndInclusive() > sourceRange.getEndInclusive())
        {
            // Split over source end
            changedMap.get(Boolean.TRUE).add(new Range(getTranslationOf(input.getStartInclusive()), getTranslationOf(sourceRange.getEndInclusive())));
            changedMap.get(Boolean.FALSE).add(new Range(sourceRange.getEndInclusive() + 1L, input.getEndInclusive()));
        }
        else if (input.getStartInclusive() < sourceRange.getStartInclusive() && input.getEndInclusive() > sourceRange.getEndInclusive())
        {
            // Spans whole mapped range.
            changedMap.get(Boolean.TRUE).add(new Range(getTranslationOf(sourceRange.getStartInclusive()), getTranslationOf(sourceRange.getEndInclusive())));
            changedMap.get(Boolean.FALSE).add(new Range(input.getStartInclusive(), sourceRange.getStartInclusive() - 1L));
            changedMap.get(Boolean.FALSE).add(new Range(sourceRange.getEndInclusive() + 1L, input.getEndInclusive()));
        }
        return changedMap;
    }

    public long getTranslationOf(final long input)
    {
        return input + diff;
    }
}
