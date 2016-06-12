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
		        
		        // 表示当天最终未持股的情况下，当天结束后的累计最大利润
		        int[] sellDp = new int[prices.length];
		        // 表示当天最终持股的情况下，当天结束后的累计最大利润
		        int[] buyDp = new int[prices.length];
		        
		        // 考虑初始情况，第一天持股，则获利为-prices[0]
		        buyDp[0] = -prices[0];
		        sellDp[0] = 0;
		        for (int i = 1; i < prices.length; i++) {
		            sellDp[i] = Math.max(sellDp[i - 1], buyDp[i - 1] + prices[i]);
		            //第i天结束状态为不持股，那么第i天的最大获利为 
		            //1:和i-1天状态一样不持股，获利即为第i-1天不持股的获利；
		            //2:第i天卖掉，那么获利为 第i-1天为持股状态的获利+第i天卖掉的价格。
		            //1,2中取最大值
		            if (i >= 2) {
		                buyDp[i] = Math.max(buyDp[i - 1], sellDp[i - 2] - prices[i]);
		            } else {
		                buyDp[i] = Math.max(buyDp[i - 1], -prices[i]);
		            }
		            //第i天结束状态为持股，那么第i天的最大获利为
		            //1:和i-1天状态一样持股，获利即为第i-1天持股的获利；
		            //2:第i天买入，则前一天必须为cooldown，前两天为卖出，即第i-2天为不持股的获利，再减去第i天的价格。
		            //1,2中取最大值
		        }
		        return sellDp[prices.length - 1];
	  }
		 public static int maxProfit1(int[] prices) {
		        // special case
		        if (prices.length == 0) return 0;
		        if (prices.length == 1) return 0;

		        // main loop
		        int inclusive = 0; // may include current profit今天为止，包含今天的获利（昨天买入，今天卖出），即不持有股票
		        int exclusive = 0; // not include current profit今天为止，不包含今天的获利，即持有股票
		        for (int i = 1; i < prices.length; i++) {
		            int profit = prices[i] - prices[i - 1]; //profit只有一种情况即（昨天买入今天卖出），因为cooldown，无法是昨天卖出今天买入。
		            int newInclusive = Math.max(inclusive + profit, exclusive);//实际求得的是截止下一天的inclusive（不持有股票状态），newInclusive为下一天不持有股票（卖出，所以要加profit）和下一天持有股票（exclusive,无卖出动作所以和前一天一样）中取最大值。
		            int newExclusive = Math.max(inclusive, exclusive);//实际求得的是截止下一天的exclusive（持有股票状态），即前一天卖出股票（今天是cooldown无法买入所以和前一天不持有股票获利一样为inclusive）和前一天持有股票今天无动作（即exclusive）中取最大值。
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
			    int max = -prices[0];//第一天买入，获利为-prices[0]
			    for (int i = 1; i < n; i++) {//第一天获利最大值为0，dp[1]为0
			        dp[i + 1] = Math.max(dp[i], max + prices[i]);//第i+1天最大获利，1：和第i天一样持有股票不动作。2：前一天买入，今天卖出。  取最大值
			        max = Math.max(dp[i - 1] - prices[i], max);//持有股票的最大获利，1：i-1天的最大获利，今天买入。2：和之前一样的持有股票不动作。取最大值
			    }

			    return dp[n];
			}
		 
		 public static int maxProfit3(int[] prices) {
			    if(prices==null||prices.length==1) {
			        return 0;
			    }
			    int[]dp=new int[prices.length+1];
			    boolean[]sell=new boolean[prices.length+1];//多了一个是否卖出的标识，实际上和上面那个差不多。
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
