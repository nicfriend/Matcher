package com.example.Matcher;
import com.example.Matcher.entities.Order;
import com.example.Matcher.entities.Trade;
import com.example.Matcher.enums.Action;
import com.example.Matcher.interfaces.MatcherInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;


@SpringBootApplication
public class Matcher implements MatcherInterface {
	private ArrayList<Order> toBuy;
	private ArrayList<Order> toSell;
	private ArrayList<Aggregate> aggregatedBuyList;
	private ArrayList<Aggregate> aggregatedSellList;
	private ArrayList<Trade> tradeList;
	ArrayList<Order> oneAccount;
	public Matcher() {
		toBuy = new ArrayList<>();
		toSell = new ArrayList<>();
		aggregatedBuyList = new ArrayList<>();
		aggregatedSellList = new ArrayList<>();
		tradeList = new ArrayList<>();
		oneAccount = new ArrayList<>();
	}

	public static void main(String[] args) {
		SpringApplication.run(Matcher.class, args);
	}

	public ArrayList<Order> getToBuy() { return toBuy; }
	public void setToBuy(ArrayList<Order> toBuy) { this.toBuy = toBuy;	}

	public ArrayList<Order> getToSell() {
		return toSell;
	}
	public void setToSell(ArrayList<Order> toSell) { this.toSell = toSell;}

	public ArrayList<Aggregate> getAggregatedBuyList() {
		return aggregatedBuyList;
	}
	public ArrayList<Aggregate> getAggregatedSellList() {
		return aggregatedSellList;
	}

	public ArrayList<Trade> getTradeList() {
		return tradeList;
	}
	public void setTradeList(ArrayList<Trade> tradeList) { this.tradeList = tradeList; }

	public ArrayList<Order> getOneAccount() {
		return oneAccount;
	}

	public void setOneAccount(ArrayList<Order> oneAccount) { this.oneAccount = oneAccount;	}

	public void addOrder(Order newOrder) {
		if (newOrder.getAction() == Action.BUY) {
			newOrder.setCounter(toBuy.size());
			toBuy.add(newOrder);
			sort(toBuy);
		} else {
			newOrder.setCounter(toSell.size());
			toSell.add(newOrder);
			sort(toSell);
		}
	}

	public void lowToHigh(ArrayList<Order> toSell) {
		for (var i = 0; i < toSell.size(); i++){
			for (var j = 0; j < toSell.size(); j++){
				if (toSell.get(i).getPrice() < toSell.get(j).getPrice()) {
					Order x = toSell.get(i);
					toSell.set(i, toSell.get(j));
					toSell.set(j, x);
				}
			}
		}
		rearrange(toSell);
	}

	public void highToLow(ArrayList<Order> toBuy) {
		for (var i = 0; i < toBuy.size(); i++){
			for (var j = 0; j < toBuy.size(); j++){
				if (toBuy.get(i).getPrice() > toBuy.get(j).getPrice()) {
					Order x = toBuy.get(j);
					toBuy.set(j, toBuy.get(i));
					toBuy.set(i, x);
				}
			}
		}
		rearrange(toBuy);
	}

	public void sort(ArrayList<Order> relevantList){
		if (relevantList.get(0).getAction().equals(Action.BUY)){
			highToLow(relevantList);
		}
		else if (relevantList.get(0).getAction().equals(Action.SELL)){
			lowToHigh(relevantList);
		}
	}

	public void rearrange(ArrayList<Order> relevantList) {
		for (var i = 0; i < relevantList.size(); i++){
			for (var j = 0; j < relevantList.size(); j++){
				boolean wrongOrder = relevantList.get(i).getCounter() < relevantList.get(j).getCounter() && i>j;
				if (relevantList.get(i).getPrice() == relevantList.get(j).getPrice() && wrongOrder){
					Order x = relevantList.get(i);
					relevantList.set(i, relevantList.get(j));
					relevantList.set(j, x);
				}
			}
		}
	}

