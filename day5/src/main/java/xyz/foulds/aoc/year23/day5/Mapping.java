package xyz.foulds.aoc.year23.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Mapping
{
    final String source;
    final String target;

    final List<Translation> translations;

    public Mapping(final String input)
    {
        final String[] lines = input.split("\n");
        final String title = lines[0];
        final String[] parts = title.split(" ")[0].split("-to-");

        this.source = parts[0];
        this.target = parts[1];

        this.translations = Arrays.asList(lines).subList(1, lines.length)
                                  .stream()
                                  .map(Translation::new)
                                  .collect(Collectors.toList());
    }

    public String getSource()
    {
        return source;
    }

    public String getTarget()
    {
        return target;
    }

    public long map(final long input)
    {
        return translations.stream()
                           .filter(translation -> translation.appliesTo(input))
                           .findAny()
                           .map(translation -> translation.getTranslationOf(input))
                           .orElse(input);
    }

    public Collection<Range> map(final Collection<Range> ranges)
    {
        Collection<Range> workingRanges = ranges;
        Collection<Range> mappedRanges = new ArrayList<>();
        for (final Translation translation : translations)
        {
            final Map<Boolean, Collection<Range>> translatedRanges = translation.getChildRanges(workingRanges);

            // Ranges that are unchanged by this translation are fair game for the next one.
            workingRanges = translatedRanges.get(Boolean.FALSE);
            // Ranges that have been translated by this are done for this mapping.
            mappedRanges.addAll(translatedRanges.get(Boolean.TRUE));

            // If everything has been translated already, no need to check the remaining Translations.
            if (workingRanges.isEmpty())
            {
                break;
            }
        }
        mappedRanges.addAll(workingRanges);
        return mappedRanges;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final Mapping mapping = (Mapping) o;
        return Objects.equals(source, mapping.source) && Objects.equals(target, mapping.target);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(source, target);
    }
}
