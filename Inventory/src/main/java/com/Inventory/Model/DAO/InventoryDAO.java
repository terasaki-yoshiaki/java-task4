package com.Inventory.Model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import com.Inventory.Model.DTO.InventoryDTO;
import com.Inventory.Model.DataBase.DataBase;

public class InventoryDAO {

		private Connection con = null;
		/**
		 * DBに接続する処理	
		 */
		public void connect() {
			try {
				// DBに接続
				con = DataBase.getConnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * テーブルからデータを取得する処理
		 * @param dto 
		 * @return 
		 */
		//index.html:ログイン処理
		public ArrayList<InventoryDTO> select(InventoryDTO dto) {
			// ステートメントはSQLを実行するオブジェクト
			Statement stmt = null;
			// リザルトセットは結果を格納するオブジェクト
			ResultSet rs = null;
			String un = dto.getUserName();
			String pw = dto.getPassword();
			String sql = "SELECT * FROM M_USER WHERE USER_NAME = '"+un+"'AND PASSWORD = '"+pw+"'";
			ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();

			try {
				// DB接続のメソッドを呼び出す
				connect();
				// ステートメントを作成
				stmt = con.createStatement();
				// SQLを実行し結果をリザルトセットへ格納
				rs = stmt.executeQuery(sql);
					
				while (rs.next()) {
					InventoryDTO a1 = new InventoryDTO();
					a1.setUserName(rs.getString("USER_NAME"));
					a1.setPassword(rs.getString("PASSWORD"));
					list.add(a1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		
		
		//ListSearch.html:日付を選択して一覧に表示する
		public ArrayList<InventoryDTO> select1(InventoryDTO dto) {
			// ステートメントはSQLを実行するオブジェクト
			Statement stmt = null;
			// リザルトセットは結果を格納するオブジェクト
			ResultSet rs = null;
			
			Date td = dto.getTradingDate();
			String sql = "SELECT * FROM PRODUCT_DETAIL WHERE TRADING_DATE = '"+td+"'";
			ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();

			try {
				// DB接続のメソッドを呼び出す
				connect();
				// ステートメントを作成
				stmt = con.createStatement();
				// SQLを実行し結果をリザルトセットへ格納
				rs = stmt.executeQuery(sql);
					
				while (rs.next()) {
					InventoryDTO a2 = new InventoryDTO();
					a2.setTradingDate(rs.getDate("trading_date"));
					list.add(a2);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		//ListSearch.html:一覧表示されたデータを選択してリストを表示させる
		public ArrayList<InventoryDTO> select2() {
			// ステートメントはSQLを実行するオブジェクト
			Statement stmt = null;
			// リザルトセットは結果を格納するオブジェクト
			ResultSet rs = null;
			
			String sql = "SELECT\n"
					+ "    A.JAN_CODE\n"
					+ "    , A.PRODUCT_NAME\n"
					+ "    , B.ID\n"
					+ "    , B.PRICE\n"
					+ "    , B.INVENTORY_BUY\n"
					+ "    , B.INVENTORY_SELL \n"
					+"	   , B.PROFIT\n"
					+ "FROM\n"
					+ "    M_PRODUCT as A\n"
					+ "    LEFT OUTER JOIN PRODUCT_DETAIL AS B\n"
					+ "        ON A.JAN_CODE = B.JAN_CODE";
			ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();

			try {
				// DB接続のメソッドを呼び出す
				connect();

				// ステートメントを作成
				stmt = con.createStatement();

				// SQLを実行し結果をリザルトセットへ格納
				rs = stmt.executeQuery(sql);
					
				while (rs.next()) {
					InventoryDTO a3 = new InventoryDTO();
					a3.setJanCode(rs.getString("jan_code"));
					a3.setProductName(rs.getString("product_name"));
					a3.setPrice(rs.getInt("Price"));
					a3.setInventoryBuy(rs.getInt("inventory_buy"));
					a3.setInventorySell(rs.getInt("inventory_sell"));
					a3.setProfit(rs.getInt("profit"));
					a3.setID(rs.getInt("ID"));
					list.add(a3);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		
		//ユーザー登録
		public void insert(InventoryDTO dto) {
			// ステートメントはSQLを実行するオブジェクト
					Statement stmt = null;

					//dtoから各項目の値を取り出す get
					String un = dto.getUserName();
					String pw = dto.getPassword();
					String au = dto.getAuthority();
					int au1;
					if(au == "yes") {
						au1 = 1;
					} else {
						au1 = 0;
					}
					 LocalDateTime nd = LocalDateTime.now();
					 
					
					String sql = String.valueOf("INSERT\n"
							+ "INTO M_USER(\n"
							+ "USER_NAME\n"
							+ ",PASSWORD\n"
							+ ",AUTHORITY\n"
							+ ",DELETE_FRAG\n"
							+ ",CREATED_DATE\n"
							+ ",CREATED_NAME\n"
							+ ",UPDATED_DATE\n"
							+ ",UPDATED_NAME\n"
							+ ")\n"
							+ "VALUES (\n"
							+ "'"+un+"'\n"
							+ ",'"+pw+"'\n"
							+ ",'"+au1+"'\n"
							+ ",'1'\n"
							+ ",'"+nd+"'\n"
							+ ",'terasaki'\n"
							+ ",'"+nd+"'\n"
							+ ",'terasaki'\n"
							+ ")");
								   
					try {
						// DB接続のメソッドを呼び出す
						connect();

						// ステートメントを作成
						stmt = con.createStatement();

						// SQLを実行し結果をリザルトセットへ格納
						stmt.executeUpdate(sql);
					
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							stmt.close();
							con.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					//voidの場合はreturnは無くてもいい

			return;
		}
		
		//accountlist.html:ユーザー名とパスワードと権限を全取得して一覧表示
				public ArrayList<InventoryDTO> select3() {
					// ステートメントはSQLを実行するオブジェクト
					Statement stmt = null;
					// リザルトセットは結果を格納するオブジェクト
					ResultSet rs = null;
					
					String sql = "SELECT * FROM M_USER";
					
					ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();

					try {
						// DB接続のメソッドを呼び出す
						connect();

						// ステートメントを作成
						stmt = con.createStatement();

						// SQLを実行し結果をリザルトセットへ格納
						rs = stmt.executeQuery(sql);
							
						while (rs.next()) {
							InventoryDTO a4 = new InventoryDTO();
							a4.setID(rs.getInt("ID"));
							a4.setUserName(rs.getString("user_name"));
							a4.setPassword(rs.getString("password"));
							a4.setAuthority(rs.getString("authority"));
							list.add(a4);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							rs.close();
							stmt.close();
							con.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return list;
				}
		
		//void=戻り値無し
		//InventoryList.html→form.htmlへ遷移して商品登録
		public void insert1(InventoryDTO dto) {
			// ステートメントはSQLを実行するオブジェクト
					Statement stmt = null;

					//dtoから各項目の値を取り出す get
					Date td = dto.getTradingDate();
					//String pn = dto.getProductName();
					String jan = dto.getJanCode();
					int pr = dto.getPrice();
					int ib = dto.getInventoryBuy();
					int is = dto.getInventorySell();
					int pf = dto.getProfit();
					 LocalDateTime nd = LocalDateTime.now();
					 
					
					String sql = String.valueOf("INSERT \n"
							+"INTO PRODUCT_DETAIL( \n"
								    
								+"    TRADING_DATE\n"
								+"    , JAN_CODE\n"
								+"    , PRICE\n"
								+"    , iNVENTORY_BUY\n"
								+"    , INVENTORY_SELL\n"
								+"    , AUTHORITY\n"
								+"    , DELETE_FRAG\n"
								+"    , CREATED_DATE\n"
								+"    , CREATED_NAME\n"
								+"    , UPDATED_DATE\n"
								+"    , UPDATED_NAME\n"
								+"    , PROFIT\n"
								+") \n"
								+"VALUES ( \n"
								   +" '"+td+"'\n"
								   +" , '"+jan+"'\n"
								   +" , '"+pr+"'\n"
								   +" , '"+ib+"'\n"
								   +" , '"+is+"'\n"
								   +" , '0'\n"
								   +" , '0'\n"
								   +" , '"+nd+"'\n"
								   +" , 'terasaki'\n"
								   +" , '"+nd+"'\n"
								   +" , 'terasaki'\n"
								   +" , '"+pf+"'\n"
								+"); ");
								   
					try {
						// DB接続のメソッドを呼び出す
						connect();

						// ステートメントを作成
						stmt = con.createStatement();

						// SQLを実行し結果をリザルトセットへ格納
						stmt.executeUpdate(sql);
					
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							stmt.close();
							con.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					//voidの場合はreturnは無くてもいい

			return;
		}

		public void update2(InventoryDTO dto) {
			
			Statement stmt = null;
			//dtoから各項目の値を取り出す get
			int ID = dto.getID();
			String un = dto.getUserName();
			String pw = dto.getPassword();
			String a = dto.getAuthority();
			LocalDateTime nd = LocalDateTime.now();
			String sql = "UPDATE M_USER\n"
					+"SET USER_NAME='"+un+"'\n"
					+", PASSWORD='"+pw+"'\n"
					+", AUTHORITY='"+a+"'\n"
					+", DELETE_FRAG='1'\n"
					+", CREATED_NAME='terasaki'\n"
					+", UPDATED_DATE='"+nd+"'\n"
					+", UPDATED_NAME='saki'\n"
					+"WHERE ID='"+ID+"'";
			
			
			try {
				// DB接続のメソッドを呼び出す
				connect();

				// ステートメントを作成
				stmt = con.createStatement();

				// SQLを実行し結果をリザルトセットへ格納
				stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/*//InventoryList.html→商品一覧から編集
		public void update1(InventoryDTO dto) {
			// TODO 自動生成されたメソッド・スタブ
			Statement stmt = null;

			//dtoから各項目の値を取り出す get
			int ID = dto.getID();
			String jan = dto.getJanCode();
			int pr = dto.getPrice();
			int ib = dto.getInventoryBuy();
			int is = dto.getInventorySell();
			int pf = dto.getProfit();
			String sql = "UPDATE PRODUCT_DETAIL\n"
					+ "SET user_name='"+un+"',password='"+ps+"',authority='"+a+"'\n"
					+ "WHERE id='"+ID+"'";
			
			try {
				// DB接続のメソッドを呼び出す
				connect();

				// ステートメントを作成
				stmt = con.createStatement();

				// SQLを実行し結果をリザルトセットへ格納
				stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		
		
		//InventoryList.html→商品一覧から編集
				public void update(InventoryDTO dto) {
					// TODO 自動生成されたメソッド・スタブ
					Statement stmt = null;

					//dtoから各項目の値を取り出す get
					int ID = dto.getID();
					int p = dto.getPrice();
					int ib = dto.getInventoryBuy();
					int is = dto.getInventorySell();
					int pf = dto.getProfit();
					
					String sql = "UPDATE M_USER\n"
							+ "SET price='"+p+"',inventory_buy='"+ib+"',inventory_sell='"+is+"',profit='"+pf+"'\n"
							+ "WHERE id='"+ID+"'";
					
					try {
						// DB接続のメソッドを呼び出す
						connect();

						// ステートメントを作成
						stmt = con.createStatement();

						// SQLを実行し結果をリザルトセットへ格納
						stmt.executeUpdate(sql);
					
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							stmt.close();
							con.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		
		//accountlist.html:ユーザー一覧から１列削除
		public void delete(InventoryDTO dto) {
			// TODO 自動生成されたメソッド・スタブ
			Statement stmt = null;

			//dtoから各項目の値を取り出す get
			int ID = dto.getID();
			String sql = "DELETE FROM M_USER\n"
					+ "WHERE id='"+ID+"'";
			
			try {
				// DB接続のメソッドを呼び出す
				connect();

				// ステートメントを作成
				stmt = con.createStatement();

				// SQLを実行し結果をリザルトセットへ格納
				stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
		
		//InventoryList.html:商品一覧から１列削除
	public void delete1(InventoryDTO dto) {
		Statement stmt = null;

		//dtoから各項目の値を取り出す get
		int ID = dto.getID();
		String sql = "DELETE FROM PRODUCT_DETAIL\n"
				+ "WHERE id='"+ID+"'";
		
		try {
			// DB接続のメソッドを呼び出す
			connect();

			// ステートメントを作成
			stmt = con.createStatement();

			// SQLを実行し結果をリザルトセットへ格納
			stmt.executeUpdate(sql);
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
}