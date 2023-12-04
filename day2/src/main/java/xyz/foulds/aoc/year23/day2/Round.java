package xyz.foulds.aoc.year23.day2;

public class Round
{
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";
    private int red = 0;
    private int green = 0;
    private int blue = 0;

    public Round(final String input)
    {
        //  3 blue, 4 red
        final String[] components = input.split(", ");
        for (final String component : components)
        {
            final String[] bits = component.split(" ");
            final String color = bits[1];
            if (RED.equals(color))
            {
                red = Integer.parseInt(bits[0]);
            }
            else if (GREEN.equals(color))
            {
                green = Integer.parseInt(bits[0]);
            }
            else if (BLUE.equals(color))
            {
                blue = Integer.parseInt(bits[0]);
            }
        }
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }
}
