package dk.bettingai.trader.pricepriceslope

import org.junit._
import Assert._
import org.slf4j.LoggerFactory
import java.util.Random
import java.io.File
import dk.bettingai.tradingoptimiser._
import dk.betex.PriceUtil._
import HillClimbing._
import CoevolutionHillClimbing._
import dk.bettingai.marketsimulator.ISimulator._

/**
 * Run trader implementation, full random mutate only.
 *
 * @author korzekwad
 *
 * Set more memory and active jmx:
 * -Xmx512m
 * -Dcom.sun.management.jmxremote
 *
 *  bestSoFar=Solution [trader=PriceSlopeTrader [id=trader869, backSlope=-0.04, laySlope=-0.02, maxPrice=3.2], expectedProfit=141.8728123260652, matchedBetsNum=11080.0],
 *  50 markets, 23/02/2011 09:51:02 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=156, bestSoFar=Solution [trader=PriceSlopeTrader [id=trader778, backSlope=-0.03, laySlope=-0.05, maxPrice=3.25], expectedProfit=195.72460059023436, matchedBetsNum=12751.0], currentBest=Solution [trader=PriceSlopeTrader [id=trader778, backSlope=-0.03, laySlope=-0.05, maxPrice=3.25], expectedProfit=195.72460059023436, matchedBetsNum=12751.0]
 *  275 markets, 24/02/2011 08:31:54 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=76, bestSoFar=Solution [trader=PriceSlopeTrader [id=trader43, backSlope=-0.02, laySlope=-0.01, maxPrice=1.81], expectedProfit=106.75496311563737, matchedBetsNum=6450.0], currentBest=Solution [trader=PriceSlopeTrader [id=trader377, backSlope=0.01, laySlope=0.01, maxPrice=1.22], expectedProfit=0.0, matchedBetsNum=0.0]
 *  275 markets  25/02/2011 07:08:27 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=73, bestSoFar=Solution [trader=PriceSlopeTrader [id=trader304, backSlope=-0.04, laySlope=-0.04, maxPrice=1.77], expectedProfit=138.8839350498075, matchedBetsNum=6266.0], currentBest=Solution [trader=PriceSlopeTrader [id=trader366, backSlope=0.01, laySlope=-0.03, maxPrice=1.05], expectedProfit=0.0, matchedBetsNum=0.0]
 *  275 markets  25/02/2011 07:08:27 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=?, bestSoFar=Solution [trader=PriceSlopeTrader [id=trader?, backSlope=-0.03, laySlope=-+0.02, maxPrice=4.3, maxNumOfWinners=13], expectedProfit=190, matchedBetsNum=50000.0]
 *  275 markets  27/02/2011 07:22:28 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=106, bestSoFar=Solution [trader=PriceSlopeTrader [id=trader100, backSlope=-0.04, laySlope=-0.04, maxPrice=1.78,numOfRunners=5], expectedProfit=215.44786786476382, matchedBetsNum=4126.0], currentBest=Solution [trader=PriceSlopeTrader [id=trader530, backSlope=0.0, laySlope=-0.01, maxPrice=1.16,numOfRunners=10], expectedProfit=0.0, matchedBetsNum=0.0]
 *  50 markets  01/03/2011 11:19:49 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=829, bestSoFar=Solution [trader=PriceSlopeTrader [id=trader820, backSlope=-0.05, laySlope=-0.03, maxPrice=3.75,mxNumOfRunners=6,minProfitLoss=-62.0,minTradedVolume=405.0], expectedProfit=9.600873255130312, matchedBetsNum=282.0], currentBest=Solution [trader=PriceSlopeTrader [id=trader4144, backSlope=-0.04, laySlope=-0.03, maxPrice=1.46,mxNumOfRunners=11,minProfitLoss=-1.0,minTradedVolume=917.0], expectedProfit=0.062050340423681453, matchedBetsNum=2.0]
 *  275 markets 03/03/2011 23:24:14 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=100, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader22, backSlope=-0.05, laySlope=-0.04, maxPrice=1.77,mxNumOfRunners=19,minProfitLoss=-91.0,minTradedVolume=178.0], expectedProfit=13.032986027794397, matchedBetsNum=1655.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader500, backSlope=0.03, laySlope=0.0, maxPrice=1.07,mxNumOfRunners=5,minProfitLoss=-31.0,minTradedVolume=673.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *  50 markets  04/03/2011 08:37:57 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=347, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader1127, backSlope=-0.03, laySlope=-0.03, maxPrice=3.45,mxNumOfRunners=6,minProfitLoss=-79.0,minTradedVolume=167.0], expectedProfit=11.973669978370275, matchedBetsNum=647.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader1736, backSlope=0.04, laySlope=0.02, maxPrice=1.75,mxNumOfRunners=11,minProfitLoss=-60.0,minTradedVolume=548.0], expectedProfit=0.06534685785395755, matchedBetsNum=91.0]
 *  50 markets  04/03/2011 15:14:17 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=542, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader230, backSlope=-0.04, laySlope=0.0, maxPrice=5.0,mxNumOfRunners=6,minProfitLoss=-62.0,minTradedVolume=45.0], expectedProfit=14.91159654799338, matchedBetsNum=533.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader2708, backSlope=-0.03, laySlope=-0.01, maxPrice=1.95,mxNumOfRunners=20,minProfitLoss=-34.0,minTradedVolume=594.0], expectedProfit=1.03329440048556, matchedBetsNum=161.0]
 *  50 markets  04/03/2011 19:17:06 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=766, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader3772, backSlope=0.0, laySlope=0.03, maxPrice=9.2,mxNumOfRunners=9,minProfitLoss=-1369.0,minTradedVolume=1.0], expectedProfit=96.28912720506212, matchedBetsNum=21993.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader3830, backSlope=-0.05, laySlope=0.02, maxPrice=4.8,mxNumOfRunners=3,minProfitLoss=-2158.0,minTradedVolume=667.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *  50 markets 05/03/2011 11:47:25 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=993, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader4144, backSlope=-0.04, laySlope=0.0, maxPrice=8.0,mxNumOfRunners=6,minProfitLoss=-1604.0,minTradedVolume=9.0], expectedProfit=119.07696684885481, matchedBetsNum=3069.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader4966, backSlope=0.01, laySlope=0.0, maxPrice=1.04,mxNumOfRunners=7,minProfitLoss=-2653.0,minTradedVolume=488.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *  275 markets 07/03/2011 22:42:45 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=144, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader353, backSlope=-0.03, laySlope=-0.04, maxPrice=1.81,mxNumOfRunners=17,minProfitLoss=-2232.0,minTradedVolume=22.0], expectedProfit=151.9900618638515, matchedBetsNum=6717.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader717, backSlope=0.05, laySlope=0.0, maxPrice=1.24,mxNumOfRunners=17,minProfitLoss=-798.0,minTradedVolume=795.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *  275 markets2 13/03/2011 15:53:46 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=2677, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader8268, backSlope=0.05, laySlope=-0.05, maxPrice=13.0,mxNumOfRunners=19,minProfitLoss=-101.0,minTradedVolume=19.0], expectedProfit=64.31752079068137, matchedBetsNum=221145.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader13385, backSlope=0.04, laySlope=0.03, maxPrice=1.82,mxNumOfRunners=3,minProfitLoss=-377.0,minTradedVolume=970.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *  575 markets 15/03/2011 09:03:32 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing$ - Iter number=569, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader338, backSlope=0.03, laySlope=-0.05, maxPrice=1.62,mxNumOfRunners=13,minProfitLoss=-761.0,minTradedVolume=708.0], expectedProfit=2.442444453994086, matchedBetsNum=1607.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader2843, backSlope=0.02, laySlope=0.04, maxPrice=1.26,mxNumOfRunners=7,minProfitLoss=-1773.0,minTradedVolume=353.0], expectedProfit=0.0603605950663688, matchedBetsNum=12.0]
 *  575 markets 20/03/2011 18:32:43 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing - Iter number=1865, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader851, backSlope=0.02, laySlope=-0.03, maxPrice=9.6,mxNumOfRunners=16,minProfitLoss=-1.0,minTradedVolume=33.0], expectedProfit=662.8046898651958, matchedBetsNum=178299.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader9324, backSlope=-0.02, laySlope=-0.03, maxPrice=1.11,mxNumOfRunners=12,minProfitLoss=-2028.0,minTradedVolume=33.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *  575 markets 29/03/2011 09:35:04 INFO  dk.bettingai.tradingoptimiser.CoevolutionHillClimbing - Iter number=513, bestSoFar=Solution [trader=PricePriceSlopeTrader [id=trader276, backSlope=0.05, laySlope=-0.04, maxPrice=6.4,mxNumOfRunners=12,minProfitLoss=-7.0,minTradedVolume=263.0], expectedProfit=56.860253957566535, matchedBetsNum=52675.0], currentBest=Solution [trader=PricePriceSlopeTrader [id=trader2566, backSlope=0.03, laySlope=-0.01, maxPrice=1.07,mxNumOfRunners=4,minProfitLoss=-1958.0,minTradedVolume=608.0], expectedProfit=0.0, matchedBetsNum=0.0]
 *
 */
