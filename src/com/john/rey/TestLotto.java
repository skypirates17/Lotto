package com.john.rey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class TestLotto {
	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		List<LottoDraw> lottoDraws = new TestLotto().getLottoDrawHistory();
		
		boolean isSame = false;
		List<Integer> genRandNum = null;
		//
		for (int i = 1; i <= 1000000; i++) {
			do {
				genRandNum = new TestLotto().generateRandomNumbers(1, 58, 6);
				if (hasConsecutive(genRandNum)) {
					System.err.println("UMULIT!!!!!=============="+genRandNum+"=========================================");
					System.err.println("UMULIT!!!!!=================="+genRandNum+"=====================================");
					System.err.println("UMULIT!!!!!================="+genRandNum+"======================================");
					System.err.println("UMULIT!!!!!================="+genRandNum+"======================================");
					System.err.println("UMULIT!!!!!=================="+genRandNum+"=====================================");
					isSame = true;
				} else {
					for(LottoDraw lottoDraw : lottoDraws) {
						List<Integer> combination = lottoDraw.getCombinations();
						isSame = isListSame(genRandNum, combination);
						if (isSame) {
							System.err.println("PAREHAS!!!!!=================="+lottoDraw+"===================================");
							System.err.println("PAREHAS!!!!!==================="+lottoDraw+"===================================");
							System.err.println("PAREHAS!!!!!===================="+lottoDraw+"===================================");
							System.err.println("PAREHAS!!!!!===================="+lottoDraw+"===================================");
							System.err.println("PAREHAS!!!!!===================="+lottoDraw+"===================================");
							Thread.sleep(2000);
							break;
						}
					}
				}
				
			} while (isSame);
			
			System.out.println(genRandNum);
		}
		
		
	}
	// no more than 2 numbers in a current list should occur in the next set of numbers
	 
	// consecutive number must occur only once
	public static boolean hasMoreThanOneConsecutive() {
		return (Boolean) null;
	}
	
	// no 3 consecutive numbers must appear
	public static boolean hasConsecutive(List<Integer> lst) {
		Collections.sort(lst);
        boolean result = false;
        for (int i = 0; i < lst.size() - 2; i++) {
            if (((lst.get(i + 2) - lst.get(i+1)) == 1) &&((lst.get(i + 1) - lst.get(i)) == 1)) {
            	result = true;break;
            }
        }
        return result;
    }
	
	public static boolean isListSame(List<Integer> lst1, List<Integer> lst2) {
		List<Integer> commonList = (List<Integer>) CollectionUtils.retainAll(lst1,lst2);
		
		return commonList.size() == lst1.size();
	}
	
	private List<Integer> generateRandomNumbers(int min, int max, int getCount) {
		List<Integer> result = new ArrayList<Integer>();
		
		List<Integer> list = new ArrayList<Integer>();
        for (int i=min; i<max; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<getCount; i++) {
        	result.add(list.get(i));
        }
        
        return result;
	}
	
	private List<LottoDraw> getLottoDrawHistory() throws NumberFormatException, IOException {
		File file = new File("C:\\JOHN\\Lotto 6 58 all results.txt");
		
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		// Read File Line By Line]
		List<LottoDraw> lottoDraws = new ArrayList<>();
		while ((strLine = br.readLine()) != null) {
			LottoDraw lottoDraw = new LottoDraw();

			StringTokenizer st = new StringTokenizer(strLine, "\t");
			st.nextToken(); // get rid of the first token
			
			String[] parts = st.nextToken().split("-");
			List<Integer> combination = new ArrayList<>();
			for(int i = 0; i< parts.length; i++) {
				//combination.set(i, Integer.valueOf(parts[i]));
				combination.add(Integer.valueOf(parts[i]));
			}
			lottoDraw.setCombinations(combination);
			
			lottoDraw.setDrawDate(st.nextToken());
			lottoDraw.setJackpot(st.nextToken());
			lottoDraw.setWinner(Integer.valueOf(st.nextToken()));
			lottoDraws.add(lottoDraw);
		}

		// Close the input stream
		br.close();
		return lottoDraws;
	}
}


class LottoDraw {
	private List<Integer> combinations;
	private String drawDate;
	private String jackpot;
	private Integer winner;

	public List<Integer> getCombinations() {
		return combinations;
	}

	public void setCombinations(List<Integer> combinations) {
		this.combinations = combinations;
	}

	public String getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(String drawDate) {
		this.drawDate = drawDate;
	}

	public String getJackpot() {
		return jackpot;
	}

	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
	}

	public Integer getWinner() {
		return winner;
	}

	public void setWinner(Integer winner) {
		this.winner = winner;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("{combinations="+this.combinations);
		sb.append(", drawDate=" + this.drawDate);
		sb.append(", jackpot=" + this.jackpot);
		sb.append(", winner=" + this.winner + "}");		
		return sb.toString();
	}

}


