# Hasira Problem Statement

This repository contains the problem statement, approach, test cases, and related resources for the Hasira project.

## Problem Statement

The Hasira problem requires processing input data according to specific rules and constraints, as described in the project documentation. The goal is to implement an efficient and correct solution that handles all edge cases.

## Approach

The solution is implemented in [`src/Main.java`](src/Main.java). The approach involves:

- **Input Parsing:** Efficiently reading and parsing the input data.
- **Core Logic:** Applying the required algorithms to process the input. The logic is modularized for clarity and maintainability.
- **Output Generation:** Formatting and printing the output as specified.

Key design decisions and optimizations are documented within the code comments in `Main.java` for easier understanding and future maintenance.

## Test Cases

The `tests/` folder includes a variety of test cases designed to validate the correctness and efficiency of the solution implemented in `src/Main.java`. Each test case provides specific input scenarios and the expected output, helping to ensure the solution handles edge cases and typical use cases.

### Example Test Cases

1. **Test Case 1:**  
    *Input:*  
    ```
    [Sample input for test case 1]
    ```
    *Expected Output:*  
    ```
    [Expected output for test case 1]
    ```

2. **Test Case 2:**  
    *Input:*  
    ```
    [Sample input for test case 2]
    ```
    *Expected Output:*  
    ```
    [Expected output for test case 2]
    ```

#### Dry Run: Test Case 2

Let's walk through the execution of the second test case:

- The input is read and parsed by `Main.java`.
- The main logic processes the input step by step, applying the required algorithms.
- Intermediate results are computed and stored as needed.
- The final output is generated and compared with the expected output.

This dry run helps verify that the implementation behaves as intended for this scenario. For more details, refer to the comments and logic in `src/Main.java`.

## How to Run

1. Clone the repository.
2. Navigate to the project directory.
3. Compile the Java source files:
    ```
    javac src/Main.java
    ```
4. Run the program with your input:
    ```
    java -cp src Main
    ```

## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements or bug fixes.

## License

This project is licensed under the MIT License.