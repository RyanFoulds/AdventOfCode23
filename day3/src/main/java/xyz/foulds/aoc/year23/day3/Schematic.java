package xyz.foulds.aoc.year23.day3;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Schematic
{
    private final Collection<PartNumber> partNumbers;

    private final Collection<Gear> gears;

    private final char[][] schematic;

    public Schematic(final String[] input)
    {
        schematic = new char[input[0].length()][input.length];

        final Collection<Coordinate> symbolCoordinates = new HashSet<>();
        for (int y = 0; y < input.length; y++)
        {
            for (int x = 0; x < input[y].length(); x++)
            {
                final char val = input[y].charAt(x);
                schematic[x][y] = val;
                if (!Character.isDigit(val) && '.' != val)
                {
                    symbolCoordinates.add(new Coordinate(x, y));
                }
            }
        }

        // Use a Set, so we eliminate duplicates, PartNumber implements equals & hashcode appropriately for this.
        partNumbers = symbolCoordinates.stream().map(this::findNumbersAdjacentTo)
                                       .flatMap(Collection::stream)
                                       .collect(Collectors.toSet());

        gears = symbolCoordinates.stream()
                                 .filter(c -> '*' == schematic[c.getX()][c.getY()])
                                 .map(this::convertCoordinateToGear)
                                 .filter(Objects::nonNull)
                                 .collect(Collectors.toSet());
    }

    public int getSumOfPartNumbers()
    {
        return partNumbers.stream().mapToInt(PartNumber::getNumber).sum();
    }

    public int getSumOfGearRatios()
    {
        return gears.stream().mapToInt(Gear::getGearRatio).sum();
    }

    private Gear convertCoordinateToGear(final Coordinate coordinate)
    {
        final Set<PartNumber> adjacentPartNumbers = findNumbersAdjacentTo(coordinate);
        if (adjacentPartNumbers.size() != 2)
        {
            return null;
        }

        return new Gear(coordinate, adjacentPartNumbers);
    }

    private Set<PartNumber> findNumbersAdjacentTo(final Coordinate coordinate)
    {
        final int x = coordinate.getX();
        final int y = coordinate.getY();

        // Only check above if we're not already in the top row.
        final PartNumber up = y == 0 ? null : findPartNumber(x, y - 1);

        // If 'up' contained a number then the horizonally adjacent coords are part of
        // the same number (or don't have one at all), so skip checking them.
        final PartNumber upLeft = (y == 0 || up != null || x == 0) ? null : findPartNumber(x - 1, y - 1);
        final PartNumber upRight = (y == 0 || up != null || x == schematic.length - 1)
                                   ? null : findPartNumber(x + 1, y - 1);

        final PartNumber down = y == schematic.length - 1 ? null : findPartNumber(x, y + 1);

        // If 'down' contained a number then the horizonally adjacent coords are part of
        // the same number (or don't have one at all), so skip checking them.
        final PartNumber downLeft = (y == schematic.length - 1 || down != null || x == 0)
                                    ? null : findPartNumber(x - 1, y + 1);
        final PartNumber downRight = (y == schematic.length - 1 || down != null || x == schematic.length - 1)
                                   ? null : findPartNumber(x + 1, y + 1);

        final PartNumber left = x == 0 ? null : findPartNumber(x - 1, y);
        final PartNumber right = x == schematic.length - 1 ? null : findPartNumber(x + 1, y);

        return Stream.of(up, upLeft, upRight, down, downLeft, downRight, left, right)
                     .filter(Objects::nonNull)
                     .collect(Collectors.toSet());
    }

    private PartNumber findPartNumber(final int x, final int y)
    {
        // No number found at this location.
        if (!Character.isDigit(schematic[x][y]))
        {
            return null;
        }

        // Move left until we stop getting digits.
        int firstDigitX = 0;
        for (int i = x - 1; i >= 0; i--)
        {
            if (!Character.isDigit(schematic[i][y]))
            {
                firstDigitX = i + 1;
                break;
            }
        }

        // Now we're at the start of the number, so move right until we stop
        // getting digits, building a string of the number as we go.
        final StringBuilder builder = new StringBuilder();
        for (int i = firstDigitX; i < schematic.length; i++)
        {
            final char val = schematic[i][y];
            if (Character.isDigit(val))
            {
                builder.append(val);
            }
            else
            {
                break;
            }
        }

        // Construct a part number from the information we've gathered.
        return new PartNumber(Integer.parseInt(builder.toString()), y, firstDigitX);
    }
}
