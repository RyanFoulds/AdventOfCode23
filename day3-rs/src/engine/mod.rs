use itertools::Itertools;

pub struct Engine {
    engine: Vec<Vec<char>>,
    height: usize,
    width: usize,
}
impl Engine {
    pub fn from_str(engine: &str) -> Engine {
        let mut final_engine: Vec<Vec<char>> = vec!();
        for line in engine.lines() {
            let line_vec: Vec<char> = line.chars().collect();
            final_engine.push(line_vec);
        }
        let height: usize = final_engine.len();
        let width: usize = final_engine.get(0).unwrap().len();

        Engine {
            engine: final_engine,
            height,
            width,
        }
    }
    pub fn find_symbols(&self) -> Vec<Symbol> {
        let mut symbol_coordinates: Vec<Symbol> = vec!();
        for row_i in 0..self.engine.len() {
            let row = self.engine.get(row_i).unwrap();
            for column_j in 0..row.len() {
                let value: char = *row.get(column_j).unwrap();
                if '.' != value && !value.is_digit(10) {
                    symbol_coordinates.push(
                        Symbol {
                            row:row_i,
                            column: column_j,
                            value});
                }
            }
        }
        return symbol_coordinates;
    }

    pub fn find_all_gear_ratios(&self, symbols: &Vec<Symbol>) -> Vec<u32>
    {
        symbols.iter()
            .filter(|s| s.value == '*')
            .map(|s| self.find_adjacent_numbers_single((s.row, s.column)))
            .filter(|v| v.len() == 2)
            .map(|v| v.iter().map(|pn| pn.value).product())
            .collect()
    }

    pub fn find_adjacent_numbers(&self, coords: &Vec<Symbol>) -> Vec<PartNumber> {
        coords.iter()
            .map(|s| (s.row, s.column))
            .flat_map(|coord| self.find_adjacent_numbers_single(coord))
            .unique()
            .collect()
    }

    fn find_adjacent_numbers_single(&self, coord: (usize, usize)) -> Vec<PartNumber> {
        let (row, column) = coord;

        let up: Option<PartNumber> = if row == 0 { None } else { self.find_a_number(row - 1, column) };
        let up_left: Option<PartNumber> = if row == 0 || column == 0 || !up.is_none() { None }
                                    else { self.find_a_number(row - 1, column - 1) };
        let up_right: Option<PartNumber> = if row == 0 || column == self.width - 1 || !up.is_none() { None }
                                    else { self.find_a_number(row - 1, column + 1) };
        let left: Option<PartNumber> = if column == 0 { None } else { self.find_a_number(row, column - 1) };
        let right: Option<PartNumber> = if column == self.width - 1 { None } else { self.find_a_number(row, column + 1) };

        let down: Option<PartNumber> = if row == self.height - 1 { None } else { self.find_a_number(row + 1, column) };
        let down_left: Option<PartNumber> = if row == self.height - 1 || column == 0 || !down.is_none() { None }
                                    else {self.find_a_number(row + 1, column - 1)};
        let down_right: Option<PartNumber> = if row == self.height - 1 || column == self.width - 1 || !down.is_none() { None }
                                    else { self.find_a_number(row + 1, column + 1) };

        return vec![up, up_left, up_right, left, right, down, down_left, down_right].into_iter()
            .flat_map(|pn| pn)
            .collect();
    }

    fn find_a_number(&self, row: usize, column: usize) -> Option<PartNumber> {
        let actual_row: &Vec<char> = self.engine.get(row).unwrap();
        if !actual_row.get(column).unwrap().is_digit(10) {
            return None;
        }

        // find the right hand side of the number.
        let mut ending_column = column;
        for i in column..self.width {
            if !actual_row.get(i).unwrap().is_digit(10) {
                ending_column = i - 1;
                break;
            }
            else if i == self.width - 1 { ending_column = i }
        }

        // Now sum the digits from right to left, multiplied by increasing powers of 10 to get the actual value of the written number.
        let mut value = 0;
        for i in 0..ending_column+1 {
            let character: &char = actual_row.get(ending_column - i).unwrap();
            if character.is_digit(10) {
                value += 10_u32.pow(i as u32) * (*character as u32 -48);
            }
            else {
                break;
            }
        }

        return Some(PartNumber{row, ending_column, value})
    }
}

#[derive(Eq, Hash, Clone, PartialEq)]
pub struct PartNumber {
    row: usize,
    ending_column: usize,
    pub value: u32,
}

pub struct Symbol {
    row: usize,
    column: usize,
    value: char,
}
