// Version: 2023-09-29

const UNITS = [
    "", "nshʉ̀'", "pʉ́ɑ́", "tāā", "kwɑ̀", "tî", "ntòhō",
    "sə̀ə̀mbʉ́ɑ́", "hə̀ə̄", "vʉ̀'ʉ̄"
];

const TENS = [
    "", "ghǎm", "mʉ̄mbʉ́ɑ́", "mʉ̄ntāā", "mʉ̄kwɑ̀", "mʉ̄ntî",
    "mʉ̄ntòhō", "mʉ̄sə̀ə̀mbʉ́ɑ́", "mʉ̄hə̀ə̄", "mʉ̄vʉ̀'ʉ̄"
];

fn translate(input) {
    let n = input.len();
    let result = "";
    let commit = if input.ends_with("'") {
        input = input.sub_string(0..(n-1));
        n = input.len();
        true
    } else { false };

    let result = switch n {
        1 => global::UNITS[parse_int(input)],
        2 => {
            let unit = parse_int(input.sub_string(1,2));
            let ten = parse_int(input.sub_string(0,1));
            print([ten, unit]);
            let nufi_unit = global::UNITS[unit];
            let nufi_ten = global::TENS[ten];

            (
                if unit == 0 { "" }
                else { "ncɔ̀ " + nufi_unit + " mɑ̀ " } + nufi_ten
            )
        },
        _ => { return },
    };

    [input, if commit { "" } else { "'" }, [result], commit]
}
