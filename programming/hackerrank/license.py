#https://www.hackerrank.com/contests/university-codesprint-5/challenges/exceeding-speed-limit/problem
#!/bin/python3

import math
import os
import random
import re
import sys

# Complete the solve function below.
def solve(s):
    
    if (s<=90):
        print(str(0)+" No punishment")
    elif(110>=s>90):
        print(str((s-90)*300)+" Warning")
    else:
        print(str((s-90)*500)+" License removed")

if __name__ == '__main__':
    s = int(input().strip())

    solve(s)

