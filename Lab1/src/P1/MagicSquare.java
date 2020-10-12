package P1;

import java.util.Scanner;
import java.io.*;

public class MagicSquare {
	public static void main(String[] args) throws IOException {
		String[] fileName = { "1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "7.txt", "8.txt"};
		for (String file : fileName) {
			try {
				System.out.println(file + ": " + isLegalMagicSquare("src/P1/txt/" + file));
			} catch(IOException ex) {
				System.out.print(file + "文件读入失败！");
			}
		}

		Scanner input = new Scanner(System.in);
		generateMagicSquare(input.nextInt());
		input.close();
		try {
			System.out.println("6.txt: " + isLegalMagicSquare("src/P1/txt/6.txt"));
		} catch(IOException ex) {
			System.out.print("6.txt文件读入失败！");
		}
	}

	public static boolean isLegalMagicSquare(String fileName) throws IOException {
		File file = new File(fileName);
		Scanner input = new Scanner(file);
		String myLine;
		String[] list;
		if(input.hasNextLine()) {
			myLine= input.nextLine();
			list = myLine.split("\t");
		}
		else {
			System.out.print("空文件输入 ");
			input.close();
			return false;
		}
		int length = list.length;
		int[][] square = new int[length][length];
		int[] rowSum = new int[length];
		int[] columnSum = new int[length];
		int sum1 = 0, sum2 = 0, i, j;
		try {
			for (i = 0, j = 0; input.hasNextLine(); i++, j = 0) {
				for (String u : list) {
					square[i][j] = Integer.valueOf(u);
					columnSum[j] += square[i][j];
					rowSum[i] += square[i][j++];
				}
				myLine = input.nextLine();
				list = myLine.split("\t");
			}
			input.close();
			for (String u : list) {
				square[i][j] = Integer.valueOf(u);
				columnSum[j] += square[i][j];
				rowSum[i] += square[i][j++];
			}
			if(i != length-1) { //行数小于列数
				System.out.print("行列数不相等 ");
				return false;
			}
		} catch (NumberFormatException ex) {
			System.out.print("含有非正整数或未用\"\\t\"分割  ");
			return false;
		} catch(ArrayIndexOutOfBoundsException ex) { //列数小于行数造成数组越界
			System.out.print("行列数不相等 ");
			return false;
		}
		if(rowSum[0] != columnSum[0]) {
			System.out.print("行列和不相等");
			return false;
		}
		for (i = 0; i < length - 1; i++) {
			if ((rowSum[i] != rowSum[0]) || (columnSum[i] != columnSum[0])) {
				System.out.print("行或列和不相等 ");
				return false;
			}
		}
		for (i = 0; i < length; i++) {
			sum1 += square[i][i];
		}
		for (i = 0, j = length - 1; i < length; i++, j--) {
			sum2 += square[i][j];
		}
		if (sum1 != sum2 || rowSum[0] != sum1) {
			System.out.print("对角线和不相等 ");
			return false;
		}
		return true;
	}

	public static boolean generateMagicSquare(int n) {
		if (n < 0 || n % 2 == 0) {
			System.out.println("请输入一个大于0的奇数");
			return false;
		}
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1; // 如果当前行是第一行，跳到最后一行
				else
					row--; // 如果当前行不是第一行，跳到下一行
				if (col == (n - 1))
					col = 0; // 如果当前列是最后一列，跳到第一列
				else
					col++; // 如果当前列不是最后一列，跳到下一列
			}
		}
		PrintStream out = System.out;
		try {
			System.setOut(new PrintStream("src/P1/txt/6.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (i = 0; i < n; i++) { // 打印MagicSquare
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}
		System.setOut(out);
		return true;
	}
}