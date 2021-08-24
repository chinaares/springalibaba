package com.example.common;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Test02 {

	public static void main(String[] args) {

		File srcFile = new File("C:/Users/20111238/Desktop/深入剖析Kubernetes.pdf");
		File dstFile = new File("C:/Users/20111238/Desktop/深入剖析Kubernetes2.pdf");
		File logFile = new File(dstFile.getParentFile(), dstFile.getName() + ".log.raf");
		RandomAccessFile logRaf = null;
		long start = 0L;
		try {
			if (logFile.exists() && logFile.length() > 0) {

				Scanner sc = new Scanner(System.in);
				System.out.println("上次拷贝结束:1 继续拷贝 0重新拷贝");
				switch (sc.nextInt()) {
					case 1:
						logRaf = new RandomAccessFile(logFile, "rw");
						start = logRaf.readLong();
						copy(srcFile, dstFile, start);
						break;
					case 0:
						copy(srcFile, dstFile, start);
					default:
						System.out.println("输入错误,请输入一个 0或1 的数字 进行选择");
						break;
				}

			} else {
				copy(srcFile, dstFile, start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static void copy(File srcDir, File dstFile, long start) {

		long length = srcDir.length();
		File logRaf = new File(dstFile.getParentFile(), dstFile.getName() + ".log.raf");
		RandomAccessFile srcRandom = null;
		RandomAccessFile dstRandom = null;
		RandomAccessFile logRandom = null;
		try {

			if (length == 0) {
				return;
			}

			srcRandom = new RandomAccessFile(srcDir, "rw");
			dstRandom = new RandomAccessFile(dstFile, "rw");
			logRandom = new RandomAccessFile(logRaf, "rw");

			long sum = start;
			int read = -1;
			int startavg = 0;
			byte b[] = new byte[1024];
			srcRandom.seek(start);
			while ((read = srcRandom.read(b)) != -1) {
				dstRandom.write(b, 0, read);
				sum += read;

				int avg = (int) (100 * sum / length);
				if (avg > startavg) {
					System.out.println("已经完成了%:" + avg);
					startavg = avg;
				}
				logRandom.seek(0);
				logRandom.writeLong(sum);
//				Thread.currentThread().sleep(1);//降低写的速度 效果明显
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (logRandom != null) {
				try {
					logRandom.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (dstRandom != null) {
				try {
					dstRandom.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (srcRandom != null) {
				try {
					srcRandom.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			logRaf.delete();
		}


	}
}