from fractions import Fraction

def recursive_power(base, exponent):
    if exponent < 0:
        return 1 / recursive_power(base, -exponent)
    if exponent == 0:
        return 1
    half = recursive_power(base, exponent // 2)
    square = half * half
    return square * base if exponent % 2 else square

if __name__ == "__main__":
    import sys
    base, exponent = sys.stdin.read().split()
    result = recursive_power(Fraction(base), int(exponent))
    if isinstance(result, int) or result.denominator == 1:
        print(result.numerator)
    else:
        print(float(result))
