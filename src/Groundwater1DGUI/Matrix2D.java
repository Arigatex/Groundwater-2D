package Groundwater1DGUI;

import java.text.DecimalFormat;

class Matrix2d {

    private DecimalFormat df = new DecimalFormat("0.00");
    private boolean exceptiontop = false;
    private boolean exceptionbot = false;
    private double[][] matrix;
    private double[] vectorRight;
    private double[] vectorLeft;
    public int casem;

    ////////////////////////////////// Constructor/////////////////////////////////

    public Matrix2d(
            int n,
            double lx,
            double ly,
            double kx,
            double ky,
            double hn,
            double hs,
            double he,
            double hw,
            double qn,
            double qs,
            double qe,
            double qw,
            double ql,
            double w0,
            double wl,
            int casem
    ) {

        double x = lx / (n - 1);
        double[] vectorRight = new double[n * n];
        double[] vectorLeft = new double[n * n];
        double[][] matrix = new double[n * n][n * n];// <-----------initialize the general // vector

//        int ky = 5;
//        int ly = 4;
//        int qs = 5;
//        int qn = 5;
//        int qe = 5;
//        int qw = 5;
//        int hs = 12;
//        int hn = 13;
//        int he = 16;
//        int hw = 18;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int k = j * n + i;
                if (j == 0) { // south face
                    if (1 == 2) { // Neumann
                        matrix[k][(j + 1) * n + i] = -ky / ly;
                        matrix[k][j * n + i] = ky / ly;
                        vectorRight[k] = qs;
                    } else if (1 == 1) { // Dirichlet
                        matrix[k][j * n + i] = 1.0;
                        vectorRight[k] = hs;// h input south
                    }
                } else if (j == n - 1) { // north face
                    if (1 == 1) { // Neumann
                        matrix[k][(j - 1) * n + i] = ky / ly;
                        matrix[k][j * n + i] = -ky / ly;
                        vectorRight[k] = qn;
                    } else if (1 == 2) { // Dirichet
                        matrix[k][j * n + i] = 1.0;
                        vectorRight[k] = hn;// h input north
                    }
                } else if (i == 0) { // west face
                    if (1 == 1) { // Neumann
                        matrix[k][j * n + i + 1] = -kx / x;
                        matrix[k][j * n + i] = kx / x;
                        vectorRight[k] = qw;
                    } else if (1 == 2) { // Dirichlet
                        matrix[k][j * n + i] = 1.0;
                        vectorRight[k] = hw;// h input west
                    }
                } else if (i == n - 1) { // east face
                    if (1 == 1) { // -Neumann
                        matrix[k][j * n + i - 1] = kx / x;
                        matrix[k][j * n + i] = -kx / x;
                        vectorRight[k] = qe;
                    } else if (1 == 2) { // Dirichlet
                        matrix[k][j * n + i] = 1.0;
                        vectorRight[k] = he;// h input west
                    }
                } else {
                    matrix[k][j * n + i] = -2 * kx / (x * x) - 2 * ky / (ly * ly);
                    matrix[k][j * n + i - 1] = kx / (x * x);
                    matrix[k][j * n + i + 1] = kx / (x * x);
                    matrix[k][(j - 1) * n + i] = ky / ly*ly;
                    matrix[k][(j + 1) * n + i] = ky / ly*ly;
                    vectorRight[k] = -w0;
                }

            }
        }

        // Filling the right vector
//        for (int i = 0; i < vectorRight.length; i++) {
//            if (i != 0 && i != n - 1) {// <----------Middle positions
//                vectorRight[i] = w0;
//            } else {
//                if (i == 0) {// <------Top position
//                    switch (casem) {
//                        case (1):
//                            vectorRight[i] = h0;
//                            break;
//                        case (3):
//                            vectorRight[i] = h0;
//                            break;
//                        default:
//                            vectorRight[i] = q0;
//                    }
//
//                } else {// <-----------Bot position
//                    switch (casem) {
//                        case (1):
//                            vectorRight[i] = hl;
//                            break;
//                        case (2):
//                            vectorRight[i] = hl;
//                            break;
//                        default:
//                            vectorRight[i] = ql;
//                    }
//
//                }
//            }
//        }
        setMatrix(matrix);
        setVectorRight(vectorRight);
        setVectorLeft(vectorLeft);

    }

    /////////////////////////// Setters/Getters//////////////////////////////////////////

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setVectorRight(double[] vector) {
        this.vectorRight = vector;
    }

    public double[] getVectorRight() {
        return vectorRight;
    }

    public void setVectorLeft(double[] vector) {
        this.vectorLeft = vector;
    }

    public double[] getVectorLeft() {
        return vectorLeft;
    }

    //////////////////////// Matrix///////////////////////////////////////////////////////////
    //////////////////////// Printer//////////////////////////////////////////////////////////

    public void MatrixPrinter() {

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {

                System.out.print(df.format(matrix[row][col]) + "\t"); // <-----------------Printing the Matrix
            }
            System.out.println("");
        }
        System.out.println();
        System.out.print("Left vector:\t");
        VectorPrinter(vectorLeft);
        System.out.print("Right vector:\t");
        VectorPrinter(vectorRight);
        System.out.println("");
    }

    /////////////////////////// Vector
    /////////////////////////// Printer///////////////////////////////////////////////

    public void VectorPrinter(double[] vector) {

        for (int row = 0; row < vector.length; row++) {

            System.out.print(df.format(vector[row]) + "\t"); // <-----------------Printing the Vector
        }
        System.out.println("");
    }
}
