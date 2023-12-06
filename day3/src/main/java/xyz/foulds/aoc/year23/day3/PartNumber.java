package xyz.foulds.aoc.year23.day3;

import java.util.Objects;

public class PartNumber
{
    private final int number;
    private final int startingX;
    private final int y;

    public PartNumber(final int number, final int lineNumber, final int startingX)
    {
        this.number = number;
        this.startingX = startingX;
        this.y = lineNumber;
    }

    public int getNumber()
    {
        return number;
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
        final PartNumber that = (PartNumber) o;
        return this.number == that.number
               && this.startingX == that.startingX
               && this.y == that.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(number, startingX, y);
    }

    @Override
    public String toString()
    {
        return String.valueOf(number);
    }
}
