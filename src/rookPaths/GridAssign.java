package rookPaths;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//This file is part of Jadoop
//Copyright (c) 2016 Grant Braught. All rights reserved.
//
//Jadoop is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published
//by the Free Software Foundation, either version 3 of the License,
//or (at your option) any later version.
//
//Jadoop is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty
//of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//See the GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public
//License along with Jadoop.
//If not, see <http://www.gnu.org/licenses/>.
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Scanner;

import jadoop.HadoopGridJob;
import jadoop.HadoopGridTask;

/**
 * Example of running multiple trials of an experiment in parallel using Jadoop.
 * The CoinFlipTask is uploaded to the HDFS and then run multiple times
 * (potentially in parallel on a cluster).
 */
public class GridAssign {

	private static final int TRIALS = 10;
	private static final int FLIPS = 1000;

	public static void main(String[] args) throws IOException, IllegalStateException, ClassNotFoundException,
			InterruptedException, URISyntaxException {

		// Create the job.
		HadoopGridJob hgj = new HadoopGridJob("GridH1");

		// Make the CoinFlipTask class file available on the cluster.
		hgj.addFile(new File("GridH1.class"));

		/*
		 * Add one command for each trial. Each command executes the
		 * CoinFlipTask with an argument indicating the number of flips to
		 * perform.
		 */
		File generatedPaths = new File("generatedPaths.txt");
		Scanner in = new Scanner(generatedPaths);
		int counter = 0;
		while (in.hasNextLine()) {
			HadoopGridTask hgt = new HadoopGridTask(in.nextLine());
			hgj.addTask(hgt);
			counter++;
		}

		// Run the job and wait for it to complete.
		hgj.runJob(true);

		/*
		 * Display each of the trial results and compute the average number of
		 * heads across all of the trials.
		 */
		PrintWriter writer = new PrintWriter("output.txt");
		int total = 0;
		for (int t = 0; t < counter; t++) {
			String key = "";
			HadoopGridTask hgt = hgj.getTask(key);

			writer(hgt.getStandardOutput());
		}
		System.out.println("Paths computed");
	}
}
