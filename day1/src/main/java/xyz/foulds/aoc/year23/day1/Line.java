package xyz.foulds.aoc.year23.day1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Line
{
    private static final Map<String, String> REPLACEMENTS = new HashMap<>();
    static {
        REPLACEMENTS.put("one", "1");
        REPLACEMENTS.put("two", "2");
        REPLACEMENTS.put("three", "3");
        REPLACEMENTS.put("four", "4");
        REPLACEMENTS.put("five", "5");
        REPLACEMENTS.put("six", "6");
        REPLACEMENTS.put("seven", "7");
        REPLACEMENTS.put("eight", "8");
        REPLACEMENTS.put("nine", "9");
    }

    private final String line;
    private int digitOnlyValue = -1;
    private int spelledValue = -1;

    public Line(final String line)
    {
        this.line = line;
    }

    public int getDigitOnlyValue()
    {
        if (digitOnlyValue != -1)
        {
            return digitOnlyValue;
        }
        else if (containsDigits(line))
        {
            digitOnlyValue = calculateDigitValue(line);
            return digitOnlyValue;
        }
        else
        {
            throw new IllegalStateException("Can't calculate a digit value for a string that does not contain digits!");
        }
    }

    public int getSpelledValue()
    {
        if (spelledValue == -1)
        {
            spelledValue = calculateSpelledValue(line);
        }
        return spelledValue;
    }

    private static int calculateDigitValue(final String input)
    {
        final List<Integer> digits = input.chars()
                                          .filter(Character::isDigit)
                                          .map(Character::getNumericValue)
                                          .boxed()
                                          .collect(Collectors.toList());

        return 10 * digits.get(0) + digits.get(digits.size() - 1);
    }

    private static int calculateSpelledValue(final String input)
    {
        final StringBuilder output = new StringBuilder(input.length());
        String workingString = input;
        while(!workingString.isEmpty())
        {
            boolean replaced = false;
            for (Map.Entry<String, String> replacement : REPLACEMENTS.entrySet())
            {
                if (workingString.startsWith(replacement.getKey()))
                {
                    replaced = true;
                    output.append(replacement.getValue());
                    break;
                }
            }
            if (!replaced)
            {
                output.append(workingString.charAt(0));
            }
            workingString = workingString.substring(1);
        }
        return calculateDigitValue(output.toString());
    }

    private static boolean containsDigits(final String line)
    {
        return line.chars().anyMatch(Character::isDigit);
    }
}
