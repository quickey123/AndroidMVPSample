package whot.what.hot.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** LeetCode practise
 * Created by Kevin on 27/10/2017.
 */

public class LeetCodePractise {
    /**
     * LeetCode 461 漢明距離
     * */
    public static int hammingDistance(int x, int y) {
        /*
        先把x y 互斥或取值
        ex: 1 = 00000001 ,4 = 00000100
        算法：
          00000001
        = 00000100
        ------------
          00000101 = 5
        */
        int res = 0, exc = x ^ y;
        while (exc != 0) {
            ++res;
            /*再將值做位元且的運算取漢明距離直到值為0
            * 位元且規則是 都是1回1 ， 都是0回0
            * 下列參數為 5 &= 4
            * 5 = 00000101
            * 4 = 00000100
            *----------------
            * =   00000100  == 4
            * 迴圈
            * */
            exc &= (exc - 1);
        }
        return res;
    }

    /**
     * 561. Array Partition I
     * */
    public static int arrayPairSum(int[] nums) {
        /*2
          n個數字分成Ñ組，每組2個數字，取每組中較小的數字，求和。要求是得到的和最大，方法返回值即為這個最大和。
          設的2n個數字升序排序後為：A0，A1，A2，A3，...，A（2n-1個）。
          猜想一下容易發現，最大和即為（a0 + a2 + a4 + ...。+ a（2n-2））

          for example： [1,4,3,2,5,8]
          先排序陣列後為 [1,2,3,4,5,8]*
          迴圈去值為 1 3 5
          result = 0 + 1 + 3 + 5 = 9
        */
        Arrays.sort(nums);
        int result = 0;

        //i 以 0 2 4 6 8 為基準跳數
        for (int i = 0; i < nums.length; i += 2) {
            result += nums[i];
        }
        return result;
    }

    /**
     * 476. Number Complement
     * 題目要求為：給定一個非負整數，
     * 求出其complement number。所謂complement number，
     * 指對整數二進制最高位為1的位之後的所有位置取反，如5的二進制表示為00……00101，
     * 起最高位為1的位置是3，因此只對3之後的所有位置取反，得到00*00010，最後得出complement number為2。

     * 解法：先求出最高位為1的位數，然後計算出掩碼，再將原數據取反後和掩碼求與，即得出最後結果。
     ```
     * */
    public static int findComplement(int num) {
        int valid = 0, tmp = num;
        /*舉11為例，先將暫存數除盡，
         * 跳脫迴圈取得valid為4*/
        while(tmp > 0) {
            tmp /= 2;
            valid++;
        }
        /*~11， 補數11二進制為00001011 反轉 11110100
        * 1<<4 = 00000001 向左位移4格 ， 為00010000 = 16
        * 16-1 = 15 , 15 二進制為00001101
        *  做位元且的運算得到
        *   11110100
        * & 00001101
        * = 00000100 == 4
        * */
        return ~num & ((1 << valid) - 1);
    }

    /**
     * 575. Distribute Candies
     * 給一個長度為偶數的整數數組，數組中不同數字都代表不同糖果，
     * 將糖果平均分給弟弟和妹妹，妹妹最多能得到幾種糖果。
     * */
    public static int distributeCandies(int[] candies) {
        /* 記錄糖果種類，若糖果種類大於數組的一半，
         * 妹妹最多得到candies.size（）/ 2 種糖果，
         * 否則每種糖果都可以得到*/
        Set<Integer> kinds = new HashSet<>();
        //參數不同才加入
        for(int i : candies){
            kinds.add(i);
        }
        //回傳兩個數區間較小值
        return Math.min(kinds.size(),candies.length/2);
    }

