def pattern(rows, columns):
    return "\n".join(
        "".join("*" if (row + column) % 2 == 0 else "." for column in range(columns))
        for row in range(rows)
    )

if __name__ == "__main__":
    cases = int(input())
    print("\n\n".join(pattern(*map(int, input().split())) for _ in range(cases)))
