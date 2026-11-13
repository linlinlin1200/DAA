def valid_numbers(n, x, y):
    return (value for value in range(x, n, x) if value > 1 and value % y)

if __name__ == "__main__":
    for _ in range(int(input())):
        print(*valid_numbers(*map(int, input().split())))
