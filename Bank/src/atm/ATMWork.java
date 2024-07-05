package atm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ATMWork extends Authorization {

	List<User> base = new ArrayList<User>();
	User authUser;
	long limit;
	boolean skipWork;

	public void Work() throws Exception {
		Authorization check = new Authorization();
		check.Auth();
		skipWork = check.skipWork;
		limit = check.limit;
		base = check.base;
		authUser = check.user;
		if (!skipWork) {
			Scanner read = new Scanner(System.in);
			boolean process = true;
			System.out.println("Доступные операции: ");
			System.out.println("1 - Проверка баланса счета.");
			System.out.println("2 - Снять средства со счета.");
			System.out.println("3 - Положить средства на счет.");
			System.out.println("4 - Закончить работу.");
			while (process) {
				System.out.print("Введите число(1-4): ");
				int number = 9;
				try {
					number = Integer.parseInt(read.nextLine());
				} catch (Exception error) {
					number = 5;
					System.out.println("Введено не число.");
				}
				switch (number) {
				case 1:
					authUser.CheckBalance();
					break;
				case 2:
					limit = authUser.WithdrawMoney(limit);
					break;
				case 3:
					limit = authUser.DepositMoney(limit);
					break;
				case 4:
					process = false;
					break;
				case 5:
					break;
				default:
					System.out.println("Введено некорректное число. Попробуйте ещё раз.");
					break;
				}
			}
		}
	}
}