package money;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
 
import javax.swing.table.DefaultTableModel;

 
//DB 처리
public class BankDAO {
	ArrayList<String> a = new ArrayList<String>();
	
 
    private static final String DRIVER
        = "com.mysql.jdbc.Driver";
    private static final String URL
        = "jdbc:mysql://localhost:3306/bank";
   
    private static final String USER = "root"; //DB ID
    private static final String PASS = "1234"; //DB 패스워드

    public BankDAO() {
   
    }
   
    /**DB연결 메소드*/
    public Connection getConn(){
        Connection con = null;
       
        try {
            Class.forName(DRIVER); //1. 드라이버 로딩
            con = DriverManager.getConnection(URL,USER,PASS); //2. 드라이버 연결
           
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return con;
    }
   
   
    /**한사람의 회원 정보를 얻는 메소드*/
    public BankDTO getMemberDTO(String id){
       
        BankDTO dto = new BankDTO();
       
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
       
        try {
           
            con = getConn();
            String sql = "select * from member where id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
           
            rs = ps.executeQuery();
           
            if(rs.next()){
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setAge(rs.getString("age"));
                dto.setTel(rs.getString("tel"));
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
       
        return dto;    
    }
   
 
    /**회원 등록*/
    public boolean insertMember(BankDTO dto){
       
        boolean ok = false;
       
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
       
        try{
           
            con = getConn();
            String sql = "insert into member values(?,?,?,?)";
           
            ps = con.prepareStatement(sql);
            ps.setString(1, dto.getId());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getAge());
            ps.setString(4, dto.getTel());       
            int r = ps.executeUpdate(); //실행 -> 저장
           
           
            if(r>0){
                System.out.println("가입 성공");   
                ok=true;
            }else{
                System.out.println("가입 실패");
            }
           
               
           
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return ok;
    }//insertMmeber
   
   
    /**회원정보 수정*/
    public boolean updateMember(String id, String tel){
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        try{
           
            con = getConn();           
            String sql =  "update member set tel = ? where id = ?";
           
            ps = con.prepareStatement(sql);
            ps.setString(1, tel);
            ps.setString(2, id);
           
            int r = ps.executeUpdate(); //실행 -> 수정
            // 1~n: 성공 , 0 : 실패
           
            if(r>0) ok = true; //수정이 성공되면 ok값을 true로 변경
           
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return ok;
    }
   
    /**회원정보 삭제 :
     *tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.*/
    public boolean deleteMember(String id){
       
        boolean ok =false ;
        Connection con =null;
        PreparedStatement ps =null;
       
        try {
            con = getConn();
            String sql = "delete from member where id=?";
           
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            int r = ps.executeUpdate(); // 실행 -> 삭제
           
            if (r>0) ok=true; //삭제됨;
           
        } catch (Exception e) {
            System.out.println(e + "-> 오류발생");
        }      
        return ok;
    }
   
   
    /**DB데이터 다시 불러오기*/   
	public ArrayList selectall() {

		BankDTO dto = null;
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
		ArrayList list = new ArrayList();

		try {
			con = getConn();
	        String sql = "select * from member";
	        ps = con.prepareStatement(sql);
	        rs = ps.executeQuery();
	       
			while (rs.next()) {
				dto = new BankDTO();
				String id = rs.getString(1);
				String name = rs.getString(2);
				String age = rs.getString(3);
				String tel = rs.getString(4);
				dto.setId(id);
				dto.setName(name);
				dto.setAge(age);
				dto.setTel(tel);
				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println("에러발생!!");
			System.out.println(e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
