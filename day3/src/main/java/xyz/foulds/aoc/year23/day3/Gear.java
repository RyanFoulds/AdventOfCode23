package xyz.foulds.aoc.year23.day3;

import java.util.Collection;
import java.util.Objects;

public class Gear
{
    private final Coordinate coordinate;
    private final int gearRatio;

    public Gear(final Coordinate coordinate, final Collection<PartNumber> partNumbers)
    {
        this.coordinate = coordinate;
        this.gearRatio = partNumbers.stream().mapToInt(PartNumber::getNumber).reduce(1, (x, y) -> x * y);
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
        final Gear gear = (Gear) o;
        return gearRatio == gear.gearRatio && Objects.equals(coordinate, gear.coordinate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(coordinate, gearRatio);
    }

    public int getGearRatio()
    {
        return gearRatio;
    }
}
