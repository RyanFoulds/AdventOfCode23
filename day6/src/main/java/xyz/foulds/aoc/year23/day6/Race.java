package xyz.foulds.aoc.year23.day6;

public class Race
{
    private final long time;
    private final long record;

    public Race(final long time, final long record)
    {
        this.time = time;
        this.record = record;
    }

    public long getNumberOfWaysToWin()
    {
        // distance achieved, y = tx - x^2, where t is time allowed for the race,
        // and x is the time spent charging the car. Find the two solutions for y = record.
        // The difference between them is the number integer charge times that beat the record.
        final double rootDiscriminant = Math.sqrt((time * time) - 4L * record);
        final double solution1 = (time + rootDiscriminant) / 2L;
        final double solution2 = (time - rootDiscriminant) / 2L;

        final long min = (long) Math.ceil(Math.min(solution1, solution2));
        final long max = (long) Math.floor(Math.max(solution1, solution2));
        long winCount = max - min + 1L;

        if (getDistance(min) == record)
        {
            winCount -= 1L;
        }
        if (getDistance(max) == record)
        {
            winCount -= 1L;
        }

        return winCount;
    }

    private long getDistance(final long holdTime)
    {
        return time * holdTime - (holdTime * holdTime);
    }
}
