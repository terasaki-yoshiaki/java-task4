package com.Inventory.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			 ArrayList<InventoryDTO> list = new ArrayList<>();
			// ステートメントはSQLを実行するオブジェクト
//			Statement stmt = null;
//			// リザルトセットは結果を格納するオブジェクト
//			ResultSet rs = null;
//			String un = dto.getUserName();
//			String pw = dto.getPassword();
			//String sql = "SELECT * FROM m_user WHERE user_name = '"+un+"'AND password = '"+pw+"'";
			 String sql = "SELECT * FROM m_user WHERE user_name = ? AND password = ?";

		        try {
		            connect();
		            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		                pstmt.setString(1, dto.getUserName());
		                pstmt.setString(2, dto.getPassword());

		                try (ResultSet rs = pstmt.executeQuery()) {
		                    while (rs.next()) {
		                        InventoryDTO a1 = new InventoryDTO();
		                        a1.setUserName(rs.getString("user_name"));
		                        a1.setPassword(rs.getString("password"));
		                        a1.setAuthority(rs.getInt("authority"));
		                        list.add(a1);
		                    }
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            closeConnection();
		        }
		        return list;
		    }
		
		 /**
	     * データベース接続を閉じるメソッド
	     */
	    private void closeConnection() {
	        if (con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
		
		//index.html:管理者としてログイン
				public ArrayList<InventoryDTO> select4(InventoryDTO dto) {
					// ステートメントはSQLを実行するオブジェクト
					Statement stmt = null;
					// リザルトセットは結果を格納するオブジェクト
					ResultSet rs = null;
					String un = dto.getUserName();
					String pw = dto.getPassword();
					String sql = "SELECT * FROM m_user WHERE user_name = '"+un+"'AND password = '"+pw+"'";
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
							a4.setUserName(rs.getString("user_name"));
							a4.setPassword(rs.getString("password"));
							a4.setAuthority(rs.getInt("authority"));
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
		
		//ListSearch.html:日付を選択して一覧に表示する
		public ArrayList<InventoryDTO> select1(InventoryDTO dto) {
			ArrayList<InventoryDTO> list = new ArrayList<>();
			// ステートメントはSQLを実行するオブジェクト
//			Statement stmt = null;
//			// リザルトセットは結果を格納するオブジェクト
//			ResultSet rs = null;
			
			Date td = dto.getTradingDate();
			String sql = "SELECT * FROM product_detail WHERE trading_date = ?";
			//ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();

			 try {
		            connect();
		            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		                pstmt.setDate(1, new java.sql.Date(dto.getTradingDate().getTime()));

		                try (ResultSet rs = pstmt.executeQuery()) {
		                    while (rs.next()) {
		                        InventoryDTO a2 = new InventoryDTO();
		                        a2.setTradingDate(rs.getDate("trading_date"));
		                        list.add(a2);
		                    }
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            closeConnection();
		        }
		        return list;
		    }

		
		//ListSearch.html:一覧表示されたデータを選択してリストを表示させる
		public ArrayList<InventoryDTO> select2(InventoryDTO dto) {
//			// ステートメントはSQLを実行するオブジェクト
//			Statement stmt = null;
//			// リザルトセットは結果を格納するオブジェクト
//			ResultSet rs = null;
//			
//			Date td = dto.getTradingDate();
//			String sql = "SELECT\n"
//					+ "    A.JAN_CODE\n"
//					+ "    , A.PRODUCT_NAME\n"
//					+ "    , B.ID\n"
//					+ "    , B.PRICE\n"
//					+ "    , B.INVENTORY_BUY\n"
//					+ "    , B.INVENTORY_SELL \n"
//					+ "	   , B.PROFIT\n"
//					+ "	   , B.TRADING_DATE\n"
//					+ "FROM\n"
//					+ "    M_PRODUCT as A\n"
//					+ "    LEFT OUTER JOIN PRODUCT_DETAIL AS B\n"
//					+ "        ON A.JAN_CODE = B.JAN_CODE\n"
//					+ "			WHERE TRADING_DATE = '"+td+"'";
//			ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();
			
			 ArrayList<InventoryDTO> list = new ArrayList<>();
			    String sql = "SELECT\n"
			            + "    A.JAN_CODE,\n"
			            + "    A.PRODUCT_NAME,\n"
			            + "    B.ID,\n"
			            + "    B.PRICE,\n"
			            + "    B.INVENTORY_BUY,\n"
			            + "    B.INVENTORY_SELL,\n"
			            + "    B.PROFIT,\n"
			            + "    B.TRADING_DATE\n"
			            + "FROM\n"
			            + "    M_PRODUCT AS A\n"
			            + "    LEFT OUTER JOIN PRODUCT_DETAIL AS B\n"
			            + "        ON A.JAN_CODE = B.JAN_CODE\n"
			            + "WHERE B.TRADING_DATE = ?";


			try {
				// DB接続のメソッドを呼び出す
				connect();

//				// ステートメントを作成
//				stmt = con.createStatement();
//
//				// SQLを実行し結果をリザルトセットへ格納
//				rs = stmt.executeQuery(sql);
//					
//				while (rs.next()) {
//					InventoryDTO a3 = new InventoryDTO();
//					a3.setJanCode(rs.getString("jan_code"));
//					a3.setProductName(rs.getString("product_name"));
//					a3.setPrice(rs.getInt("Price"));
//					a3.setInventoryBuy(rs.getInt("inventory_buy"));
//					a3.setInventorySell(rs.getInt("inventory_sell"));
//					a3.setProfit(rs.getInt("profit"));
//					a3.setID(rs.getInt("ID"));
//					a3.setTradingDate(rs.getDate("trading_date"));
//					list.add(a3);
//				}
		        // プリペアドステートメントを作成
		        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		            pstmt.setDate(1, new java.sql.Date(dto.getTradingDate().getTime()));

		            // SQLを実行し結果をリザルトセットへ格納
		            try (ResultSet rs = pstmt.executeQuery()) {
		                while (rs.next()) {
		                    InventoryDTO a3 = new InventoryDTO();
		                    a3.setJanCode(rs.getString("jan_code"));
		                    a3.setProductName(rs.getString("product_name"));
		                    a3.setPrice(rs.getInt("price"));
		                    a3.setInventoryBuy(rs.getInt("inventory_buy"));
		                    a3.setInventorySell(rs.getInt("inventory_sell"));
		                    a3.setProfit(rs.getInt("profit"));
		                    a3.setID(rs.getInt("ID"));
		                    a3.setTradingDate(rs.getDate("trading_date"));
		                    list.add(a3);
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        closeConnection();
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
					int au = dto.getAuthority();
					int au1;
					if(au == 1) {
						au1 = 1;
					} else {
						au1 = 0;
					}
					 LocalDateTime nd = LocalDateTime.now();
					 
					 String sql = "INSERT INTO M_USER (USER_NAME, PASSWORD, AUTHORITY, DELETE_FRAG, CREATED_DATE, CREATED_NAME, UPDATED_DATE, UPDATED_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//					String sql = String.valueOf("INSERT\n"
//							+ "INTO M_USER(\n"
//							+ "USER_NAME\n"
//							+ ",PASSWORD\n"
//							+ ",AUTHORITY\n"
//							+ ",DELETE_FRAG\n"
//							+ ",CREATED_DATE\n"
//							+ ",CREATED_NAME\n"
//							+ ",UPDATED_DATE\n"
//							+ ",UPDATED_NAME\n"
//							+ ")\n"
//							+ "VALUES (\n"
//							+ "'"+un+"'\n"
//							+ ",'"+pw+"'\n"
//							+ ",'"+au1+"'\n"
//							+ ",'1'\n"
//							+ ",'"+nd+"'\n"
//							+ ",'terasaki'\n"
//							+ ",'"+nd+"'\n"
//							+ ",'terasaki'\n"
//							+ ")");
								   
					try {
						// DB接続のメソッドを呼び出す
						connect();

						 try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				                pstmt.setString(1, dto.getUserName());
				                pstmt.setString(2, dto.getPassword());
				                pstmt.setInt(3, dto.getAuthority() == 1 ? 1 : 0);
				                pstmt.setInt(4, 1);
				                pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
				                pstmt.setString(6, "terasaki");
				                pstmt.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
				                pstmt.setString(8, "terasaki");

				                pstmt.executeUpdate();
				            }
				        } catch (SQLException e) {
				            e.printStackTrace();
				        } finally {
				            closeConnection();
				        }
				    }

		
		//accountlist.html:ユーザー名とパスワードと権限を全取得して一覧表示
				public ArrayList<InventoryDTO> select3() {
					// ステートメントはSQLを実行するオブジェクト
					Statement stmt = null;
					// リザルトセットは結果を格納するオブジェクト
					ResultSet rs = null;
					
					String sql = "SELECT * FROM M_USER";
					
					ArrayList<InventoryDTO> list1 = new ArrayList<InventoryDTO>();

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
							a4.setAuthority(rs.getInt("authority"));
							list1.add(a4);
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
					return list1;
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
			int a = dto.getAuthority();
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
					String sql = "UPDATE PRODUCT_DETAIL SET price = ?, inventory_buy = ?, inventory_sell = ?, profit = ? WHERE id = ?";

//					// TODO 自動生成されたメソッド・スタブ
//					Statement stmt = null;
//
//					//dtoから各項目の値を取り出す get
//					int ID = dto.getID();
//					int p = dto.getPrice();
//					int ib = dto.getInventoryBuy();
//					int is = dto.getInventorySell();
//					int pf = dto.getProfit();
//					
//					String sql = "UPDATE PRODUCT_DETAIL\n"
//							+ "SET price='"+p+"',inventory_buy='"+ib+"',inventory_sell='"+is+"',profit='"+pf+"'\n"
//							+ "WHERE id='"+ID+"'";
//					
					try {
						// DB接続のメソッドを呼び出す
						connect();

//						// ステートメントを作成
//						stmt = con.createStatement();
//
//						// SQLを実行し結果をリザルトセットへ格納
//						stmt.executeUpdate(sql);
          			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		                pstmt.setInt(1, dto.getPrice());
		                pstmt.setInt(2, dto.getInventoryBuy());
		                pstmt.setInt(3, dto.getInventorySell());
		                pstmt.setInt(4, dto.getProfit());
		                pstmt.setInt(5, dto.getID());

		                pstmt.executeUpdate();
		            }
					 } catch (SQLException e) {
				            e.printStackTrace();
				        } finally {
				            closeConnection();
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