    /**
     * 344. Reverse String
     * Given s = "hello", return "olleh".
     * */
    public static String reverseString(String s) {
        /* 解法一
         * 將字串加入StringBuffer內再進行翻轉即可*/

//        return new StringBuffer().append(s).reverse().toString();

        /* 解法二
         * 先將參數每一項 */
        char tmp[] = s.toCharArray();
        String result ="";
        int i = tmp.length -1;
        while(i>=0){
            result += tmp[i];
            i--;
        }
//        for(int i = tmp.length;i>0 ;i--){
//            result.append(tmp[i - 1]);
//        }


        return result;
    }
    /**
     * 521. Longest Uncommon Subsequence I
     * 給定兩個字符串，計算其“最長不公共子序列”。最長不公共子序列是指：兩字符串中某一個的子序列，該子序列不是另一個字符串的子序列，並且長度最長。
     * 子序列是指從一個序列中刪除一些字符，剩餘字符順序保持不變得到的新序列。任何字符串都是其本身的子序列，空串不屬於任意字符串的子序列。
     * 返回最長不公共子序列，若不存在，返回-1。
     * */
    public static int findLUSlength(String a, String b) {
        /* 若兩字符串不相等，選擇較長的字符串返回長度即可。
         * 否則返回-1。 （若兩字符串相等，則任意字符串的子串均為另一個的子串*/
        return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }

    /**
     * 283. Move Zeroes
     * nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
     * */
    public static int[] moveZeroes(int[] nums) {
//        /*解法一，效率值比較低*/
//        int nums_len = nums.length;
//        int j = 0;
//        for(int i = 0;i<nums_len;i++){
//            /*先將0的寫入暫存，再將排序互換，互換後在將j+1往後依序比對*/
//            if(nums[i] != 0){
//                int temp = nums[j];
//                nums[j] = nums[i];
//                nums[i] = temp;
//                j++;
//            }
//        }
//        /*解法二*/
//        int i = 0;
//        int j = 0;
//        int len = nums.length;
//
//        while (len > i) {
//            if (nums[i] != 0) {
//                int temp = nums[j];
//                nums[j] = nums[i];
//                nums[i] = temp;
//                j++;
//            }
//            i++;
//        }

        /*解法三，先將數值不為0的都先移到前面*/
        int index=0;

        for (int i=0;i<nums.length;i++){
            if (nums[i]!=0) nums[index++]=nums[i];
        }
        /*再將後面的參數設為0*/
        while(index<nums.length){
            nums[index++]=0;
        }
        return  nums;
    }

    /**
     * 371. Sum of Two Integers
     * 加總兩個參數，但不能使用+號
     * */
    public static int getSum(int a, int b) {
        /*以7+3為例子
        * 先進行互斥取值
        * 7的二次方 = 00000111
        * 3的二次方 = 00000011
        *          = 00000100 = 4
        * 第二步驟
        * 將兩位數值做位元且運算
        * 得到  00000011 在左移 變 00000110 = 6
        * 6+4 = 10
        * done.
        * */
        return b == 0 ? a : getSum(a ^ b, (a & b) << 1);
    }

