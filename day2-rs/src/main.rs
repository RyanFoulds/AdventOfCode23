pub mod game;

use std::time::{Duration, Instant};
use std::{env, fs};
use crate::game::Game;

fn main() {
    let file_path = &env::args().collect::<Vec<String>>()[1];
    let file: String = fs::read_to_string(file_path).expect("Couldn't read the file.");
    let start: Instant = Instant::now();

    // Parse games.
    let games: Vec<Game> = file.lines().map(|l| Game::create(l)).collect();

    // Part 1.
    let possible_id_sum: u32 = games.iter()
        .filter(|g| g.is_possible(12, 13, 14))
        .map(|g| g.id)
        .sum();

    // Part 2.
    let power_sum: u32 = games.iter().map(|g| g.get_power()).sum();

    let elapsed: Duration = start.elapsed();

    println!("Part 1: {}", possible_id_sum);
    println!("Part 2: {}", power_sum);

    println!("{:.2?}", elapsed);
}
