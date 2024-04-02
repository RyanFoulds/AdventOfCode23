use std::cmp;

pub struct Game {
    pub id: u32,
    red: u32,
    blue: u32,
    green: u32,
}

impl Game {
    pub fn create(line: &str) -> Self
    {
        let first_split: Vec<&str> = line.split(": ").collect();
        let id: u32 = first_split[0].split(" ").collect::<Vec<&str>>()[1].parse::<u32>().unwrap();
        let mut red: u32 = 0;
        let mut green: u32 = 0;
        let mut blue: u32 = 0;

        for round in first_split[1].split("; ") {
            for component in round.split(", ")
            {
                let bits: Vec<&str> = component.split(" ").collect();
                match bits[1] {
                    "red" => red = cmp::max(red, bits[0].parse::<u32>().unwrap()),
                    "green" => green = cmp::max(green, bits[0].parse::<u32>().unwrap()),
                    "blue" => blue = cmp::max(blue, bits[0].parse::<u32>().unwrap()),
                    _ => println!("Uh oh! Unknown colour: {}", bits[1]),
                }
            }
        }

        Game {
            id,
            red,
            blue,
            green,
        }
    }

    pub fn is_possible(&self, loaded_reds: u32, loaded_greens: u32, loaded_blues: u32) -> bool
    {
        return loaded_reds >= self.red && loaded_blues >= self.blue && loaded_greens >= self.green;
    }

    pub fn get_power(&self) -> u32 {
        return self.red * self.blue * self.green;
    }

}
