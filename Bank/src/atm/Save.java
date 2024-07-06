package atm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Save extends ATMWork {

	public static void main(String[] args) throws Exception {
		ATMWork atm = new ATMWork();
		atm.Work();
		if ((!atm.skipWork) || (!atm.base.isEmpty())) {
			EndWork(atm.authUser, atm.base, atm.limit);
		}
	}

	public static void EndWork(User a, List<User> b, long c) throws IOException {
		File open = new File("Save.txt");
		if (open.exists() & open.canWrite()) {
			System.out.println("Сохранение данных.");
			FileWriter write = new FileWriter("Save.txt");
			for (User elem : b) {
				if (elem.cardId != a.cardId) {
					write.write(elem.cardId + " " + elem.pinCode + " " + elem.balance + " " + elem.block + " "
							+ elem.blockTime + " ");
				} else {
					write.write(a.cardId + " " + a.pinCode + " " + a.balance + " " + a.block + " " + a.blockTime + " ");
				}
			}
			write.write(String.valueOf(c));
			write.close();
			System.out.println("Данные сохранены.");
		} else {
			try {
				System.out.println("Файл сохранения создается");
				open.createNewFile();
				System.out.println("Файл успешно создан");
			} catch (IOException e) {
				System.out.println("Файл не создан " + e);
			}
		}
	}
}