    /**
     * 728. Self Dividing Numbers
     * 算出區間的數字 分開後原數字%是否都為0
     * 範例 128   128%1 128%2 128%8 都為0
     * */
    public static List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer>result = new ArrayList<>();
        for(int i = left ; i<=right ; i++){
            boolean isTrue = false;
            for(char a : ("" + i).toCharArray()){
                if(String.valueOf(i).contains("0"))break;
                if(i % Character.getNumericValue(a) != 0){
                    isTrue = false;
                    break;
                }else isTrue = true;
            }
            if(isTrue) result.add(i);
        }
        return  result;
    }

    /**
     * 463. Island Perimeter
     * example
     * {0,1,0,0},
     * {1,1,1,0},
     * {0,1,0,0},
     * {1,1,0,0},
     * */
    public static int islandPerimeter(int[][] grid) {
        int count = 0;
        //先取得總共有幾個排
        for(int i=0;i<grid.length;i++){
            //在取出目前
            for(int j=0;j<grid[0].length;j++) {
                if (grid[i][j] == 1) {
                    count += 4;
                    if (i > 0 && grid[i-1][j] == 1) count -= 2;
                    if (j > 0 && grid[i][j-1] == 1) count -= 2;
                }
            }
        }
        return count;
    }

    /**
     * 136. Single Number
     * 給一個整數陣列 nums, 裡面只有一個數字出現一次，其他都是出現兩次，找出那個孤單的數字。
     * */
    public static int singleNumber(int[] nums) {
        /* solution 1
        //先進行由小到大的排序
        Arrays.sort(nums);
        int result = 0,i=0;
        for (int num : nums) {
            //前後檢查相符合的參數
            if (!(i + 1 < nums.length && num == nums[i + 1]) && !(i - 1 >= 0 && num == nums[i - 1])) result = num;
            i++;
        }
        return result;
        */
        int ans =0;
        int len = nums.length;
        for(int i=0;i!=len;i++)
            ans ^= nums[i];
        return ans;
    }

    /**
     * 520. Detect Capital
     * All letters in this word are capitals, like "USA".
     * All letters in this word are not capitals, like "leetcode".
     * Only the first letter in this word is capital if it has more than one letter, like "Google".
     * */
    public static boolean detectCapitalUse(String word) {
        String char_case = "";
        for(int i=0;i<word.length();i++){
            //先檢查第一個字母為大寫或者小寫
            if( i == 0 && Character.isUpperCase(word.charAt(i))) char_case = "U";
            if(i == 0 && Character.isLowerCase(word.charAt(i))) char_case = "L";
            if(i>0){
                switch (char_case){
                    case "U":
                        //2比3，3比4依序類推跟後面比對，不同者就回傳false
                        if(i+1<word.length() && Character.isUpperCase(word.charAt(i)) != Character.isUpperCase(word.charAt(i+1))) return false;
                        break;
                    case "L":
                        //只要不是小寫就回傳false
                        if(!Character.isLowerCase(word.charAt(i))) return false;
                        break;
                }
            }
        }
        return true;
    }

    /**
     * 258. Add Digits
     * Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.
     * */
    public static int addDigits(int num) {
        if(num ==0) return 0;
        return num % 9 ==0 ?9:num % 9;
    }

    /**
     * 383. Ransom Note
     * You may assume that both strings contain only lowercase letters.
     canConstruct("a", "b") -> false
     canConstruct("aa", "ab") -> false
     canConstruct("aa", "aab") -> true
     對ransom字符串來說，每讀一個字母，則將對應位置的字母數目減一，
     如果某個字母數目小於0了，則表明字母不夠用，從而返回false；否則返回true。
     * */
    public static boolean canConstruct(String ransomNote, String magazine) {
        int[] cnt = new int[26];
        for (int i = 0; i < magazine.length(); i++) cnt[magazine.charAt(i) - 'a']++;
        for (int i = 0; i < ransomNote.length(); i++) if (--cnt[ransomNote.charAt(i) - 'a'] < 0) return false;
        return true;
    }

    /**
     * 760. Find Anagram Mappings
     * A = [12, 28, 46, 32, 50]
       B = [50, 12, 32, 46, 28]
       We should return
       [1, 4, 3, 2, 0]
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int[] anagramMappings(int[] A, int[] B) {
        int i  = 0, j = 0;
        int[] result = new int[A.length];
        while (i< A.length){
            if(A[i] == B[j]){
                result[i] = j;
                i++;
                j=0;
            }else j++;
        }
        return result;
    }

    /**
     * 448. Find All Numbers Disappeared in an Array
     * for example Input: [4,3,2,7,8,2,3,1]
     * */
    public static List<Integer> findDisappearedNumbers(int[] nums) {

        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j=1;
        while(i<nums.length){
            if(nums[Math.abs( nums[i])-1]>0){
                nums[Math.abs( nums[i])-1] = -nums[Math.abs( nums[i])-1];
            }
            i++;
        }
        for(int num : nums){
            if(num>0){
                result.add(j);
            }
            ++j;
        }
        return result;
    }

    /**
     * 500. Keyboard Row
     * Input: ["Hello", "Alaska", "Dad", "Peace"]
     Output: ["Alaska", "Dad"]
     * */
    public static String[] findWords(String[] words) {
        List<String> list = new ArrayList<>();
        String q = "qwertyuiopQWERTYUIOP";
        String a = "asdfghjklASDFGHJKL";
        String z = "zxcvbnmZXCVBNM";
        int i = 0;
        while (i<words.length){
            boolean isQContain = false;
            boolean isAContain = false;
            boolean isZContain = false;
            char[] chars = words[i].toCharArray();
            //逐一檢查字串是否都符合q列
            for(char c :chars){
                if(!q.contains(String.valueOf(c))){
                    isQContain = true;
                    break;
                }
            }
            //如果皆符合就將字串寫入array list
            if(!isQContain) list.add(words[i]);
            //逐一檢查字串是否都符合z列
            for(char c :chars){
                if(!a.contains(String.valueOf(c))){
                    isAContain = true;
                    break;
                }
            }
            if(!isAContain) list.add(words[i]);
            //逐一檢查字串是否都符合z列
            for(char c :chars){
                if(!z.contains(String.valueOf(c))){
                    isZContain = true;
                    break;
                }
            }
            //如果皆符合就將字串寫入array list
            if(!isZContain) list.add(words[i]);
            i++;
        }
        return list.toArray(new String[0]);
    }

    /**
     * 766. Toeplitz Matrix
     * Input: matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
     Output: True
     Explanation:
     1234
     5123
     9512
     ------------------------------------------------------------------------------------------------------
     In the above grid, the diagonals are "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]",
     and in each diagonal all elements are the same, so the answer is True.
     * */
    public static boolean isToeplitzMatrix(int[][] matrix) {
        int i = 0;
        while (i<matrix.length){
            int j = 0 ;
            while (j<matrix[0].length) {
                if(j+1==matrix[0].length) break;
                if(i+1==matrix.length) break;
                if(!(matrix[i][j] == matrix[i+1][j+1])) return false;
                j++;
            }
            i++;
        }
        return true;
    }

    /**
     * 485. Max Consecutive Ones
     * Input: [1,1,0,1,1,1]
       Output: 3
       Explanation: The first two digits or the last three digits are consecutive 1s.
       The maximum number of consecutive 1s is 3.
     *
     * example data : 1,0,1,1,0,1
     * */
    public static int findMaxConsecutiveOnes(int[] nums) {
        int result = 0,temp = 0;
        for (int i : nums) {
            if(i!=0) result += i;
            else{
                if(result>temp) temp = result;
                result = 0;
            }
        }
        return Math.max(temp,result);
    }

    /**
     * 349. Intersection of Two Arrays
     *
     * */
    public static int[] intersection(int[] nums1, int[] nums2) {
        int i=0,j = 0,k=0,max = Math.max(nums1.length,nums2.length) ,min = Math.min(nums1.length,nums2.length);
        boolean isnums1max = nums1.length>nums2.length;
        List<Integer> temp = new ArrayList<>();
        List<Integer> temp2 = new ArrayList<>();
        while(i<max){
            temp.add(isnums1max ?nums1[i]:nums2[i]);
            i++;
        }
        while (j<min){
            if(isnums1max) {
                if(temp.contains(nums2[j]) && !temp2.contains(nums2[j])) temp2.add(nums2[j]);
            }else{
                if(temp.contains(nums1[j]) && !temp2.contains(nums1[j])) temp2.add(nums1[j]);
            }
            j++;
        }
        int[] result = new int[temp2.size()];
        for(Integer num : temp2) result[k++] = num;
        return result;
    }

    /**
     * 171. Excel Sheet Column Number
     * For example:
     A -> 1
     B -> 2
     C -> 3
     ...
     Z -> 26
     AA -> 27
     AB -> 28
     * */
    public static int titleToNumber(String s) {
        int result = 0;
        for(char c : s.toCharArray()){
            result *= 26;
            result += c-'A'+1;
        }
        return result;
    }

    /**
     * 268. Missing Number
     * Example 1

     Input: [3,0,1]
     Output: 2
     Example 2

     Input: [9,6,4,2,3,5,7,0,1]
     Output:
     * */
    public static int missingNumber(int[] nums) {
        int[] temp = new int[nums.length+1];
        for (int num : nums) temp[num] = 1;
        for(int i = 0 ; i< temp.length ; i++) if(temp[i] == 0) return i;
        return 0;
    }


    /**
     * 242. Valid Anagram
     * For example,
     s = "anagram", t = "nagaram", return true.
     s = "rat", t = "car", return false.
     * */
    public static boolean isAnagram(String s, String t) {
        /*
        solution 1
        if(s.length() != t.length()) return false;
        char[] char_s = s.toCharArray() , char_t = t.toCharArray();
        Arrays.sort(char_s);
        Arrays.sort(char_t);
        int i = 0;
        for(char c : char_s){
            if (c != char_t[i]) return false;
            i++;
        }
        return true;
        */
        if(s.length() == 0 && t.length() == 0) return true;
        if(s.length() != t.length()) return false;
        int[] temp = new int[25];
        for(char c : s.toCharArray()) temp[c-'a'] +=1;
        for(char c : t.toCharArray()){
            temp[c-'a'] -=1;
            if(temp[c-'a'] <0) return false;
        }
        return true;
    }

    /**
     * 167. Two Sum II - Input array is sorted
     * Input: numbers={2, 7, 11, 15}, target=9
       Output: index1=1, index2=2
     * */
    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        int j =numbers.length-1 , i =0;
        while (numbers[i] + numbers[j] != target){
            if (numbers[i] + numbers[j] < target ) i++;
            else j--;
        }
        result[0] = i+1;
        result[1] = j+1;
        return result;
    }

    /**
     * 387. First Unique Character in a String
     * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
     Examples:
     s = "leetcode"
     return 0.
     s = "loveleetcode",
     return 2.
     * */
    public static int firstUniqChar(String s) {
        int[] chars = new int[26];
        for(char c : s.toCharArray()) chars[c -'a']++;
        int result = 0;
        for(char c : s.toCharArray()){
            if(chars[c - 'a'] == 1 ){
                return result;
            }
            else result++;
        }
        return -1;
    }

    /**
     * 169. Majority Element
     *
     * */
    public static int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int temp_count = 0, temp = 0, result = 0;
        if (nums.length == 1) return nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (i + 1 == nums.length) {
                if (temp_count > temp) result = nums[i];
                break;
            }
            if (nums[i] == nums[i + 1]) temp_count++;
            else {
                if (temp_count > temp) {
                    temp = temp_count;
                    result = nums[i];
                }
                temp_count = 0;
            }
        }
        return result;
    }

    /**
     * 628. Maximum Product of Three Numbers
     * Given an integer array, find three numbers whose product is maximum and output the maximum product.
     Example 1:
     Input: [1,2,3]
     Output: 6
     Example 2:
     Input: [1,2,3,4]
     Output: 24
     * */
    public static int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        //One of the Three Numbers is the maximum value in the array.
        int a = nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3];
        int b = nums[0] * nums[1] * nums[nums.length - 1];
        return Math.max(a,b);
    }

    /**
     * 217. Contains Duplicate
     * 找出陣列是否有無重複數值
     * */
    public static boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i=0;i<nums.length-1;i++) if(nums[i] == nums[i+1]) return true;
        return false;
    }

    /**
     * 405. Convert a Number to Hexadecimal
     * */
    public static String toHex(int num) {
        char[] map = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        if(num == 0) return "0";
        StringBuilder result = new StringBuilder();
        while(num != 0){
            result.insert(0, map[(num & 15)]);
            num = (num >>> 4);
        }
        return result.toString();
    }


    /**
     * 409. Longest Palindrome
     * Input:
     "abccccdd"
     Output:
     7
     Explanation:
     One longest palindrome that can be built is "dccaccd", whose length is 7.
     * */
    public static int longestPalindrome(String s) {
        if(s==null || s.length()==0) return 0;
        HashSet<Character> hs = new HashSet<Character>();
        int count = 0;
        for(int i=0; i<s.length(); i++){
            if(hs.contains(s.charAt(i))){
                hs.remove(s.charAt(i));
                count++;
            }else{
                hs.add(s.charAt(i));
            }
        }
        if(!hs.isEmpty()) return count*2+1;
        return count*2;
    }
}
