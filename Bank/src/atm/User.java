package atm;

import java.util.Scanner;
import java.util.Random;

class User {

	String cardId;
	int pinCode;
	long balance;
	boolean block;
	long blockTime;

	User() {
		Random rand = new Random();
		cardId = String.valueOf(rand.nextInt(8999) + 1000) + "-" + String.valueOf(rand.nextInt(8999) + 1000) + "-"
				+ String.valueOf(rand.nextInt(8999) + 1000) + "-" + String.valueOf(rand.nextInt(8999) + 1000);
		pinCode = rand.nextInt(8999) + 1000;
		balance = rand.nextInt(8999) + 1000;
		block = false;
		blockTime = -1;
	}

	User(String a, int b, long c, boolean d, long e) {
		cardId = a;
		pinCode = b;
		balance = c;
		block = d;
		blockTime = e;
	}

	public void CheckBalance() {
		System.out.println("Баланс вашей карты составляет: " + balance);
	}

	public long WithdrawMoney(long limit) {
		Scanner read = new Scanner(System.in);
		System.out.print("Введите сумму денег, которую хотите снять: ");
		int money = 0;
		try {
			money = Integer.parseInt(read.nextLine());
			if (money <= 0)
				throw new Exception();
			if ((balance - money >= 0) && (limit - money >= 0)) {
				balance -= money;
				System.out.println("Деньги успешно сняты.");
				read.close();
				return limit - (long) money;
			} else {
				if (balance - money >= 0) {
					System.out.println("В банкомате недостаточно денег.");
				} else {
					System.out.println("На счёте недостаточно денег.");
				}
			}
		} catch (Exception error) {
			System.out.println("Сумма денег должна быть положительным целым числом.");
		}
		return limit;
	}

	public long DepositMoney(long limit) {
		Scanner read = new Scanner(System.in);
		System.out.print("Введите сумму денег, которую вы бы хотели внести на счёт: ");
		int money = 0;
		try {
			money = Integer.parseInt(read.nextLine());
			if (money <= 0)
				throw new Exception();
			if (money <= 1000000) {
				balance += money;
				System.out.println("Деньги успешно поступили на ваш счет.");
				read.close();
				return limit + (long) money;
			} else {
				System.out.println("Вы можете положить сумму не более 1.000.000.");
				read.close();
				return limit;
			}
		} catch (Exception error) {
			System.out.println("Сумма денег должна быть положительным числом.");
			return limit;
		}

	}

}