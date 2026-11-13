def pattern(rows, columns):
    return "\n".join(
        "".join("*" if row in (0, rows - 1) or column in (0, columns - 1) else "." for column in range(columns))
        for row in range(rows)
    )

if __name__ == "__main__":
    cases = int(input())
    print("\n\n".join(pattern(*map(int, input().split())) for _ in range(cases)))
