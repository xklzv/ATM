package atm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Authorization extends Upload {

	User user;
	long limit;
	List<User> base = new ArrayList<User>();
	boolean skipWork;

	public void Auth() throws Exception {
		Upload load = new Upload();
		Scanner read = new Scanner(System.in);
		base = load.Start();
		limit = load.limit;
		skipWork = load.skipWork;
		if (!skipWork) {
			boolean access = true;
			while (access) {
				System.out.print("Введите номер вашей карты: ");
				String usercardId = read.nextLine();
				for (User element : base) {
					if ((element.cardId.equals(usercardId)) && !element.block) {
						System.out.print("Введите PIN-код: ");
						for (int i = 0; i < 3; i++) {
							int pin = 0000;
							try {
								pin = Integer.parseInt(read.nextLine());
								if ((pin < 1000) || (pin > 9999)) {
									throw new Exception();
								}
							} catch (Exception error) {
								System.out.println("PIN-code состоит только из 4 чисел, где первое число не 0.");
							}
							if (element.pinCode == pin) {
								user = element;
								access = false;
								System.out.println("Вы успешно зашли в аккаунт.");
								break;
							} else {
								System.out.print("Неверный PIN-code(" + (2 - i) + "): ");
								if (2 - i == 0) {
									access = false;
									skipWork = true;
									element.block = true;
									element.blockTime = (new Date()).getTime();
									user = element;
									System.out.println("Ваша карта заблокирована на 24 часа. ");
								}
							}
						}
					} else {
						if ((element.cardId.equals(usercardId)) && element.block) {
							if ((element.block == true) && (new Date().getTime() - element.blockTime >= 86400000)) {
								element.block = false;
								element.blockTime = -1;
								System.out.println("Ваша карта разблокирована.");
							} else {
								System.out.println("Увы, Ваша карта заблокирована.");
								skipWork = true;
								access = false;
							}

						}
					}
				}
			}
		} else {
			if (!base.isEmpty()) {
				user = base.getFirst();
			}
		}
	}
}
