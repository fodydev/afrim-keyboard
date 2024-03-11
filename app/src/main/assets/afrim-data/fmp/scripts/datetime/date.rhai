// Version: 2023-09-29

const MONTHS = [
    "Ngù'fī", "Nkùɑ̀nʉ̀ɑ̀", "Mbàkngòfāt",
    "Sò'njɑ̀ɑ̀", "Njwēnɑ̌hntà'", "Mòmòshʉ̄",
    "Ntûmbhìngòfāt", "Mɑ̂ngà'nshì", "Kùkū'",
    "Ndʉ̌'nzɑ̄", "Nkhʉ̀ɑ̀nʉ̀ɑ̀", "Ncátmɑ̄ŋū",
];

fn parse_date(input) {
    let data = input.split('/');

    if data.len() != 3 {
        return [];
    }

    let day = parse_int(data[0]);
    let month = parse_int(data[1]);
    let year = parse_int(data[2]);

    if day in 1..31 && month in 1..13 && year in 1..2100 {
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

    if date.is_empty() { return }

    let nufi_month = global::MONTHS[date[1]-1];

    [input, "", [
        (
            if date[0] == 1 { "Ntûmbhì līē'" }
            else { `līē' ${date[0]}` }
        ) + `, ${nufi_month} ${date[2]}`
    ], commit]
}
