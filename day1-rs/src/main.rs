use std::time::{Duration, Instant};
use std::{env, fs};

const ASCII_ZERO: u32 = 48;
const DIGIT_WORDS: [&str; 9] = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"];

fn main() {
    let file_path = &env::args().collect::<Vec<_>>()[1];
    let file: String = fs::read_to_string(file_path).expect("Couldn't read the file.");
    let start: Instant = Instant::now();

    let part1: u32 = solve_part_one(&file);
    let part2: u32 = solve_part_two(&file);

    let elapsed: Duration = start.elapsed();
    println!("That took {:.2?}", elapsed);

    println!("Part 1: {}", part1);
    println!("Part 2: {}", part2);
}

fn solve_part_one(file: &String) -> u32 {
    let mut sum: u32 = 0;
    let mut line_count: u32 = 0;

    for line in file.lines() {
        for byte in line.bytes() {
            if byte.is_ascii_digit() {
                line_count += 1;
                sum += 10 * (byte as u32);
                break;
            }
        }
        for byte in line.bytes().rev() {
            if byte.is_ascii_digit() {
                sum += byte as u32;
                break;
            }
        }
    }
    sum -= 11 * ASCII_ZERO * line_count;
    return sum;
}

fn solve_part_two(file: &String) -> u32 {
    let mut sum: u32 = 0;

    for line in file.lines() {
        sum += 10 * find_first_digit(&line) + find_last_digit(&line);
    }
    return sum;
}

fn find_first_digit(line: &str) -> u32 {
    let bytes: &[u8] = line.as_bytes();
    for i in 0..bytes.len() {
        let byte: u8 = bytes[i];
        if byte.is_ascii_digit() {
            return (byte as u32) - ASCII_ZERO;
        }

        let sub_string: &str = &line[i..];
        for (digit, word) in (1..10).zip(DIGIT_WORDS.iter())
        {
            if sub_string.starts_with(word)
            {
                return digit;
            }
        }
    }
    return 0;
}

fn find_last_digit(line: &str) -> u32 {
    let bytes: &[u8] = line.as_bytes();
    for i in 0..(bytes.len()) {
        let index: usize = bytes.len() - 1 - i;
        let byte: u8 = bytes[index];
        if byte.is_ascii_digit() {
            return (byte as u32) - ASCII_ZERO;
        }

        let sub_string: &str = &line[..index+1];
        for (digit, word) in (1..10).zip(DIGIT_WORDS.iter())
        {
            if sub_string.ends_with(word)
            {
                return digit;
            }
        }
    }
    return 0;
}

