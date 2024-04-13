mod engine;

use std::time::{Duration, Instant};
use std::{env, fs};
use crate::engine::{Engine, Symbol};

fn main() {
    let file_path = &env::args().collect::<Vec<String>>()[1];
    let file: String = fs::read_to_string(file_path).expect("Couldn't read the file.");
    let trimmed_file: String = file.trim().to_string();
    let start: Instant = Instant::now();

    let engine: Engine = Engine::from_str(&trimmed_file);
    let symbols: Vec<Symbol> = engine.find_symbols();
    // Part 1.
    let sum_of_numbers: u32 = engine.find_adjacent_numbers(&symbols).iter().map(|pn| pn.value).sum::<u32>();
    // Part 2.
    let sum_of_gear_ratios: u32 = engine.find_all_gear_ratios(&symbols).iter().sum();

    let elapsed: Duration = start.elapsed();
    println!("Sum of part numbers: {}", sum_of_numbers);
    println!("Sum of gear ratios: {}", sum_of_gear_ratios);
    println!("That took: {:.2?}", elapsed);
}
