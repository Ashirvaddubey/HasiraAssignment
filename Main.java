import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static class Point {
        BigInteger x, y;
        Point(BigInteger x, BigInteger y) { this.x = x; this.y = y; }
    }
    // ---- Utility Functions ----

    static BigInteger decodeValue(String val, int base) {
        return new BigInteger(val, base);
    }

    // Parse simple JSON: {"1": {"base": "6", "value": "324"}, ...}
    static List<Point> getPointsFromJson(String filename, int[] nAndk) throws Exception {
        List<Point> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            String json = sb.toString().replaceAll("\\s+", "");
            // Extract n and k:
            String sub = json.split("\"keys\":\\{")[1].split("}")[0];
            String[] kv = sub.split(",");
            int n = Integer.parseInt(kv[0].split(":")[1]);
            int k = Integer.parseInt(kv[1].split(":")[1]);
            nAndk[0] = n; nAndk[1] = k;
            // Now extract points:
            for (int i = 1; i <= n; ++i) {
                String pattern = "\"" + i + "\":\\{\"base\":\"(\\d+)\",\"value\":\"([^\"]+)\"}";
                java.util.regex.Matcher m = java.util.regex.Pattern.compile(pattern).matcher(json);
                if (m.find()) {
                    int base = Integer.parseInt(m.group(1));
                    BigInteger y = decodeValue(m.group(2), base);
                    BigInteger x = BigInteger.valueOf(i);
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    // Solve kxk linear equations for coefficients (row-major) using Gaussian elimination
    // Equations: [X][A] = [Y] --> return [A]
    static BigInteger[] solveSystem(BigInteger[][] X, BigInteger[] Y) {
        int k = Y.length;
        // Augmented matrix
        BigInteger[][] mat = new BigInteger[k][k+1];
        for (int i = 0; i < k; i++) {
            System.arraycopy(X[i], 0, mat[i], 0, k);
            mat[i][k] = Y[i];
        }
        // Forward Elimination
        for (int i = 0; i < k; ++i) {
            // Find max row
            int maxRow = i;
            for (int j = i+1; j < k; ++j)
                if (mat[j][i].abs().compareTo(mat[maxRow][i].abs()) > 0) maxRow = j;
            // Swap
            BigInteger[] tmp = mat[i]; mat[i] = mat[maxRow]; mat[maxRow] = tmp;

            if (mat[i][i].equals(BigInteger.ZERO))
                throw new IllegalStateException("Singular Matrix!");

            // Eliminate
            for (int j = i+1; j < k; ++j) {
                BigInteger fac = mat[j][i].divide(mat[i][i]);
                for (int c = i; c < k+1; ++c)
                    mat[j][c] = mat[j][c].subtract(fac.multiply(mat[i][c]));
            }
        }
        // Back-substitute
        BigInteger[] A = new BigInteger[k];
        for (int i = k-1; i >= 0; --i) {
            BigInteger sum = mat[i][k];
            for (int j = i+1; j < k; ++j)
                sum = sum.subtract(mat[i][j].multiply(A[j]));
            A[i] = sum.divide(mat[i][i]);
        }
        return A;
    }

    static BigInteger solveSecretFromPoints(List<Point> points, int k) {
        // Use first k points (could generalize with all combinations and check consistency)
        BigInteger[][] xMat = new BigInteger[k][k];
        BigInteger[] yVec = new BigInteger[k];
        for (int i = 0; i < k; i++) {
            BigInteger x = points.get(i).x;
            xMat[i][0] = BigInteger.ONE;
            for (int j = 1; j < k; j++)
                xMat[i][j] = x.pow(j);
            yVec[i] = points.get(i).y;
        }
        // But in usual Shamir: row = [1, x, x^2, ..., x^{k-1}]
        // But if you want standard polynomial: [x^{k-1},...,1], then reverse Powers
        // We want [x^{k-1},...,x^1,1]
        for (int i = 0; i < k; i++) {
            BigInteger x = points.get(i).x;
            for (int j = 0; j < k; j++)
                xMat[i][j] = x.pow(k-1-j);
        }
        BigInteger[] coeff = solveSystem(xMat, yVec);
        return coeff[k-1]; // constant term 'c'
    }
    public static void main(String[] args) throws Exception {
        // Provide your JSON file names here
        String[] filenames = {"testcase1.json", "testcase2.json"};
        for(String filename : filenames) {
            int[] nAndk = new int[2];
            List<Point> points = getPointsFromJson(filename, nAndk);
            BigInteger secret = solveSecretFromPoints(points, nAndk[1]);
            System.out.println(secret);
        }
    }
}
