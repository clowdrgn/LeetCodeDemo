package fjl.dp;

//	Say you have an array for which the ith element is the price of a given stock on day i.
//
//	Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
//
//	You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
//	After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
//	Example:
//
//	prices = [1, 2, 3, 0, 2]
//	maxProfit = 3
//	transactions = [buy, sell, cooldown, buy, sell]
public class Dp309 {
		public static int maxProfit(int[] prices) {
			  if (prices == null || prices.length == 0) {
		            return 0;
		        }
		        
		        // ��ʾ��������δ�ֹɵ�����£������������ۼ��������
		        int[] sellDp = new int[prices.length];
		        // ��ʾ�������ճֹɵ�����£������������ۼ��������
		        int[] buyDp = new int[prices.length];
		        
		        // ���ǳ�ʼ�������һ��ֹɣ������Ϊ-prices[0]
		        buyDp[0] = -prices[0];
		        sellDp[0] = 0;
		        for (int i = 1; i < prices.length; i++) {
		            sellDp[i] = Math.max(sellDp[i - 1], buyDp[i - 1] + prices[i]);
		            //��i�����״̬Ϊ���ֹɣ���ô��i���������Ϊ 
		            //1:��i-1��״̬һ�����ֹɣ�������Ϊ��i-1�첻�ֹɵĻ�����
		            //2:��i����������ô����Ϊ ��i-1��Ϊ�ֹ�״̬�Ļ���+��i�������ļ۸�
		            //1,2��ȡ���ֵ
		            if (i >= 2) {
		                buyDp[i] = Math.max(buyDp[i - 1], sellDp[i - 2] - prices[i]);
		            } else {
		                buyDp[i] = Math.max(buyDp[i - 1], -prices[i]);
		            }
		            //��i�����״̬Ϊ�ֹɣ���ô��i���������Ϊ
		            //1:��i-1��״̬һ���ֹɣ�������Ϊ��i-1��ֹɵĻ�����
		            //2:��i�����룬��ǰһ�����Ϊcooldown��ǰ����Ϊ����������i-2��Ϊ���ֹɵĻ������ټ�ȥ��i��ļ۸�
		            //1,2��ȡ���ֵ
		        }
		        return sellDp[prices.length - 1];
	  }
		 public static int maxProfit1(int[] prices) {
		        // special case
		        if (prices.length == 0) return 0;
		        if (prices.length == 1) return 0;

		        // main loop
		        int inclusive = 0; // may include current profit����Ϊֹ����������Ļ������������룬�������������������й�Ʊ
		        int exclusive = 0; // not include current profit����Ϊֹ������������Ļ����������й�Ʊ
		        for (int i = 1; i < prices.length; i++) {
		            int profit = prices[i] - prices[i - 1]; //profitֻ��һ������������������������������Ϊcooldown���޷������������������롣
		            int newInclusive = Math.max(inclusive + profit, exclusive);//ʵ����õ��ǽ�ֹ��һ���inclusive�������й�Ʊ״̬����newInclusiveΪ��һ�첻���й�Ʊ������������Ҫ��profit������һ����й�Ʊ��exclusive,�������������Ժ�ǰһ��һ������ȡ���ֵ��
		            int newExclusive = Math.max(inclusive, exclusive);//ʵ����õ��ǽ�ֹ��һ���exclusive�����й�Ʊ״̬������ǰһ��������Ʊ��������cooldown�޷��������Ժ�ǰһ�첻���й�Ʊ����һ��Ϊinclusive����ǰһ����й�Ʊ�����޶�������exclusive����ȡ���ֵ��
		            inclusive = newInclusive;
		            exclusive = newExclusive;
		        }

		        // return result
		        return Math.max(inclusive, exclusive);
		    }
		 public static int maxProfit2(int[] prices) {
			    int n = prices.length;
			    if(n<=1) return 0;
			    int[] dp = new int[n + 1];
			    int max = -prices[0];//��һ�����룬����Ϊ-prices[0]
			    for (int i = 1; i < n; i++) {//��һ��������ֵΪ0��dp[1]Ϊ0
			        dp[i + 1] = Math.max(dp[i], max + prices[i]);//��i+1����������1���͵�i��һ�����й�Ʊ��������2��ǰһ�����룬����������  ȡ���ֵ
			        max = Math.max(dp[i - 1] - prices[i], max);//���й�Ʊ����������1��i-1������������������롣2����֮ǰһ���ĳ��й�Ʊ��������ȡ���ֵ
			    }

			    return dp[n];
			}
		 
		 public static int maxProfit3(int[] prices) {
			    if(prices==null||prices.length==1) {
			        return 0;
			    }
			    int[]dp=new int[prices.length+1];
			    boolean[]sell=new boolean[prices.length+1];//����һ���Ƿ������ı�ʶ��ʵ���Ϻ������Ǹ���ࡣ
			    for(int i=2;i<=prices.length;i++){
			        int max=0;
			        for(int j=1;j<i;j++){
			            int profit=Math.max(0,prices[i-1]-prices[j-1]);
			            int predpIndex=j-1;
			            if(sell[predpIndex]){
			                predpIndex--;
			            }
			            max=Math.max(max,dp[predpIndex]+profit);
			        }
			        dp[i]=max;
			        if(dp[i]>dp[i-1]){
			            sell[i]=true;
			        }else{
			            dp[i]=dp[i-1];
			        }
			    }
			    return dp[prices.length];
			}
		public static void main(String[] args) {
			int[] prices = {1,2,3,0,2};
			System.out.println(maxProfit2(prices));
		}
}
