import sys

def recur_partial(text, start=0):
    if start < len(text):
        print(text[start + 1:])
        recur_partial(text, start + 1)

if __name__ == "__main__":
    text = input()
    sys.setrecursionlimit(max(sys.getrecursionlimit(), len(text) + 100))
    recur_partial(text)
