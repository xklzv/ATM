package atm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Upload extends User {

	boolean skipWork = false;
	List<User> data = new ArrayList<User>();
	long limit;

	public List<User> Start() {
		Scanner read = new Scanner(System.in);
		List<User> tempData = new ArrayList<User>();
		System.out.println("Поиск файла сохранения.");
		File open = new File("Save.txt");
		if (open.exists() & open.canRead()) {
			try {
				System.out.println("Файл сохранения найден.");
				Scanner file = new Scanner(open);
				String[] content = file.nextLine().split(" ");
				for (int i = 0; i < content.length - 1; i += 5) {
					User temp;
					String cardId = content[i];
					String[] partcardId = cardId.split("-");
					if (partcardId.length == 4) {
						for (int j = 0; j < 4; j++) {
							Integer.parseInt(partcardId[j]);
							if (partcardId[j].length() != 4)
								throw new Exception();
						}
					} else {
						throw new Exception();
					}
					int pinCode = Integer.parseInt(content[i + 1]);
					if ((pinCode < 1000) || (pinCode > 9999)) {
						throw new Exception();						
					}
					long balance = Long.parseLong(content[i + 2]);
					if (balance < 0)
						throw new Exception();
					boolean block = Boolean.parseBoolean(content[i + 3]);
					long blockTime = Long.parseLong(content[i + 4]);
					if ((block == false) && (blockTime != -1))
						blockTime = -1;
					temp = new User(cardId, pinCode, balance, block, blockTime);
					tempData.add(temp);
				}

				limit = Long.parseLong(content[content.length - 1]);
				data = tempData;
				System.out.println("Файл сохранения успешно загружен.");
			} catch (Exception error) {
				System.out.println("Файл сохранения записан некорректно.");
				System.out.print("Желаете сгенерировать новый файл сохранения?(YES/NO): ");
				if (read.nextLine().equals("YES")) {
					for (int i = 0; i < 3; i++) {
						data.add(new User());
					}
					Random rand = new Random();
					limit = rand.nextInt(99999) + 1000000;
					skipWork = true;
				} else {
					System.out.println("Закрытие программы.");
					skipWork = true;
				}
			}
		} else {
			System.out.println("Файл сохранения не найден.");
			try {
				System.out.print("Желаете сгенерировать новый файл сохранения?(YES/NO): ");
				if (read.nextLine().equals("YES")) {
					System.out.println("Файл создается.");
					open.createNewFile();
					System.out.println("Файл успешно создан.");
					for (int i = 0; i < 3; i++) {
						data.add(new User());
					}
					Random rand = new Random();
					limit = rand.nextInt(99999) + 1000000;
					System.out.println(limit);
					skipWork = true;
					System.out.println("Файл сохранения записан успешно.");
				} else {
					System.out.println("Закрытие программы.");
					skipWork = true;
				}
			} catch (IOException e) {
				System.out.println("Файл не создан.");
				System.out.println("Закрытие программы.");
				skipWork = true;
			}
		}

		return data;
	}
}