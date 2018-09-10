package com.sheldon.leetcode;

import java.util.ArrayList;
import java.util.List;

public class ConcatenateString {

	List<StringBuffer> concatenatedStr = new ArrayList<>();
	
	List<Integer> findSubstring(String s, String[] words) {
		
		recursionString(words, 0, words.length);
		
		List<Integer> indices = new ArrayList<>();
		
		if(words.length == 0 || s.trim().equals("")){
			return new ArrayList<>();
		}
		
		Integer strLength;
		if(concatenatedStr.size() !=0 ){
			strLength = concatenatedStr.get(0).length();
		}else{
			concatenatedStr.add(new StringBuffer(words[0]));
			strLength = words[0].length();
		}
		
		for(int i = 0; i < s.length() - strLength + 1; i++){
			String tempS = s;
			String str = tempS.substring(i, strLength + i);
			if(concatenatedStr.toString().contains(str)){
				indices.add(i);
			}
		}
		
		return indices;
	}

	void recursionString(String[] words, int startIndex, int wordsLength) {
		
		int changeIndex;
		String changeString;
		
		if(startIndex < wordsLength) {
			recursionString(words, startIndex + 1, wordsLength);
			for(changeIndex = startIndex + 1; changeIndex < wordsLength; changeIndex ++) {
				changeString = words[startIndex];
				words[startIndex] = words[changeIndex];
				words[changeIndex] = changeString;
				recursionString(words, startIndex + 1, wordsLength);
				changeString = words[startIndex];
				words[startIndex] = words[changeIndex];
				words[changeIndex] = changeString;
			}
		}else {
			StringBuffer sb = new StringBuffer();
			for(String str : words) {
				sb.append(str);
			}
			concatenatedStr.add(sb);
		}
	}

	public static void main(String[] args) {

		String[] words = {"dhvf","sind","ffsl","yekr","zwzq","kpeo","cila","tfty","modg","ztjg","ybty","heqg","cpwo","gdcj","lnle","sefg","vimw","bxcb"};
		String s = "pjzkrkevzztxductzzxmxsvwjkxpvukmfjywwetvfnujhweiybwvvsrfequzkhossmootkmyxgjgfordrpapjuunmqnxxdrqrfgkrsjqbszgiqlcfnrpjlcwdrvbumtotzylshdvccdmsqoadfrpsvnwpizlwszrtyclhgilklydbmfhuywotjmktnwrfvizvnmfvvqfiokkdprznnnjycttprkxpuykhmpchiksyucbmtabiqkisgbhxngmhezrrqvayfsxauampdpxtafniiwfvdufhtwajrbkxtjzqjnfocdhekumttuqwovfjrgulhekcpjszyynadxhnttgmnxkduqmmyhzfnjhducesctufqbumxbamalqudeibljgbspeotkgvddcwgxidaiqcvgwykhbysjzlzfbupkqunuqtraxrlptivshhbihtsigtpipguhbhctcvubnhqipncyxfjebdnjyetnlnvmuxhzsdahkrscewabejifmxombiamxvauuitoltyymsarqcuuoezcbqpdaprxmsrickwpgwpsoplhugbikbkotzrtqkscekkgwjycfnvwfgdzogjzjvpcvixnsqsxacfwndzvrwrycwxrcismdhqapoojegggkocyrdtkzmiekhxoppctytvphjynrhtcvxcobxbcjjivtfjiwmduhzjokkbctweqtigwfhzorjlkpuuliaipbtfldinyetoybvugevwvhhhweejogrghllsouipabfafcxnhukcbtmxzshoyyufjhzadhrelweszbfgwpkzlwxkogyogutscvuhcllphshivnoteztpxsaoaacgxyaztuixhunrowzljqfqrahosheukhahhbiaxqzfmmwcjxountkevsvpbzjnilwpoermxrtlfroqoclexxisrdhvfsindffslyekrzwzqkpeocilatftymodgztjgybtyheqgcpwogdcjlnlesefgvimwbxcbzvaibspdjnrpqtyeilkcspknyylbwndvkffmzuriilxagyerjptbgeqgebiaqnvdubrtxibhvakcyotkfonmseszhczapxdlauexehhaireihxsplgdgmxfvaevrbadbwjbdrkfbbjjkgcztkcbwagtcnrtqryuqixtzhaakjlurnumzyovawrcjiwabuwretmdamfkxrgqgcdgbrdbnugzecbgyxxdqmisaqcyjkqrntxqmdrczxbebemcblftxplafnyoxqimkhcykwamvdsxjezkpgdpvopddptdfbprjustquhlazkjfluxrzopqdstulybnqvyknrchbphcarknnhhovweaqawdyxsqsqahkepluypwrzjegqtdoxfgzdkydeoxvrfhxusrujnmjzqrrlxglcmkiykldbiasnhrjbjekystzilrwkzhontwmehrfsrzfaqrbbxncphbzuuxeteshyrveamjsfiaharkcqxefghgceeixkdgkuboupxnwhnfigpkwnqdvzlydpidcljmflbccarbiegsmweklwngvygbqpescpeichmfidgsjmkvkofvkuehsmkkbocgejoiqcnafvuokelwuqsgkyoekaroptuvekfvmtxtqshcwsztkrzwrpabqrrhnlerxjojemcxel";
		List<Integer> result = new ConcatenateString().findSubstring(s, words);
		System.out.println(result);
	}
}
