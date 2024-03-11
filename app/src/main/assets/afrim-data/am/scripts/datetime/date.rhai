// Version: 2023-09-29

const MONTHS = [
    "መስከረም", "ጥቅምት", "ሕዳር", "ታህሳስ", "ጥር", "የካቲት", "መጋቢት",
    "ሚያዝያ", "ግንቦት", "ሰኔ", "ሐምሌ", "ነሐሴ", "ጳጉሜ",
];

fn parse_date(input) {
    let data = input.split('/');
    
    if data.len() != 3 {
        return [];
    }

    let day = parse_int(data[0]);
    let month = parse_int(data[1]);
    let year = parse_int(data[2]);

    if day in 1..31 && month in 1..=13 && year in 1..2100 {
        return [day, month, year];
    }
}

// Main function
fn translate(input) {
    let n = input.len();
    let result = "";
    let commit = if input.ends_with("'") {
        input = input.sub_string(0..(n-1));
        n = input.len();
        true
    } else { false };
    let date = parse_date(input);
    
    if date.is_empty() { return [] }

    let amharic_month = global::MONTHS[date[1]-1];

    [input, "", [
        `${date[0]} ${amharic_month} ${date[2]}`,
        `${amharic_month} ${date[0]}, ${date[2]}`
    ], commit]
}
