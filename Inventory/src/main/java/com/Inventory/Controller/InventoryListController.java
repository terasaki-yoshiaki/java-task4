package com.Inventory.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class InventoryListController {

	InventoryDAO dao = new InventoryDAO();

	@PostMapping("")
	//DTOからDAOへ
	public String index(@ModelAttribute InventoryDTO dto, Model model) {
		if (dto != null) {
			InventoryDAO InventoryDAO = new InventoryDAO();
			InventoryDAO.insert(dto);
			return "redirect:InventoryList.html";
		} else {
			return "redirect:InventoryList.html";
		}
	}

	@PostMapping("/IC")
	public String index() {
		return "form.html";
	}

	@PostMapping("/iup")
	//DTOからDAOへ
	public String IU(@ModelAttribute InventoryDTO dto, Model model) {
		//DAOを呼び出して処理
		InventoryDAO InventoryDAO = new InventoryDAO();
		InventoryDAO.update(dto);
		List<InventoryDTO> list = dao.select2(dto);
		model.addAttribute("Inventory", list);
		return "InventoryList.html";
	}

	@PostMapping("/ide")
	//DTOからDAOへ
	public String IDe(@ModelAttribute InventoryDTO dto, Model model) {
		//DAOを呼び出して処理
		InventoryDAO InventoryDAO = new InventoryDAO();
		InventoryDAO.delete1(dto);
		List<InventoryDTO> list = dao.select2(dto);
		model.addAttribute("Inventory", list);
		return "InventoryList.html";
	}

	@RequestMapping("/csvdownload")
	public String InventoryList(@ModelAttribute InventoryDTO dto, Model model, HttpServletResponse response) {
		//DAOを介してDBからデータを取得しlistに格納（日付別に登録されている商品データ）
		List<InventoryDTO> list = dao.select2(dto);
		model.addAttribute("Inventory", list);
		// 現在の日時を取得(ファイル名に使うため)
		LocalDateTime nowDate = LocalDateTime.now();

		// yyyyMMddHHmm形式に変換
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatNowDate = dtf.format(nowDate);

		// パスの文字列を生成する 現在の日時が2024年6月8日16:59:59の場合、C:\\command-batch-practice\\20240608165959.txtとなる
		String createPath = "C:\\command-batch-practice\\" + formatNowDate + ".txt";
		Path p = Paths.get(createPath);

		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"test.csv\"");
		// ファイル出力処理
		try (PrintWriter pw = response.getWriter()) {
			// ファイルを作成
			Files.createFile(p);

			// ファイルを開く
			File file = new File(createPath);
			FileWriter filewriter = new FileWriter(file);
			//listに格納された配列データから項目ごとに１行ずつ取り出す：変数janの中にlistから取り出した1行目のJanCodeは〇〇
			//2行目のJanCodeは××、の順でlistに格納された行数分だけ代入される
			for (int i = 0; i < list.size(); i++) {
				String pn = list.get(i).getProductName();
				String jan = list.get(i).getJanCode();
				int pri = list.get(i).getPrice();
				int ib = list.get(i).getInventoryBuy();
				int is = list.get(i).getInventorySell();
				int pro = list.get(i).getProfit();

				
				
				//CSVファイル内部に記載する形式で文字列を設定
				String outputString = pn + "," + jan + "," + pri + "," + ib + "," + is + "," + pro
						+ "\r\n";
				// 開いたファイルに書き込む
				filewriter.write(outputString);
				//CSVファイルに書き込む
				 pw.print(outputString);

				// ファイルを閉じる
			}
			filewriter.close();
		} catch (IOException e) {
			System.out.println(e);
		}

		return "InventoryList.html";
	}
}