	public void trade(Order newOrder) {
		if (newOrder.getAction().equals(Action.BUY)) {
			if (toSell.size() == 0){
				addOrder(newOrder);
			}
			else{
				sort(toSell);
				for(int i = 0; i < toSell.size(); i++){
					boolean validPrice = newOrder.getPrice() >= toSell.get(i).getPrice();
					boolean validAccount = !newOrder.getAccount().equals(toSell.get(i).getAccount());
					if(validPrice && validAccount && newOrder.getQuantity() > toSell.get(i).getQuantity()){
						tradeList.add(0, tradeHistory(toSell.get(i).getAccount(), newOrder.getAccount(), toSell.get(i).getQuantity(), toSell.get(i).getPrice(), 1000));
						newOrder.setQuantity(newOrder.getQuantity() - toSell.get(i).getQuantity());
						toSell.get(i).setQuantity(0);
						if (i == (toSell.size()-1)) {
							addOrder(newOrder);
						}
					}
					else if (!validAccount){
						if (i == (toSell.size()-1)) {
							addOrder(newOrder);
						}
					}
					else if (validPrice && newOrder.getQuantity() == toSell.get(i).getQuantity()){
						tradeList.add(0, tradeHistory(toSell.get(i).getAccount(), newOrder.getAccount(), toSell.get(i).getQuantity(), toSell.get(i).getPrice(), 1000));
						toSell.get(i).setQuantity(0);
						newOrder.setQuantity(0);
						break;
					}
					else if (validPrice && newOrder.getQuantity() < toSell.get(i).getQuantity()){
						tradeList.add(0, tradeHistory(toSell.get(i).getAccount(), newOrder.getAccount(), newOrder.getQuantity(), toSell.get(i).getPrice(), 1000));
						toSell.get(i).setQuantity(toSell.get(i).getQuantity() - newOrder.getQuantity());
						newOrder.setQuantity(0);
						break;
					}
					else if (!validPrice){
						addOrder(newOrder);
						break;
					}
				}
				if (toSell.size() != 0){
					for(int i = (toSell.size()-1); i >= 0; i--){
						if (toSell.get(i).getQuantity() == 0){
							toSell.remove(i);
						}
					}
				}
			}

		}

		if (newOrder.getAction().equals(Action.SELL)) {
			if (toBuy.size() == 0){
				addOrder(newOrder);
			}
			else {
				sort(toBuy);
				for(int i = 0; i < toBuy.size(); i++){
					boolean validPrice = newOrder.getPrice() <= toBuy.get(i).getPrice();
					boolean validAccount = !newOrder.getAccount().equals(toBuy.get(i).getAccount());
					if(validPrice && validAccount && newOrder.getQuantity() > toBuy.get(i).getQuantity()){
						tradeList.add(0, tradeHistory(newOrder.getAccount(), toBuy.get(i).getAccount(), toBuy.get(i).getQuantity(), newOrder.getPrice(), 1000));
						newOrder.setQuantity(newOrder.getQuantity()-toBuy.get(i).getQuantity());
						toBuy.get(i).setQuantity(0);
						if (i == (toBuy.size()-1)) {
							addOrder(newOrder);
						}
					}
					else if (!validAccount){
						if (i == (toBuy.size()-1)) {
							addOrder(newOrder);
						}
					}
					else if (validPrice && newOrder.getQuantity() == toBuy.get(i).getQuantity()){
						tradeList.add(0, tradeHistory(toBuy.get(i).getAccount(), newOrder.getAccount(), toBuy.get(i).getQuantity(), newOrder.getPrice(), 1000));
						toBuy.get(i).setQuantity(0);
						newOrder.setQuantity(0);
						break;
					}
					else if (validPrice && newOrder.getQuantity() < toBuy.get(i).getQuantity()){
						tradeList.add(0, tradeHistory(newOrder.getAccount(), toBuy.get(i).getAccount(), newOrder.getQuantity(), newOrder.getPrice(), 1000));
						toBuy.get(i).setQuantity(toBuy.get(i).getQuantity() - newOrder.getQuantity());
						newOrder.setQuantity(0);
						break;
					}
					else if (!validPrice){
						addOrder(newOrder);
						break;
					}
				}
				if (toBuy.size() != 0){
					for(int i = toBuy.size()-1; i >= 0; i--){
						if (toBuy.get(i).getQuantity() == 0){
							toBuy.remove(i);
						}
					}
				}
			}
		}
	}

	public ArrayList<ArrayList<Aggregate>> aggregate(int aggNum) {
		if (toBuy.size() != 0){
			sort(toBuy);
		}
		if (toSell.size() != 0){
			sort(toSell);
		}

		aggregatedBuyList = new ArrayList<>();
		int lastPrice = -1;
		for (Order value : toBuy) {
			int price = (int) (Math.round((double)value.getPrice() / aggNum) * aggNum);
			if (lastPrice != price) {
				aggregatedBuyList.add(new Aggregate(price, value.getQuantity()));
				lastPrice = price;
			} else {
				aggregatedBuyList.get(aggregatedBuyList.size() - 1).quantity += value.getQuantity();
			}
		}

		aggregatedSellList = new ArrayList<>();
		lastPrice = -1;
		for (Order order : toSell) {
			int price = (int) (Math.round((double)order.getPrice() / aggNum) * aggNum);
			if (lastPrice != price) {
				aggregatedSellList.add(new Aggregate(price, order.getQuantity()));
				lastPrice = price;
			} else {
				aggregatedSellList.get(aggregatedSellList.size() - 1).quantity += order.getQuantity();
			}
		}
		ArrayList<ArrayList<Aggregate>> returnList = new ArrayList<ArrayList<Aggregate>>();
		returnList.add(aggregatedBuyList);
		returnList.add(aggregatedSellList);
		return returnList;
	}

	public void privateOrderBook(String accountName) {
		oneAccount = new ArrayList<>();
		for (Order order : toBuy) {
			if (order.getAccount().equals(accountName)) {
				oneAccount.add(order);
			}
		}

		for (Order order : toSell) {
			if (order.getAccount().equals(accountName)) {
				oneAccount.add(order);
			}
		}

		for (Trade trade : tradeList) {
			if (trade.getBuyAcc().equals(accountName)) {
				Order pastTrade = new Order(accountName, trade.getPrice(), trade.getQuantity(), Action.BOUGHT, trade.getCounter());
				oneAccount.add(pastTrade);
			} else if (trade.getSellAcc().equals(accountName)) {
				Order pastTrade = new Order(accountName, trade.getPrice(), trade.getQuantity(), Action.SOLD, trade.getCounter());
				oneAccount.add(pastTrade);
			}
		}
	}

	public Trade tradeHistory(String sellAcc, String buyAcc, int quantity, int price, int counter) {
		return new Trade(sellAcc, buyAcc, quantity, price, counter);
	}

}

