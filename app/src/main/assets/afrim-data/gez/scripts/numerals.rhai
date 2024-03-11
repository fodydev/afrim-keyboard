// Based on https://www.geez.org/Numerals/
// Tested with https://www.geez.org/Numerals/NumberSamples.html
// Version: 2023-09-01 

// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
const UNITS = ["", "፩", "፪", "፫", "፬", "፭", "፮", "፯", "፰", "፱"];
// 0, 10, 20, 30, 40, 40, 50, 60, 70, 80, 90
const TENS = ["", "፲", "፳", "፴", "፵", "፶", "፷", "፸", "፹", "፺"];
// 0, 100, 10000
const GROUPS = ["", "፻", "፼"];

fn translate(input) {
    let n = input.len();
    let result = "";
    let commit = if input.ends_with("'") {
        input = input.sub_string(0..(n-1));
        n = input.len();
        true
    } else { false };
    
    // Just a limit like that
    if n > 13 {
        return;
    } else if n != 0 && n % 2 != 0 {
        input = "0" + input;
        n = n + 1;
    }
    
    for i in 0..n {
        let pos = n - i - 1;
        
        if pos % 2 == 0 {
            let unit = parse_int(input.sub_string((pos+1)..(pos+2))
);
            let ten = parse_int(input.sub_string(pos..(pos+1)));
            let group = (
                if i < 2 { 0 }
                else if (i / 2) % 2 == 0 { 2 }
                else { 1 }
            );
            
            let ethio_unit = global::UNITS[unit];
            let ethio_ten = global::TENS[ten];
            let ethio_group = global::GROUPS[group];
            let is_thousand = (pos == 0 || group != 2);

            result = switch [ten != 0, unit != 0] {
                [false, false] => (
                    if group == 2 { ethio_group } else { "" }
                ),
                [false, true] => (
                    // unit
                    // not in the begining
                    // in the end or not thousands
                    if unit == 1 && i > 1 && is_thousand { "" } 
                    else { ethio_unit } + ethio_group
                ),
                [true, false] => ethio_ten + ethio_group,
                [true, true] => ethio_ten + ethio_unit + ethio_group,
            } + result;
        }
    }

    [input, if commit { "" } else { "'" }, [result], commit]
};
