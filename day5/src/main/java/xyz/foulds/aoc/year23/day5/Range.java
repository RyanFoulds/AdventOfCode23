package xyz.foulds.aoc.year23.day5;

public class Range
{
    private final long startInclusive;
    private final long endInclusive;

    public Range(final long startInclusive, final long endInclusive)
    {
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    public long getStartInclusive()
    {
        return startInclusive;
    }

    public long getEndInclusive()
    {
        return endInclusive;
    }
}