class PricePriceSlopeTraderRandomTest {

  private val log = LoggerFactory.getLogger(getClass)

  val baseTrader = PricePriceSlopeTrader("baseTrader", -0.21, 0.21, 5, 20, -10, 10)

  private val populationSize = 5
  private val generationNum = 5

  private val rand = new Random(System.currentTimeMillis)

  @Test
  def testTwoMarkets {

    log.info("Initial trader=" + baseTrader)

    var lastTraderId = 1
    def nextTraderId = { lastTraderId += 1; lastTraderId }

    val bank = 1000
    // val marketData = MarketData("c:/daniel/marketdataall")
    val marketData = MarketData("./src/test/resources/two_hr_10mins_before_inplay")

    /**Full random mutate only.*/
    val mutate = (solution: Solution[PricePriceSlopeTrader]) => {
      val backPriceSlopeSignal = ((rand.nextInt(11) - 5) * 0.01)
      val layPriceSlopeSignal = ((rand.nextInt(11) - 5) * 0.01)
      val maxPrice = priceUp(1 / rand.nextDouble)
      val maxNumOfRunners = 3 + rand.nextInt(20)
      val minProfitLoss = -rand.nextInt(3000)
      val minTradedVolume = rand.nextInt(1000)
      new TraderFactory[PricePriceSlopeTrader]() {
        def create() = PricePriceSlopeTrader("trader" + nextTraderId, backPriceSlopeSignal, layPriceSlopeSignal, maxPrice, maxNumOfRunners, minProfitLoss, minTradedVolume)
      }
    }
    val bestSolution = CoevolutionHillClimbing(marketData, mutate, populationSize, bank).optimise(baseTrader, generationNum)

    log.info("Best solution=" + bestSolution)
  }

}